package com.example.fornow

import kotlinx.coroutines.flow.Flow
import java.util.*

class TaskRepository (private val taskDAO: TaskDAO) {

    val todoTasks : Flow<List<Task>> = taskDAO.getSortedTasks()

    val finishedTasks :  Flow<List<Task>> = taskDAO.getFinishedTasks()

    suspend fun insert(task: Task) {taskDAO.insert(task)}

    suspend fun delete(id: Int) {taskDAO.delete(id)}

    suspend fun updateTaskInfo(name: String, description: String, deadline: Long, timeNeeded: Int, importance: Int, id: Int) {
        taskDAO.updateTaskInfo(name, description, deadline, timeNeeded, importance, id)
    }

    suspend fun updateTaskStatus(done: Boolean, id: Int) {
        taskDAO.updateTaskStatus(done, id)
    }

    suspend fun updatePriority(priority: Int, id: Int) {
        taskDAO.updatePriority(priority, id)
    }

    suspend fun getTask(id: Int) : Task{
        return taskDAO.getTask(id)
    }
}