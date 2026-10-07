package com.kelas.test1_c14230068

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kelas.test1_c14230068.ui.theme.Test1_c14230068Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val tvEmail = "c14230068@john.petra.ac.id"

        findViewById<android.view.View>(R.id.about).setOnClickListener {
            val intent = Intent(
                this@MainActivity,
                AboutActivity::class.java)
            startActivity(intent)
        }

        findViewById<android.view.View>(R.id.developer).setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:${tvEmail}")
            }
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Tidak ada aplikasi email", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<android.view.View>(R.id.itemKegiatanSeminar).setOnClickListener {
            val intent = Intent(
                this@MainActivity,
                DetailActivity::class.java)
            startActivity(intent)
        }

    }
}
