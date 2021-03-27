package com.example.ratiba.room

import androidx.lifecycle.LiveData
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
    @Update
    suspend fun updateCartegory(cartegories: Cartegories)





}