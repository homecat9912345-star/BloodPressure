package com.bloodpressure.app.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bloodpressure.app.R
import com.bloodpressure.app.data.BloodPressureRecord
import com.bloodpressure.app.databinding.FragmentHistoryChartBinding
import com.bloodpressure.app.viewmodel.BloodPressureViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class HistoryChartFragment : Fragment() {
    
    private var _binding: FragmentHistoryChartBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: BloodPressureViewModel by viewModels()
    private val dateFormat = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryChartBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupViewModel()
    }
    
    private fun setupViewModel() {
        viewModel.allRecordsOrdered.observe(viewLifecycleOwner) { records ->
            if (records.isNotEmpty()) {
                updateStatistics(records)
                setupChart(records)
            }
        }
    }
    
    private fun updateStatistics(records: List<BloodPressureRecord>) {
        val systolicSum = records.sumOf { it.systolic.toDouble() }
        val diastolicSum = records.sumOf { it.diastolic.toDouble() }
        val heartRateSum = records.sumOf { it.heartRate.toDouble() }
        val count = records.size.toDouble()
        
        val systolicAvg = (systolicSum / count).coerceIn(0.0, 999.0)
        val diastolicAvg = (diastolicSum / count).coerceIn(0.0, 999.0)
        val heartRateAvg = (heartRateSum / count).coerceIn(0.0, 999.0)
        
        binding.tvSystolicAvg.text = String.format("%.1f", systolicAvg)
        binding.tvDiastolicAvg.text = String.format("%.1f", diastolicAvg)
        binding.tvHeartRateAvg.text = String.format("%.1f", heartRateAvg)
    }
    
    private fun setupChart(records: List<BloodPressureRecord>) {
        val chart = binding.chartBloodPressure
        chart.description.isEnabled = false
        chart.legend.isEnabled = true
        chart.legend.textColor = Color.BLACK
        chart.legend.position = com.github.mikephil.charting.components.Legend.LegendPosition.BELOW_CHART_CENTER
        chart.setTouchEnabled(true)
        chart.setDragEnabled(true)
        chart.setScaleEnabled(true)
        chart.setPinchZoom(true)
        
        val systolicEntries = records.mapIndexed { index, record ->
            Entry(index.toFloat(), record.systolic.toFloat())
        }
        
        val diastolicEntries = records.mapIndexed { index, record ->
            Entry(index.toFloat(), record.diastolic.toFloat())
        }
        
        val heartRateEntries = records.mapIndexed { index, record ->
            Entry(index.toFloat(), record.heartRate.toFloat())
        }
        
        val systolicSet = LineDataSet(systolicEntries, "收缩压").apply {
            color = Color.RED
            lineWidth = 2f
            circleRadius = 4f
            setCircleColor(Color.RED)
            mode = LineDataSet.Mode.CUBIC_BEZIER
            valueTextSize = 10f
            valueTextColor = Color.RED
        }
        
        val diastolicSet = LineDataSet(diastolicEntries, "舒张压").apply {
            color = Color.BLUE
            lineWidth = 2f
            circleRadius = 4f
            setCircleColor(Color.BLUE)
            mode = LineDataSet.Mode.CUBIC_BEZIER
            valueTextSize = 10f
            valueTextColor = Color.BLUE
        }
        
        val heartRateSet = LineDataSet(heartRateEntries, "心率").apply {
            color = Color.rgb(255, 152, 0)
            lineWidth = 2f
            circleRadius = 4f
            setCircleColor(Color.rgb(255, 152, 0))
            mode = LineDataSet.Mode.CUBIC_BEZIER
            valueTextSize = 10f
            valueTextColor = Color.rgb(255, 152, 0)
        }
        
        val data = LineData(systolicSet, diastolicSet, heartRateSet)
        data.setValueFormatter(object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString()
            }
        })
        
        chart.data = data
        chart.invalidate()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
