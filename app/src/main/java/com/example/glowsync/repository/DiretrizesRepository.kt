package com.example.glowsync.repository

import com.example.glowsync.model.ClimaAtual
import com.example.glowsync.model.DiretrizClima
import com.example.glowsync.supabase.SupabaseClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class DiretrizesRepository {

    // Coloque os mesmos valores existentes no server.js
    private val channelId = "3406682"
    private val thingSpeakReadKey = "37XC3LE7FWZ5GSAD"

    suspend fun buscarClimaERecomendacoes(): Result<ClimaAtual> =
        withContext(Dispatchers.IO) {
            runCatching {
                check(SupabaseClient.isConfigured) {
                    "A configuração do Supabase está vazia."
                }

                val clima = buscarClimaThingSpeak()
                val temperatura = clima.first
                val umidade = clima.second

                val recomendacoes = buscarDiretrizes(
                    temperatura = temperatura,
                    umidade = umidade
                )

                ClimaAtual(
                    temperatura = temperatura,
                    umidade = umidade,
                    recomendacoes = recomendacoes
                )
            }
        }

    private fun buscarClimaThingSpeak(): Pair<Double, Double> {
        val endpoint =
            "https://api.thingspeak.com/channels/" +
                    "$channelId/feeds/last.json" +
                    "?api_key=$thingSpeakReadKey"

        val resposta = executarGet(endpoint)
        val json = JSONObject(resposta)

        val temperatura = json
            .optString("field1")
            .toDoubleOrNull()
            ?: error("O ThingSpeak retornou uma temperatura inválida.")

        val umidade = json
            .optString("field2")
            .toDoubleOrNull()
            ?: error("O ThingSpeak retornou uma umidade inválida.")

        return temperatura to umidade
    }

    private fun buscarDiretrizes(
        temperatura: Double,
        umidade: Double
    ): List<DiretrizClima> {

        val endpoint = buildString {
            append(SupabaseClient.url.trimEnd('/'))
            append("/rest/v1/diretrizes_clima")
            append("?select=categoria_produto,mensagem_alerta")
            append("&temp_min=lte.$temperatura")
            append("&temp_max=gte.$temperatura")
            append("&umi_min=lte.$umidade")
            append("&umi_max=gte.$umidade")
            append("&order=id.asc")
        }

        val resposta = executarGet(
            endpoint = endpoint,
            cabecalhos = mapOf(
                "apikey" to SupabaseClient.anonKey,
                "Authorization" to
                        "Bearer ${SupabaseClient.anonKey}"
            )
        )

        val rows = JSONArray(resposta)

        return buildList {
            for (indice in 0 until rows.length()) {
                val row = rows.getJSONObject(indice)

                add(
                    DiretrizClima(
                        categoriaProduto = row.optString(
                            "categoria_produto",
                            "Cuidado diário"
                        ),
                        mensagemAlerta = row.optString(
                            "mensagem_alerta",
                            "Cuide da sua pele hoje."
                        )
                    )
                )
            }
        }
    }

    private fun executarGet(
        endpoint: String,
        cabecalhos: Map<String, String> = emptyMap()
    ): String {

        val connection =
            URL(endpoint).openConnection() as HttpURLConnection

        try {
            connection.requestMethod = "GET"
            connection.connectTimeout = 10_000
            connection.readTimeout = 10_000
            connection.setRequestProperty(
                "Accept",
                "application/json"
            )

            cabecalhos.forEach { (nome, valor) ->
                connection.setRequestProperty(nome, valor)
            }

            val status = connection.responseCode

            val stream = if (status in 200..299) {
                connection.inputStream
            } else {
                connection.errorStream
            }

            val body = stream
                ?.bufferedReader()
                ?.use { it.readText() }
                .orEmpty()

            check(status in 200..299) {
                "O servidor respondeu com status $status: $body"
            }

            return body
        } finally {
            connection.disconnect()
        }
    }
}