package com.bloodpressure.app.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bloodpressure.app.R
import com.bloodpressure.app.data.BloodPressureLevel
import com.bloodpressure.app.databinding.ActivityAddRecordBinding
import com.bloodpressure.app.viewmodel.BloodPressureViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddRecordActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityAddRecordBinding
    private lateinit var viewModel: BloodPressureViewModel
    
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        setupViewModel()
        setupDateTime()
        setupListeners()
        updateBloodPressureLevel()
    }
    
    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(BloodPressureViewModel::class.java)
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
    
    private fun setupDateTime() {
        val currentTime = Date()
        binding.tvCurrentDateTime.text = dateFormat.format(currentTime)
    }
    
    private fun setupListeners() {
        binding.etSystolic.addTextChangedListener {
            updateBloodPressureLevel()
        }
        
        binding.etDiastolic.addTextChangedListener {
            updateBloodPressureLevel()
        }
        
        binding.fabSave.setOnClickListener {
            saveRecord()
        }
    }
    
    private fun updateBloodPressureLevel() {
        val systolicText = binding.etSystolic.text.toString()
        val diastolicText = binding.etDiastolic.text.toString()
        
        if (systolicText.isEmpty() || diastolicText.isEmpty()) {
            binding.tvBloodPressureLevel.text = "血压水平: -"
            return
        }
        
        val systolic = systolicText.toIntOrNull()
        val diastolic = diastolicText.toIntOrNull()
        
        if (systolic == null || diastolic == null) {
            binding.tvBloodPressureLevel.text = "血压水平: -"
            return
        }
        
        val level = BloodPressureLevel.fromValues(systolic, diastolic)
        binding.tvBloodPressureLevel.text = "血压水平: ${level.description}"
        binding.tvBloodPressureLevel.setTextColor(getColor(level.color))
    }
    
    private fun saveRecord() {
        val systolicText = binding.etSystolic.text.toString()
        val diastolicText = binding.etDiastolic.text.toString()
        val heartRateText = binding.etHeartRate.text.toString()
        val note = binding.etNote.text.toString()
        
        if (systolicText.isEmpty() || diastolicText.isEmpty() || heartRateText.isEmpty()) {
            showToast("请填写完整的血压数据")
            return
        }
        
        val systolic = systolicText.toInt()
        val diastolic = diastolicText.toInt()
        val heartRate = heartRateText.toInt()
        
        val record = com.bloodpressure.app.data.BloodPressureRecord(
            systolic = systolic,
            diastolic = diastolic,
            heartRate = heartRate,
            timestamp = System.currentTimeMillis(),
            note = note
        )
        
        viewModel.insert(record)
        
        showToast("记录保存成功")
        finish()
    }
    
    private fun showToast(message: String) {
        val toast = android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT)
        toast.show()
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
