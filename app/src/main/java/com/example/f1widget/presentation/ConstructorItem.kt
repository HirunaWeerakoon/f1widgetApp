package com.example.f1widget.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.f1widget.model.ConstructorStanding
import com.example.f1widget.ui.theme.F1Red

@Composable
fun ConstructorItem(team: ConstructorStanding) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(80.dp), // Slightly smaller than Driver card
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Position
            Text(
                text = team.position,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Black,
                color = F1Red, // Red number for teams
                modifier = Modifier.width(50.dp)
            )

            // Team Name
            Text(
                text = team.constructorInfo.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )

            // Points
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "${team.points} PTS", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Text(text = "${team.wins} Wins", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
            }
        }
    }
}