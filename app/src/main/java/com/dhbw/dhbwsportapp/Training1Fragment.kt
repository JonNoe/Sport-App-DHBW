package com.dhbw.dhbwsportapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Training1Fragment : Fragment(), OnItemClickListener {
    private lateinit var recyclerViewTraining1Fragment: RecyclerView
    private lateinit var dataList: ArrayList<TrainingDataClass>
    private lateinit var imageList: Array<Int>
    private lateinit var titleList: Array<String>
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
            R.drawable.ic_launcher_foreground
        )
        titleList = arrayOf(
            "HIT-Training",
            "Bauchmuskel-Training",
            "Dehnungen für die Pause",
            "Ganzkörperworkout",
            "Spagat-Übungen",
            "Arm-Übungen"
        )

        dataList = ArrayList()
        getData()

        val adapter = TrainingAdapterClass(dataList, this)
        recyclerViewTraining1Fragment.adapter = adapter

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


}