package com.example.ratiba

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.ProgressDialog.show
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*
import android.view.MenuItem
import android.widget.MediaController
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.view.SupportActionModeWrapper
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.ratiba.adapters.NotesListAdapter
import com.example.ratiba.fragments.CartegoriesFragment
import com.example.ratiba.models.Notes
import com.example.ratiba.room.TaskDatabase
import com.example.ratiba.viewmodels.NotesViewModel
import com.example.ratiba.viewmodels.NotesViewmodelFactoryProvider
import com.example.ratiba.viewmodels.TaskViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class MainActivity : AppCompatActivity(){
    //notification constants
    private val CHANNEL_ID = "example_channel_id"
    private val notification_id = 101
    lateinit var mTaskViewModel: TaskViewModel
    lateinit var mNotesViewModel:NotesViewModel


    private lateinit var navController: NavController
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTaskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        val notesDao = TaskDatabase.getDatabase(this@MainActivity).notesDao()
        val notesRepository =  NotesRepository(notesDao)
        val notesViewModelProviderFactory = NotesViewmodelFactoryProvider(notesRepository)
        mNotesViewModel = ViewModelProvider(this@MainActivity,notesViewModelProviderFactory).get(NotesViewModel::class.java)



        val navHost = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment

        navController = navHost.findNavController()




    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()

    }

 /*   override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return true
    }*/


    fun toastMessage(message:String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    fun Snackbar(newNote:Notes) {
        Snackbar.make(parentLayout, "Note successfully deleted", Snackbar.LENGTH_LONG).apply {
          /*  setAction("Undo") {
                mNotesViewModel.addNote(newNote)
                val notesAdapter = NotesListAdapter()
                notesAdapter.noteslist.add(newNote)
            }*/
            show()
        }
    }


}