package com.kelas.test1_c14230068

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_about)
        findViewById<View>(R.id.main).applySystemBarsPadding()

        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }

        // Email -> compose email
        findViewById<View>(R.id.itemEmail).setOnClickListener {
            val email = findViewById<TextView>(R.id.tvEmail).text.toString()
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$email")
            }
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Tidak ada aplikasi email", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<View>(R.id.itemPhone).setOnClickListener {
            val phone = findViewById<TextView>(R.id.tvPhone).text.toString()
                .filter { it.isDigit() || it == '+' }
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Tidak ada aplikasi telepon", Toast.LENGTH_SHORT).show()
            }
        }
    }
}