package com.example.kayjaklog.fragments.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.kayjaklog.R
import kotlinx.android.synthetic.main.fragment_start.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_start, container, false)
        view.floatingActionButton2.setOnClickListener {
            Toast.makeText(requireContext(), "OnClickListener", Toast.LENGTH_LONG).show()

            findNavController().navigate(R.id.action_startFragment_to_sensorView)

        }
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

}