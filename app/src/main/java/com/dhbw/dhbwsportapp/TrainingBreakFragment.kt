package com.dhbw.dhbwsportapp

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import kotlin.concurrent.timer


class TrainingBreakFragment : Fragment() {
    private lateinit var timerTextView: TextView
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var progressBar: ProgressBar
    private var currentExerciseIndex = 0
    private lateinit var exerciseTitles: List<String>
    private lateinit var prevButton: ImageButton
    private var pauseButton: ImageButton? = null
    private lateinit var nextButton: ImageButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_training_break, container, false)


        timerTextView = view.findViewById(R.id.pauseTimer)
        progressBar = view.findViewById(R.id.pauseProgressBar)
        nextButton = view.findViewById((R.id.next))

        exerciseTitles = arguments?.getStringArrayList("exerciseTitles") ?: listOf()
        currentExerciseIndex = arguments?.getInt("currentExerciseIndex") ?: 0

        startTimer()

        nextButton.setOnClickListener{
            navigateToNextExercise()
        }

        return view

    }

    fun startTimer() {
        countDownTimer = object : CountDownTimer(20000,5) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished/600000
                val seconds = (millisUntilFinished % 60000) / 1000
                timerTextView.text = String.format("%02d:%02d", minutes, seconds)
                progressBar.progress = ((millisUntilFinished).toFloat() / 20000 * 100).toInt()
            }
            override fun onFinish() {
                timerTextView.text = "00:00"
                navigateToNextExercise()
            }
        }
        countDownTimer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }

    private fun navigateToNextExercise() {
        val trainingStartFragment = TrainingStartFragment()
        val args = Bundle()
        args.putStringArrayList("exerciseTitles", ArrayList(exerciseTitles))
        args.putInt("currentExerciseIndex", currentExerciseIndex + 1)
        trainingStartFragment.arguments = args

        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_trainings, trainingStartFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}