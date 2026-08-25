package com.example

import com.example.data.MasterBearingCatalog
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testMasterBearingCatalog_has268Records() {
        val bearings = MasterBearingCatalog.allBearings
        assertEquals(268, bearings.size)

        val uniqueDesignations = bearings.map { it.number }.toSet()
        assertEquals(268, uniqueDesignations.size)
    }

    @Test
    fun testMasterBearingCatalog_specificEntriesMatchExcel() {
        val bearings = MasterBearingCatalog.allBearings.associateBy { it.number }

        // Test Record 1: 623
        val b623 = bearings["623"]
        assertNotNull(b623)
        assertEquals(3.0, b623!!.boreMm, 0.001)
        assertEquals(10.0, b623.outsideMm, 0.001)
        assertEquals(4.0, b623.widthMm, 0.001)
        assertEquals(488.0, b623.dynamicLoadC, 0.001)
        assertEquals(170.0, b623.staticLoadC0, 0.001)
        assertEquals(40000, b623.limitingSpeedGreaseRpm)
        assertEquals(48000, b623.limitingSpeedOilRpm)
        assertEquals(0.0015, b623.weightKg, 0.0001)

        // Test Record 2: 618/4
        val b618_4 = bearings["618/4"]
        assertNotNull(b618_4)
        assertEquals(4.0, b618_4!!.boreMm, 0.001)
        assertEquals(9.0, b618_4.outsideMm, 0.001)
        assertEquals(2.5, b618_4.widthMm, 0.001)
        assertEquals(540.0, b618_4.dynamicLoadC, 0.001)
        assertEquals(183.0, b618_4.staticLoadC0, 0.001)
        assertEquals(45000, b618_4.limitingSpeedGreaseRpm)
        assertEquals(53000, b618_4.limitingSpeedOilRpm)
        assertEquals(0.0007, b618_4.weightKg, 0.0001)

        // Test Last Record: 618/900
        val b618_900 = bearings["618/900"]
        assertNotNull(b618_900)
        assertEquals(900.0, b618_900!!.boreMm, 0.001)
        assertEquals(1090.0, b618_900.outsideMm, 0.001)
        assertEquals(85.0, b618_900.widthMm, 0.001)
        assertEquals(618000.0, b618_900.dynamicLoadC, 0.001)
        assertEquals(1120000.0, b618_900.staticLoadC0, 0.001)
        assertEquals(380, b618_900.limitingSpeedGreaseRpm)
        assertEquals(450, b618_900.limitingSpeedOilRpm)
        assertEquals(160.0, b618_900.weightKg, 0.001)
    }
}
