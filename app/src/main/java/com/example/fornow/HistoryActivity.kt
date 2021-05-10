package com.example.fornow

import android.app.ActionBar
import android.app.Activity
import android.hardware.SensorManager
import android.os.Bundle
import android.view.Gravity.CENTER
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoryActivity : AppCompatActivity() ,ShakeDetector.Listener{

    private lateinit var recyclerView: RecyclerView

    private val taskViewModel: TaskViewModel by viewModels{
        TaskViewModelFactory((application as TaskApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        recyclerView = findViewById(R.id.recyclerView_history)

        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val sd = ShakeDetector(this)
        sd.start(sensorManager)

        val adapter = HistoryListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                this@HistoryActivity,
                LinearLayoutManager.VERTICAL
            )
        )

        taskViewModel.finishedTasks.observe(this, Observer {
            // update the cashed copy of tasks in the adapter to it
                tasks ->
            tasks?.let {
                adapter.submitList(it)
            }
        })

    }

    override fun hearShake() {

        val numHistory = recyclerView.adapter?.itemCount

        Toast.makeText(this, "So far, you have finished " + numHistory.toString()
            + " tasks. Keep up the good work!", Toast.LENGTH_SHORT).show()
    }

}