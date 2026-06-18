package com.example.glowsync.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.glowsync.model.DiretrizClima
import com.example.glowsync.repository.DiretrizesRepository

@Composable
fun HomeScreen(repository: DiretrizesRepository = remember { DiretrizesRepository() }) {
    var loading by remember { mutableStateOf(true) }
    var diretriz by remember {
        mutableStateOf(DiretrizClima("Protetor solar", "Não esqueça do protetor solar hoje."))
    }
    var usandoFallback by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        repository.buscarRecomendacao()
            .onSuccess { diretriz = it }
            .onFailure { usandoFallback = true }
        loading = false
    }

    Column(
        Modifier.fillMaxSize().background(Color(0xFFFFF8FB)).padding(20.dp)
    ) {
        Text("GlowSync ✨", style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold, color = Color(0xFF7E57A8))
        Text("Seu cuidado, no ritmo do dia", color = Color(0xFF6F6673))
        Spacer(Modifier.height(24.dp))

        Card(
            Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5E7F2))
        ) {
            Column(Modifier.padding(20.dp)) {
                Text("Recomendação do dia", style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))
                if (loading) {
                    CircularProgressIndicator()
                } else {
                    Text(diretriz.categoriaProduto.uppercase(), color = Color(0xFF7E57A8),
                        fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(6.dp))
                    Text(diretriz.mensagemAlerta)
                    if (usandoFallback) {
                        Spacer(Modifier.height(8.dp))
                        Text("Recomendação offline", style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray)
                    }
                }
            }
        }

        Spacer(Modifier.height(18.dp))
        Text("Ambiente agora", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            SensorCard("26 °C", "Temperatura", Modifier.weight(1f))
            SensorCard("45%", "Umidade", Modifier.weight(1f))
            SensorCard("Alto", "Índice UV", Modifier.weight(1f))
        }
    }
}

@Composable
private fun SensorCard(value: String, label: String, modifier: Modifier = Modifier) {
    Card(modifier, shape = RoundedCornerShape(16.dp)) {
        Column(Modifier.padding(14.dp)) {
            Text(value, fontWeight = FontWeight.Bold)
            Text(label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        }
    }
}
