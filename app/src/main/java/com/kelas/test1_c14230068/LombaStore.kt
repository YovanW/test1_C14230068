package com.kelas.test1_c14230068

import android.content.Context
import org.json.JSONArray
import org.json.JSONException

/**
 * Stores the list of lomba (acara) so MainActivity and KelolaAcaraActivity
 * share the same data, and it survives closing the app.
 */
object LombaStore {

    private const val PREFS = "acara_prefs"
    private const val KEY = "lomba"

    private val defaults = listOf(
        "Lomba Makan",
        "Lomba Tarik Tambang",
        "Lomba Balap Karung",
        "Lomba Menari",
        "Lomba Panjat"
    )

    fun load(context: Context): MutableList<String> {
        val prefs = context.applicationContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY, null) ?: return defaults.toMutableList()
        return try {
            val array = JSONArray(json)
            MutableList(array.length()) { array.getString(it) }
        } catch (e: JSONException) {
            defaults.toMutableList()
        }
    }

    fun save(context: Context, data: List<String>) {
        val array = JSONArray()
        data.forEach { array.put(it) }
        context.applicationContext
            .getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY, array.toString())
            .apply()
    }
}