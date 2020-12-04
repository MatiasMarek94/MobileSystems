package com.example.kayjaklog.fragments.sensor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kayjaklog.R
import com.example.kayjaklog.data.Coordinate
import com.example.kayjaklog.data.CoordinateViewModel
import com.example.kayjaklog.location.ILocationChangeObserver
import com.example.kayjaklog.location.LocationChangeWrapperSingleton
import com.example.kayjaklog.location.LocationSensorEvent


/**
 * A simple [Fragment] subclass.
 * Use the [SensorView.newInstance] factory method to
 * create an instance of this fragment.
 */
class SensorView : Fragment(), ILocationChangeObserver {

    private lateinit var mCoordinateViewModel: CoordinateViewModel
    private var coordinateList = emptyList<Coordinate>()
    var locationChangeWrapper = LocationChangeWrapperSingleton.getInstance()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.fragment_sensor_view, container, false)
        mCoordinateViewModel = ViewModelProvider(this).get(CoordinateViewModel::class.java)
        locationChangeWrapper.addObserver(this)
        locationChangeWrapper.requestNewLocation()

        // CoordinateViewModel
        mCoordinateViewModel.readAllData.observe(viewLifecycleOwner, Observer { coordinate ->
            setData(coordinate)
        })
        //Button listeners
        (view?.findViewById(R.id.refresh_button) as Button?)?.setOnClickListener(refreshListener)
        (view?.findViewById(R.id.removeData_button) as Button?)?.setOnClickListener(deleteListener)




        return view
    }

    private fun setData(coordinate: List<Coordinate>) {
        this.coordinateList = coordinate
    }

    private fun getOnWaterStatus(): Boolean{
        return mCoordinateViewModel.getOnWaterStatus()

    }

    private fun insertDataToDatabase(coordinate: Coordinate){
        println("InsertDataToDatabase $coordinate")
        mCoordinateViewModel.addCoordinate(coordinate)
    }

    private val deleteListener = View.OnClickListener { deleteData() }
    private fun deleteData(){
        println("deleteData")
        mCoordinateViewModel.deleteAllData()
    }


    private val refreshListener = View.OnClickListener { refreshData() }
    private fun refreshData(): List<Coordinate>{
        return this.coordinateList;
    }

    override fun onLocationChange(event: LocationSensorEvent) {
        if(event.timestamp!=null && event.lat!=null && event.lng !=null) {
            val coordinate = Coordinate(id = 0, time = event.timestamp, latitude = event.lat, longitude = event.lng)
            insertDataToDatabase(coordinate)
        }
    }


}