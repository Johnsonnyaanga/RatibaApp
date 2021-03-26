package com.example.ratiba.onBoarding.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.ratiba.R




class ThirdScreen : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_third_screen, container, false)
        val finish_text = v.findViewById<TextView>(R.id.finish)
        finish_text.setOnClickListener(View.OnClickListener {
            view ->
            findNavController().navigate(R.id.action_viewPager_to_home2)
            onBoardingFinished()
        })



        return v
    }

    fun onBoardingFinished(){
        val sharedPref = requireActivity().getSharedPreferences("onBoarding",Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("finished",true)
        editor.apply()
    }


    }
