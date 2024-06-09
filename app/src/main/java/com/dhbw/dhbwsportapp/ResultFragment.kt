package com.dhbw.dhbwsportapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import java.util.concurrent.TimeUnit

class ResultFragment : Fragment() {

    private lateinit var totalElapsedTimeTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_result, container, false)

        totalElapsedTimeTextView = view.findViewById(R.id.totalElapsedTimeTextView)

        val trainingStartTime = TrainingManager.trainingStartTime
        val trainingEndTime = System.currentTimeMillis()
        val totalElapsedTime = trainingEndTime - trainingStartTime

        // Calculate the elapsed time in hours, minutes, and seconds
        val elapsedMinutes = TimeUnit.MILLISECONDS.toMinutes(totalElapsedTime)
        val elapsedSeconds = TimeUnit.MILLISECONDS.toSeconds(totalElapsedTime) % 60

        // Display the total elapsed time
        totalElapsedTimeTextView.text = String.format("Gesamtzeit: %02d:%02d", elapsedMinutes, elapsedSeconds)


        val backButton = view.findViewById<Button>(R.id.backToMainMenuButton)
        backButton.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
        return view
    }
}
