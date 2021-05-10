package com.example.fornow

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "task_table")
class Task (@ColumnInfo(name = "name") val name: String,
            @ColumnInfo(name = "description") val description: String,
            @ColumnInfo(name = "deadline") val deadline: Long,
            @ColumnInfo(name = "timeNeeded") val timeNeeded: Int,
            @ColumnInfo(name = "importance") val importance: Int,
            @ColumnInfo(name = "done") val done: Boolean,
            @ColumnInfo(name = "priority") val priority: Int,) {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int = 0
}