package com.example.ratiba

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
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
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.ratiba.fragments.CartegoriesFragment
import com.example.ratiba.room.TaskDatabase
import com.example.ratiba.viewmodels.TaskViewModel
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

    private lateinit var navController: NavController
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotification()



        val currentDateTime = LocalDateTime.now()
     


        val todaydate:String = currentDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)).toString()
        toastMessage(todaydate)
      Log.d("main",todaydate)
        val model = ViewModelProvider(this).get(TaskViewModel::class.java)
        val datect = model.getDateCount(todaydate)
            datect.observe(this@MainActivity, Observer {
                count ->
                val Count = count
                toastMessage(count.toString())
                if (Count > 0){
                    sendNotification()

                }
            })


       val cal = Calendar.getInstance()






        /*  val toolbar = findViewById<Toolbar>(R.id.toolbar)
          setSupportActionBar(toolbar)*/
        val navHost = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment

        navController = navHost.findNavController()
        /*setupActionBarWithNavController(navController)
        setupActionBarWithNavController(findNavController(R.id.fragment))*/



    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()

    }

/*   override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item,menu)
        return true
    }

    // actions on click menu items
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return item!!.onNavDestinationSelected(navController)|| super.onOptionsItemSelected(item)
    }*/

    private fun toastMessage(message:String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }
    private fun createNotification() {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){

            val name = "notificatio title"
            val descriptiont = "notfication description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,name,importance).apply {
                description = descriptiont
            }

            val notifiCAtionManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notifiCAtionManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(){
        val intent = Intent(this,MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntnent = PendingIntent.getActivity(this, 0,intent,0)
        val bitmap =  BitmapFactory.decodeResource(applicationContext.resources,R.drawable.ic_launcher_foreground)
        val bitmapLArgeIcon =  BitmapFactory.decodeResource(applicationContext.resources,R.drawable.ic_launcher_foreground)


        val builder = NotificationCompat.Builder(this,CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Title")
            .setContentText("my descriptiton")
            .setContentIntent(pendingIntnent)
            .setStyle(NotificationCompat.BigTextStyle().bigText("oka i am the big text niggas. hope you get it.cool i am extended now whatcan you do"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(this)){
            notify(notification_id,builder.build())
        }


    }
}