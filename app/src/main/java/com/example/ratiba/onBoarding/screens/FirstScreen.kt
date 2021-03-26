package com.example.ratiba.onBoarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.ratiba.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_view_pager.*

class FirstScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_first_screen, container, false)

        val Viewpager = activity?.findViewById<ViewPager2>(R.id.view_pager)
        val next = v.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        next.setOnClickListener(View.OnClickListener {
            view ->
            Viewpager?.currentItem = 1
        })

        return v
    }

}