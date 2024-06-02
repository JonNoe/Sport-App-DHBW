package com.dhbw.dhbwsportapp

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity

class TrainingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)

        val fragmentToOpen = intent.getStringExtra("fragment_to_open")

        // Laden des entsprechenden Fragments
        if (fragmentToOpen == "Training1Fragment") {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_trainings, Training1Fragment())
                .addToBackStack(null)
                .commit()
        }
    }
}