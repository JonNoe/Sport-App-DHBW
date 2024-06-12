package com.dhbw.dhbwsportapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class HomeFragment : Fragment() {
    private val PREFS_NAME = "LoginPrefs"
    private val CONSECUTIVE_DAYS = "consecutiveDays"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val consecutiveDaysTextView = view.findViewById<TextView>(R.id.consecutiveDays)
        val sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val consecutiveDays = sharedPreferences.getInt(CONSECUTIVE_DAYS, 1)

        consecutiveDaysTextView.text = "Du hast dich schon $consecutiveDays Tage in Folge eingeloggt, mach weiter so! "

        val trainingButton = view.findViewById<Button>(R.id.startTraining)
        trainingButton.setOnClickListener {
            val mainActivity = activity as MainActivity
            mainActivity.navigateToFragment(1)
        }
        return view
    }


}