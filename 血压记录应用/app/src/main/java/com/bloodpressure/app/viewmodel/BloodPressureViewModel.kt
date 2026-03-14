package com.bloodpressure.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bloodpressure.app.data.AverageValues
import com.bloodpressure.app.data.BloodPressureRecord
import com.bloodpressure.app.repository.BloodPressureRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class BloodPressureViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: BloodPressureRepository
    
    val allRecords: LiveData<List<BloodPressureRecord>>
    val allRecordsOrdered: LiveData<List<BloodPressureRecord>>
    
    init {
        val dao = com.bloodpressure.app.data.BloodPressureDatabase.getDatabase(application).bloodPressureDao()
        repository = BloodPressureRepository(dao)
        allRecords = dao.getAllRecords()
        allRecordsOrdered = dao.getAllRecordsOrdered()
    }
    
    fun getRecentRecords(limit: Int = 10) = viewModelScope.launch(Dispatchers.IO) {
        repository.getRecentRecords(limit)
    }
    
    fun getAverageValues() = viewModelScope.launch(Dispatchers.IO) {
        repository.getAverageValues()
    }
    
    fun insert(record: BloodPressureRecord) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(record)
    }
    
    fun update(record: BloodPressureRecord) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(record)
    }
    
    fun delete(record: BloodPressureRecord) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(record)
    }
    
    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }
    
    fun getRecordById(id: Long) = viewModelScope.launch(Dispatchers.IO) {
        repository.getRecordById(id)
    }
    
    fun getRecordsByDateRange(startTime: Long, endTime: Long) = viewModelScope.launch(Dispatchers.IO) {
        repository.getRecordsByDateRange(startTime, endTime)
    }
}
