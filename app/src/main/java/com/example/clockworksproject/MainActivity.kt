package com.example.clockworksproject

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import java.text.DateFormat
import java.util.ArrayList
import java.util.Arrays
import java.util.Collections
import java.util.Comparator
import java.util.*


class MainActivity : AppCompatActivity() {

    var localDate = Date()
    var dateBtn: Button? = null
    var selectTimeZoneBtn: Button? = null
    var userTimeZone: TimeZone? = null
    var selectedTimeZones = arrayListOf("Europe/Bucharest", "Europe/London", "Europe/Paris")
    var selectedTimeZone: TimeZone? = null
    var convertedTimeTv: TextView? = null
    var convertedDateTv: TextView? = null
    var listView: ListView? = null
    var adapter: ArrayAdapter<String>? = null

    private val CHOOSE_TIME_ZONE_REQUEST_CODE = 1
    private val SELECT_TIME_ZONES_REQUEST_CODE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        val userTime = findViewById<TextView>(R.id.userTime)
        convertedTimeTv = findViewById(R.id.convertedTime)
        convertedDateTv = findViewById(R.id.convertedDate)


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

                convertDate(userTimeZone, selectedTimeZone)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })


        seekBar.progress = localDate.getHours()

        dateBtn = findViewById<Button>(R.id.dateButton)
        dateBtn?.text = DateFormat.getDateInstance().format(localDate)

        selectTimeZoneBtn = findViewById(R.id.timeZoneButton)

//        val adapter = ArrayAdapter(
//            this,
//            android.R.layout.simple_list_item_activated_1,
//            android.R.id.text1,
//            selectedTimeZones
//        )
//        val listView = findViewById<ListView>(R.id.listView)
//        listView.adapter = adapter

        listView = findViewById(R.id.listView)
        setupAdapter()

        listView?.setOnItemClickListener { adapterView, view, i, l ->
            selectedTimeZone = TimeZone.getTimeZone(selectedTimeZones[i])
            convertDate(userTimeZone, selectedTimeZone)
        }

//        listView?.setOnItemClickListener { parent, view, position, id ->
//            selectedTimeZone = TimeZone.getTimeZone(selectedTimeZones[position])
//        }

//        listView?.onItemClickListener =
//            OnItemClickListener { adapterView, view, i, l ->
//                selectedTimeZone = TimeZone.getTimeZone(selectedTimeZones[i])
//                convertDate(userTimeZone, selectedTimeZone)
//            }

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
            convertDate(userTimeZone, selectedTimeZone)
        }

    }

    fun chooseTimezone(view: View) {
        val intent = Intent(this, TimeZoneActivity::class.java)
        startActivityForResult(intent, CHOOSE_TIME_ZONE_REQUEST_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CHOOSE_TIME_ZONE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val timezone = data?.getStringExtra("timezone")
            selectTimeZoneBtn?.text = timezone
            userTimeZone = TimeZone.getTimeZone(timezone ?: "")
        }

//        if (requestCode == SELECT_TIME_ZONES_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            data?.getBundleExtra("selectedTimezonesBundle")?.let { bundle ->
//                bundle.getStringArrayList("selectedTimezones")?.let { selectedTimezonesArrayList ->
//                    selectedTimezonesArrayList.sortedWith(Comparator { s, t1 ->
//                        s.compareTo(t1, ignoreCase = true)
//                    }).toTypedArray().also { selectedTimezones ->
//                        setupAdapter()
//                    }
//                }
//            }
//        }

        if (requestCode == SELECT_TIME_ZONES_REQUEST_CODE && resultCode == RESULT_OK) {
            val bundle = data?.getBundleExtra("selectedTimezonesBundle")
            val selectedTimezonesArrayList = bundle?.getStringArrayList("selectedTimezones")
            selectedTimezonesArrayList?.sortWith(compareBy({ it.lowercase() }, { it.lowercase() }))
            if (selectedTimezonesArrayList != null) {
                selectedTimeZones = selectedTimezonesArrayList
            }
            setupAdapter()
        }

        convertDate(userTimeZone, selectedTimeZone)
    }


    private fun convertDate(fromTimeZone: TimeZone?, toTimeZone: TimeZone?) {
        if (fromTimeZone != null && toTimeZone != null) {
            val fromOffset = fromTimeZone.getOffset(localDate.time).toLong()
            val toOffset = toTimeZone.getOffset(localDate.time).toLong()
            val convertedTime = localDate.time - (fromOffset - toOffset)
            val convertedDate = Date(convertedTime)
            val hours = convertedDate.hours
            val minutes = convertedDate.minutes
            val time = ((if (hours < 10) "0" + Integer.toString(hours) else Integer.toString(hours))
                    + ":" + if (minutes < 10) "0" + Integer.toString(minutes) else Integer.toString(
                minutes
            ))
            convertedTimeTv?.text = time
            convertedDateTv?.text = DateFormat.getDateInstance().format(convertedDate)
        }
    }

    fun selectTimezones(view: View) {
        val bundle = Bundle()
        bundle.putStringArrayList("selectedTimezones", ArrayList(selectedTimeZones.toList()))
        val intent = Intent(this, SelectTimezonesActivity::class.java)
        intent.putExtra("selectedTimezonesBundle", bundle)
        startActivityForResult(intent, 2)
    }

    private fun setupAdapter() {
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_activated_1,
            android.R.id.text1,
            selectedTimeZones
        )
        listView?.adapter = adapter
    }



}


