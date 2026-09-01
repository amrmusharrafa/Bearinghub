package com.example.data.drawing

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import com.example.model.Bearing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.Locale

object BearingDrawingManager {

    /**
     * Resolves the default bundled drawable resource ID for a bearing.
     * Uses deepgroovebearingdrawing as the default drawing for all Deep Groove Ball Bearings.
     */
    fun getBundledDrawingResId(context: Context, bearing: Bearing): Int? {
        return com.example.R.drawable.deepgroovebearingdrawing
    }

    /**
     * Copies a selected photo Uri into app-internal persistent storage for a specific bearing.
     */
    suspend fun saveUploadedBearingPhoto(
        context: Context,
        bearingNumber: String,
        sourceUri: Uri
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val drawingsDir = File(context.filesDir, "bearing_drawings")
            if (!drawingsDir.exists()) {
                drawingsDir.mkdirs()
            }

            val sanitized = sanitizeDesignation(bearingNumber)
            val targetFile = File(drawingsDir, "photo_${sanitized}_${System.currentTimeMillis()}.png")

            context.contentResolver.openInputStream(sourceUri)?.use { input ->
                FileOutputStream(targetFile).use { output ->
                    input.copyTo(output)
                }
            } ?: return@withContext Result.failure(Exception("Cannot read source image."))

            Result.success(Uri.fromFile(targetFile).toString())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Sanitizes a bearing designation string (e.g. "618/4" -> "618_4", "6205-2RS" -> "6205_2rs")
     * for safe resource naming and filename creation.
     */
    fun sanitizeDesignation(number: String): String {
        return number.trim()
            .lowercase(Locale.ROOT)
            .replace("/", "_")
            .replace("-", "_")
            .replace(" ", "_")
            .replace(".", "_")
    }

    /**
     * Returns true if a drawing is available for this bearing (custom photo or bundled static vector).
     */
    fun hasDrawingAvailable(context: Context, bearing: Bearing): Boolean {
        if (!bearing.customDrawingUri.isNullOrBlank()) return true
        return getBundledDrawingResId(context, bearing) != null
    }

    /**
     * Loads the drawing Bitmap for the given bearing (custom photo or bundled static vector).
     * Returns null if no drawing is available.
     */
    fun getDrawingBitmap(
        context: Context,
        bearing: Bearing,
        widthPx: Int = 1600,
        heightPx: Int = 1200
    ): Bitmap? {
        // 1. Check custom uploaded photo URI
        if (!bearing.customDrawingUri.isNullOrBlank()) {
            try {
                val uri = Uri.parse(bearing.customDrawingUri)
                val inputStream = if (uri.scheme == "file") {
                    File(uri.path ?: "").inputStream()
                } else {
                    context.contentResolver.openInputStream(uri)
                }
                inputStream?.use { stream ->
                    val decoded = android.graphics.BitmapFactory.decodeStream(stream)
                    if (decoded != null) return decoded
                }
            } catch (e: Exception) {
                // Fall back to bundled
            }
        }

        val bundledResId = getBundledDrawingResId(context, bearing)
        if (bundledResId != null) {
            try {
                val drawable = ContextCompat.getDrawable(context, bundledResId)
                if (drawable != null) {
                    val intrinsicWidth = if (drawable.intrinsicWidth > 0) drawable.intrinsicWidth else widthPx
                    val intrinsicHeight = if (drawable.intrinsicHeight > 0) drawable.intrinsicHeight else heightPx
                    val targetW = widthPx.coerceAtLeast(intrinsicWidth)
                    val targetH = heightPx.coerceAtLeast(intrinsicHeight)
                    val bitmap = Bitmap.createBitmap(targetW, targetH, Bitmap.Config.ARGB_8888)
                    val canvas = Canvas(bitmap)
                    canvas.drawColor(android.graphics.Color.WHITE)
                    drawable.setBounds(0, 0, targetW, targetH)
                    drawable.draw(canvas)
                    return bitmap
                }
            } catch (e: Exception) {
                // Fall through to null if drawable conversion fails
            }
        }

        return null
    }

    /**
     * Saves the bearing drawing image locally to the device's Pictures gallery
     * using the modern Android MediaStore API (Scoped Storage compliant).
     */
    suspend fun saveDrawingToDevice(
        context: Context,
        bearing: Bearing
    ): Result<Uri> = withContext(Dispatchers.IO) {
        try {
            val bitmap = getDrawingBitmap(context, bearing, 2048, 1536)
                ?: return@withContext Result.failure(Exception("No drawing available to save."))

            val cleanManufacturer = bearing.manufacturer.ifBlank { "SKF" }
                .replace(Regex("[^a-zA-Z0-9]"), "")
            val cleanNumber = sanitizeDesignation(bearing.number).uppercase(Locale.ROOT)
            val filename = "${cleanManufacturer}_${cleanNumber}_Bearing_Drawing.png"

            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, filename)
                put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(
                        MediaStore.Images.Media.RELATIVE_PATH,
                        Environment.DIRECTORY_PICTURES + File.separator + "BearingHub"
                    )
                    put(MediaStore.Images.Media.IS_PENDING, 1)
                }
            }

            val resolver = context.contentResolver
            val collectionUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }

            val imageUri = resolver.insert(collectionUri, contentValues)
                ?: return@withContext Result.failure(Exception("Could not create MediaStore entry."))

            var outputStream: OutputStream? = null
            try {
                outputStream = resolver.openOutputStream(imageUri)
                if (outputStream != null) {
                    val saved = bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    outputStream.flush()
                    if (!saved) {
                        return@withContext Result.failure(Exception("Failed to compress PNG image stream."))
                    }
                } else {
                    return@withContext Result.failure(Exception("Failed to open MediaStore output stream."))
                }
            } finally {
                outputStream?.close()
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentValues.clear()
                contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                resolver.update(imageUri, contentValues, null, null)
            }

            Result.success(imageUri)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
