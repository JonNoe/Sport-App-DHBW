package com.dhbw.dhbwsportapp

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var frameContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContentView(R.layout.activity_main)


        //frag container
        frameContainer = findViewById(R.id.frame_container)

        //Setup -Wischen Navigation
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val wischenadapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = wischenadapter

        //Bottom Navigation
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.bottom_home -> {
                    viewPager.setCurrentItem(0, false)
                    true
                }
                R.id.bottom_training -> {
                    viewPager.setCurrentItem(1, false)
                    true
                }
                R.id.bottom_community -> {
                    viewPager.setCurrentItem(2, false)
                    true
                }
                R.id.bottom_profile -> {
                    viewPager.setCurrentItem(3, false)
                    true
                }
                else -> false
            }

        }


        //synch both navigation
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}

            override fun onPageSelected(position: Int) {
                // Update selected item in BottomNavigationView when ViewPager page changes
                bottomNavigationView.selectedItemId = when(position) {
                    0 -> R.id.bottom_home
                    1 -> R.id.bottom_training
                    2 -> R.id.bottom_community
                    3 -> R.id.bottom_profile
                    else -> throw IllegalArgumentException("Invalid position")
                }
            }
        })
    }
}
