package com.example.ratiba.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.ratiba.models.Cartegories
import com.example.ratiba.models.Task

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task:Task)
    @Update
    suspend fun updateTask(task: Task)

    @Query("SELECT * FROM task_table ORDER BY id ASC ")
     fun reterieveAllTasks(): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCartegory(cartegories: Cartegories)

    @Query("SELECT * FROM cartegories_table ORDER BY cartegory_ID ASC ")
    fun reterieveAllCartegories(): LiveData<List<Cartegories>>
    @Query("SELECT * FROM cartegories_table WHERE cartegory_ID=:cartid ORDER BY cartegory_ID ASC ")
    fun reterieveAllCartegoryTasks( cartid:Int): LiveData<List<Cartegories>>
    @Update
    suspend fun updateCartegory(cartegories: Cartegories)

    @Query("SELECT COUNT(id)  FROM task_table WHERE category = :cart")
    fun getCartCount(cart:String):Int
    @Query("UPDATE cartegories_table SET cartegoryCount  = :count WHERE cartegoryName =:cartname")
    fun updateCartCount(count:Int,cartname:String)
    @Query("SELECT cartegoryName FROM cartegories_table ORDER BY cartegory_ID")
    fun reterieveAllCartegoryNames(): LiveData<List<String>>







}