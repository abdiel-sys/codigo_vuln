package com.example.cloudsecuritylab.network


// ADVERTENCIA: Este código contiene vulnerabilidades intencionales para fines didácticos.

import java.net.HttpURLConnection
import java.net.URL
import java.io.BufferedReader
import java.io.InputStreamReader

class CloudNetworkManager {

    // Vulnerabilidad 1: Credenciales (API Key) hardcodeadas en el código fuente.
    private val cloudApiKey = "AKIAIOSFODNN7EXAMPLE12345"

    // Vulnerabilidad 2: Uso de HTTP en lugar de HTTPS (datos en texto plano).
    private val cloudEndpoint = "http://api.servicio-nube.com/v1/users"

    fun fetchCloudData(): String {
        var response = ""
        try {
            val url = URL("$cloudEndpoint?apikey=$cloudApiKey")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            // Vulnerabilidad 3: Falta de validación de certificados o timeout.

            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                response += line
            }
            reader.close()
        } catch (e: Exception) {
            // Vulnerabilidad 4: Exposición de la traza de la pila completa al usuario/logs.
            e.printStackTrace()
            response = "Error interno del servidor: ${e.message}. Traza: ${e.stackTraceToString()}"
        }
        return response
    }
}