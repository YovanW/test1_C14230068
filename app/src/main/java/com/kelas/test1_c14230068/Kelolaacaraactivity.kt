package com.kelas.test1_c14230068

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class KelolaAcaraActivity : AppCompatActivity() {

    private lateinit var data: MutableList<String>
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_kelola_acara)
        findViewById<View>(R.id.main).applySystemBarsPadding()

        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }

        data = LombaStore.load(this)
        adapter = ArrayAdapter(this, R.layout.item_acara, data)

        val lvAcara = findViewById<ListView>(R.id.lvAcara)
        lvAcara.adapter = adapter
        lvAcara.emptyView = findViewById<TextView>(R.id.tvKosong)

        val etAcara = findViewById<EditText>(R.id.etAcara)

        // Tambah
        findViewById<Button>(R.id.btnTambah).setOnClickListener {
            val name = etAcara.text.toString().trim()
            when {
                name.isEmpty() -> toast("Nama lomba tidak boleh kosong")
                isDuplicate(name, -1) -> toast("Lomba \"$name\" sudah ada")
                else -> {
                    data.add(name)
                    saveAndRefresh()
                    etAcara.text.clear()
                    toast("Lomba \"$name\" ditambahkan")
                }
            }
        }

        // Tap an item -> Edit / Hapus
        lvAcara.setOnItemClickListener { _, _, position, _ ->
            showActionDialog(position)
        }
    }

    private fun showActionDialog(position: Int) {
        val name = data[position]
        AlertDialog.Builder(this)
            .setTitle(name)
            .setMessage("Pilih tindakan yang ingin dilakukan:")
            .setPositiveButton("Edit") { _, _ -> showEditDialog(position) }
            .setNegativeButton("Hapus") { _, _ -> showDeleteConfirm(position) }
            .setNeutralButton("Batal", null)
            .show()
    }

    private fun showEditDialog(position: Int) {
        val oldValue = data[position]

        val input = EditText(this).apply {
            setText(oldValue)
            setSelection(text.length)
            hint = "Nama lomba"
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
            setSingleLine()
        }
        val pad = (20 * resources.displayMetrics.density).toInt()
        val container = FrameLayout(this).apply {
            setPadding(pad, pad / 2, pad, 0)
            addView(input)
        }

        AlertDialog.Builder(this)
            .setTitle("Edit Lomba")
            .setView(container)
            .setPositiveButton("Simpan") { _, _ ->
                val newValue = input.text.toString().trim()
                when {
                    newValue.isEmpty() -> toast("Nama lomba tidak boleh kosong")
                    isDuplicate(newValue, position) -> toast("Lomba \"$newValue\" sudah ada")
                    else -> {
                        data[position] = newValue
                        saveAndRefresh()
                        toast("\"$oldValue\" diubah menjadi \"$newValue\"")
                    }
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun showDeleteConfirm(position: Int) {
        val name = data[position]
        AlertDialog.Builder(this)
            .setTitle("Hapus Lomba")
            .setMessage("Yakin ingin menghapus \"$name\"?")
            .setPositiveButton("Hapus") { _, _ ->
                data.removeAt(position)
                saveAndRefresh()
                toast("\"$name\" dihapus")
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    /** True if another item (not at ignoreIndex) already has this name. */
    private fun isDuplicate(name: String, ignoreIndex: Int): Boolean {
        return data.withIndex().any { (i, value) ->
            i != ignoreIndex && value.equals(name, ignoreCase = true)
        }
    }

    private fun saveAndRefresh() {
        LombaStore.save(this, data)
        adapter.notifyDataSetChanged()
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}