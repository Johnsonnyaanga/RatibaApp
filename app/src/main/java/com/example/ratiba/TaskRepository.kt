package com.example.ratiba

import androidx.lifecycle.LiveData
import com.example.ratiba.models.Task
import com.example.ratiba.room.TaskDao

class TaskRepository(private val taskDao:TaskDao) {
    val readAllTasks:LiveData<List<Task>> = taskDao.reterieveAllTasks()

    suspend fun addTask(task:Task){
        taskDao.addTask(task)
    }

    suspend fun updateTask(task: Task){
        taskDao.updateTask(task)
    }




}