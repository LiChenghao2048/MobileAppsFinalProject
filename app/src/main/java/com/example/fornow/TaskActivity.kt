package com.example.fornow

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import java.util.*

class TaskActivity : AppCompatActivity() {

    private lateinit var textView_name: TextView
    private lateinit var textView_decription: TextView
    private lateinit var textView_deadline: TextView
    private lateinit var textView_time: TextView
    private lateinit var textView_importance: TextView

    private lateinit var buttonDone: Button
    private lateinit var buttonUpdate: Button

    private val taskViewModel: TaskViewModel by viewModels{
        TaskViewModelFactory((application as TaskApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_activity)

        textView_name = this.findViewById(R.id.textView_task)
        textView_decription = this.findViewById(R.id.textView_teskDescription)
        textView_deadline = this.findViewById(R.id.textView_taskDeadline)
        textView_time = this.findViewById(R.id.textView_taskTime)
        textView_importance = this.findViewById(R.id.textView_taskImportance)

        buttonDone = findViewById(R.id.button_done)
        buttonUpdate = findViewById(R.id.button_update)

        var receivedIntent: Intent = intent
        var id = receivedIntent.getStringExtra("id")?.toInt()!!

        taskViewModel.getTask(id)
        taskViewModel.returnedTask.observe(this, Observer {
            var task: Task? = taskViewModel.returnedTask.value
            if (task != null) {
                textView_name.text = task.name
                textView_decription.text = task.description
                textView_deadline.text = Date(task.deadline).toString()
                textView_time.text = (task.timeNeeded+1).toString() + " hrs"

                val importanceArray: Array<out String> = applicationContext.resources.getStringArray(R.array.importance_array)
                textView_importance.text = importanceArray.get(task.importance)
            }
        });

        buttonDone.setOnClickListener {
            taskViewModel.updateTaskStatus(true, id)
            finish()
        }

        buttonUpdate.setOnClickListener {
            val intent = Intent(this@TaskActivity, AddActivity::class.java)
            intent.putExtra("update", true)
            intent.putExtra("id", id.toString())
            startActivity(intent)
            finish()
        }
    }
}