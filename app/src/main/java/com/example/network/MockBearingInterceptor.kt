package com.example.network

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class MockBearingInterceptor : Interceptor {

    private data class BearingSpec(
        val number: String,
        val manufacturer: String,
        val boreMm: Double,
        val outsideMm: Double,
        val widthMm: Double,
        val chamferMm: Double,
        val weightKg: Double,
        val quantity: Int,
        val price: Double,
        val shelfLocation: String,
        val condition: String = "New",
        val referenceSpeedRpm: Int = 0,
        val limitingSpeedGreaseRpm: Int = 0,
        val limitingSpeedOilRpm: Int = 0,
        val dynamicLoadC: Double = 0.0,
        val staticLoadC0: Double = 0.0
    )

    private val bearingDatabase = mapOf(
        "6200" to BearingSpec("6200", "SKF", 10.0, 30.0, 9.0, 0.6, 0.032, 120, 45.0, "A-01-01"),
        "6201" to BearingSpec("6201", "NSK", 12.0, 32.0, 10.0, 0.6, 0.037, 85, 50.0, "A-01-02"),
        "6202" to BearingSpec("6202", "FAG", 15.0, 35.0, 11.0, 0.6, 0.045, 95, 60.0, "A-01-03"),
        "6203" to BearingSpec("6203", "NTN", 17.0, 40.0, 12.0, 1.0, 0.065, 70, 75.0, "A-02-01"),
        "6204" to BearingSpec("6204", "SKF", 20.0, 47.0, 14.0, 1.0, 0.106, 150, 95.0, "A-02-02"),
        "6205" to BearingSpec("6205", "KOYO", 25.0, 52.0, 15.0, 1.0, 0.128, 110, 120.0, "A-02-03"),
        "6206" to BearingSpec("6206", "TIMKEN", 30.0, 62.0, 16.0, 1.0, 0.199, 64, 160.0, "B-01-01"),
        "6207" to BearingSpec("6207", "FAG", 35.0, 72.0, 17.0, 1.1, 0.288, 48, 210.0, "B-01-02"),
        "6208" to BearingSpec("6208", "SKF", 40.0, 80.0, 18.0, 1.1, 0.366, 32, 280.0, "B-01-03"),
        "6209" to BearingSpec("6209", "SKF", 45.0, 85.0, 19.0, 1.1, 0.407, 25, 350.0, "A-03-B2"),
        "6210" to BearingSpec("6210", "NSK", 50.0, 90.0, 20.0, 1.1, 0.463, 40, 410.0, "B-02-01"),
        "6211" to BearingSpec("6211", "KOYO", 55.0, 100.0, 21.0, 1.1, 0.595, 18, 520.0, "B-02-02"),
        "6212" to BearingSpec("6212", "FAG", 60.0, 110.0, 22.0, 1.5, 0.789, 22, 680.0, "C-01-01"),
        "6213" to BearingSpec("6213", "TIMKEN", 65.0, 120.0, 23.0, 1.5, 0.99, 14, 840.0, "C-01-02"),
        "6214" to BearingSpec("6214", "SKF", 70.0, 125.0, 24.0, 1.5, 1.11, 30, 980.0, "C-01-03"),
        "6215" to BearingSpec("6215", "NTN", 75.0, 130.0, 25.0, 1.5, 1.24, 16, 1150.0, "C-02-01"),
        "6216" to BearingSpec("6216", "FAG", 80.0, 140.0, 26.0, 1.5, 1.54, 12, 1380.0, "C-02-02"),
        "6217" to BearingSpec("6217", "NSK", 85.0, 150.0, 28.0, 2.0, 1.93, 10, 1650.0, "C-02-03"),
        "6218" to BearingSpec("6218", "SKF", 90.0, 160.0, 30.0, 2.0, 2.37, 8, 1950.0, "D-01-01"),
        "6219" to BearingSpec("6219", "KOYO", 95.0, 170.0, 32.0, 2.1, 2.9, 6, 2350.0, "D-01-02"),
        "6220" to BearingSpec("6220", "SKF", 100.0, 180.0, 34.0, 2.1, 3.47, 15, 2800.0, "D-01-03"),
        "6221" to BearingSpec("6221", "FAG", 105.0, 190.0, 36.0, 2.1, 4.2, 5, 3300.0, "D-02-01"),
        "6222" to BearingSpec("6222", "TIMKEN", 110.0, 200.0, 38.0, 2.1, 5.04, 7, 3900.0, "D-02-02"),
        "6224" to BearingSpec("6224", "SKF", 120.0, 215.0, 40.0, 2.1, 6.32, 9, 4800.0, "D-02-03"),
        "6226" to BearingSpec("6226", "NSK", 130.0, 230.0, 40.0, 2.1, 7.38, 4, 5600.0, "E-01-01"),
        "6228" to BearingSpec("6228", "NTN", 140.0, 250.0, 42.0, 2.1, 9.18, 3, 6700.0, "E-01-02"),
        "6230" to BearingSpec("6230", "SKF", 150.0, 270.0, 45.0, 2.1, 11.0, 6, 8200.0, "E-01-03"),
        "6232" to BearingSpec("6232", "FAG", 160.0, 290.0, 48.0, 2.1, 13.6, 2, 9800.0, "E-02-01"),
        "6234" to BearingSpec("6234", "KOYO", 170.0, 310.0, 52.0, 2.1, 17.1, 4, 12500.0, "E-02-02"),
        "6236" to BearingSpec("6236", "SKF", 180.0, 320.0, 52.0, 2.1, 18.3, 3, 14200.0, "E-02-03"),
        "6238" to BearingSpec("6238", "TIMKEN", 190.0, 340.0, 55.0, 2.1, 21.7, 2, 16800.0, "F-01-01"),
        "6240" to BearingSpec("6240", "SKF", 200.0, 360.0, 58.0, 2.1, 25.6, 5, 19500.0, "F-01-02"),
        "6244" to BearingSpec("6244", "FAG", 220.0, 400.0, 65.0, 3.0, 34.3, 2, 26000.0, "F-01-03"),
        "6248" to BearingSpec("6248", "NSK", 240.0, 440.0, 72.0, 3.0, 44.8, 1, 34000.0, "F-02-01"),
        "6252" to BearingSpec("6252", "SKF", 260.0, 480.0, 80.0, 3.0, 58.4, 2, 45000.0, "F-02-02"),
        "6256" to BearingSpec("6256", "NTN", 280.0, 500.0, 80.0, 3.0, 63.2, 1, 52000.0, "F-02-03"),
        "6260" to BearingSpec("6260", "KOYO", 300.0, 540.0, 85.0, 3.0, 75.5, 2, 64000.0, "G-01-01"),
        "6264" to BearingSpec("6264", "SKF", 320.0, 580.0, 92.0, 3.0, 91.9, 1, 78000.0, "G-01-02"),
        "6268" to BearingSpec("6268", "FAG", 340.0, 620.0, 92.0, 3.0, 103.0, 1, 92000.0, "G-01-03"),
        "6272" to BearingSpec("6272", "TIMKEN", 360.0, 650.0, 100.0, 3.0, 123.0, 1, 110000.0, "G-02-01"),
        "6276" to BearingSpec("6276", "NSK", 380.0, 680.0, 100.0, 3.0, 137.0, 1, 128000.0, "G-02-02"),
        "6280" to BearingSpec("6280", "SKF", 400.0, 720.0, 100.0, 3.0, 157.0, 1, 150000.0, "G-02-03")
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val pathSegments = request.url.pathSegments

        if (pathSegments.size >= 4 && pathSegments[0] == "api" && pathSegments[1] == "v1" && pathSegments[2] == "bearings") {
            val rawNumber = pathSegments[3].uppercase().trim()

            if (rawNumber.contains("ERR") || rawNumber.contains("ERROR")) {
                throw java.io.IOException("Simulated network connection failure. Please check your workshop network.")
            }

            // Match exact code or match prefix (e.g., 6209-2RS -> 6209)
            val key = bearingDatabase.keys.firstOrNull { rawNumber == it || rawNumber.startsWith(it) }
            val spec = key?.let { bearingDatabase[it] }

            val statusCode = if (spec != null) 200 else 404
            val responseBodyString = if (spec != null) {
                buildJsonResponse(spec, rawNumber)
            } else {
                """
                {
                  "success": false,
                  "message": "Bearing $rawNumber not found in workshop inventory database."
                }
                """.trimIndent()
            }

            return Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(statusCode)
                .message(if (statusCode == 200) "OK" else "Not Found")
                .body(responseBodyString.toResponseBody("application/json".toMediaType()))
                .build()
        }

        return chain.proceed(request)
    }

    private fun buildJsonResponse(spec: BearingSpec, searchedNumber: String): String {
        return """
            {
              "success": true,
              "data": {
                "bearing": {
                  "number": "$searchedNumber",
                  "manufacturer": "${spec.manufacturer}",
                  "boreMm": ${spec.boreMm},
                  "outsideMm": ${spec.outsideMm},
                  "widthMm": ${spec.widthMm},
                  "chamferMm": ${spec.chamferMm},
                  "weightKg": ${spec.weightKg},
                  "referenceSpeedRpm": ${spec.referenceSpeedRpm},
                  "limitingSpeedGreaseRpm": ${spec.limitingSpeedGreaseRpm},
                  "limitingSpeedOilRpm": ${spec.limitingSpeedOilRpm},
                  "dynamicLoadC": ${spec.dynamicLoadC},
                  "staticLoadC0": ${spec.staticLoadC0}
                },
                "inventory": [
                  {
                    "condition": "${spec.condition}",
                    "quantity": ${spec.quantity},
                    "sellingPrice": ${spec.price},
                    "shelfLocation": "${spec.shelfLocation}"
                  }
                ]
              }
            }
        """.trimIndent()
    }
}
