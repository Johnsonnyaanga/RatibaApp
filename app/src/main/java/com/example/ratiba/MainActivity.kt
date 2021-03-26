package com.example.ratiba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.view.SupportActionModeWrapper
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)

        setupActionBarWithNavController(findNavController(R.id.fragment))



    }

    override fun onSupportNavigateUp(): Boolean {
        val naController = findNavController(R.id.fragment)
        return naController.navigateUp() || super.onSupportNavigateUp()

    }

   override fun onCreateOptionsMenu(menu: Menu?): Boolean {



        menuInflater.inflate(R.menu.menu_item,menu)
        return super.onCreateOptionsMenu(menu)
    }

    // actions on click menu items
    override fun onOptionsItemSelected(item: MenuItem?):Boolean {

      val navController = findNavController(R.id.fragment)
    return item!!.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
        when (item?.itemId) {
            R.id.action_settings -> {

               return true
            }
            R.id.action_cartegories -> {
              return  true
            }
            R.id.action_about_dev -> {

                return  true

            }
            R.id.action_feedback -> {
                return  true


            }
            R.id.action_remove_ads -> {
                return  true

            }

            else -> super.onOptionsItemSelected(item)

        }
return true
    }
}