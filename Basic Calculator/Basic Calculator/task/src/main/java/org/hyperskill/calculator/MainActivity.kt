package org.hyperskill.calculator

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.lang.NumberFormatException
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText

    private var term: Double = 0.0
    private var operation: String = ""
    private var retainedOperation = ""
    private var retainedTerm: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.displayEditText)
        editText.inputType = InputType.TYPE_NULL

        listOf<Button>(
            findViewById(R.id.button0),
            findViewById(R.id.button1),
            findViewById(R.id.button2),
            findViewById(R.id.button3),
            findViewById(R.id.button4),
            findViewById(R.id.button5),
            findViewById(R.id.button6),
            findViewById(R.id.button7),
            findViewById(R.id.button8),
            findViewById(R.id.button9),
            findViewById(R.id.dotButton)
        ).forEach { b ->
            b.setOnClickListener {
                handleInput(b.text.toString())
            }
        }

        listOf<Button>(
            findViewById(R.id.divideButton),
            findViewById(R.id.multiplyButton),
            findViewById(R.id.subtractButton),
            findViewById(R.id.addButton),
            findViewById(R.id.equalButton)
        ).forEach { b ->
            b.setOnClickListener {
                handleOperation(b.text.toString())
            }
        }

        findViewById<Button>(R.id.clearButton).setOnClickListener {
            clear()
        }
    }

    private fun getTerm(): Double = try {
        editText.text.toString().toDouble()
    } catch (e: NumberFormatException) {
        term
    }

    private fun clear() {
        editText.setText("")
        editText.hint = "0"
        term = 0.0
        operation = ""
    }

    private fun handleInput(input: String) {
        val (value, sign) = editText.text.toString().let {
            Pair(it.trimStart('-'), if (it.startsWith("-")) "-" else "")
        }
        val next = when (input) {
            "0" -> value + if (value == "0") "" else input
            "." -> value + if (value == "") {
                "0."
            } else if (value.contains(".")) {
                ""
            } else {
                "."
            }
            else -> if (value == "0") input else value + input
        }
        editText.setText(sign + next)
    }

    private fun handleOperation(operation: String) {
        if (editText.text.toString() == "" && operation == "-" && this.operation != "=") {
            editText.setText("-")
            return
        }

        if (operation != "=") {
            retainedOperation = operation
        }
        val term2 = if (this.operation == "=") {
            retainedTerm
        } else {
            retainedTerm = getTerm()
            retainedTerm
        }
        Log.i("", "${retainedOperation} ${operation} ${this.operation} term: ${term},  term2: ${term2}")
        term = when (if (this.operation == "=" && operation == "=") retainedOperation else this.operation) {
            "/" -> term / term2
            "*" -> term * term2
            "-" -> term - term2
            "+" -> term + term2
            else -> getTerm()
        }
        this.operation = operation
        editText.setText("")
        editText.hint = DecimalFormat("#.################").apply {
            roundingMode = RoundingMode.CEILING
        }.format(term)
    }
}