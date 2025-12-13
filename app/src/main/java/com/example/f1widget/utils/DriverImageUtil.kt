package com.example.f1widget.utils

import com.example.f1widget.R // Make sure to import your R file

object DriverImageUtil {
    // Returns an Int (Drawable Resource ID) instead of a String
    fun getDriverImage(driverId: String): Int {
        return when (driverId) {
            "max_verstappen" -> R.drawable.driver_verstappen
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
            "alonso"->R.drawable.driver_alonso
            "tsunoda"->R.drawable.driver_tsunoda
            "bortoleto"->R.drawable.driver_bortoleto
            "stroll"->R.drawable.driver_stroll
            "lawson"->R.drawable.driver_lawson
            "colapinto"->R.drawable.driver_colapinto
            "ocon"->R.drawable.driver_ocon
            "russell"->R.drawable.driver_russell
            "hulkenberg"->R.drawable.driver_nico
            "hadjar"->R.drawable.driver_hadjar
            "doohan"->R.drawable.driver_doohan




            // Add the rest of the grid here...

            else -> R.drawable.driver_unkown // The fallback silhouette
        }
    }
}