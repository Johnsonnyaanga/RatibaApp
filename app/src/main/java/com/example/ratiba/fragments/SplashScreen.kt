package com.example.ratiba.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.ratiba.R


class SplashScreen : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        


          Handler().postDelayed({
              if (onBoardingFinished()){
                  findNavController().navigate(R.id.action_splashScreen_to_home2)
              }else{
                  findNavController().navigate(R.id.action_splashScreen_to_viewPager)

              }

          }, 2000)
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    fun onBoardingFinished():Boolean{
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("finished",false)

    }


}