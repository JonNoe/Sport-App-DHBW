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

class TrainingStartFragment : Fragment() {

    private lateinit var timerTextView: TextView
    private lateinit var currentExerciseTextView: TextView
    private lateinit var exerciseCountTextView: TextView
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var prevButton: ImageButton
    private var pauseButton: ImageButton? = null
    private lateinit var nextButton: ImageButton
    private lateinit var progressBar: ProgressBar
    private var isPaused = false
    private var timeRemaining: Long = 40000
    private var currentExerciseIndex = 0
    private var totalExercises = 0
    private lateinit var exerciseTitles : List<String>
    private var isNavigating = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_training_start, container, false)

        timerTextView = view.findViewById(R.id.timerTextView)
        currentExerciseTextView = view.findViewById(R.id.currentExerciseTextView)
        exerciseCountTextView = view.findViewById(R.id.exerciseCountTextView)
        prevButton = view.findViewById(R.id.previous)
        pauseButton = view.findViewById(R.id.pause)
        nextButton = view.findViewById(R.id.next)
        progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        exerciseTitles = arguments?.getStringArrayList("exerciseTitles") ?: listOf()
        currentExerciseIndex = arguments?.getInt("currentExerciseIndex") ?: 0
        totalExercises = exerciseTitles.size

        updateExerciseCountText()

        if (exerciseTitles.isNotEmpty()){
            currentExerciseTextView.text = exerciseTitles[currentExerciseIndex]
        } else {
            currentExerciseTextView.text = "No exercises"
        }


        startCountDownTimer(timeRemaining)

        nextButton.setOnClickListener{
            if (!isNavigating) {
                isNavigating = true
                navigateToPauseFragment()
            }
        }

        pauseButton?.setOnClickListener{
            if (!isPaused){
                countDownTimer.cancel()
                isPaused = true
                pauseButton?.setImageResource(R.drawable.play)
            } else {
                startCountDownTimer(timeRemaining)
                isPaused = false
                pauseButton?.setImageResource(R.drawable.pause)
            }
        }

        prevButton = view.findViewById(R.id.previous)
        prevButton.setOnClickListener {
            navigateToPreviousFragment()
        }

        return view
    }

    private fun startCountDownTimer(timeInMillis: Long) {
        countDownTimer = object : CountDownTimer(timeInMillis,1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished
                val minutes = millisUntilFinished / 60000
                val seconds = (millisUntilFinished % 60000) / 1000
                timerTextView.text = String.format("%02d:%02d", minutes, seconds)
            }
            override fun onFinish() {
                timerTextView.text = "00:00"
                if (!isNavigating) {
                    isNavigating = true
                    navigateToPauseFragment()
                }
            }
        }
        countDownTimer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }

    private fun navigateToPauseFragment(){

        if (currentExerciseIndex < exerciseTitles.size - 1) {
            val trainingBreakFragment = TrainingBreakFragment()
            val args = Bundle()
            args.putStringArrayList("exerciseTitles", ArrayList(exerciseTitles))
            args.putInt("currentExerciseIndex", currentExerciseIndex)
            trainingBreakFragment.arguments = args

            if (isAdded && !requireActivity().supportFragmentManager.isStateSaved) {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container_trainings, trainingBreakFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        } else {
            navigateToResultFragment()
        }
    }

    private fun navigateToResultFragment(){
        if (isAdded) {
            val resultFragment = ResultFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container_trainings, resultFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun navigateToPreviousFragment(){
        if (currentExerciseIndex > 0){
            currentExerciseIndex--
            timeRemaining = 40000
            currentExerciseTextView.text = exerciseTitles[currentExerciseIndex]
            startCountDownTimer(timeRemaining)
        }
    }

    private fun updateExerciseCountText() {
        if (exerciseTitles.isNotEmpty()){
            exerciseCountTextView.text = "${currentExerciseIndex + 1}/$totalExercises"
            val progress = ((currentExerciseIndex+1)/exerciseTitles.size.toFloat())*100
            progressBar.progress = progress.toInt()
        }
        else {
            exerciseCountTextView.text = ""
            progressBar.progress = 0
        }

    }
}
