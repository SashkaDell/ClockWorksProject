package com.example.clockworksproject

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import java.util.*


class TimeZoneActivity : AppCompatActivity() {

    var adapter: TimeZoneAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_zone)

        val listView: ListView = findViewById(R.id.listView)

       val timezones: ArrayList<String> = ArrayList(TimeZone.getAvailableIDs().asList())

//        var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, timezones)
        adapter = TimeZoneAdapter(
            this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            timezones
        )
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val result = Intent(applicationContext, MainActivity::class.java)
            result.putExtra("timezone", timezones[position])
            setResult(RESULT_OK, result)
            finish()
        }




    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(s: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String?): Boolean {
                adapter?.filter?.filter(s)
                return true
            }
        })
        return true
    }


}