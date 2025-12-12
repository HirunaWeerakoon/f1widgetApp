package com.example.f1widget.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.f1widget.model.ConstructorStanding
import com.example.f1widget.model.DriverStanding

@Dao
interface F1Dao {

    // 1. Get all drivers from the DB
    @Query("SELECT * FROM driver_standings")
    suspend fun getAllDrivers(): List<DriverStanding>

    // 2. Save drivers to the DB
    // OnConflictStrategy.REPLACE means: "If driver #1 already exists, overwrite them with the new data."
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrivers(drivers: List<DriverStanding>)

    // 3. Delete everything (Optional, good for resetting)
    @Query("DELETE FROM driver_standings")
    suspend fun clearDrivers()

    @Query("SELECT * FROM constructor_standings")
    suspend fun getAllConstructors(): List<ConstructorStanding>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConstructors(teams: List<ConstructorStanding>)
}