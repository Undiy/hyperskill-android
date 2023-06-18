package org.hyperskill.secretdiary

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var newWriting: EditText
    private lateinit var diary: TextView

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
                diary.text = note
                newWriting.setText("")
            }
        }
    }

}