package com.example.f1widget.utils

import com.example.f1widget.R // Make sure to import your R file

object DriverImageUtil {
    // Returns an Int (Drawable Resource ID) instead of a String
    fun getDriverImage(driverId: String): Int {
        return when (driverId) {
            "verstappen" -> R.drawable.driver_verstappen
            "hamilton" -> R.drawable.driver_hamilton
            "leclerc" -> R.drawable.driver_leclerc
            "sainz" -> R.drawable.driver_sainz
            "norris" -> R.drawable.driver_norris
            "piastri" -> R.drawable.driver_piastri
            "albon"-> R.drawable.driver_albon
            "bearman" -> R.drawable.driver_bearman
            "gasly" -> R.drawable.driver_gasly
            "tsunoda"->R.drawable.driver_tsunoda
            "antonelli"->R.drawable.driver_antonelli



            // Add the rest of the grid here...

            else -> R.drawable.driver_unkown // The fallback silhouette
        }
    }
}