package com.example.glowsync.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.glowsync.model.ClimaAtual
import com.example.glowsync.repository.DiretrizesRepository

@Composable
fun HomeScreen() {

    val repository = remember {
        DiretrizesRepository()
    }

    var climaAtual by remember {
        mutableStateOf<ClimaAtual?>(null)
    }

    var carregando by remember {
        mutableStateOf(true)
    }

    var erro by remember {
        mutableStateOf<String?>(null)
    }

    LaunchedEffect(Unit) {
        carregando = true
        erro = null

        repository
            .buscarClimaERecomendacoes()
            .onSuccess {
                climaAtual = it
            }
            .onFailure {
                erro = it.message
            }

        carregando = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8FB))
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "GlowSync ✨",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF9C6ADE)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Bem-vinda!",
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF9EAF5)
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "🌤️ Recomendações do dia",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                when {
                    carregando -> {
                        CircularProgressIndicator(
                            color = Color(0xFF9C6ADE)
                        )
                    }

                    erro != null -> {
                        Text(
                            text = erro.orEmpty(),
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    climaAtual
                        ?.recomendacoes
                        .isNullOrEmpty() -> {

                        Text(
                            "Clima estável. Nenhuma recomendação encontrada."
                        )
                    }

                    else -> {
                        climaAtual!!.recomendacoes.forEach {
                                diretriz ->

                            Text(
                                text = diretriz.categoriaProduto,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF9C6ADE)
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = diretriz.mensagemAlerta
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Resumo do clima",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "🌡 Temperatura: ${
                        climaAtual?.temperatura ?: "--"
                    }°C"
                )

                Text(
                    text = "💧 Umidade: ${
                        climaAtual?.umidade ?: "--"
                    }%"
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "GlowSync ©",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth()
        )
    }
}