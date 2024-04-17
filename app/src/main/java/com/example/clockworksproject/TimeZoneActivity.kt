package com.example.clockworksproject

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class TimeZoneActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_zone)

        val listView: ListView = findViewById(R.id.listView)

       val timezones: ArrayList<String> = ArrayList(TimeZone.getAvailableIDs().asList())

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, timezones)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val result = Intent(applicationContext, MainActivity::class.java)
            result.putExtra("timezone", timezones[position])
            setResult(RESULT_OK, result)
            finish()
        }


    }
}