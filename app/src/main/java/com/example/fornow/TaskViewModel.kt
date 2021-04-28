package com.example.fornow

import androidx.lifecycle.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.util.*

class TaskViewModel (private val repository: TaskRepository) : ViewModel(){

    val todoTasks : LiveData<List<Task>> = repository.todoTasks.asLiveData()

    val finishedTasks :  LiveData<List<Task>> = repository.finishedTasks.asLiveData()

    fun insert(task: Task) = viewModelScope.launch { repository.insert(task) }

    fun delete(id: Int) = viewModelScope.launch { repository.delete(id) }

    fun updateTaskInfo(name: String, description: String, deadline: Date, timeNeeded: Int, importance: String, id: Int)
        = viewModelScope.launch { repository.updateTaskInfo(name, description, deadline, timeNeeded, importance, id) }

    fun updateTaskStatus(status: Int, id: Int) = viewModelScope.launch { repository.updateTaskStatus(status, id) }

    fun updatePriority(priority: Int, id: Int) = viewModelScope.launch { repository.updatePriority(priority, id) }

    fun getTask(id: Int):LiveData<Task> = repository.getTask(id).asLiveData()
}

class TaskViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory{

    // override the create method to return the TaskViewModel

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)){
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }

}