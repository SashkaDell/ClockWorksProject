package com.example.clockworksproject

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import java.util.*


class TimeZoneAdapter(
    context: Context,
    resource: Int,
    textViewResourceId: Int,
    objects: ArrayList<String>
) :
    ArrayAdapter<String?>(context, resource, textViewResourceId, objects as List<String?>) {
    private val original: ArrayList<String>
    private val filter: Filter? = null

    init {
        original = ArrayList(objects)
    }

    override fun getFilter(): Filter {
        return TimeZoneFilter()
    }

    private inner class TimeZoneFilter : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val results = FilterResults()
            var filtered = ArrayList<String>()
            val search = charSequence.toString().lowercase(Locale.getDefault())
            if (search == null || search.length == 0) {
                filtered = ArrayList(original)
            } else {
                for (i in original.indices) {
                    if (original[i].lowercase(Locale.getDefault()).contains(charSequence)) {
                        filtered.add(original[i])
                    }
                }
            }
            results.values = filtered
            results.count = filtered.size
            return results
        }

        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            val items = filterResults.values as ArrayList<String>
            clear()
            for (i in items.indices) {
                add(items[i])
            }
            notifyDataSetChanged()
        }
    }
}