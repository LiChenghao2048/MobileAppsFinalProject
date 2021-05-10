package com.example.fornow

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import android.widget.CalendarView.OnDateChangeListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class AddActivity : AppCompatActivity() {

    private lateinit var editText_name : EditText
    private lateinit var editText_description : EditText
    private lateinit var calendarView_deadline: CalendarView
    private lateinit var spinner_hours : Spinner
    private lateinit var spinner_importance : Spinner
    private lateinit var button_save : Button

    var selectedDate:Long = Date().time

    //val calendar = Calendar.getInstance()
    //calendar[Calendar.DATE] = Calendar.getInstance().getActualMinimum(Calendar.DATE)

    private val taskViewModel : TaskViewModel by viewModels {
        TaskViewModelFactory((application as TaskApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        editText_name = this.findViewById(R.id.editText_name)
        editText_description = this.findViewById(R.id.editText_description)

        calendarView_deadline = this.findViewById(R.id.calendarView_deadline)
        calendarView_deadline.setOnDateChangeListener(OnDateChangeListener { view, year, month, day ->

            //show the selected date as a toast
            //Toast.makeText(applicationContext, "$day/$month/$year", Toast.LENGTH_LONG).show()
            val c = Calendar.getInstance()
            c[year, month] = day
            selectedDate = c.timeInMillis //this is what you want to use later
        })


        spinner_hours = this.findViewById(R.id.spinner_hours)
        ArrayAdapter.createFromResource(
            this,
            R.array.hour_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner_hours.adapter = adapter
        }

        spinner_importance = this.findViewById(R.id.spinner_importance)
        ArrayAdapter.createFromResource(
            this,
            R.array.importance_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner_importance.adapter = adapter
        }

        var receivedIntent: Intent = intent
        if (receivedIntent.getBooleanExtra("update", false)) {

            var id = receivedIntent.getStringExtra("id")?.toInt()!!

            taskViewModel.getTask(id)
            taskViewModel.returnedTask.observe(this, androidx.lifecycle.Observer {
                var task: Task? = taskViewModel.returnedTask.value
                if (task != null) {
                    editText_name.setText(task.name)
                    editText_description.setText(task.description)
                    calendarView_deadline.setDate(task.deadline, false, false)
                    spinner_hours.setSelection(task.timeNeeded, false)
                    spinner_importance.setSelection(task.importance, false)
                }
            })
        }

        button_save = findViewById(R.id.button_save)
        button_save.setOnClickListener {

            if (receivedIntent.getBooleanExtra("update", false)) {
                taskViewModel.updateTaskInfo(
                    editText_name.text.toString(),
                    editText_description.text.toString(),
                    selectedDate,
                    spinner_hours.selectedItemPosition,
                    spinner_importance.selectedItemPosition,
                    receivedIntent.getStringExtra("id")?.toInt()!!
                )
                finish()

            } else {

                if (TextUtils.isEmpty(editText_name.text) or TextUtils.isEmpty(editText_description.text)) {
                    toastError("Missing fields")

                } else {

                    val task = Task(
                        editText_name.text.toString(), editText_description.text.toString(),
                        selectedDate, spinner_hours.selectedItemPosition,
                        spinner_importance.selectedItemPosition, false, 0
                    )
                    taskViewModel.insert(task)
                    finish()

                }

            }

        }
    }

    private fun toastError(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

}