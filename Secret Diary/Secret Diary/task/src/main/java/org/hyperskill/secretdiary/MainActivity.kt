package org.hyperskill.secretdiary

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class MainActivity : AppCompatActivity() {

    private lateinit var newWriting: EditText
    private lateinit var diary: TextView
    private val notes: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newWriting = findViewById(R.id.etNewWriting)
        diary = findViewById(R.id.tvDiary)

        findViewById<Button>(R.id.btnSave).setOnClickListener {
            val note = newWriting.text.toString()
            if (note.trim() == "") {
                Toast.makeText(
                    this,
                    "Empty or blank input cannot be saved",
                    Toast.LENGTH_SHORT).show()
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
                .setMessage( "Do you really want to remove the last writing? This operation cannot be undone!")
                .setPositiveButton("Yes") { _, _ ->
                    notes.removeLastOrNull()
                    updateDiary()
                }
                .setNegativeButton("No") { _, _ -> }
                .show()
        }
    }

    private fun updateDiary() {
        diary.text = notes.reversed().joinToString("\n\n")
    }

}