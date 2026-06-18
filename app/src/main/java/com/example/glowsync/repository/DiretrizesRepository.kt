package com.example.glowsync.repository

import com.example.glowsync.model.DiretrizClima
import com.example.glowsync.supabase.SupabaseClient
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray

class DiretrizesRepository {
    suspend fun buscarRecomendacao(): Result<DiretrizClima> = withContext(Dispatchers.IO) {
        runCatching {
            check(SupabaseClient.isConfigured) {
                "Configure SUPABASE_URL e SUPABASE_ANON_KEY no local.properties"
            }
            val endpoint = "${SupabaseClient.url}/rest/v1/diretrizes_clima" +
                "?select=categoria_produto,mensagem_alerta&limit=1"
            val connection = (URL(endpoint).openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                connectTimeout = 10_000
                readTimeout = 10_000
                setRequestProperty("apikey", SupabaseClient.anonKey)
                setRequestProperty("Authorization", "Bearer ${SupabaseClient.anonKey}")
            }
            try {
                val status = connection.responseCode
                val stream = if (status in 200..299) connection.inputStream else connection.errorStream
                val body = stream?.bufferedReader()?.use { it.readText() }.orEmpty()
                check(status in 200..299) { "Supabase respondeu com status $status" }
                val rows = JSONArray(body)
                check(rows.length() > 0) { "Nenhuma diretriz cadastrada" }
                val row = rows.getJSONObject(0)
                DiretrizClima(
                    categoriaProduto = row.optString("categoria_produto", "Cuidado diário"),
                    mensagemAlerta = row.optString("mensagem_alerta", "Cuide da sua pele hoje.")
                )
            } finally {
                connection.disconnect()
            }
        }
    }
}
