package com.example.fornow

import android.app.Application

class TaskApplication : Application() {

    val database by lazy { TaskRoomDataBase.getDatabase(this) }
    val repository by lazy { TaskRepository(database.TaskDAO()) }

}