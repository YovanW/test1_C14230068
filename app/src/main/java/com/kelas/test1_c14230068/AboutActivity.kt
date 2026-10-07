package com.kelas.test1_c14230068

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)


        findViewById<android.view.View>(R.id.btnBack).setOnClickListener {
            val intent = Intent(
                this@AboutActivity,
                MainActivity::class.java)
            startActivity(intent)
        }

    }
}