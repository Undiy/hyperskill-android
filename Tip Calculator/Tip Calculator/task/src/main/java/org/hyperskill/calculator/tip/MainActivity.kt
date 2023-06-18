package org.hyperskill.calculator.tip

import android.os.Bundle
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {

    private lateinit var billValue: TextView
    private lateinit var tipPercent: TextView
    private lateinit var tipAmount: TextView
    private lateinit var seekBar: SeekBar
    private var billAmount: BigDecimal = BigDecimal.ZERO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        billValue = findViewById(R.id.bill_value_tv)
        tipPercent = findViewById(R.id.tip_percent_tv)
        tipAmount = findViewById(R.id.tip_amount_tv)
        seekBar = findViewById(R.id.seek_bar)

        findViewById<EditText>(R.id.edit_text).addTextChangedListener {
            billAmount = it.toString().toBigDecimalOrNull() ?: BigDecimal.ZERO
            updateViews()
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar, progress: Int, fromUser: Boolean) {
                updateViews()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }

        })
    }

    private fun updateViews() {
        if (billAmount > BigDecimal.ZERO) {
            billValue.text = "Bill Value: $%.2f".format(billAmount)
            tipPercent.text = "Tip: ${seekBar.progress}%"
            tipAmount.text = "Tip Amount: $%.2f".format(
                (billAmount * seekBar.progress.toBigDecimal()).divide(BigDecimal(100), 2, RoundingMode.UP)
            )
        } else {
            billValue.text = ""
            tipPercent.text = ""
            tipAmount.text = ""

        }
    }
}