package com.example.f1widget.glance

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.f1widget.data.F1Database
import com.example.f1widget.model.DriverStanding
import com.example.f1widget.repository.F1Repository
import com.example.f1widget.ui.theme.F1Red
import com.example.f1widget.utils.DriverImageUtil

class F1Widget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        // 1. SETUP THE DATA CONNECTION
        // We can't inject ViewModel here, so we manually grab the Repository
        val database = F1Database.getDatabase(context)
        val dao = database.f1Dao()
        val repository = F1Repository(dao)

        var top3Drivers: List<DriverStanding> = emptyList()

        try {
            // 2. FETCH DATA
            // We use the repository logic (Try Network -> Fallback to DB)
            val allDrivers = repository.getDrivers()
            top3Drivers = allDrivers.take(3) // Only want the podium sitters
        } catch (e: Exception) {
            // If everything fails (No Net + No DB), list stays empty
        }

        // 3. RENDER UI
        provideContent {
            F1WidgetContent(top3Drivers)
        }
    }

    @Composable
    private fun F1WidgetContent(drivers: List<DriverStanding>) {
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(12.dp)
        ) {
            // HEADER
            Row(verticalAlignment = Alignment.Vertical.CenterVertically) {
                // Little Red Bar
                Box(modifier = GlanceModifier.width(4.dp).height(16.dp).background(F1Red)) {}
                Spacer(modifier = GlanceModifier.width(8.dp))
                Text(
                    text = "F1 TOP 3",
                    style = TextStyle(
                        color = ColorProvider(Color.White),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
            }

            Spacer(modifier = GlanceModifier.height(12.dp))

            if (drivers.isEmpty()) {
                // Loading or Error State
                Text(
                    text = "Loading data...",
                    style = TextStyle(color = ColorProvider(Color.Gray))
                )
            } else {
                // DATA ROWS
                drivers.forEach { driver ->
                    WidgetDriverRow(driver)
                    Spacer(modifier = GlanceModifier.height(8.dp))
                }
            }
        }
    }

    @Composable
    private fun WidgetDriverRow(driver: DriverStanding) {
        Row(
            modifier = GlanceModifier
                .fillMaxWidth()
                .background(Color(0xFF1E1E1E)) // Dark Gray Card
                .cornerRadius(8.dp)
                .padding(8.dp),
            verticalAlignment = Alignment.Vertical.CenterVertically
        ) {
            // Position
            Text(
                text = "#${driver.position}",
                style = TextStyle(
                    color = ColorProvider(F1Red),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            )

            Spacer(modifier = GlanceModifier.width(12.dp))

            // Face Image (Using your Local Drawables!)
            Image(
                provider = ImageProvider(DriverImageUtil.getDriverImage(driver.driverInfo.driverId)),
                contentDescription = "Driver",
                modifier = GlanceModifier.size(32.dp)
            )

            Spacer(modifier = GlanceModifier.width(8.dp))

            // Name
            Column(modifier = GlanceModifier.defaultWeight()) {
                Text(
                    text = driver.driverInfo.familyName.uppercase(),
                    style = TextStyle(
                        color = ColorProvider(Color.White),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                )
                Text(
                    text = driver.constructors.firstOrNull()?.name ?: "",
                    style = TextStyle(
                        color = ColorProvider(Color.LightGray),
                        fontSize = 10.sp
                    )
                )
            }

            // Points
            Text(
                text = "${driver.points} pts",
                style = TextStyle(
                    color = ColorProvider(Color.White),
                    fontSize = 12.sp
                )
            )
        }
    }

    // Helper helper function since Glance doesn't have Box composable in all versions
    @Composable
    fun Box(modifier: GlanceModifier, content: @Composable () -> Unit) {
        Column(modifier = modifier) { content() }
    }
}