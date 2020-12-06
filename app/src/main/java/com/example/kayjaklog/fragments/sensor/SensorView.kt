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
import com.example.kayjaklog.application.ImprovedTrip
import com.example.kayjaklog.application.StaticTrip
import com.example.kayjaklog.data.Coordinate
import com.example.kayjaklog.data.CoordinateViewModel
import com.example.kayjaklog.location.ILocationChangeObserver
import com.example.kayjaklog.location.LocationChangeWrapperSingleton
import com.example.kayjaklog.location.LocationSensorEvent
import kotlinx.android.synthetic.main.fragment_sensor_view.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [SensorView.newInstance] factory method to
 * create an instance of this fragment.
 */
class SensorView : Fragment(), ILocationChangeObserver {

    private lateinit var mCoordinateViewModel: CoordinateViewModel
    private var coordinateList = emptyList<Coordinate>()
    var locationChangeWrapper = LocationChangeWrapperSingleton.getInstance()
    var staticTrip: StaticTrip? = null;
    var improvedTrip: ImprovedTrip? = null

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

        mCoordinateViewModel = ViewModelProvider(this).get(CoordinateViewModel::class.java)

        mCoordinateViewModel.readAllData.observe(viewLifecycleOwner, Observer { coordinate ->
//            println(coordinate)
            this.coordinateList = coordinate
//            println(coordinateList)
        })
        //Button listeners
        (view?.findViewById(R.id.refresh_button) as Button?)?.setOnClickListener(refreshListener)
        (view?.findViewById(R.id.removeData_button) as Button?)?.setOnClickListener(deleteListener)
        (view?.findViewById(R.id.startButton) as Button?)?.setOnClickListener(startListener)
        return view
    }

     fun setData(coordinate: List<Coordinate>) {
        this.coordinateList = coordinate
//        println(this.coordinateList)
    }

    private fun isOnWater(coordinate: Coordinate) {
        println("is On Water")
        var onWater = mCoordinateViewModel.getOnWaterStatus(coordinate)
        requireView().findViewById<ImageView>(R.id.onWaterImage)
        val onWaterText = requireView().findViewById(R.id.onWaterText) as TextView
        val onWaterImg = requireView().findViewById(R.id.onWaterImage) as ImageView

        if (onWater){
            onWaterText.setText("You are not on Water.");
            onWaterImg.setImageResource(R.drawable.walking);
        }
        else {
            onWaterText.setText("You are on Water.");
            onWaterImg.setImageResource(R.drawable.kayak);
        }

    }

    private fun insertDataToDatabase(coordinate: Coordinate){
        println("insert into the database $coordinate")
        mCoordinateViewModel.addCoordinate(coordinate)
    }

    private val deleteListener = View.OnClickListener { deleteData() }
    private fun deleteData(){
        println("deleteData")
        mCoordinateViewModel.deleteAllData()
        this.coordinateList = emptyList<Coordinate>()
        requireView().findViewById<TextView>(R.id.recyclerview).text = "Coordinate:\n ${this.coordinateList}"
    }

    private val refreshListener = View.OnClickListener { refreshData() }
    private fun refreshData(): List<Coordinate>{
        requireView().findViewById<TextView>(R.id.recyclerview).text = "Coordinate:\n ${this.coordinateList}"
        return this.coordinateList;
    }

    private val startListener = View.OnClickListener { startStaticTrip() }
    private fun startStaticTrip() {
        mCoordinateViewModel.deleteAllData()
        staticTrip = StaticTrip(context)
        staticTrip!!.start()
        improvedTrip = ImprovedTrip(context)
        improvedTrip!!.start()
    }

    override fun onLocationChange(event: LocationSensorEvent) {
        println("onLocationChange")
        if(event.timestamp!=null && event.lat!=null && event.lng !=null) {
            val coordinate = Coordinate(id = 0, time = event.timestamp, latitude = event.lat, longitude = event.lng, tripId = -1)
            isOnWater(coordinate)
            insertDataToDatabase(coordinate)
        }
    }
}