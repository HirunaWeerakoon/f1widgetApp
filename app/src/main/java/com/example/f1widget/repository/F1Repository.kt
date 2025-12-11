package com.example.f1widget.repository

import com.example.f1widget.data.F1Dao
import com.example.f1widget.model.DriverStanding
import com.example.f1widget.network.RetrofitInstance

class F1Repository(private val dao: F1Dao) {

    // The Logic: Network First -> Fallback to Database
    suspend fun getDrivers(): List<DriverStanding> {
        try {
            // 1. Try to fetch from the Internet
            val response = RetrofitInstance.api.getDriverStandings()
            val drivers = response.mrData.standingsTable.standingsLists.firstOrNull()?.driverStandings ?: emptyList()

            // 2. If successful, SAVE to the database (Cache it!)
            if (drivers.isNotEmpty()) {
                dao.insertDrivers(drivers)
            }

            // 3. Return the fresh data
            return drivers

        } catch (e: Exception) {
            // 4. If Internet fails (Exception), check the Database
            val cachedDrivers = dao.getAllDrivers()

            if (cachedDrivers.isNotEmpty()) {
                return cachedDrivers // Success! We survived offline.
            } else {
                // 5. If Database is ALSO empty, re-throw the error (Real failure)
                throw e
            }
        }
    }
}