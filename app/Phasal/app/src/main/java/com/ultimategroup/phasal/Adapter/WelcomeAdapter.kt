package com.ultimategroup.phasal.Adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ultimategroup.phasal.Fragments.diseaseFragment
import com.ultimategroup.phasal.Fragments.phasalFragment
import com.ultimategroup.phasal.Fragments.weatherFragment

class WelcomeAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                //  val homeFragment: HomeFragment = HomeFragment()
                return weatherFragment()
            }
            1 -> {
                return diseaseFragment()
            }
            2 -> {
                return phasalFragment()
            }
            else -> return weatherFragment()
        }
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }
}