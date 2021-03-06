package com.example.ratiba.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.ratiba.R
import com.example.ratiba.onBoarding.screens.FirstScreen
import com.example.ratiba.onBoarding.screens.SecondScreen
import com.example.ratiba.onBoarding.screens.ThirdScreen

class ViewPager : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)


  val fragmentList = arrayListOf<Fragment>(
      FirstScreen(),
      SecondScreen(),
      ThirdScreen()
  )
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

      val viewPAger = view.findViewById<ViewPager2>(R.id.view_pager)
        viewPAger.adapter = adapter


        return view
    }


    }
