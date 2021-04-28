package com.example.fornow

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Task::class), version = 1, exportSchema = false)
public abstract class TaskRoomDataBase :RoomDatabase() { // has to be abstract
    // connected with DAO
    abstract fun TaskDAO():TaskDAO // getter

    companion object{
        // there should only be one instance of the database for the whole app

        // singleton - software design pattern
        // when you need exactly 1 object to coordinate actions across the system
        // using singleton to prevent multiple instance of database opening at the same time
        @Volatile // singleton
        private var INSTANCE:TaskRoomDataBase? = null
        // write a method to get the database

        fun getDatabase(context: Context): TaskRoomDataBase{
            // if our instance is not null
            // we can return it

            // if it is null, we want to create the database
            return INSTANCE ?: synchronized(this){
                // creating the database
                // return the database
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskRoomDataBase::class.java,
                    "task_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}