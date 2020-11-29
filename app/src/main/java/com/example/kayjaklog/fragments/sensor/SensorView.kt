package com.example.kayjaklog.fragments.sensor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.kayjaklog.R
import com.example.kayjaklog.data.CoordinateViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SensorView.newInstance] factory method to
 * create an instance of this fragment.
 */
class SensorView : Fragment() {

    private lateinit var mCoordinateViewModel: CoordinateViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sensor_view, container, false)
        mCoordinateViewModel = ViewModelProvider(this).get(CoordinateViewModel::class.java)
        //TODO: ADD Sensor observer
        //view.add_btr.setOnClickListener { insertDataToDatabase() }
        return view
    }

    private fun insertDataToDatabase(){
        //get value from sensor
        //check if input is not null ?
        // instatiate Coordinate
        //mCoordinateViewModel.addCoordinate(coordinate)
        //add coordinate to view
    }
}