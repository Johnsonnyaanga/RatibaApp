package com.example.ratiba.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.ratiba.TaskRepository
import com.example.ratiba.models.Cartegories
import com.example.ratiba.models.Task
import com.example.ratiba.room.TaskDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application):AndroidViewModel(application) {
    val readAllTasks:LiveData<List<Task>>
    val readAllCategories:LiveData<List<Cartegories>>
    private val repository:TaskRepository
    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        readAllTasks = repository.readAllTasks
        readAllCategories = repository.readAllCategorys
    }

    fun addTask(task:Task){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(task)
        }
    }

    fun updateTask(task:Task){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(task)
        }
    }

   fun addCartegory(cartegories: Cartegories){
       viewModelScope.launch(Dispatchers.IO){
           repository.addCartegories(cartegories)
       }
   }

    fun updateCartegories(cartegories: Cartegories){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCartegories(cartegories)
        }
   }
    fun getCartCount(cart:String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getcartCount(cart)

        }

    }
    fun updateCartCount(count:Int,name:String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCartCount(count,name)

        }
    }



    }


