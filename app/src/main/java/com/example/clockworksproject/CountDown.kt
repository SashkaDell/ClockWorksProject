package com.example.clockworksproject

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CountDown : AppCompatActivity() {

    private var edittext: EditText? = null
    private var button1: Button? = null
    private var textView: TextView? = null

    private var edittext2: EditText? = null
    private var button2: Button? = null
    private var textView2: TextView? = null

    private var edittext3: EditText? = null
    private var button3: Button? = null
    private var textView3: TextView? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_down)

        val mainbutton = findViewById<FloatingActionButton>(R.id.mainActivityFloatingActionButton)

        mainbutton.setOnClickListener {
            val intent = Intent(this@CountDown, MainActivity::class.java)
            startActivity(intent)
        }

        edittext = findViewById(R.id.editTextNumber1)
        button1 = findViewById(R.id.button1)
        textView = findViewById(R.id.textView1)

        edittext2 = findViewById(R.id.editTextNumber2)
        button2 = findViewById(R.id.button2)
        textView2 = findViewById(R.id.textView2)

        edittext3 = findViewById(R.id.editTextNumber3)
        button3 = findViewById(R.id.button3)
        textView3 = findViewById(R.id.textView3)

        button1?.setOnClickListener {
            val text = edittext?.text.toString()
            if (text.isNotBlank()) {
                val seconds = text.toInt()
                val countDownTimer = object : CountDownTimer((seconds * 1000).toLong(), 1000) {
                    override fun onTick(millis: Long) {
                        textView?.text = "seconds: " + (millis / 1000).toInt()
                    }

                    override fun onFinish() {
                        textView?.text = "Finished"
                    }
                }.start()
            }
        }

        button2?.setOnClickListener {
            val text2 = edittext2?.text.toString()
            if (text2.isNotBlank()) {
                val seconds = text2.toInt()
                val countDownTimer = object : CountDownTimer((seconds * 1000).toLong(), 1000) {
                    override fun onTick(millis: Long) {
                        textView2?.text = "seconds: " + (millis / 1000).toInt()
                    }

                    override fun onFinish() {
                        textView2?.text = "Finished"
                    }
                }.start()
            }
        }

        button3?.setOnClickListener {
            val text3 = edittext3?.text.toString()
            if (text3.isNotBlank()) {
                val seconds = text3.toInt()
                val countDownTimer = object : CountDownTimer((seconds * 1000).toLong(), 1000) {
                    override fun onTick(millis: Long) {
                        textView3?.text = "seconds: " + (millis / 1000).toInt()
                    }

                    override fun onFinish() {
                        textView3?.text = "Finished"
                    }
                }.start()
            }
        }
    }
}

