package com.example.ratiba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*
import android.view.MenuItem
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.view.SupportActionModeWrapper
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.ratiba.fragments.CartegoriesFragment

class MainActivity : AppCompatActivity(){
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val navHost = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHost.findNavController()
        setupActionBarWithNavController(navController)





        setupActionBarWithNavController(findNavController(R.id.fragment))



    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()

    }

   override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item,menu)
        return true
    }

    // actions on click menu items
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return item!!.onNavDestinationSelected(navController)|| super.onOptionsItemSelected(item)
    }

    private fun toastMessage(message:String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
}