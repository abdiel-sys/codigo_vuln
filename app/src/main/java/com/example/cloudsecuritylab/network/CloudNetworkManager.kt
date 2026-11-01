package com.example.cloudsecuritylab


import com.example.cloudsecuritylab.BuildConfig
import android.util.Log
import java.net.HttpURLConnection
import java.net.URL
import java.io.BufferedReader
import java.io.InputStreamReader

class CloudNetworkManager {

    // Fortificado: Credencial protegida mediante BuildConfig y local.properties
    private val cloudApiKey = BuildConfig.CLOUD_API_KEY

    // Fortificado: Uso estricto de HTTPS
    private val cloudEndpoint = "https://api.servicio-nube.com/v1/users"

    fun fetchCloudData(): String {
        var response = ""
        try {
            val url = URL("$cloudEndpoint?apikey=$cloudApiKey")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            // Fortificado: Timeouts para evitar bloqueos (10 segundos)
            connection.connectTimeout = 10000
            connection.readTimeout = 10000

            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                response += line
            }
            reader.close()
        } catch (e: java.net.SocketTimeoutException) {
            // Fortificado: Log seguro interno y mensaje genérico para el usuario
            Log.e("CloudNetworkManager", "Timeout en la conexión: ${e.message}", e)
            response = "El servidor tardó demasiado en responder. Por favor, inténtalo más tarde."
        } catch (e: Exception) {
            Log.e("CloudNetworkManager", "Error inesperado en red: ${e.message}", e)
            response = "Ocurrió un error al procesar tu solicitud. Inténtalo de nuevo."
        }
        return response
    }
}