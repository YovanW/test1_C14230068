package com.kelas.test1_c14230068

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_SUBTITLE = "extra_subtitle"
        const val EXTRA_DATE = "extra_date"
        const val EXTRA_TIME = "extra_time"
        const val EXTRA_LOCATION = "extra_location"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_START = "extra_start"
        const val EXTRA_END = "extra_end"

        private const val PANITIA_PHONE = "+6285103994924"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        findViewById<View>(R.id.main).applySystemBarsPadding()

        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        val tvSubtitle = findViewById<TextView>(R.id.tvSubtitle)
        val tvDate = findViewById<TextView>(R.id.tvDate)
        val tvTime = findViewById<TextView>(R.id.tvTime)
        val tvLocation = findViewById<TextView>(R.id.tvLocation)
        val tvDescription = findViewById<TextView>(R.id.tvDescription)

        // Fill the screen with the event that was tapped
        intent.getStringExtra(EXTRA_TITLE)?.let { tvTitle.text = it }
        intent.getStringExtra(EXTRA_SUBTITLE)?.let { tvSubtitle.text = it }
        intent.getStringExtra(EXTRA_DATE)?.let { tvDate.text = it }
        intent.getStringExtra(EXTRA_TIME)?.let { tvTime.text = it }
        intent.getStringExtra(EXTRA_LOCATION)?.let { tvLocation.text = it }
        intent.getStringExtra(EXTRA_DESCRIPTION)?.let { tvDescription.text = it }

        val startMillis = intent.getLongExtra(EXTRA_START, 0L)
        val endMillis = intent.getLongExtra(EXTRA_END, 0L)

        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }

        // Tambahkan Kalender
        findViewById<View>(R.id.kalendar).setOnClickListener {
            if (startMillis == 0L || endMillis == 0L) {
                Toast.makeText(this, "Jadwal kegiatan tidak tersedia", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val eventIntent = Intent(Intent.ACTION_INSERT).apply {
                data = CalendarContract.Events.CONTENT_URI
                putExtra(CalendarContract.Events.TITLE, tvTitle.text.toString())
                putExtra(CalendarContract.Events.EVENT_LOCATION, tvLocation.text.toString())
                putExtra(CalendarContract.Events.DESCRIPTION, tvDescription.text.toString())
                putExtra(CalendarContract.Events.ALL_DAY, false)
                putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
                putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
            }
            try {
                startActivity(eventIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Tidak ada aplikasi Kalender ditemukan", Toast.LENGTH_SHORT).show()
            }
        }

        // Hubungi Panitia
        findViewById<View>(R.id.panitia).setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$PANITIA_PHONE"))
            try {
                startActivity(dialIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Tidak ada aplikasi telepon", Toast.LENGTH_SHORT).show()
            }
        }
    }
}