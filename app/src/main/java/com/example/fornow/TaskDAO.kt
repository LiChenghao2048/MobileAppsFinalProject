package com.example.fornow

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.*

interface TaskDAO {

    @Query("SELECT * FROM task_table WHERE status < 3 ORDER BY priority ASC")
    fun getSortedTasks() : Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE status == 3 ORDER BY priority ASC")
    fun getFinishedTasks() : Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Query("DELETE FROM task_table WHERE id=:id")
    suspend fun delete(id: Int)

    @Query("UPDATE task_table SET name=:name, description=:description, deadline=:deadline, timeNeeded=:timeNeeded, importance=:importance WHERE id=:id")
    suspend fun updateTaskInfo(name: String, description: String, deadline: Date, timeNeeded: Int, importance: String, id: Int)

    @Query("UPDATE task_table SET status=:status where id=:id")
    suspend fun updateTaskStatus(status: Int, id: Int)

    @Query("UPDATE task_table SET priority=:priority where id=:id")
    suspend fun updatePriority(priority: Int, id: Int)

    @Query ("SELECT * FROM task_table WHERE id=:id")
    fun getTask(id: Int) : Flow<Task>
}