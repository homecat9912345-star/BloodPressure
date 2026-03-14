package com.bloodpressure.app.data

import androidx.room.*
import java.util.Date

@Entity(tableName = "blood_pressure_records")
data class BloodPressureRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val systolic: Int,
    val diastolic: Int,
    val heartRate: Int,
    val timestamp: Long,
    val note: String = ""
)

enum class BloodPressureLevel(val level: Int, val color: Int, val description: String) {
    NORMAL(1, com.google.android.material.R.color.design_default_color_primary, "正常"),
    HIGH(2, com.google.android.material.R.color.design_default_color_error, "高血压"),
    LOW(3, com.google.android.material.R.color.design_default_color_secondary, "低血压");
    
    companion object {
        fun fromValues(systolic: Int, diastolic: Int): BloodPressureLevel {
            return when {
                systolic >= 140 || diastolic >= 90 -> HIGH
                systolic < 90 || diastolic < 60 -> LOW
                else -> NORMAL
            }
        }
    }
}
