package com.dhbw.dhbwsportapp


import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView

class TrainingFragment_2_break : Fragment() {
    private lateinit var timerTextView: TextView
    private lateinit var countDownTimer: CountDownTimer

    private var currentExerciseIndex = 0
    private lateinit var exerciseTitles: List<String>
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private var pauseButton: ImageButton? = null
    private lateinit var progressBar: KreisProgressBarView
    private var isPaused = false
    private var timeRemaining: Long = 15000
    private var isNavigating = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_training_2_break, container, false)

        timerTextView = view.findViewById(R.id.pauseTimer)
        progressBar = view.findViewById(R.id.pauseprogressBar)
        nextButton = view.findViewById(R.id.next)
        prevButton = view.findViewById(R.id.previous)
        pauseButton = view.findViewById(R.id.pause)

        exerciseTitles = arguments?.getStringArrayList("exerciseTitles") ?: listOf()
        currentExerciseIndex = arguments?.getInt("currentExerciseIndex") ?: 0

        startTimer(timeRemaining)

        nextButton.setOnClickListener {
            if (!isNavigating) {
                isNavigating = true
                navigateToNextExercise()
            }
        }

        prevButton.setOnClickListener {
            if (!isNavigating) {
                isNavigating = true
                navigateToPreviousExercise()
            }
        }

        pauseButton?.setOnClickListener {
            if (!isPaused) {
                countDownTimer.cancel()
                isPaused = true
                pauseButton?.setImageResource(R.drawable.play)
            } else {
                startTimer(timeRemaining)
                isPaused = false
                pauseButton?.setImageResource(R.drawable.pause)
            }
        }

        return view
    }

    private fun startTimer(timeInMillis: Long) {
        countDownTimer = object : CountDownTimer(timeInMillis, 50) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished
                val minutes = millisUntilFinished / 60000
                val seconds = (millisUntilFinished % 60000) / 1000
                timerTextView.text = String.format("%02d:%02d", minutes, seconds)
                progressBar.setProgress(((millisUntilFinished.toFloat() / 20000) * 100).toInt())
            }

            override fun onFinish() {
                timerTextView.text = "00:00"
                if (!isNavigating) {
                    isNavigating = true
                    navigateToNextExercise()
                }
            }
        }
        countDownTimer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }

    private fun navigateToNextExercise() {
        val trainingStartFragment = TrainingFragment_2_start()
        val args = Bundle()
        args.putStringArrayList("exerciseTitles", ArrayList(exerciseTitles))
        args.putInt("currentExerciseIndex", currentExerciseIndex + 1)
        trainingStartFragment.arguments = args

        if (isAdded) {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container_trainings, trainingStartFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun navigateToPreviousExercise() {
        val trainingStartFragment = TrainingFragment_2_start()
        val args = Bundle()
        args.putStringArrayList("exerciseTitles", ArrayList(exerciseTitles))
        args.putInt("currentExerciseIndex", currentExerciseIndex)
        trainingStartFragment.arguments = args

        if (isAdded) {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container_trainings, trainingStartFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

}
