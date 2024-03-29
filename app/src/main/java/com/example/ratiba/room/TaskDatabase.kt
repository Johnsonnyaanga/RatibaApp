package com.example.ratiba.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ratiba.models.Cartegories
import com.example.ratiba.models.Notes
import com.example.ratiba.models.Task

@Database(entities = [Task::class,Cartegories::class, Notes::class],version = 1,exportSchema = false)
abstract class TaskDatabase: RoomDatabase() {

    abstract fun taskDao():TaskDao
    abstract fun notesDao():NotesDao

    companion object{
        private var INSTANCE:TaskDatabase?=null

        fun getDatabase(context: Context):TaskDatabase{
            val tempInstance = INSTANCE
            if (tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "ratiba_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }

        }


    }





}