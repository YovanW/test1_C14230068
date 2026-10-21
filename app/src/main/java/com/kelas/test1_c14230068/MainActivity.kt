package com.kelas.test1_c14230068

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import java.util.TimeZone

class MainActivity : AppCompatActivity() {

    private val developerEmail = "c14230068@john.petra.ac.id"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.main).applySystemBarsPadding()

        findViewById<View>(R.id.about).setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }

        findViewById<View>(R.id.developer).setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$developerEmail")
            }
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Tidak ada aplikasi email", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<View>(R.id.itemKegiatanSeminar).setOnClickListener {
            openDetail(
                title = "Seminar Teknologi 2026",
                subtitle = "Membangun Aplikasi Android Modern",
                dateText = "20 Oktober 2026",
                timeText = "09.00 - 12.00 WIB",
                location = "Auditorium Kampus XYZ",
                description = "Seminar ini menghadirkan pembicara dari industri teknologi untuk membahas pengembangan aplikasi Android, mulai dari arsitektur modern, UI/UX, hingga best practice dalam pembuatan aplikasi yang berkualitas.",
                start = jakartaMillis(2026, Calendar.OCTOBER, 20, 9, 0),
                end = jakartaMillis(2026, Calendar.OCTOBER, 20, 12, 0)
            )
        }

        findViewById<View>(R.id.itemPhone).setOnClickListener {
            openDetail(
                title = "Workshop UI/UX Design",
                subtitle = "Dari Ide Menjadi Desain yang Menarik",
                dateText = "25 Oktober 2026",
                timeText = "13.00 - 16.00 WIB",
                location = "Lab Komputer Lt. 3",
                description = "Workshop ini membahas proses perancangan UI/UX, mulai dari riset pengguna, wireframe, hingga prototipe interaktif, dengan praktik langsung di laboratorium komputer.",
                start = jakartaMillis(2026, Calendar.OCTOBER, 25, 13, 0),
                end = jakartaMillis(2026, Calendar.OCTOBER, 25, 16, 0)
            )
        }

        findViewById<View>(R.id.itemCampusFair).setOnClickListener {
            openDetail(
                title = "Campus Fair 2026",
                subtitle = "Temukan Peluang untuk Masa Depanmu",
                dateText = "28 Oktober 2026",
                timeText = "08.00 - 15.00 WIB",
                location = "Lapangan Kampus XYZ",
                description = "Campus Fair 2026 mempertemukan mahasiswa dengan perusahaan, komunitas, dan organisasi kampus untuk mengenal peluang karier, magang, dan pengembangan diri.",
                start = jakartaMillis(2026, Calendar.OCTOBER, 28, 8, 0),
                end = jakartaMillis(2026, Calendar.OCTOBER, 28, 15, 0)
            )
        }

        findViewById<View>(R.id.btnKelolaAcara).setOnClickListener {
            startActivity(Intent(this, KelolaAcaraActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        showAcara()
    }

    private fun showAcara() {
        val container = findViewById<LinearLayout>(R.id.containerAcara)
        val tvKosong = findViewById<TextView>(R.id.tvAcaraKosong)
        val data = LombaStore.load(this)

        container.removeAllViews()
        tvKosong.visibility = if (data.isEmpty()) View.VISIBLE else View.GONE

        val gap = (8 * resources.displayMetrics.density).toInt()
        data.forEach { name ->
            val row = layoutInflater.inflate(R.layout.item_acara, container, false) as TextView
            row.text = name
            (row.layoutParams as LinearLayout.LayoutParams).bottomMargin = gap
            container.addView(row)
        }
    }

    private fun openDetail(
        title: String,
        subtitle: String,
        dateText: String,
        timeText: String,
        location: String,
        description: String,
        start: Long,
        end: Long
    ) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_TITLE, title)
            putExtra(DetailActivity.EXTRA_SUBTITLE, subtitle)
            putExtra(DetailActivity.EXTRA_DATE, dateText)
            putExtra(DetailActivity.EXTRA_TIME, timeText)
            putExtra(DetailActivity.EXTRA_LOCATION, location)
            putExtra(DetailActivity.EXTRA_DESCRIPTION, description)
            putExtra(DetailActivity.EXTRA_START, start)
            putExtra(DetailActivity.EXTRA_END, end)
        }
        startActivity(intent)
    }

    private fun jakartaMillis(year: Int, month: Int, day: Int, hour: Int, minute: Int): Long {
        return Calendar.getInstance(TimeZone.getTimeZone("Asia/Jakarta")).apply {
            clear()
            set(year, month, day, hour, minute)
        }.timeInMillis
    }
}