package com.example.glowsync.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun RoutineScreen() {
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Text("Minha rotina", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text("Passos rápidos para manhã e noite")
        LazyColumn(Modifier.padding(top = 20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            item { RoutineCard("Manhã ☀", listOf("1. Limpar o rosto", "2. Aplicar sérum", "3. Hidratar", "4. Usar protetor solar")) }
            item { RoutineCard("Noite ☾", listOf("1. Remover maquiagem", "2. Limpar o rosto", "3. Aplicar tratamento", "4. Hidratar")) }
        }
    }
}

@Composable
private fun RoutineCard(title: String, steps: List<String>) {
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp)) {
        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            steps.forEach { Text(it) }
        }
    }
}
