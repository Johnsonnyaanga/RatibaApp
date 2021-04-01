package com.example.ratiba.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ratiba.MainActivity
import com.example.ratiba.R
import com.example.ratiba.TaskRepository
import com.example.ratiba.adapters.CartegorySpecificClassAdapter
import com.example.ratiba.adapters.TaskListAdapter
import com.example.ratiba.room.TaskDatabase
import com.example.ratiba.viewmodels.TaskViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.play.core.tasks.Tasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CartegorySpecificTasks : Fragment() {
    private lateinit var Recycler: RecyclerView
    private lateinit var mTaskViewModel: TaskViewModel
    //private lateinit var args:CartegorySpecificTasksArgs
    private  val args by navArgs<CartegorySpecificTasksArgs>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cartegory_specific_tasks, container, false)


        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        //setSupportActionBar(toolbar)
        val navHost = requireActivity().supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment

        val navController = navHost.findNavController()
        NavigationUI.setupActionBarWithNavController(activity as AppCompatActivity, navController)


        val context = activity?.applicationContext
        //requireActivity().title = args.cartTasks.cartegoryName

        (requireActivity() as AppCompatActivity).supportActionBar?.title = args.cartTasks.cartegoryName





        Recycler = view.findViewById(R.id.recycler)
        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        val adapter = CartegorySpecificClassAdapter()
        Recycler.adapter = adapter
        Recycler.layoutManager = LinearLayoutManager(context)


        val cartTasks = mTaskViewModel.retrieveCartegoryTasks(args.cartTasks.cartegoryName)

            cartTasks.observe(viewLifecycleOwner, Observer {
                    tasks ->
                adapter.setData(tasks)
            })
















        return view
    }


}