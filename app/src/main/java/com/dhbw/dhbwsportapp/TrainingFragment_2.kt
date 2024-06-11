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

class TrainingFragment_2 : Fragment(), OnItemClickListener {
    private lateinit var recyclerViewTrainingFragment: RecyclerView
    private lateinit var dataList: ArrayList<Training_2_DataClass>
    private lateinit var imageList: Array<Int>
    private lateinit var titleList: Array<String>
    private lateinit var startButton: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_training_2, container, false)
        recyclerViewTrainingFragment = view.findViewById(R.id.recycler_view_T1)
        recyclerViewTrainingFragment.layoutManager = LinearLayoutManager(requireContext())

        imageList = arrayOf(
            R.drawable.push,
            R.drawable.plank,
            R.drawable.squats,
            R.drawable.lunge_jump,
            R.drawable.dips,
            R.drawable.swimmer,
            R.drawable.bicycle_crunch,
            R.drawable.bridge,
            R.drawable.wall_sit,
            R.drawable.calf_raise,
            R.drawable.push,
            R.drawable.plank,
            R.drawable.squats,
            R.drawable.lunge_jump,
            R.drawable.dips,
            R.drawable.swimmer,
            R.drawable.bicycle_crunch,
            R.drawable.bridge,
            R.drawable.wall_sit,
            R.drawable.calf_raise
            )

        titleList = arrayOf(
            "Push-Ups",
            "Planks",
            "Squats",
            "Lunges",
            "Dips an Bank",
            "Superman",
            "Bicycle Crunches",
            "Glute Bridge",
            "Wall Sit",
            "Calf Raises",
            "Push-Ups",
            "Planks",
            "Squats",
            "Lunges",
            "Dips an Bank",
            "Superman",
            "Bicycle Crunches",
            "Glute Bridge",
            "Wall Sit",
            "Calf Raises"
        )

        dataList = ArrayList()
        getData()

        val adapter = Training_2_AdapterClass(dataList, this)
        recyclerViewTrainingFragment.adapter = adapter

        startButton = view.findViewById(R.id.StartButton)
        startButton.setOnClickListener {
            navigateToTrainingStartFragment(0)
        }

        return view
    }

    private fun getData(){
        for (i in imageList.indices){
            val dataClass = Training_2_DataClass(imageList[i], titleList[i])
            dataList.add(dataClass)
        }
        recyclerViewTrainingFragment.adapter = Training_2_AdapterClass(dataList, this)
    }

    override fun onItemClick(position: Int){
        navigateToTrainingStartFragment(position)
    }

    private fun navigateToTrainingStartFragment(startIndex: Int) {
        val trainingStartFragment = TrainingFragment_2_start()
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