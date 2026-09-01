package com.example.ui.components

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Architecture
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.drawing.BearingDrawingManager
import com.example.model.Bearing
import com.example.ui.theme.IndustrialBorderColor
import kotlinx.coroutines.launch

@Composable
fun BearingDrawingSection(
    bearing: Bearing,
    modifier: Modifier = Modifier,
    onPhotoUploaded: ((String?) -> Unit)? = null,
    onShowMessage: ((String) -> Unit)? = null
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var isZoomOpen by remember { mutableStateOf(false) }
    var isDownloading by remember { mutableStateOf(false) }
    var isUploading by remember { mutableStateOf(false) }
    var downloadSuccess by remember { mutableStateOf(false) }

    // Check if custom user uploaded photo exists
    val customPhotoUri = bearing.customDrawingUri

    // Check if drawing is available (custom photo or bundled static drawing)
    val hasDrawing = remember(bearing) {
        BearingDrawingManager.hasDrawingAvailable(context, bearing)
    }

    // Check for bundled drawable resource
    val bundledResId = remember(bearing) {
        BearingDrawingManager.getBundledDrawingResId(context, bearing)
    }

    // Photo picker launcher for uploading custom bearing drawings / photos
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { selectedUri: Uri? ->
        if (selectedUri != null) {
            isUploading = true
            coroutineScope.launch {
                val result = BearingDrawingManager.saveUploadedBearingPhoto(
                    context = context,
                    bearingNumber = bearing.number,
                    sourceUri = selectedUri
                )
                isUploading = false
                result.fold(
                    onSuccess = { savedPath ->
                        onPhotoUploaded?.invoke(savedPath)
                        val msg = "Custom bearing drawing photo uploaded successfully."
                        if (onShowMessage != null) onShowMessage(msg)
                        else Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    },
                    onFailure = {
                        val msg = "Failed to upload drawing photo. Please try again."
                        if (onShowMessage != null) onShowMessage(msg)
                        else Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }

    fun handleDownload() {
        if (isDownloading) return
        isDownloading = true
        downloadSuccess = false

        coroutineScope.launch {
            val result = BearingDrawingManager.saveDrawingToDevice(context, bearing)
            isDownloading = false
            result.fold(
                onSuccess = {
                    downloadSuccess = true
                    val message = "Bearing drawing saved successfully."
                    if (onShowMessage != null) {
                        onShowMessage(message)
                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                },
                onFailure = {
                    val message = "Unable to save bearing drawing. Please try again."
                    if (onShowMessage != null) {
                        onShowMessage(message)
                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, IndustrialBorderColor, RoundedCornerShape(24.dp))
            .testTag("bearing_drawing_section"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            // Header Row: Section Title, Badge & Upload Action
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "TECHNICAL SCHEMATICS & DRAWINGS",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.2.sp
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "Bearing Drawing",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = if (!customPhotoUri.isNullOrBlank()) {
                            "Custom uploaded drawing for ${bearing.manufacturer} ${bearing.number}"
                        } else {
                            "${bearing.manufacturer} ${bearing.number} Technical Drawing"
                        },
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Upload / Change Photo Quick Action Button
                    IconButton(
                        onClick = { photoPickerLauncher.launch("image/*") },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier
                            .size(44.dp)
                            .testTag("upload_bearing_photo_icon_button")
                    ) {
                        if (isUploading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.AddPhotoAlternate,
                                contentDescription = "Upload Drawing Photo",
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Architecture,
                            contentDescription = "Drawing Architecture Icon",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main Drawing Container
            if (!hasDrawing && customPhotoUri.isNullOrBlank()) {
                // Placeholder State: "No drawing available" + Upload button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 10f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                        .border(1.dp, IndustrialBorderColor, RoundedCornerShape(16.dp))
                        .testTag("no_drawing_available_placeholder"),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ImageNotSupported,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No drawing available",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Upload your own schematic or photo for bearing '${bearing.number}'.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Button(
                            onClick = { photoPickerLauncher.launch("image/*") },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("upload_photo_placeholder_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddPhotoAlternate,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Upload Photo")
                        }
                    }
                }
            } else {
                val drawingBgColor = if (!customPhotoUri.isNullOrBlank()) Color(0xFF121A2B) else Color.White
                // Drawing Available Container with Zoom affordance
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 10f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(drawingBgColor)
                        .border(1.dp, IndustrialBorderColor.copy(alpha = 0.8f), RoundedCornerShape(16.dp))
                        .clickable { isZoomOpen = true }
                        .testTag("bearing_drawing_image"),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        !customPhotoUri.isNullOrBlank() -> {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(customPhotoUri)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Uploaded Drawing Photo for ${bearing.number}",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                            )
                        }

                        bundledResId != null -> {
                            Image(
                                painter = painterResource(id = bundledResId),
                                contentDescription = "Technical Drawing for Bearing ${bearing.number}",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                            )
                        }
                    }

                    // Custom Upload Tag (Top Left Badge if custom)
                    if (!customPhotoUri.isNullOrBlank()) {
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "Custom Photo",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                ),
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    // Zoom Badge Overlay in top right
                    Surface(
                        color = Color.Black.copy(alpha = 0.65f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ZoomIn,
                                contentDescription = "Tap to zoom",
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Zoom",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                ),
                                color = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action Buttons: Upload Photo & Download Drawing
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Upload / Replace Photo Button
                    OutlinedButton(
                        onClick = { photoPickerLauncher.launch("image/*") },
                        enabled = !isUploading,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .testTag("upload_bearing_photo_button"),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        if (isUploading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Uploading...")
                        } else {
                            Icon(
                                imageVector = Icons.Default.AddPhotoAlternate,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (customPhotoUri.isNullOrBlank()) "Upload Photo" else "Change Photo",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }

                    // Reset / Remove custom photo button if uploaded
                    if (!customPhotoUri.isNullOrBlank()) {
                        IconButton(
                            onClick = {
                                onPhotoUploaded?.invoke(null)
                                val msg = "Custom drawing photo removed."
                                if (onShowMessage != null) onShowMessage(msg)
                                else Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                            },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f),
                                contentColor = MaterialTheme.colorScheme.error
                            ),
                            modifier = Modifier
                                .size(52.dp)
                                .testTag("remove_custom_photo_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Remove custom photo and reset to default drawing"
                            )
                        }
                    }

                    // Download Button
                    Button(
                        onClick = { handleDownload() },
                        enabled = !isDownloading,
                        modifier = Modifier
                            .weight(1.3f)
                            .height(52.dp)
                            .testTag("download_bearing_drawing_button"),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        if (isDownloading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.5.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Saving...",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        } else if (downloadSuccess) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Saved",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Download,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Download Drawing",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    // Full-Screen Interactive Zoomable Dialog
    if (isZoomOpen && (hasDrawing || !customPhotoUri.isNullOrBlank())) {
        ZoomableDrawingDialog(
            bearing = bearing,
            customPhotoUri = customPhotoUri,
            bundledResId = bundledResId,
            onDismiss = { isZoomOpen = false },
            onDownload = { handleDownload() },
            isDownloading = isDownloading
        )
    }
}

@Composable
private fun ZoomableDrawingDialog(
    bearing: Bearing,
    customPhotoUri: String?,
    bundledResId: Int?,
    onDismiss: () -> Unit,
    onDownload: () -> Unit,
    isDownloading: Boolean
) {
    val context = LocalContext.current
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.94f))
                .testTag("drawing_zoom_dialog")
        ) {
            // Top Controls Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "${bearing.manufacturer} ${bearing.number}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.White
                    )
                    Text(
                        text = "Pinch to zoom • Drag to pan",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = {
                            scale = 1f
                            offset = Offset.Zero
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.White.copy(alpha = 0.15f),
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.RestartAlt,
                            contentDescription = "Reset Zoom"
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = onDismiss,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.White.copy(alpha = 0.15f),
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Zoom View"
                        )
                    }
                }
            }

            // Interactive Zoom / Pan Canvas
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 70.dp, bottom = 80.dp)
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            scale = (scale * zoom).coerceIn(0.8f, 5.0f)
                            if (scale > 1f) {
                                offset = Offset(
                                    x = offset.x + pan.x,
                                    y = offset.y + pan.y
                                )
                            } else {
                                offset = Offset.Zero
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            translationX = offset.x,
                            translationY = offset.y
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        !customPhotoUri.isNullOrBlank() -> {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(customPhotoUri)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Zoomed Custom Photo for ${bearing.number}",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        bundledResId != null -> {
                            Image(
                                painter = painterResource(id = bundledResId),
                                contentDescription = "Zoomed Drawing for ${bearing.number}",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }

            // Bottom Floating Bar: Quick Download Action
            Surface(
                color = Color.Black.copy(alpha = 0.8f),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Scale: ${(scale * 100).toInt()}%",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White.copy(alpha = 0.8f)
                    )

                    Button(
                        onClick = onDownload,
                        enabled = !isDownloading,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (isDownloading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Saving...")
                        } else {
                            Icon(
                                imageVector = Icons.Default.Download,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Download Drawing")
                        }
                    }
                }
            }
        }
    }
}

