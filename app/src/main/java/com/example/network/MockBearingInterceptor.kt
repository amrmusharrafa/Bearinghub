package com.example.network

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class MockBearingInterceptor : Interceptor {

    private data class BearingSpec(
        val number: String,
        val manufacturer: String = "SKF",
        val boreMm: Double,
        val outsideMm: Double,
        val widthMm: Double,
        val chamferMm: Double,
        val weightKg: Double,
        val quantity: Int? = null,
        val price: Double? = null,
        val shelfLocation: String = "",
        val condition: String = "New",
        val currency: String = "EGP",
        val referenceSpeedRpm: Int = 0,
        val limitingSpeedGreaseRpm: Int = 0,
        val limitingSpeedOilRpm: Int = 0,
        val dynamicLoadC: Double = 0.0,
        val staticLoadC0: Double = 0.0
    )

    private val bearingDatabase = mapOf(
        "6200" to BearingSpec("6200", "SKF", 10.0, 30.0, 9.0, 0.6, 0.032),
        "6201" to BearingSpec("6201", "SKF", 12.0, 32.0, 10.0, 0.6, 0.037),
        "6202" to BearingSpec("6202", "SKF", 15.0, 35.0, 11.0, 0.6, 0.045),
        "6203" to BearingSpec("6203", "SKF", 17.0, 40.0, 12.0, 1.0, 0.065),
        "6204" to BearingSpec("6204", "SKF", 20.0, 47.0, 14.0, 1.0, 0.106),
        "6205" to BearingSpec("6205", "SKF", 25.0, 52.0, 15.0, 1.0, 0.128),
        "6206" to BearingSpec("6206", "SKF", 30.0, 62.0, 16.0, 1.0, 0.199),
        "6207" to BearingSpec("6207", "SKF", 35.0, 72.0, 17.0, 1.1, 0.288),
        "6208" to BearingSpec("6208", "SKF", 40.0, 80.0, 18.0, 1.1, 0.366),
        "6209" to BearingSpec("6209", "SKF", 45.0, 85.0, 19.0, 1.1, 0.407),
        "6210" to BearingSpec("6210", "SKF", 50.0, 90.0, 20.0, 1.1, 0.463),
        "6211" to BearingSpec("6211", "SKF", 55.0, 100.0, 21.0, 1.1, 0.595),
        "6212" to BearingSpec("6212", "SKF", 60.0, 110.0, 22.0, 1.5, 0.789),
        "6213" to BearingSpec("6213", "SKF", 65.0, 120.0, 23.0, 1.5, 0.99),
        "6214" to BearingSpec("6214", "SKF", 70.0, 125.0, 24.0, 1.5, 1.11),
        "6215" to BearingSpec("6215", "SKF", 75.0, 130.0, 25.0, 1.5, 1.24),
        "6216" to BearingSpec("6216", "SKF", 80.0, 140.0, 26.0, 1.5, 1.54),
        "6217" to BearingSpec("6217", "SKF", 85.0, 150.0, 28.0, 2.0, 1.93),
        "6218" to BearingSpec("6218", "SKF", 90.0, 160.0, 30.0, 2.0, 2.37),
        "6219" to BearingSpec("6219", "SKF", 95.0, 170.0, 32.0, 2.1, 2.9),
        "6220" to BearingSpec("6220", "SKF", 100.0, 180.0, 34.0, 2.1, 3.47),
        "6221" to BearingSpec("6221", "SKF", 105.0, 190.0, 36.0, 2.1, 4.2),
        "6222" to BearingSpec("6222", "SKF", 110.0, 200.0, 38.0, 2.1, 5.04),
        "6224" to BearingSpec("6224", "SKF", 120.0, 215.0, 40.0, 2.1, 6.32),
        "6226" to BearingSpec("6226", "SKF", 130.0, 230.0, 40.0, 2.1, 7.38),
        "6228" to BearingSpec("6228", "SKF", 140.0, 250.0, 42.0, 2.1, 9.18),
        "6230" to BearingSpec("6230", "SKF", 150.0, 270.0, 45.0, 2.1, 11.0),
        "6232" to BearingSpec("6232", "SKF", 160.0, 290.0, 48.0, 2.1, 13.6),
        "6234" to BearingSpec("6234", "SKF", 170.0, 310.0, 52.0, 2.1, 17.1),
        "6236" to BearingSpec("6236", "SKF", 180.0, 320.0, 52.0, 2.1, 18.3),
        "6238" to BearingSpec("6238", "SKF", 190.0, 340.0, 55.0, 2.1, 21.7),
        "6240" to BearingSpec("6240", "SKF", 200.0, 360.0, 58.0, 2.1, 25.6),
        "6244" to BearingSpec("6244", "SKF", 220.0, 400.0, 65.0, 3.0, 34.3),
        "6248" to BearingSpec("6248", "SKF", 240.0, 440.0, 72.0, 3.0, 44.8),
        "6252" to BearingSpec("6252", "SKF", 260.0, 480.0, 80.0, 3.0, 58.4),
        "6256" to BearingSpec("6256", "SKF", 280.0, 500.0, 80.0, 3.0, 63.2),
        "6260" to BearingSpec("6260", "SKF", 300.0, 540.0, 85.0, 3.0, 75.5),
        "6264" to BearingSpec("6264", "SKF", 320.0, 580.0, 92.0, 3.0, 91.9),
        "6268" to BearingSpec("6268", "SKF", 340.0, 620.0, 92.0, 3.0, 103.0),
        "6272" to BearingSpec("6272", "SKF", 360.0, 650.0, 100.0, 3.0, 123.0),
        "6276" to BearingSpec("6276", "SKF", 380.0, 680.0, 100.0, 3.0, 137.0),
        "6280" to BearingSpec("6280", "SKF", 400.0, 720.0, 100.0, 3.0, 157.0)
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
                  "manufacturer": "SKF",
                  "bearingType": "Deep Groove Ball Bearings",
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
                "inventory": []
              }
            }
        """.trimIndent()
    }
}
