package com.example.fornow

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface TaskDAO {

    @Query("SELECT * FROM task_table WHERE not done ORDER BY deadline ASC, importance DESC, timeNeeded ASC")
    fun getSortedTasks() : Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE done ORDER BY id DESC")
    fun getFinishedTasks() : Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Query("DELETE FROM task_table WHERE id=:id")
    suspend fun delete(id: Int)

    @Query("UPDATE task_table SET name=:name, description=:description, deadline=:deadline, timeNeeded=:timeNeeded, importance=:importance WHERE id=:id")
    suspend fun updateTaskInfo(name: String, description: String, deadline: Long, timeNeeded: Int, importance: Int, id: Int)

    @Query("UPDATE task_table SET done=:done where id=:id")
    suspend fun updateTaskStatus(done: Boolean, id: Int)

    @Query("UPDATE task_table SET priority=:priority where id=:id")
    suspend fun updatePriority(priority: Int, id: Int)

    @Query ("SELECT * FROM task_table WHERE id=:id")
    suspend fun getTask(id: Int): Task
}