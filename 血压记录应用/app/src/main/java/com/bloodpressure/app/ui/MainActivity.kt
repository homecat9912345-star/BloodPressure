package com.bloodpressure.app.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bloodpressure.app.R
import com.bloodpressure.app.data.BloodPressureRecord
import com.bloodpressure.app.databinding.ActivityMainBinding
import com.bloodpressure.app.viewmodel.BloodPressureViewModel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: BloodPressureViewModel
    private lateinit var adapter: RecordAdapter
    
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupRecyclerView()
        setupViewModel()
        setupListeners()
        updateLastRecord()
    }
    
    private fun setupRecyclerView() {
        adapter = RecordAdapter(emptyList()) { record ->
            val intent = Intent(this, AddRecordActivity::class.java)
            startActivity(intent)
        }
        
        binding.rvRecords?.layoutManager = LinearLayoutManager(this)
        binding.rvRecords?.adapter = adapter
    }
    
    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(BloodPressureViewModel::class.java)
        
        viewModel.allRecords.observe(this) { records ->
            if (records.isNotEmpty()) {
                adapter.submitList(records)
                updateLastRecord()
            }
        }
    }
    
    private fun setupListeners() {
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddRecordActivity::class.java)
            startActivity(intent)
        }
        
        binding.btnAddRecord.setOnClickListener {
            val intent = Intent(this, AddRecordActivity::class.java)
            startActivity(intent)
        }
        
        binding.btnHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }
    
    private fun updateLastRecord() {
        viewModel.allRecords.observe(this) { records ->
            if (records.isNotEmpty()) {
                val lastRecord = records[0]
                binding.tvLastRecord.text = "${lastRecord.systolic}/${lastRecord.diastolic} mmHg"
                binding.tvLastRecordTime.text = "测量时间: ${dateFormat.format(Date(lastRecord.timestamp))}"
                
                val level = com.bloodpressure.app.data.BloodPressureLevel.fromValues(
                    lastRecord.systolic,
                    lastRecord.diastolic
                )
                binding.tvLastRecord.setTextColor(getColor(level.color))
            } else {
                binding.tvLastRecord.text = "暂无记录"
                binding.tvLastRecord.setTextColor(getColor(android.R.color.darker_gray))
            }
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                val intent = Intent(this, AddRecordActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_history -> {
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
