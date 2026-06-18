package com.example.glowsync.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ProductsScreen() {
    val products = listOf(
        Triple("Protetor solar", "FPS 50", "Use todos os dias e reaplique."),
        Triple("Sérum antioxidante", "Vitamina C", "Aplique pela manhã, antes do hidratante."),
        Triple("Hidratante facial", "Uso diário", "Ajuda a manter a barreira da pele.")
    )
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Text("Seus produtos", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text("O essencial para uma rotina simples")
        LazyColumn(Modifier.padding(top = 20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(products) { product ->
                Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp)) {
                    Column(Modifier.padding(18.dp)) {
                        Text(product.first, style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold)
                        Text(product.second, color = MaterialTheme.colorScheme.primary)
                        Text(product.third, modifier = Modifier.padding(top = 8.dp))
                    }
                }
            }
        }
    }
}
