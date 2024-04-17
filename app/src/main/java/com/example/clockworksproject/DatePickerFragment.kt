package com.example.clockworksproject

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.widget.DatePicker
import java.util.*


class DatePickerFragment : DialogFragment(), OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
        val c = Calendar.getInstance()
        val year = c[Calendar.YEAR]
        val month = c[Calendar.MONTH]
        val day = c[Calendar.DAY_OF_MONTH]
        return DatePickerDialog(activity, this, year, month, day)
    }

    override fun onDateSet(datePicker: DatePicker, year: Int, month: Int, day: Int) {
        (activity as MainActivity).localDate = Date(year - 1900, month, day)
    }
}