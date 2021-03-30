package com.example.ratiba

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ratiba.models.Cartegories
import com.example.ratiba.models.Task
import com.example.ratiba.room.TaskDao

class TaskRepository(private val taskDao:TaskDao) {
    val readAllTasks:LiveData<List<Task>> = taskDao.reterieveAllTasks()
    val readAllCategorys:LiveData<List<Cartegories>> = taskDao.reterieveAllCartegories()
    val readAllCategorynames: LiveData<List<String>> = taskDao.reterieveAllCartegoryNames()


    suspend fun addTask(task:Task){
        taskDao.addTask(task)
    }

    suspend fun updateTask(task: Task){
        taskDao.updateTask(task)
    }
    suspend fun addCartegories(cartegories: Cartegories){
        taskDao.addCartegory(cartegories)
    }

    suspend fun updateCartegories(cartegories: Cartegories){
        taskDao.updateCartegory(cartegories)

    }
     fun getcartCount(cart:String):Int{
        val m:Int = taskDao.getCartCount(cart)
        return m
    }
    fun updateCartCount(count:Int,name:String){
        taskDao.updateCartCount(count,name)
    }

    fun retrieveCartegoryTasks(cartid:Int){
        taskDao.reterieveAllCartegoryTasks(cartid)
    }




}