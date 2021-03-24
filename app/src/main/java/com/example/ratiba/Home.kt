package com.example.ratiba

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ratiba.adapters.TaskListAdapter
import com.example.ratiba.viewmodels.TaskViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class Home : Fragment() {
    private lateinit var addfloat: FloatingActionButton
    private lateinit var Recycler: RecyclerView
    private lateinit var mTaskViewModel: TaskViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     val view = inflater.inflate(R.layout.fragment_home, container, false)

        val context = activity?.applicationContext

        addfloat = view.findViewById(R.id.add_task_float)
        Recycler = view.findViewById(R.id.recycler)
        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        val adapter = TaskListAdapter()
        Recycler.adapter = adapter
        Recycler.layoutManager = LinearLayoutManager(context)
        mTaskViewModel.readAllTasks.observe(viewLifecycleOwner, Observer {
            task ->
            adapter.setData(task)
        })




        addfloat.setOnClickListener(View.OnClickListener {
                view ->
            // go to add fragment
            findNavController().navigate(R.id.action_home_to_addTask)
        })







        return view
    }



}