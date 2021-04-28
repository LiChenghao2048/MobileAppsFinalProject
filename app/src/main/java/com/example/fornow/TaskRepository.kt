package com.example.fornow

import kotlinx.coroutines.flow.Flow
import java.util.*

class TaskRepository (private val taskDAO: TaskDAO) {

    val todoTasks : Flow<List<Task>> = taskDAO.getSortedTasks()

    val finishedTasks :  Flow<List<Task>> = taskDAO.getFinishedTasks()

    suspend fun insert(task: Task) {taskDAO.insert(task)}

    suspend fun delete(id: Int) {taskDAO.delete(id)}

    suspend fun updateTaskInfo(name: String, description: String, deadline: Date, timeNeeded: Int, importance: String, id: Int) {
        taskDAO.updateTaskInfo(name, description, deadline, timeNeeded, importance, id)
    }

    suspend fun updateTaskStatus(status: Int, id: Int) {
        taskDAO.updateTaskStatus(status, id)
    }

    suspend fun updatePriority(priority: Int, id: Int) {
        taskDAO.updatePriority(priority, id)
    }

    fun getTask(id: Int) : Flow<Task> {return taskDAO.getTask(id)}
}