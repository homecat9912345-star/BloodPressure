package com.bloodpressure.app.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.bloodpressure.app.R
import com.bloodpressure.app.databinding.ActivityHistoryBinding
import com.bloodpressure.app.ui.HistoryListFragment
import com.bloodpressure.app.ui.HistoryChartFragment
import com.bloodpressure.app.ui.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class HistoryActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityHistoryBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupToolbar()
        setupViewPager()
    }
    
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
    
    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(HistoryListFragment(), "列表")
        adapter.addFragment(HistoryChartFragment(), "图表")
        
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
