package com.example.ratiba.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.ratiba.TaskRepository
import com.example.ratiba.models.Cartegories
import com.example.ratiba.models.Task
import com.example.ratiba.room.TaskDatabase
import com.google.android.play.core.tasks.Tasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application):AndroidViewModel(application) {
    val readAllTasks:LiveData<List<Task>>
    val readAllCategories:LiveData<List<Cartegories>>
    val readAllCategorynames: LiveData<List<String>>

    private val repository:TaskRepository
    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        readAllTasks = repository.readAllTasks
        readAllCategories = repository.readAllCategorys
        readAllCategorynames = repository.readAllCategorynames
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

    lateinit var cartCount:LiveData<Int>
    fun getCartCount(cart:String) =
        viewModelScope.launch {
            cartCount = repository.getcartCount(cart)

        }


    fun updateCartCount(count:Int,name:String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCartCount(count,name)

        }
    }

    fun retrieveCartegoryTasks(cartname:String):LiveData<List<Task>>{
           val mss =  repository.retrieveCartegoryTasks(cartname)
           return mss
    }

    fun getDateCount(date:String):LiveData<Int>{
        val liveint = repository.getdateCount(date)
        return liveint
    }

    fun deleteAllTasks(){
        viewModelScope.launch(Dispatchers.IO)  {
            repository.deleteAlltasks()
        }
    }

    fun deleteAllCartegories(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllcartegories()
        }
    }







    }


