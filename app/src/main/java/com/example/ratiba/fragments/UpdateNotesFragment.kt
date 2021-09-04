package com.example.ratiba.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.ratiba.NotesRepository
import com.example.ratiba.R
import com.example.ratiba.models.Notes
import com.example.ratiba.room.TaskDatabase
import com.example.ratiba.viewmodels.NotesViewModel
import com.example.ratiba.viewmodels.NotesViewmodelFactoryProvider
import java.text.SimpleDateFormat
import java.util.*

class UpdateNotesFragment : Fragment(R.layout.fragment_update_notes) {
    private lateinit var notesViewModel: NotesViewModel
    private lateinit var notetitleEdt: EditText
    private lateinit var notedescEdt: EditText
    private lateinit var addNote: Button
    private lateinit var args: UpdateNotesFragmentArgs
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notetitleEdt = view.findViewById(R.id.note_title)
        notedescEdt = view.findViewById(R.id.note_description)
        addNote = view.findViewById(R.id.addNote)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar_updatenotes)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        //setSupportActionBar(toolbar)
        val navHost =
            requireActivity().supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHost.findNavController()
        NavigationUI.setupActionBarWithNavController(activity as AppCompatActivity, navController)

        val notesDao = TaskDatabase.getDatabase(requireActivity()).notesDao()
        val notesRepository =  NotesRepository(notesDao)
        val notesViewModelProviderFactory = NotesViewmodelFactoryProvider(notesRepository)
        notesViewModel = ViewModelProvider(this,notesViewModelProviderFactory)
            .get(NotesViewModel::class.java)
        arguments?.let {
            args = UpdateNotesFragmentArgs.fromBundle(it)
        }

        fillFields()


        Log.d("args",args.noteItem.toString())

        addNote.setOnClickListener{
            val currentDate = getcurrentDate()

            addNote(
                title = notetitleEdt.text.toString(),
                desc = notedescEdt.text.toString(),
                date = currentDate
            )
        }

    }

    fun addNote(title:String,desc:String,date:String){
        if (title.isNotEmpty()&&desc.isNotEmpty()&&date.isNotEmpty()){
            val newNote = Notes(id=args.noteItem.id,title = title,description = desc,date = date)
            notesViewModel.updateNote(newNote)
            findNavController().navigate(R.id.action_updateNotesFragment_to_notesFragment)
        }else{
            toastMessage("please fill a fields")
        }
    }

    fun fillFields(){
        notetitleEdt.setText(args.noteItem.title)
        notedescEdt.setText(args.noteItem.description)
    }

    fun toastMessage(message:String){
        Toast.makeText(requireActivity(),message, Toast.LENGTH_SHORT).show()
    }
    fun getcurrentDate():String{
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())
        Log.d("date",currentDate)
        return currentDate
    }
}