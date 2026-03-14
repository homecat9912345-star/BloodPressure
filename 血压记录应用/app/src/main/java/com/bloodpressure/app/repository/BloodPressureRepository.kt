package com.bloodpressure.app.repository

import com.bloodpressure.app.data.AverageValues
import com.bloodpressure.app.data.BloodPressureDao
import com.bloodpressure.app.data.BloodPressureRecord
import kotlinx.coroutines.flow.Flow

class BloodPressureRepository(private val dao: BloodPressureDao) {
    
    fun getAllRecords(): Flow<List<BloodPressureRecord>> = dao.getAllRecords()
    
    fun getAllRecordsOrdered(): Flow<List<BloodPressureRecord>> = dao.getAllRecordsOrdered()
    
    suspend fun getRecentRecords(limit: Int = 10): List<BloodPressureRecord> = dao.getRecentRecords(limit)
    
    suspend fun getRecordById(id: Long): BloodPressureRecord? = dao.getRecordById(id)
    
    suspend fun getRecordsByDateRange(startTime: Long, endTime: Long): List<BloodPressureRecord> =
        dao.getRecordsByDateRange(startTime, endTime)
    
    suspend fun getAverageValues(): AverageValues? = dao.getAverageValues()
    
    suspend fun insert(record: BloodPressureRecord): Long = dao.insert(record)
    
    suspend fun update(record: BloodPressureRecord) = dao.update(record)
    
    suspend fun delete(record: BloodPressureRecord) = dao.delete(record)
    
    suspend fun deleteAll() = dao.deleteAll()
}
