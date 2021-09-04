package com.example.ratiba.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ratiba.NotesRepository
import com.example.ratiba.R
import com.example.ratiba.adapters.NotesListAdapter
import com.example.ratiba.room.TaskDatabase
import com.example.ratiba.viewmodels.NotesViewModel
import com.example.ratiba.viewmodels.NotesViewmodelFactoryProvider
import com.example.ratiba.viewmodels.TaskViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class NotesFragment : Fragment(R.layout.fragment_notes) {
    private lateinit var addfloat: FloatingActionButton
    private lateinit var recycler: RecyclerView
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var notesAdapter:NotesListAdapter
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addfloat = view.findViewById(R.id.floating_add_note)
        recycler = view.findViewById(R.id.recycler_notes)
        notesAdapter = NotesListAdapter()

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_notes)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        //setSupportActionBar(toolbar)
        val navHost =
            requireActivity().supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHost.findNavController()
        NavigationUI.setupActionBarWithNavController(activity as AppCompatActivity, navController)


        val notesDao = TaskDatabase.getDatabase(requireActivity()).notesDao()
        val notesRepository =  NotesRepository(notesDao)
        val notesViewModelProviderFactory = NotesViewmodelFactoryProvider(notesRepository)
        notesViewModel = ViewModelProvider(this,notesViewModelProviderFactory).get(NotesViewModel::class.java)

        notesViewModel.allNotes.observe(this as LifecycleOwner, Observer {
            Log.d("notesthing",it.toString())
            notesAdapter.setData(it)
        })

        //initializing recyclerview
        initRecyclerView()



        addfloat.setOnClickListener { view ->
            // go to add fragment
            findNavController().navigate(R.id.action_notesFragment_to_addNotesFragment)
        }

    }

    fun initRecyclerView(){
        recycler.apply {
            adapter = notesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }



}

