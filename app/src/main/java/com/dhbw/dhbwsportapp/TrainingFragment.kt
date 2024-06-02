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
    private lateinit var dataList: ArrayList<TraingDataClass>
    private lateinit var imageList: Array<Int>
    private lateinit var titleList: Array<String>
    private lateinit var targetActivities: Array<Class<*>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_training, container, false)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

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

        targetActivities = arrayOf(
            Training1Activity::class.java,
            Training2Activity::class.java,
            Training1Activity::class.java,
            Training1Activity::class.java,
            Training1Activity::class.java,
            Training1Activity::class.java,

            //Training3Activity::class.java,
            //Training4Activity::class.java,
            //Training5Activity::class.java,
            //Training6Activity::class.java
        )


        dataList = ArrayList()
        getData()

        val adapter = TrainingAdapterClass(dataList, this)
        recyclerView.adapter = adapter

        return view
    }
    private fun getData(){
        for (i in imageList.indices){
            val dataClass = TraingDataClass(imageList[i], titleList[i], targetActivities[i])
            dataList.add(dataClass)
        }
        recyclerView.adapter = TrainingAdapterClass(dataList, this)
    }

    override fun onItemClick(position: Int){
        val selectedItem = dataList[position]
        val explicitIntent = Intent(requireContext(), selectedItem.targetActivity)
        startActivity(explicitIntent)
    }



}