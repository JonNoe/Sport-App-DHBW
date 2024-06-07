package com.dhbw.dhbwsportapp

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class TrainingStartFragment : Fragment() {

    private lateinit var timerTextView: TextView
    private lateinit var currentExerciseTextView: TextView
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var prevButton: ImageButton
    private var pauseButton: ImageButton? = null
    private lateinit var nextButton: ImageButton
    private var isPaused = false
    private var timeRemaining: Long = 40000
    private var currentExerciseIndex = 0
    private lateinit var exerciseTitles : List<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_training_start, container, false)


        timerTextView = view.findViewById(R.id.timerTextView)
        currentExerciseTextView = view.findViewById(R.id.currentExerciseTextView)
        prevButton = view.findViewById((R.id.previous))
        pauseButton = view.findViewById((R.id.pause))
        nextButton = view.findViewById((R.id.next))

        exerciseTitles = arguments?.getStringArrayList("exerciseTitles") ?: listOf()
        currentExerciseIndex = arguments?.getInt("currentExerciseIndex") ?: 0

        if (exerciseTitles.isNotEmpty()){
            currentExerciseTextView.text = exerciseTitles[currentExerciseIndex]
        }else{
            currentExerciseTextView.text = "No exercises"
        }


        startCountDownTimer(timeRemaining)

        nextButton.setOnClickListener{
            navigateToPauseFragment()
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

        return view
    }

    private fun startCountDownTimer(timeInMillis: Long) {
        countDownTimer = object : CountDownTimer(timeInMillis,1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished
                val minutes = millisUntilFinished/600000
                val seconds = (millisUntilFinished % 60000) / 1000
                timerTextView.text = String.format("%02d:%02d", minutes, seconds)
            }
            override fun onFinish() {
                timerTextView.text = "00:00"
                navigateToPauseFragment()
            }
        }
        countDownTimer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }

    fun setNextExercise(){
        currentExerciseIndex++
        if (currentExerciseIndex < exerciseTitles.size){
            currentExerciseTextView.text = exerciseTitles[currentExerciseIndex]
            timeRemaining = 40000
            startCountDownTimer(timeRemaining)
        }else{
            navigateToResultFragment()
        }
    }

    private fun navigateToPauseFragment(){
        if (isAdded) {
            val trainingBreakFragment = TrainingBreakFragment()

            // Pass current exercise index to the break fragment
            val args = Bundle()
            args.putStringArrayList("exerciseTitles", ArrayList(exerciseTitles))
            args.putInt("currentExerciseIndex", currentExerciseIndex)
            trainingBreakFragment.arguments = args

            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container_trainings, trainingBreakFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun navigateToResultFragment(){
        val trainingBreakFragment = ResultFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_trainings, trainingBreakFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}

