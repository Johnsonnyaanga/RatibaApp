package com.example.ratiba

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.ratiba.Receivers.AlarmReceiver
import com.example.ratiba.util.RandomIntUtil

class AlarmService(private val context:Context) {
    private val alarmManager:AlarmManager? = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?

    fun setExactAlarm(timeInmills:Long){
        setAlarm(
            timeInmills,
            getPendingIntent(
                getIntent().apply {

                }
            )
        )

    }
    fun setRepetativeAlarm(timeInmills:Long){

    }
    private fun setAlarm(timeInmills: Long,pendingIntent: PendingIntent){
        alarmManager?.let {
            if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.M){
                alarmManager.setExactAndAllowWhileIdle(

                    AlarmManager.RTC_WAKEUP,
                    timeInmills,
                    pendingIntent
                )


            }else{



            }

        }

    }


    private fun getIntent(): Intent = Intent(context,AlarmReceiver::class.java)
    private fun getPendingIntent(intent: Intent) =
        PendingIntent.getBroadcast(
            context,
            RandomIntUtil.getRandomInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )




}