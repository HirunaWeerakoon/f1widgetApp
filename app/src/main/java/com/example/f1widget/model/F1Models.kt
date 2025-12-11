package com.example.f1widget.model

import com.google.gson.annotations.SerializedName
import androidx.room.Entity
import androidx.room.PrimaryKey


data class Driver(
    @SerializedName("driverId") val driverId: String,
    @SerializedName("permanentNumber") val permanentNumber: String, // String is safer!
    @SerializedName("givenName") val givenName: String,
    @SerializedName("familyName") val familyName: String,
    @SerializedName("nationality") val nationality: String
)

data class Constructor(
    @SerializedName("constructorId") val constructorId: String,
    @SerializedName("name") val name: String
)

@Entity(tableName = "driver_standings") // <--- This makes it a Database Table
data class DriverStanding(

    @PrimaryKey // <--- This ensures every row is unique
    @SerializedName("position")
    val position: String,

    @SerializedName("points") val points: String,
    @SerializedName("Driver") val driverInfo: Driver,
    @SerializedName("Constructors") val constructors: List<Constructor>
)
@Entity(tableName = "constructor_standings")
data class ConstructorStanding(
    @PrimaryKey
    @SerializedName("position") val position: String,

    @SerializedName("points") val points: String,
    @SerializedName("wins") val wins: String,
    @SerializedName("Constructor") val constructorInfo: Constructor
)

// Wrappers
data class F1Response(
    @SerializedName("MRData") val mrData: MRData
)

data class MRData(
    @SerializedName("StandingsTable") val standingsTable: StandingsTable
)

data class StandingsTable(
    @SerializedName("StandingsLists") val standingsLists: List<StandingsList>
)

data class StandingsList(
    @SerializedName("season") val season: String,
    @SerializedName("DriverStandings") val driverStandings: List<DriverStanding>,
    @SerializedName("ConstructorStandings") val constructorStandings: List<ConstructorStanding>

)