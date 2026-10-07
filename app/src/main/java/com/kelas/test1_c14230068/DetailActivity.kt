package com.kelas.test1_c14230068

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar
import java.util.TimeZone

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)

        findViewById<android.view.View>(R.id.kalendar).setOnClickListener {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("asia/jakarta"))
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->

                    val selectedDateTime = Calendar.getInstance().apply {
                        set(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute)
                    }

                    val endTime = (selectedDateTime.clone() as Calendar).apply {
                        add(Calendar.HOUR_OF_DAY, 1)
                    }

                    val eventIntent = Intent(Intent.ACTION_INSERT).apply {
                        data = CalendarContract.Events.CONTENT_URI
                        putExtra(CalendarContract.Events.TITLE, "Meeting")
                        putExtra(CalendarContract.Events.EVENT_LOCATION, "Kantor")
                        putExtra(CalendarContract.Events.DESCRIPTION, "Deskripsi Meeting")
                        putExtra(CalendarContract.Events.ALL_DAY, false)
                        putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, selectedDateTime.timeInMillis)
                        putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.timeInMillis)
                    }
                    if (eventIntent.resolveActivity(packageManager) != null) {
                        startActivity(eventIntent)
                    } else {
                        Toast.makeText(this, "Tidak ada aplikasi Kalender ditemukan", Toast.LENGTH_SHORT).show()
                    }

                }, hour, minute, true)
                timePickerDialog.show()
            }, year, month, day)
            datePickerDialog.show()
        }

    }
}