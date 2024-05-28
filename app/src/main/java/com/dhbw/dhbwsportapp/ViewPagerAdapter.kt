package com.dhbw.dhbwsportapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter (manager: FragmentManager) : FragmentPagerAdapter(manager) {

    override fun getCount(): Int {
        return  4 //Anzahl Fragmente
    }

    override fun getItem(position: Int): Fragment {
        return  when (position){
            0 -> HomeFragment()
            1 -> TrainingFragment()
            2 -> CommmunityFragment()
            3 -> ProfileFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}