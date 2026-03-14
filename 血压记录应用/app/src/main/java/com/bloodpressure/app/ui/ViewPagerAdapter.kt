package com.bloodpressure.app.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.bloodpressure.app.ui.HistoryListFragment
import com.bloodpressure.app.ui.HistoryChartFragment

class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    
    private val fragments = mutableListOf<Fragment>()
    private val titles = mutableListOf<String>()
    
    override fun getItem(position: Int): Fragment = fragments[position]
    
    override fun getCount(): Int = fragments.size
    
    override fun getPageTitle(position: Int): CharSequence? = titles[position]
    
    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        titles.add(title)
    }
}
