package com.bloodpressure.app.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BloodPressureDao {
    @Insert
    suspend fun insert(record: BloodPressureRecord): Long
    
    @Update
    suspend fun update(record: BloodPressureRecord)
    
    @Delete
    suspend fun delete(record: BloodPressureRecord)
    
    @Query("SELECT * FROM blood_pressure_records ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getRecentRecords(limit: Int = 10): List<BloodPressureRecord>
    
    @Query("SELECT * FROM blood_pressure_records ORDER BY timestamp DESC")
    fun getAllRecords(): LiveData<List<BloodPressureRecord>>
    
    @Query("SELECT * FROM blood_pressure_records WHERE id = :id")
    suspend fun getRecordById(id: Long): BloodPressureRecord?
    
    @Query("SELECT * FROM blood_pressure_records WHERE timestamp >= :startTime AND timestamp <= :endTime ORDER BY timestamp DESC")
    suspend fun getRecordsByDateRange(startTime: Long, endTime: Long): List<BloodPressureRecord>
    
    @Query("SELECT AVG(systolic) as avgSystolic, AVG(diastolic) as avgDiastolic, AVG(heartRate) as avgHeartRate FROM blood_pressure_records")
    suspend fun getAverageValues(): AverageValues?
    
    @Query("SELECT * FROM blood_pressure_records ORDER BY timestamp ASC")
    fun getAllRecordsOrdered(): LiveData<List<BloodPressureRecord>>
    
    @Query("DELETE FROM blood_pressure_records")
    suspend fun deleteAll()
}

data class AverageValues(
    val avgSystolic: Double?,
    val avgDiastolic: Double?,
    val avgHeartRate: Double?
)
