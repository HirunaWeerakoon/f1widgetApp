package com.example.f1widget.network

import com.example.f1widget.model.F1Response
import retrofit2.http.GET

interface F1ApiService {

    // The part of the URL *after* the base URL
    // Base: https://ergast.com/api/f1/
    // Endpoint: current/driverStandings.json

    @GET("current/driverStandings.json")
    suspend fun getDriverStandings(): F1Response
}