package com.dhbw.dhbwsportapp

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class TrainingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)

        val fragmentToOpen = intent.getStringExtra("fragment_to_open")

        val fragment: Fragment = when (fragmentToOpen) {
            Training1Fragment::class.java.name -> Training1Fragment()
            TrainingFragment_2::class.java.name -> TrainingFragment_2()
            TrainingFragment_3::class.java.name -> TrainingFragment_3()
            else -> throw  IllegalArgumentException("Invalid fragment class name")
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_trainings, fragment)
            .addToBackStack(null)
            .commit()


        // Laden des entsprechenden Fragments
        /*if (fragmentToOpen == "Training1Fragment") {


            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_trainings, Training1Fragment())
                .addToBackStack(null)
                .commit()
        }*/

        //back arrow
        val backArrowButton: ImageButton = findViewById(R.id.back_Arrow_Training)
        backArrowButton.setOnClickListener{
            finish()
        }
    }
}