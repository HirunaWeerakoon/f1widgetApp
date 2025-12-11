package com.example.f1widget.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.f1widget.model.Converters
import com.example.f1widget.model.DriverStanding

// Add ConstructorStanding to entities array if you annotated it
@Database(entities = [DriverStanding::class], version = 1)
@TypeConverters(Converters::class) // Register the GSON converters
abstract class F1Database : RoomDatabase() {

    abstract fun f1Dao(): F1Dao

    companion object {
        @Volatile
        private var INSTANCE: F1Database? = null

        fun getDatabase(context: Context): F1Database {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    F1Database::class.java,
                    "f1_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}