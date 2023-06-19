package org.hyperskill.secretdiary

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

const val PREF_NAME = "PREF_DIARY"
const val PREF_KEY = "KEY_DIARY_TEXT"

class MainActivity : AppCompatActivity() {

    private lateinit var newWriting: EditText
    private lateinit var diary: TextView
    private lateinit var prefs: SharedPreferences
    private val notes: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newWriting = findViewById(R.id.etNewWriting)
        diary = findViewById(R.id.tvDiary)

        prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        findViewById<Button>(R.id.btnSave).setOnClickListener {
            val note = newWriting.text.toString()
            if (note.trim() == "") {
                Toast.makeText(
                    this,
                    "Empty or blank input cannot be saved",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val ts = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                    .toString().substringBefore('.').replaceFirst('T', ' ')
                notes.add("$ts\n$note")
                updateDiary()
                newWriting.setText("")
            }
        }

        findViewById<Button>(R.id.btnUndo).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Remove last note")
                .setMessage("Do you really want to remove the last writing? This operation cannot be undone!")
                .setPositiveButton("Yes") { _, _ ->
                    notes.removeLastOrNull()
                    updateDiary()
                }
                .setNegativeButton("No") { _, _ -> }
                .show()
        }

        loadDiary()
    }

    private fun updateDiary() {
        diary.text = notes.reversed().joinToString("\n\n").also {
            prefs.edit().putString(PREF_KEY, it).apply()
        }
    }

    private fun loadDiary() {
        diary.text = prefs.getString(PREF_KEY, "")?.also {
            if (it != "") {
                notes.addAll(it.split("\n\n").reversed())
            }
        }
    }

}