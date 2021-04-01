package com.example.ratiba.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ratiba.R
import com.example.ratiba.adapters.TaskListAdapter
import com.example.ratiba.viewmodels.TaskViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class Home : Fragment() {
    private lateinit var addfloat: FloatingActionButton
    private lateinit var Recycler: RecyclerView
    private lateinit var mTaskViewModel: TaskViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


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
        mTaskViewModel.readAllTasks.observe(viewLifecycleOwner, Observer { task ->
            adapter.setData(task)
        })




        addfloat.setOnClickListener(View.OnClickListener { view ->
            // go to add fragment
            findNavController().navigate(R.id.action_home_to_addTask)
        })



        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.home2
            )
        )


        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
    //setSupportActionBar(toolbar)
        val navHost = requireActivity().supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment

        navController = navHost.findNavController()
        setupActionBarWithNavController(activity as AppCompatActivity, navController,appBarConfiguration)







        return view
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item,menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item!!.onNavDestinationSelected(navController)|| super.onOptionsItemSelected(item)
    }



}