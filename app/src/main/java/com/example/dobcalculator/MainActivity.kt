package com.example.dobcalculator

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var tvSelectedDate: TextView? = null
    private var tvAgeInMinutes: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.selectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)

        btnDatePicker.setOnClickListener {
            handleSelectDate()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleSelectDate() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this,
            { _, yearSelected, monthSelected, dayOfMonthSelected ->
                // runs if date is selected then "Ok" is pressed
                Toast.makeText(this, "Date Picker Works (year: $yearSelected)", Toast.LENGTH_LONG)
                    .show()

                val selectedDate = "${monthSelected + 1}/$dayOfMonthSelected/$yearSelected"
                tvSelectedDate?.text = selectedDate

                val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)
                val selectedDateInMinutes =
                    theDate?.time?.div(60000) // divide into minutes since 1970

                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                val currentDateInMinutes = currentDate?.time?.div(60000)

                // time between selected and current date in minutes
                val differenceInMinutes = currentDateInMinutes?.minus(selectedDateInMinutes!!)

                tvAgeInMinutes?.text = differenceInMinutes?.toString() ?: "Invalid"
            },
            year,
            month,
            day
        )

        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000 // max date is yesterday
        dpd.show()
    }
}
