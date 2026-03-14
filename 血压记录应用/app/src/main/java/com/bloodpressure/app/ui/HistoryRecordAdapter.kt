package com.bloodpressure.app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bloodpressure.app.data.BloodPressureRecord
import com.bloodpressure.app.data.BloodPressureLevel
import com.bloodpressure.app.databinding.ItemRecordBinding
import java.text.SimpleDateFormat
import java.util.*

class HistoryRecordAdapter(
    private val onItemClickListener: (BloodPressureRecord) -> Unit
) : ListAdapter<BloodPressureRecord, HistoryRecordAdapter.RecordViewHolder>(DiffCallback()) {
    
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val binding = ItemRecordBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecordViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class RecordViewHolder(private val binding: ItemRecordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(record: BloodPressureRecord) {
            binding.tvDateTime.text = dateFormat.format(Date(record.timestamp))
            binding.tvBloodPressure.text = "${record.systolic}/${record.diastolic} mmHg"
            binding.tvHeartRate.text = "心率: ${record.heartRate} bpm"
            
            if (record.note.isNotEmpty()) {
                binding.tvNote.text = "备注: ${record.note}"
                binding.tvNote.visibility = android.view.View.VISIBLE
            } else {
                binding.tvNote.visibility = android.view.View.GONE
            }
            
            val level = BloodPressureLevel.fromValues(record.systolic, record.diastolic)
            binding.tvLevel.text = level.description
            binding.tvLevel.setTextColor(itemView.context.getColor(level.color))
            
            binding.root.setOnClickListener {
                onItemClickListener(record)
            }
        }
    }
    
    class DiffCallback : DiffUtil.ItemCallback<BloodPressureRecord>() {
        override fun areItemsTheSame(oldItem: BloodPressureRecord, newItem: BloodPressureRecord): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: BloodPressureRecord, newItem: BloodPressureRecord): Boolean {
            return oldItem == newItem
        }
    }
}
