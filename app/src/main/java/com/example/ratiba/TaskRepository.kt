package com.example.ratiba

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ratiba.models.Cartegories
import com.example.ratiba.models.Task
import com.example.ratiba.room.TaskDao
import com.google.android.play.core.tasks.Tasks

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
     suspend fun getcartCount(cart:String) = taskDao.getCartCount(cart)

    fun updateCartCount(count:Int,name:String){
        taskDao.updateCartCount(count,name)
    }

    fun retrieveCartegoryTasks(cartname:String):LiveData<List<Task>>{
       val carts = taskDao.reterieveAllCartegoryTasks(cartname)
        return carts
    }

    fun getdateCount(date:String):LiveData<Int>{
        val m:LiveData<Int> = taskDao.getdateCount(date)
        return m
    }

    fun deleteAlltasks(){
        taskDao.deleteTasks()
    }

    fun deleteAllcartegories(){
        taskDao.deleteCartegories()
    }




}