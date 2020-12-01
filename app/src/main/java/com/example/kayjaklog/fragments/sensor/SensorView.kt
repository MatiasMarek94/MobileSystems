package com.example.kayjaklog.fragments.sensor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kayjaklog.R
import com.example.kayjaklog.data.Coordinate
import com.example.kayjaklog.data.CoordinateViewModel
import com.example.kayjaklog.location.ILocationChangeObserver
import com.example.kayjaklog.location.LocationChangeWrapperSingleton
import com.example.kayjaklog.location.LocationSensorEvent
import com.google.android.gms.location.LocationServices

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SensorView.newInstance] factory method to
 * create an instance of this fragment.
 */
class SensorView : Fragment(), ILocationChangeObserver {


    private lateinit var mCoordinateViewModel: CoordinateViewModel
    var locationChangeWrapper = LocationChangeWrapperSingleton.getInstance()




    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.fragment_sensor_view, container, false)
        mCoordinateViewModel = ViewModelProvider(this).get(CoordinateViewModel::class.java)
        locationChangeWrapper.addObserver(this)
        locationChangeWrapper.requestNewLocation()
        val string = mCoordinateViewModel.getCoordinateByTime()
        return view
    }

    private fun insertDataToDatabase(coordinate: Coordinate){
        println("InsertDataToDatabase $coordinate")
        mCoordinateViewModel.addCoordinate(coordinate)
    }

    private fun deleteData(){
        mCoordinateViewModel.deleteAllData()
    }

    private fun getCoordinateHistory() {
        mCoordinateViewModel.readAllData();
    }

    override fun onLocationChange(event: LocationSensorEvent) {
        if(event.timestamp!=null && event.lat!=null && event.lng !=null) {
            val coordinate = Coordinate(id = 0, time = event.timestamp, latitude = event.lat, longitude = event.lng)
            insertDataToDatabase(coordinate)
        }
    }





}