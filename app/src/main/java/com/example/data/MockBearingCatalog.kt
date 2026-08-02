package com.example.data

import com.example.model.Bearing
import com.example.model.Inventory

object MockBearingCatalog {

    data class CatalogEntry(
        val bearing: Bearing,
        val inventory: Inventory
    )

    private val catalogMap = mapOf(
        "6200" to CatalogEntry(Bearing("6200", "SKF", 10.0, 30.0, 9.0, 0.6, 0.032, 56000, 28000, 34000, 5.4, 2.36), Inventory("New", 18, 45.0, "A-01-A1")),
        "6201" to CatalogEntry(Bearing("6201", "SKF", 12.0, 32.0, 10.0, 0.6, 0.037, 50000, 26000, 32000, 6.9, 3.09), Inventory("New", 25, 52.0, "A-01-A2")),
        "6202" to CatalogEntry(Bearing("6202", "NSK", 15.0, 35.0, 11.0, 0.6, 0.045, 43000, 22000, 28000, 7.73, 3.76), Inventory("Sealed", 12, 60.0, "A-01-A3")),
        "6203" to CatalogEntry(Bearing("6203", "FAG", 17.0, 40.0, 12.0, 1.0, 0.065, 38000, 19000, 24000, 9.55, 4.76), Inventory("New", 30, 75.0, "A-02-B1")),
        "6204" to CatalogEntry(Bearing("6204", "SKF", 20.0, 47.0, 14.0, 1.0, 0.106, 32000, 17000, 20000, 12.79, 6.58), Inventory("New", 42, 110.0, "A-02-B2")),
        "6205" to CatalogEntry(Bearing("6205", "NTN", 25.0, 52.0, 15.0, 1.0, 0.128, 28000, 14000, 18000, 14.02, 7.88), Inventory("Sealed", 15, 135.0, "A-02-B3")),
        "6206" to CatalogEntry(Bearing("6206", "SKF", 30.0, 62.0, 16.0, 1.0, 0.199, 24000, 12000, 15000, 19.5, 11.2), Inventory("New", 8, 180.0, "B-01-C1")),
        "6207" to CatalogEntry(Bearing("6207", "KOYO", 35.0, 72.0, 17.0, 1.1, 0.288, 20000, 10000, 13000, 25.5, 15.3), Inventory("New", 20, 240.0, "B-01-C2")),
        "6208" to CatalogEntry(Bearing("6208", "FAG", 40.0, 80.0, 18.0, 1.1, 0.366, 18000, 9000, 11000, 29.6, 18.0), Inventory("Sealed", 5, 310.0, "B-02-D1")),
        "6209" to CatalogEntry(Bearing("6209", "SKF", 45.0, 85.0, 19.0, 1.1, 0.407, 16000, 8000, 10000, 32.5, 20.5), Inventory("New", 14, 380.0, "B-02-D2")),
        "6210" to CatalogEntry(Bearing("6210", "NSK", 50.0, 90.0, 20.0, 1.1, 0.463, 15000, 8000, 10000, 35.07, 23.18), Inventory("New", 9, 420.0, "B-03-E1")),
        "6211" to CatalogEntry(Bearing("6211", "KOYO", 55.0, 100.0, 21.0, 1.5, 0.595, 13000, 7000, 9000, 43.38, 29.22), Inventory("New", 18, 520.0, "B-02-02")),
        "6212" to CatalogEntry(Bearing("6212", "FAG", 60.0, 110.0, 22.0, 1.5, 0.789, 12000, 6300, 8000, 52.42, 36.17), Inventory("New", 22, 680.0, "C-01-01")),
        "6213" to CatalogEntry(Bearing("6213", "TIMKEN", 65.0, 120.0, 23.0, 1.5, 0.99, 11000, 5900, 7500, 57.2, 39.5), Inventory("New", 14, 840.0, "C-01-02")),
        "6214" to CatalogEntry(Bearing("6214", "SKF", 70.0, 125.0, 24.0, 1.5, 1.11, 10500, 5600, 7000, 62.19, 43.99), Inventory("New", 30, 980.0, "C-01-03")),
        "6215" to CatalogEntry(Bearing("6215", "NTN", 75.0, 130.0, 25.0, 1.5, 1.24, 10000, 5300, 6700, 67.36, 48.18), Inventory("New", 16, 1150.0, "C-02-01")),
        "6216" to CatalogEntry(Bearing("6216", "FAG", 80.0, 140.0, 26.0, 1.5, 1.54, 9500, 5000, 6300, 72.8, 55.0), Inventory("New", 12, 1380.0, "C-02-02")),
        "6217" to CatalogEntry(Bearing("6217", "NSK", 85.0, 150.0, 28.0, 2.0, 1.93, 9000, 4500, 5600, 83.2, 64.0), Inventory("New", 10, 1650.0, "C-02-03")),
        "6218" to CatalogEntry(Bearing("6218", "SKF", 90.0, 160.0, 30.0, 2.0, 2.37, 8500, 4300, 5300, 95.98, 71.45), Inventory("New", 8, 1950.0, "D-01-01")),
        "6219" to CatalogEntry(Bearing("6219", "KOYO", 95.0, 170.0, 32.0, 2.1, 2.9, 8000, 4000, 5000, 108.7, 81.7), Inventory("New", 6, 2350.0, "D-01-02")),
        "6220" to CatalogEntry(Bearing("6220", "SKF", 100.0, 180.0, 34.0, 2.1, 3.47, 7500, 3800, 4800, 122.11, 92.88), Inventory("Special Order", 2, 2850.0, "C-01-F1")),
        "6221" to CatalogEntry(Bearing("6221", "SKF", 105.0, 190.0, 36.0, 2.1, 4.2, 7200, 3600, 4500, 132.99, 104.45), Inventory("New", 5, 3200.0, "D-02-01")),
        "6222" to CatalogEntry(Bearing("6222", "FAG", 110.0, 200.0, 38.0, 2.1, 5.04, 6700, 3400, 4300, 151.0, 118.0), Inventory("New", 4, 3800.0, "D-02-02")),
        "6224" to CatalogEntry(Bearing("6224", "SKF", 120.0, 215.0, 40.0, 2.1, 6.32, 6300, 3200, 4000, 146.0, 118.0), Inventory("New", 6, 4500.0, "D-03-01")),
        "6226" to CatalogEntry(Bearing("6226", "NSK", 130.0, 230.0, 40.0, 2.1, 7.38, 6000, 3000, 3800, 156.0, 132.0), Inventory("Sealed", 3, 5200.0, "D-03-02")),
        "6228" to CatalogEntry(Bearing("6228", "SKF", 140.0, 250.0, 42.0, 2.1, 9.18, 5600, 2800, 3500, 166.0, 145.0), Inventory("New", 4, 6100.0, "E-01-01")),
        "6230" to CatalogEntry(Bearing("6230", "FAG", 150.0, 270.0, 45.0, 2.1, 11.0, 5300, 2700, 3300, 178.0, 158.0), Inventory("New", 2, 7400.0, "E-01-02")),
        "6232" to CatalogEntry(Bearing("6232", "SKF", 160.0, 290.0, 48.0, 2.1, 13.6, 5000, 2500, 3100, 186.0, 166.0), Inventory("Special Order", 2, 8800.0, "E-02-01")),
        "6234" to CatalogEntry(Bearing("6234", "NTN", 170.0, 310.0, 52.0, 2.1, 17.1, 4700, 2400, 2900, 212.0, 190.0), Inventory("New", 3, 10500.0, "E-02-02")),
        "6236" to CatalogEntry(Bearing("6236", "SKF", 180.0, 320.0, 52.0, 2.1, 18.3, 4500, 2300, 2800, 220.0, 200.0), Inventory("New", 2, 12000.0, "E-03-01")),
        "6238" to CatalogEntry(Bearing("6238", "KOYO", 190.0, 340.0, 55.0, 2.1, 21.7, 4300, 2200, 2700, 235.0, 215.0), Inventory("Sealed", 1, 14200.0, "E-03-02")),
        "6240" to CatalogEntry(Bearing("6240", "SKF", 200.0, 360.0, 58.0, 2.1, 25.6, 4100, 2100, 2600, 250.0, 230.0), Inventory("New", 2, 16800.0, "F-01-01")),
        "6244" to CatalogEntry(Bearing("6244", "SKF", 220.0, 400.0, 65.0, 3.0, 34.3, 3800, 1900, 2400, 285.0, 265.0), Inventory("Heavy Industrial", 2, 21000.0, "F-01-02")),
        "6248" to CatalogEntry(Bearing("6248", "FAG", 240.0, 440.0, 72.0, 3.0, 44.8, 3500, 1800, 2200, 315.0, 295.0), Inventory("Heavy Industrial", 1, 26500.0, "F-02-01")),
        "6252" to CatalogEntry(Bearing("6252", "SKF", 260.0, 480.0, 80.0, 3.0, 58.4, 3300, 1700, 2100, 345.0, 325.0), Inventory("Heavy Industrial", 1, 32000.0, "F-02-02")),
        "6256" to CatalogEntry(Bearing("6256", "NSK", 280.0, 500.0, 80.0, 3.0, 63.2, 3100, 1600, 2000, 360.0, 340.0), Inventory("Heavy Industrial", 1, 36000.0, "F-03-01")),
        "6260" to CatalogEntry(Bearing("6260", "SKF", 300.0, 540.0, 85.0, 3.0, 75.5, 2900, 1500, 1900, 385.0, 365.0), Inventory("Heavy Industrial", 1, 41000.0, "F-03-02")),
        "6264" to CatalogEntry(Bearing("6264", "SKF", 320.0, 580.0, 92.0, 3.0, 91.9, 2800, 1400, 1800, 415.0, 395.0), Inventory("Heavy Industrial", 1, 44000.0, "G-01-01")),
        "6268" to CatalogEntry(Bearing("6268", "FAG", 340.0, 620.0, 92.0, 3.0, 103.0, 2700, 1400, 1700, 440.0, 420.0), Inventory("Heavy Industrial", 1, 46000.0, "G-01-02")),
        "6272" to CatalogEntry(Bearing("6272", "SKF", 360.0, 650.0, 100.0, 3.0, 123.0, 2600, 1300, 1600, 465.0, 445.0), Inventory("Heavy Industrial", 1, 47500.0, "G-02-01")),
        "6276" to CatalogEntry(Bearing("6276", "SKF", 380.0, 680.0, 100.0, 3.0, 137.0, 2500, 1300, 1600, 490.0, 470.0), Inventory("Heavy Industrial", 1, 48000.0, "G-02-02")),
        "6280" to CatalogEntry(Bearing("6280", "SKF", 400.0, 720.0, 100.0, 3.0, 157.0, 2400, 1200, 1500, 515.0, 495.0), Inventory("Heavy Industrial", 1, 48500.0, "C-05-SEC"))
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

