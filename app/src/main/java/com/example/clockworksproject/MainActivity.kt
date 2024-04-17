package com.example.clockworksproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.DateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    var localDate = Date()
    var dateBtn: Button? = null
    var selectTimeZoneBtn: Button? = null
    var userTimeZone: TimeZone? = null

    private val CHOOSE_TIME_ZONE_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        val userTime = findViewById<TextView>(R.id.userTime)
        selectTimeZoneBtn = findViewById(R.id.timeZoneButton)


        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar, i: Int, fromUser: Boolean) {
                userTime.text =
                    (if (seekBar.progress < 10) "0" + Integer.toString(seekBar.progress) else Integer.toString(
                        seekBar.progress
                    )) + ":00"

                localDate.setHours(seekBar.getProgress())
                if (fromUser) {
                    localDate.setMinutes(0)
                }

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

//        final TextView userTime = findViewById(R.id.userTime);
        seekBar.setProgress(localDate.getHours())

        val dateBtn = findViewById<Button>(R.id.dateButton)
        dateBtn.text = DateFormat.getDateInstance().format(localDate)
        selectTimeZoneBtn = findViewById(R.id.timeZoneButton)

        fun showDatePicker(view: View) {
            val dialog = DatePickerFragment()
            dialog.show(fragmentManager, "datePicker")
        }

        fun setLocalDate(date: Date) {
            val hours = localDate.hours
            val minutes = localDate.minutes
            localDate = date
            localDate.hours = hours
            localDate.minutes = minutes
            val dateBtn = findViewById<Button>(R.id.dateButton)
            dateBtn.text = DateFormat.getDateInstance().format(localDate)
        }

    }

    fun chooseTimezone(view: View) {
        val intent = Intent(this, TimeZoneActivity::class.java)
        startActivityIfNeeded(intent, CHOOSE_TIME_ZONE_REQUEST_CODE)

    }

    private fun onActivityResult2(requestCode: Int, resultCode: Int, data: Intent) {
        onActivityResult2(requestCode, resultCode, data)
        if (requestCode == CHOOSE_TIME_ZONE_REQUEST_CODE && resultCode == RESULT_OK) {
            val timezone = data.getStringExtra("timezone")
            selectTimeZoneBtn?.text = timezone
            userTimeZone = TimeZone.getTimeZone(timezone)
        }
    }

}


