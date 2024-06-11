package com.dhbw.dhbwsportapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class TrainingFragment : Fragment(), OnItemClickListener {

    //TrainingList
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<TrainingDataClass>
    private lateinit var imageList: Array<Int>
    private lateinit var titleList: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_training, container, false)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        imageList = arrayOf(
            R.drawable.hiit,
            R.drawable.full_body,
            R.drawable.bauchmuskel,
            R.drawable.beinmuskel,
            R.drawable.full_body,
            R.drawable.oberkoerper
        )
        titleList = arrayOf(
            "HIIT-Workout",
            "Ganzkörper-Krafttraining",
            "Core-Workout",
            "Unterkörper-Workout",
            "Fortgeschrittenes Ganzkörper-Workout",
            "Fortgeschrittenes Oberkörper-Workout"
        )


        dataList = ArrayList()
        getData()

        val adapter = TrainingAdapterClass(dataList, this)
        recyclerView.adapter = adapter

        return view
    }
    private fun getData(){
        for (i in imageList.indices){
            val dataClass = TrainingDataClass(imageList[i], titleList[i])
            dataList.add(dataClass)
        }
        recyclerView.adapter = TrainingAdapterClass(dataList, this)
    }

    override fun onItemClick(position: Int){
        val fragmentClassName: String = when (position){
            0 -> Training1Fragment::class.java.name
            1 -> TrainingFragment_2::class.java.name
            2 -> TrainingFragment_3::class.java.name
            3 -> TrainingFragment_4::class.java.name
            4 -> TrainingFragment_5::class.java.name
            5 -> TrainingFragment_6::class.java.name

        else -> throw IllegalArgumentException("Invalid positioning")
        }

        val intent = Intent(requireContext(), TrainingActivity::class.java)
        intent.putExtra("fragment_to_open", fragmentClassName)
        startActivity(intent)
    }
}