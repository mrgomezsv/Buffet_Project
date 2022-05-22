package com.buffetapp.pro.AdapterFragment

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.buffetapp.pro.Fragment.About
import com.buffetapp.pro.Fragment.Menus
import com.buffetapp.pro.Fragment.Snack

internal class MyAdapterFragmentHome (var context: Context, fm:FragmentManager, var totalTabs: Int): FragmentPagerAdapter(fm){


    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                Menus()
            }
            1 -> {
                Snack()
            }
            2 -> {
                About()
            }
            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}