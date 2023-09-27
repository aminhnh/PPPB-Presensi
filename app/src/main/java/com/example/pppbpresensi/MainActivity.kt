package com.example.pppbpresensi

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.pppbpresensi.databinding.ActivityMainBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            val calendar = Calendar.getInstance()
            var date = calendar.get(Calendar.DAY_OF_MONTH)
            var year = calendar.get(Calendar.YEAR)
            var monthNumber = calendar.get(Calendar.MONTH)

            val scalingFactor = 0.8f
            timePickerCard.scaleX = scalingFactor
            timePickerCard.scaleY = scalingFactor

            var selectedKeterangan : String
            editTextKeterangan.visibility = View.INVISIBLE

            val listKeterangan = resources.getStringArray(R.array.keterangan_kehadiran)
            val adapterKeterangan = ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_spinner_item, listKeterangan)
            spinnerKeterangan.adapter = adapterKeterangan

            spinnerKeterangan.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        selectedKeterangan = listKeterangan[position]
                        if (selectedKeterangan != listKeterangan[0]) {
                            editTextKeterangan.visibility = View.VISIBLE
                        } else {
                            editTextKeterangan.visibility = View.INVISIBLE
                        }
                    }
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }

                }

            calendarView.setOnDateChangeListener { calendarView, selectedYear, selectedMonth, dayOfMonth ->
                date = dayOfMonth
                year = selectedYear
                monthNumber = selectedMonth
            }

            btnSubmit.setOnClickListener{
                val monthsArray = arrayOf(
                    "Januari", "Februari", "Maret", "April", "Mei", "Juni",
                    "Juli", "Agustus", "September", "Oktober", "November", "Desember"
                )
                val month = monthsArray[monthNumber]

                val am_pm : String
                val minute = timePicker.minute
                var hour = timePicker.hour
                if (hour > 12){
                    hour -= 12
                    am_pm = "PM"
                } else {
                    am_pm = "AM"
                }

                val text = "Presensi berhasil $date $month $year jam $hour:$minute $am_pm"
                Toast.makeText(this@MainActivity, text, Toast.LENGTH_LONG).show()
            }
        }
    }
}