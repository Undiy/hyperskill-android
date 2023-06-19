package org.hyperskill.secretdiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

const val PIN = "1234"

class LoginActivity : AppCompatActivity() {

    private lateinit var pin: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        pin = findViewById(R.id.etPin)

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            if (pin.text.toString() == PIN) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                pin.error = "Wrong PIN!"
            }
        }
    }
}