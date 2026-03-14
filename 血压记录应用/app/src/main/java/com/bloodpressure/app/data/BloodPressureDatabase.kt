package com.bloodpressure.app.data

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory

@Database(
    entities = [BloodPressureRecord::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class BloodPressureDatabase : RoomDatabase() {
    
    abstract fun bloodPressureDao(): BloodPressureDao
    
    companion object {
        @Volatile
        private var INSTANCE: BloodPressureDatabase? = null
        
        fun getDatabase(context: Context): BloodPressureDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BloodPressureDatabase::class.java,
                    "blood_pressure_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return value?.joinToString(",")
    }
    
    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return value?.split(",")?.filter { it.isNotEmpty() }
    }
}
