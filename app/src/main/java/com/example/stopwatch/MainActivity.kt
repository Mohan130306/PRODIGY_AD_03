package com.example.stopwatch

import android.os.Bundle
import android.os.Handler
import android.os.Looper

import android.widget.Button
import android.widget.TextView

import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var tvTime: TextView
    private lateinit var btnStart: Button
    private lateinit var btnPause: Button
    private lateinit var btnReset: Button

    private var milliseconds = 0L
    private var isRunning = false

    private val handler = Handler(Looper.getMainLooper())

    private val runnable = object : Runnable {
        override fun run() {
            if (isRunning) {
                milliseconds += 10
                updateTimer()
                handler.postDelayed(this, 10)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTime = findViewById(R.id.tvTime)
        btnStart = findViewById(R.id.btnStart)
        btnPause = findViewById(R.id.btnPause)
        btnReset = findViewById(R.id.btnReset)

        btnStart.setOnClickListener {
            if (!isRunning) {
                isRunning = true
                handler.post(runnable)
            }
        }

        btnPause.setOnClickListener {
            isRunning = false
        }

        btnReset.setOnClickListener {
            isRunning = false
            milliseconds = 0
            updateTimer()
        }
    }

    private fun updateTimer() {
        val minutes = (milliseconds / 60000)
        val seconds = (milliseconds / 1000) % 60
        val millis = milliseconds % 1000

        val time = String.format("%02d:%02d:%03d", minutes, seconds, millis)
        tvTime.text = time
    }
}
