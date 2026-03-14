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
import com.bloodpressure.app.databinding.FragmentHistoryListBinding
import com.bloodpressure.app.viewmodel.BloodPressureViewModel
import com.google.android.material.snackbar.Snackbar

class HistoryListFragment : Fragment() {
    
    private var _binding: FragmentHistoryListBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var adapter: HistoryRecordAdapter
    private val viewModel: BloodPressureViewModel by viewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryListBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerView()
        setupViewModel()
    }
    
    private fun setupRecyclerView() {
        adapter = HistoryRecordAdapter { record ->
            showDeleteDialog(record)
        }
        
        binding.rvRecords.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
        binding.rvRecords.adapter = adapter
    }
    
    private fun setupViewModel() {
        viewModel.allRecords.observe(viewLifecycleOwner) { records ->
            if (records.isEmpty()) {
                binding.tvEmpty.visibility = View.VISIBLE
                binding.rvRecords.visibility = View.GONE
            } else {
                binding.tvEmpty.visibility = View.GONE
                binding.rvRecords.visibility = View.VISIBLE
                adapter.submitList(records)
            }
        }
    }
    
    private fun showDeleteDialog(record: BloodPressureRecord) {
        Snackbar.make(binding.root, "确定删除这条记录？", Snackbar.LENGTH_LONG)
            .setAction("删除") {
                viewModel.delete(record)
            }
            .show()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
