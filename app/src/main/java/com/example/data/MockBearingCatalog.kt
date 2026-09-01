package com.example.data

import com.example.model.Bearing
import com.example.model.Inventory

object MockBearingCatalog {

    data class CatalogEntry(
        val bearing: Bearing,
        val inventory: Inventory
    )

    private val defaultInventory = Inventory(condition = "New", quantity = null, sellingPrice = null, shelfLocation = "", currency = "EGP")

    private val catalogMap = mapOf(
        "6200" to CatalogEntry(Bearing("6200", "SKF", "Deep Groove Ball Bearings", 10.0, 30.0, 9.0, 0.6, 0.032, 56000, 28000, 34000, 5.4, 2.36), defaultInventory),
        "6201" to CatalogEntry(Bearing("6201", "SKF", "Deep Groove Ball Bearings", 12.0, 32.0, 10.0, 0.6, 0.037, 50000, 26000, 32000, 6.9, 3.09), defaultInventory),
        "6202" to CatalogEntry(Bearing("6202", "SKF", "Deep Groove Ball Bearings", 15.0, 35.0, 11.0, 0.6, 0.045, 43000, 22000, 28000, 7.73, 3.76), defaultInventory),
        "6203" to CatalogEntry(Bearing("6203", "SKF", "Deep Groove Ball Bearings", 17.0, 40.0, 12.0, 1.0, 0.065, 38000, 19000, 24000, 9.55, 4.76), defaultInventory),
        "6204" to CatalogEntry(Bearing("6204", "SKF", "Deep Groove Ball Bearings", 20.0, 47.0, 14.0, 1.0, 0.106, 32000, 17000, 20000, 12.79, 6.58), defaultInventory),
        "6205" to CatalogEntry(Bearing("6205", "SKF", "Deep Groove Ball Bearings", 25.0, 52.0, 15.0, 1.0, 0.128, 28000, 14000, 18000, 14.02, 7.88), defaultInventory),
        "6206" to CatalogEntry(Bearing("6206", "SKF", "Deep Groove Ball Bearings", 30.0, 62.0, 16.0, 1.0, 0.199, 24000, 12000, 15000, 19.5, 11.2), defaultInventory),
        "6207" to CatalogEntry(Bearing("6207", "SKF", "Deep Groove Ball Bearings", 35.0, 72.0, 17.0, 1.1, 0.288, 20000, 10000, 13000, 25.5, 15.3), defaultInventory),
        "6208" to CatalogEntry(Bearing("6208", "SKF", "Deep Groove Ball Bearings", 40.0, 80.0, 18.0, 1.1, 0.366, 18000, 9000, 11000, 29.6, 18.0), defaultInventory),
        "6209" to CatalogEntry(Bearing("6209", "SKF", "Deep Groove Ball Bearings", 45.0, 85.0, 19.0, 1.1, 0.407, 16000, 8000, 10000, 32.5, 20.5), defaultInventory),
        "6210" to CatalogEntry(Bearing("6210", "SKF", "Deep Groove Ball Bearings", 50.0, 90.0, 20.0, 1.1, 0.463, 15000, 8000, 10000, 35.07, 23.18), defaultInventory),
        "6211" to CatalogEntry(Bearing("6211", "SKF", "Deep Groove Ball Bearings", 55.0, 100.0, 21.0, 1.5, 0.595, 13000, 7000, 9000, 43.38, 29.22), defaultInventory),
        "6212" to CatalogEntry(Bearing("6212", "SKF", "Deep Groove Ball Bearings", 60.0, 110.0, 22.0, 1.5, 0.789, 12000, 6300, 8000, 52.42, 36.17), defaultInventory),
        "6213" to CatalogEntry(Bearing("6213", "SKF", "Deep Groove Ball Bearings", 65.0, 120.0, 23.0, 1.5, 0.99, 11000, 5900, 7500, 57.2, 39.5), defaultInventory),
        "6214" to CatalogEntry(Bearing("6214", "SKF", "Deep Groove Ball Bearings", 70.0, 125.0, 24.0, 1.5, 1.11, 10500, 5600, 7000, 62.19, 43.99), defaultInventory),
        "6215" to CatalogEntry(Bearing("6215", "SKF", "Deep Groove Ball Bearings", 75.0, 130.0, 25.0, 1.5, 1.24, 10000, 5300, 6700, 67.36, 48.18), defaultInventory),
        "6216" to CatalogEntry(Bearing("6216", "SKF", "Deep Groove Ball Bearings", 80.0, 140.0, 26.0, 1.5, 1.54, 9500, 5000, 6300, 72.8, 55.0), defaultInventory),
        "6217" to CatalogEntry(Bearing("6217", "SKF", "Deep Groove Ball Bearings", 85.0, 150.0, 28.0, 2.0, 1.93, 9000, 4500, 5600, 83.2, 64.0), defaultInventory),
        "6218" to CatalogEntry(Bearing("6218", "SKF", "Deep Groove Ball Bearings", 90.0, 160.0, 30.0, 2.0, 2.37, 8500, 4300, 5300, 95.98, 71.45), defaultInventory),
        "6219" to CatalogEntry(Bearing("6219", "SKF", "Deep Groove Ball Bearings", 95.0, 170.0, 32.0, 2.1, 2.9, 8000, 4000, 5000, 108.7, 81.7), defaultInventory),
        "6220" to CatalogEntry(Bearing("6220", "SKF", "Deep Groove Ball Bearings", 100.0, 180.0, 34.0, 2.1, 3.47, 7500, 3800, 4800, 122.11, 92.88), defaultInventory),
        "6221" to CatalogEntry(Bearing("6221", "SKF", "Deep Groove Ball Bearings", 105.0, 190.0, 36.0, 2.1, 4.2, 7200, 3600, 4500, 132.99, 104.45), defaultInventory),
        "6222" to CatalogEntry(Bearing("6222", "SKF", "Deep Groove Ball Bearings", 110.0, 200.0, 38.0, 2.1, 5.04, 6700, 3400, 4300, 151.0, 118.0), defaultInventory),
        "6224" to CatalogEntry(Bearing("6224", "SKF", "Deep Groove Ball Bearings", 120.0, 215.0, 40.0, 2.1, 6.32, 6300, 3200, 4000, 146.0, 118.0), defaultInventory),
        "6226" to CatalogEntry(Bearing("6226", "SKF", "Deep Groove Ball Bearings", 130.0, 230.0, 40.0, 2.1, 7.38, 6000, 3000, 3800, 156.0, 132.0), defaultInventory),
        "6228" to CatalogEntry(Bearing("6228", "SKF", "Deep Groove Ball Bearings", 140.0, 250.0, 42.0, 2.1, 9.18, 5600, 2800, 3500, 166.0, 145.0), defaultInventory),
        "6230" to CatalogEntry(Bearing("6230", "SKF", "Deep Groove Ball Bearings", 150.0, 270.0, 45.0, 2.1, 11.0, 5300, 2700, 3300, 178.0, 158.0), defaultInventory),
        "6232" to CatalogEntry(Bearing("6232", "SKF", "Deep Groove Ball Bearings", 160.0, 290.0, 48.0, 2.1, 13.6, 5000, 2500, 3100, 186.0, 166.0), defaultInventory),
        "6234" to CatalogEntry(Bearing("6234", "SKF", "Deep Groove Ball Bearings", 170.0, 310.0, 52.0, 2.1, 17.1, 4700, 2400, 2900, 212.0, 190.0), defaultInventory),
        "6236" to CatalogEntry(Bearing("6236", "SKF", "Deep Groove Ball Bearings", 180.0, 320.0, 52.0, 2.1, 18.3, 4500, 2300, 2800, 220.0, 200.0), defaultInventory),
        "6238" to CatalogEntry(Bearing("6238", "SKF", "Deep Groove Ball Bearings", 190.0, 340.0, 55.0, 2.1, 21.7, 4300, 2200, 2700, 235.0, 215.0), defaultInventory),
        "6240" to CatalogEntry(Bearing("6240", "SKF", "Deep Groove Ball Bearings", 200.0, 360.0, 58.0, 2.1, 25.6, 4100, 2100, 2600, 250.0, 230.0), defaultInventory),
        "6244" to CatalogEntry(Bearing("6244", "SKF", "Deep Groove Ball Bearings", 220.0, 400.0, 65.0, 3.0, 34.3, 3800, 1900, 2400, 285.0, 265.0), defaultInventory),
        "6248" to CatalogEntry(Bearing("6248", "SKF", "Deep Groove Ball Bearings", 240.0, 440.0, 72.0, 3.0, 44.8, 3500, 1800, 2200, 315.0, 295.0), defaultInventory),
        "6252" to CatalogEntry(Bearing("6252", "SKF", "Deep Groove Ball Bearings", 260.0, 480.0, 80.0, 3.0, 58.4, 3300, 1700, 2100, 345.0, 325.0), defaultInventory),
        "6256" to CatalogEntry(Bearing("6256", "SKF", "Deep Groove Ball Bearings", 280.0, 500.0, 80.0, 3.0, 63.2, 3100, 1600, 2000, 360.0, 340.0), defaultInventory),
        "6260" to CatalogEntry(Bearing("6260", "SKF", "Deep Groove Ball Bearings", 300.0, 540.0, 85.0, 3.0, 75.5, 2900, 1500, 1900, 385.0, 365.0), defaultInventory),
        "6264" to CatalogEntry(Bearing("6264", "SKF", "Deep Groove Ball Bearings", 320.0, 580.0, 92.0, 3.0, 91.9, 2800, 1400, 1800, 415.0, 395.0), defaultInventory),
        "6268" to CatalogEntry(Bearing("6268", "SKF", "Deep Groove Ball Bearings", 340.0, 620.0, 92.0, 3.0, 103.0, 2700, 1400, 1700, 440.0, 420.0), defaultInventory),
        "6272" to CatalogEntry(Bearing("6272", "SKF", "Deep Groove Ball Bearings", 360.0, 650.0, 100.0, 3.0, 123.0, 2600, 1300, 1600, 465.0, 445.0), defaultInventory),
        "6276" to CatalogEntry(Bearing("6276", "SKF", "Deep Groove Ball Bearings", 380.0, 680.0, 100.0, 3.0, 137.0, 2500, 1300, 1600, 490.0, 470.0), defaultInventory),
        "6280" to CatalogEntry(Bearing("6280", "SKF", "Deep Groove Ball Bearings", 400.0, 720.0, 100.0, 3.0, 157.0, 2400, 1200, 1500, 515.0, 495.0), defaultInventory)
    )

    fun findBearing(rawNumber: String): CatalogEntry? {
        val clean = rawNumber.trim().uppercase()
        if (clean.isEmpty()) return null

        // 1. Direct match
        catalogMap[clean]?.let { return it }

        // 2. Exact digits match e.g. "6204-2RS" or "6204 2RS"
        val extractedDigits = Regex("""62\d{2}""").find(clean)?.value
        if (extractedDigits != null && catalogMap.containsKey(extractedDigits)) {
            val baseEntry = catalogMap[extractedDigits]!!
            return CatalogEntry(
                bearing = baseEntry.bearing.copy(number = clean),
                inventory = baseEntry.inventory
            )
        }

        // 3. Substring match
        val matchedKey = catalogMap.keys.firstOrNull { key ->
            clean.contains(key) || key.contains(clean)
        }
        if (matchedKey != null) {
            val baseEntry = catalogMap[matchedKey]!!
            return CatalogEntry(
                bearing = baseEntry.bearing.copy(number = clean),
                inventory = baseEntry.inventory
            )
        }

        return null
    }

    fun getAllInitialSeed(): List<CatalogEntry> = catalogMap.values.toList()
}

