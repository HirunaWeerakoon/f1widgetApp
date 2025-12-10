package com.example.f1widget.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.f1widget.R
import com.example.f1widget.model.DriverStanding
import com.example.f1widget.ui.theme.F1Red
import com.example.f1widget.utils.DriverImageUtil

@Composable
fun DriverItem(driver: DriverStanding) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .height(100.dp), // Fixed height for consistency
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // 1. The "Racing Stripe" (Visual Polish)
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(6.dp)
                    .background(F1Red)
            )

            // 2. Position Number (Big & Bold)
            Text(
                text = driver.position,
                style = MaterialTheme.typography.displaySmall, // BIG font
                fontWeight = FontWeight.Black,
                fontStyle = FontStyle.Italic, // Fast feeling
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .width(40.dp) // Fixed width so names align nicely
            )

            // 3. Driver Image (The new Feature)
            // Inside DriverItem...

            AsyncImage(
                // Now calling getDriverImage which returns an Int (e.g., R.drawable.driver_verstappen)
                model = DriverImageUtil.getDriverImage(driver.driverInfo.driverId),
                contentDescription = "Driver Image",
                modifier = Modifier
                    .size(80.dp)
                    .padding(start = 8.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,

                // Add a placeholder while loading (optional since local images are instant)
                // placeholder = painterResource(R.drawable.driver_unknown),

                // Fallback if the image fails (though local images shouldn't fail)
                error = painterResource(R.drawable.driver_unkown)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // 4. Name and Team
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = driver.driverInfo.givenName,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray
                )
                Text(
                    text = driver.driverInfo.familyName.uppercase(), // F1 uses UPPERCASE names
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = driver.constructors.firstOrNull()?.name ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = F1Red // Team name in Red
                )
            }

            // 5. Points
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Text(
                    text = "PTS",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
                Text(
                    text = driver.points,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}