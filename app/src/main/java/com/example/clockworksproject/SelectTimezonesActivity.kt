package com.example.clockworksproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.core.view.MenuItemCompat
import java.util.TimeZone
import java.util.ArrayList
import android.widget.Filter
import java.util.Arrays

class SelectTimezonesActivity : AppCompatActivity() {
    private val selectedTimezones = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>
    private val timezones = TimeZone.getAvailableIDs().toMutableList()
    private lateinit var listView: ListView
    private var showAll = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_timezones)

        title = "Choose Time Zones"

        val intent = intent
        val bundle = intent.getBundleExtra("selectedTimezonesBundle")
        selectedTimezones.addAll(bundle?.getStringArrayList("selectedTimezones") ?: emptyList())

        listView = findViewById(R.id.listView)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, timezones)
        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->
            if (listView.isItemChecked(i)) {
                selectedTimezones.add(adapter.getItem(i)!!)
            } else {
                selectedTimezones.remove(adapter.getItem(i))
            }
        }

        checkSelectedTimezones()
    }



    fun done(view: View) {
        val bundle = Bundle()
        bundle.putStringArrayList("selectedTimezones", selectedTimezones as ArrayList<String>)
        val result = Intent(this, MainActivity::class.java)
        result.putExtra("selectedTimezonesBundle", bundle)
        setResult(RESULT_OK, result)
        finish()
    }



    fun showChecked(view: View) {
        val button = view as Button
        adapter.clear()
        if (showAll) {
            for (timezone in selectedTimezones) {
                adapter.add(timezone)
            }
            adapter.notifyDataSetChanged()

            button.text = "Show All"
            showAll = false
        } else {
            for (timezone in timezones) {
                adapter.add(timezone)
            }
            adapter.notifyDataSetChanged()

            button.text = "Show Checked"
            showAll = true
        }

        checkSelectedTimezones()
    }

    fun uncheckAll(view: View) {
        selectedTimezones.clear()
        checkSelectedTimezones()
    }

    private fun checkSelectedTimezones() {
        for (j in 0 until adapter.count) {
            listView.setItemChecked(j, selectedTimezones.contains(adapter.getItem(j)))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String?): Boolean {
                adapter.filter.filter(s, object : Filter.FilterListener {
                    override fun onFilterComplete(i: Int) {
                        checkSelectedTimezones()
                    }
                })
                return true
            }
        })
        return true
    }
}

