package com.dhbw.dhbwsportapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController

class Training1Fragment : Fragment(), OnItemClickListener {
    private lateinit var recyclerViewTraining1Fragment: RecyclerView
    private lateinit var dataList: ArrayList<TrainingDataClass>
    private lateinit var imageList: Array<Int>
    private lateinit var titleList: Array<String>
    private lateinit var startButton: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_training1, container, false)
        recyclerViewTraining1Fragment = view.findViewById(R.id.recycler_view_T1)
        recyclerViewTraining1Fragment.layoutManager = LinearLayoutManager(requireContext())

        imageList = arrayOf(
            R.drawable.baseline_co_present_24,
            R.drawable.ic_home_black_24dp,
            R.drawable.baseline_community_24,
            R.drawable.baseline_account_circle_24,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground

        )
        titleList = arrayOf(
            "Burpees",
            "Crunches und Hip-Thrusts",
            "High-Knee-Jumps",
            "Speed Push-Ups",
            "Ausfallsprünge",
            "Stand Sprint",
            "Squad & Side Jump",
            "Mountain Climbers",
            "Low-Walk",
            "Superman Swimmers",
            "Speed Push-Ups",
            "Toe-Touches",
            "Speed Squats",
            "Chin Shafts",
            "Side Squats",
            "Mountain Climbers"
        )

        dataList = ArrayList()
        getData()

        val adapter = TrainingAdapterClass(dataList, this)
        recyclerViewTraining1Fragment.adapter = adapter

        startButton = view.findViewById(R.id.StartButton)
        startButton.setOnClickListener {
            navigateToTrainingStartFragment(0)
        }

        return view
    }

    private fun getData(){
        for (i in imageList.indices){
            val dataClass = TrainingDataClass(imageList[i], titleList[i])
            dataList.add(dataClass)
        }
        recyclerViewTraining1Fragment.adapter = TrainingAdapterClass(dataList, this)
    }

    override fun onItemClick(position: Int){
        val explicitIntent = Intent(requireContext(), MainActivity::class.java)
        startActivity(explicitIntent)
    }

    private fun navigateToTrainingStartFragment(startIndex: Int) {
        val trainingStartFragment = TrainingStartFragment()
        val args = Bundle()
        args.putStringArrayList("exerciseTitles", ArrayList(titleList.toList()))
        args.putInt("currentExerciseIndex", startIndex)
        trainingStartFragment.arguments = args

        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_trainings, trainingStartFragment)
        transaction.addToBackStack(null)
        transaction.commit()
        //findNavController().navigate(R.id.trainingStartFragment)
    }


}