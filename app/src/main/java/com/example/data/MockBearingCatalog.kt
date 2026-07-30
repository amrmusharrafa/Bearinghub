package com.example.data

import com.example.model.Bearing
import com.example.model.Inventory

object MockBearingCatalog {

    data class CatalogEntry(
        val bearing: Bearing,
        val inventory: Inventory
    )

    private val catalogMap = mapOf(
        "6200" to CatalogEntry(Bearing("6200", "SKF", 10.0, 30.0, 9.0, 0.6, 0.032), Inventory("New", 18, 45.0, "A-01-A1")),
        "6201" to CatalogEntry(Bearing("6201", "SKF", 12.0, 32.0, 10.0, 0.6, 0.037), Inventory("New", 25, 52.0, "A-01-A2")),
        "6202" to CatalogEntry(Bearing("6202", "NSK", 15.0, 35.0, 11.0, 0.6, 0.045), Inventory("Sealed", 12, 60.0, "A-01-A3")),
        "6203" to CatalogEntry(Bearing("6203", "FAG", 17.0, 40.0, 12.0, 1.0, 0.065), Inventory("New", 30, 75.0, "A-02-B1")),
        "6204" to CatalogEntry(Bearing("6204", "SKF", 20.0, 47.0, 14.0, 1.0, 0.106), Inventory("New", 42, 110.0, "A-02-B2")),
        "6205" to CatalogEntry(Bearing("6205", "NTN", 25.0, 52.0, 15.0, 1.0, 0.128), Inventory("Sealed", 15, 135.0, "A-02-B3")),
        "6206" to CatalogEntry(Bearing("6206", "SKF", 30.0, 62.0, 16.0, 1.0, 0.199), Inventory("New", 8, 180.0, "B-01-C1")),
        "6207" to CatalogEntry(Bearing("6207", "KOYO", 35.0, 72.0, 17.0, 1.1, 0.288), Inventory("New", 20, 240.0, "B-01-C2")),
        "6208" to CatalogEntry(Bearing("6208", "FAG", 40.0, 80.0, 18.0, 1.1, 0.366), Inventory("Sealed", 5, 310.0, "B-02-D1")),
        "6209" to CatalogEntry(Bearing("6209", "SKF", 45.0, 85.0, 19.0, 1.1, 0.407), Inventory("New", 14, 380.0, "B-02-D2")),
        "6210" to CatalogEntry(Bearing("6210", "NSK", 50.0, 90.0, 20.0, 1.1, 0.463), Inventory("New", 9, 420.0, "B-03-E1")),
        "6211" to CatalogEntry(Bearing("6211", "KOYO", 55.0, 100.0, 21.0, 1.1, 0.595), Inventory("New", 18, 520.0, "B-02-02")),
        "6212" to CatalogEntry(Bearing("6212", "FAG", 60.0, 110.0, 22.0, 1.5, 0.789), Inventory("New", 22, 680.0, "C-01-01")),
        "6213" to CatalogEntry(Bearing("6213", "TIMKEN", 65.0, 120.0, 23.0, 1.5, 0.99), Inventory("New", 14, 840.0, "C-01-02")),
        "6214" to CatalogEntry(Bearing("6214", "SKF", 70.0, 125.0, 24.0, 1.5, 1.11), Inventory("New", 30, 980.0, "C-01-03")),
        "6215" to CatalogEntry(Bearing("6215", "NTN", 75.0, 130.0, 25.0, 1.5, 1.24), Inventory("New", 16, 1150.0, "C-02-01")),
        "6216" to CatalogEntry(Bearing("6216", "FAG", 80.0, 140.0, 26.0, 1.5, 1.54), Inventory("New", 12, 1380.0, "C-02-02")),
        "6217" to CatalogEntry(Bearing("6217", "NSK", 85.0, 150.0, 28.0, 2.0, 1.93), Inventory("New", 10, 1650.0, "C-02-03")),
        "6218" to CatalogEntry(Bearing("6218", "SKF", 90.0, 160.0, 30.0, 2.0, 2.37), Inventory("New", 8, 1950.0, "D-01-01")),
        "6219" to CatalogEntry(Bearing("6219", "KOYO", 95.0, 170.0, 32.0, 2.1, 2.9), Inventory("New", 6, 2350.0, "D-01-02")),
        "6220" to CatalogEntry(Bearing("6220", "SKF", 100.0, 180.0, 34.0, 2.1, 3.47), Inventory("Special Order", 2, 2850.0, "C-01-F1")),
        "6221" to CatalogEntry(Bearing("6221", "SKF", 105.0, 190.0, 36.0, 2.1, 4.2), Inventory("New", 5, 3200.0, "D-02-01")),
        "6222" to CatalogEntry(Bearing("6222", "FAG", 110.0, 200.0, 38.0, 2.1, 5.04), Inventory("New", 4, 3800.0, "D-02-02")),
        "6224" to CatalogEntry(Bearing("6224", "SKF", 120.0, 215.0, 40.0, 2.1, 6.32), Inventory("New", 6, 4500.0, "D-03-01")),
        "6226" to CatalogEntry(Bearing("6226", "NSK", 130.0, 230.0, 40.0, 2.1, 7.38), Inventory("Sealed", 3, 5200.0, "D-03-02")),
        "6228" to CatalogEntry(Bearing("6228", "SKF", 140.0, 250.0, 42.0, 2.1, 9.18), Inventory("New", 4, 6100.0, "E-01-01")),
        "6230" to CatalogEntry(Bearing("6230", "FAG", 150.0, 270.0, 45.0, 2.1, 11.0), Inventory("New", 2, 7400.0, "E-01-02")),
        "6232" to CatalogEntry(Bearing("6232", "SKF", 160.0, 290.0, 48.0, 2.1, 13.6), Inventory("Special Order", 2, 8800.0, "E-02-01")),
        "6234" to CatalogEntry(Bearing("6234", "NTN", 170.0, 310.0, 52.0, 2.1, 17.1), Inventory("New", 3, 10500.0, "E-02-02")),
        "6236" to CatalogEntry(Bearing("6236", "SKF", 180.0, 320.0, 52.0, 2.1, 18.3), Inventory("New", 2, 12000.0, "E-03-01")),
        "6238" to CatalogEntry(Bearing("6238", "KOYO", 190.0, 340.0, 55.0, 2.1, 21.7), Inventory("Sealed", 1, 14200.0, "E-03-02")),
        "6240" to CatalogEntry(Bearing("6240", "SKF", 200.0, 360.0, 58.0, 2.1, 25.6), Inventory("New", 2, 16800.0, "F-01-01")),
        "6244" to CatalogEntry(Bearing("6244", "SKF", 220.0, 400.0, 65.0, 3.0, 34.3), Inventory("Heavy Industrial", 2, 21000.0, "F-01-02")),
        "6248" to CatalogEntry(Bearing("6248", "FAG", 240.0, 440.0, 72.0, 3.0, 44.8), Inventory("Heavy Industrial", 1, 26500.0, "F-02-01")),
        "6252" to CatalogEntry(Bearing("6252", "SKF", 260.0, 480.0, 80.0, 3.0, 58.4), Inventory("Heavy Industrial", 1, 32000.0, "F-02-02")),
        "6256" to CatalogEntry(Bearing("6256", "NSK", 280.0, 500.0, 80.0, 3.0, 63.2), Inventory("Heavy Industrial", 1, 36000.0, "F-03-01")),
        "6260" to CatalogEntry(Bearing("6260", "SKF", 300.0, 540.0, 85.0, 3.0, 75.5), Inventory("Heavy Industrial", 1, 41000.0, "F-03-02")),
        "6264" to CatalogEntry(Bearing("6264", "SKF", 320.0, 580.0, 92.0, 3.0, 91.9), Inventory("Heavy Industrial", 1, 44000.0, "G-01-01")),
        "6268" to CatalogEntry(Bearing("6268", "FAG", 340.0, 620.0, 92.0, 3.0, 103.0), Inventory("Heavy Industrial", 1, 46000.0, "G-01-02")),
        "6272" to CatalogEntry(Bearing("6272", "SKF", 360.0, 650.0, 100.0, 3.0, 123.0), Inventory("Heavy Industrial", 1, 47500.0, "G-02-01")),
        "6276" to CatalogEntry(Bearing("6276", "SKF", 380.0, 680.0, 100.0, 3.0, 137.0), Inventory("Heavy Industrial", 1, 48000.0, "G-02-02")),
        "6280" to CatalogEntry(Bearing("6280", "SKF", 400.0, 720.0, 100.0, 3.0, 157.0), Inventory("Heavy Industrial", 1, 48500.0, "C-05-SEC"))
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

