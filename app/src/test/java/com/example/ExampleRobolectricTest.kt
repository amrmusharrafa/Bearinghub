package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.drawing.BearingDrawingManager
import com.example.model.Bearing
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("BearingHub", appName)
  }

  @Test
  fun `verify bearing drawing resolution for standard bearing`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val bearing6200 = Bearing(
      number = "6200",
      manufacturer = "SKF",
      boreMm = 10.0,
      outsideMm = 30.0,
      widthMm = 9.0,
      chamferMm = 0.6,
      weightKg = 0.032
    )

    val resId = BearingDrawingManager.getBundledDrawingResId(context, bearing6200)
    assertNotNull("Drawing resource should exist for 6200", resId)
    assertTrue("Drawing should be marked as available", BearingDrawingManager.hasDrawingAvailable(context, bearing6200))
  }

  @Test
  fun `verify designation sanitization`() {
    assertEquals("618_4", BearingDrawingManager.sanitizeDesignation("618/4"))
    assertEquals("6205_2rs", BearingDrawingManager.sanitizeDesignation("6205-2RS"))
  }

  @Test
  fun `verify custom photo drawing availability`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val bearingWithPhoto = Bearing(
      number = "6200",
      manufacturer = "SKF",
      boreMm = 10.0,
      outsideMm = 30.0,
      widthMm = 9.0,
      chamferMm = 0.6,
      weightKg = 0.032,
      customDrawingUri = "file:///data/user/0/com.example/files/bearing_drawings/photo_6200.png"
    )

    assertTrue(
      "Bearing with custom photo should report drawing available",
      BearingDrawingManager.hasDrawingAvailable(context, bearingWithPhoto)
    )
  }
}

