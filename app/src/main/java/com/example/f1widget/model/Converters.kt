package com.example.f1widget.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    // --- DRIVER CONVERTER ---
    @TypeConverter
    fun fromDriver(driver: Driver): String {
        return gson.toJson(driver) // Converts Object -> "{ 'name': 'Max' }"
    }

    @TypeConverter
    fun toDriver(driverString: String): Driver {
        return gson.fromJson(driverString, Driver::class.java) // Converts String -> Object
    }

    // --- CONSTRUCTOR LIST CONVERTER ---
    @TypeConverter
    fun fromConstructorList(list: List<Constructor>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toConstructorList(value: String): List<Constructor> {
        val listType = object : TypeToken<List<Constructor>>() {}.type
        return gson.fromJson(value, listType)
    }
    @TypeConverter
    fun fromConstructor(constructor: Constructor): String {
        return gson.toJson(constructor)
    }

    @TypeConverter
    fun toConstructor(constructorString: String): Constructor {
        return gson.fromJson(constructorString, Constructor::class.java)
    }
}