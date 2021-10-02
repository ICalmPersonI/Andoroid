package org.hyperskill.calculator.tip

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.slider.Slider
import java.lang.Math.floor
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var editText: EditText
    private lateinit var slider: Slider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.text_view)
        editText = findViewById(R.id.edit_text)
        slider = findViewById(R.id.slider)

        inputListener()
        slider()
    }

    private var billValue: Double = 0.0
    private var tipPercentage: Int = 0

    private val setValueInTextView: () -> Unit = {
        if (billValue == 0.0) textView.text = null
        else {
            val tip: Double = billValue * tipPercentage / 100
            textView.text = "Tip amount: ${String.format("%.2f", tip)}"
        }
    }

    private fun inputListener() {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                billValue = try {
                    s.toString().toDouble()
                } catch (e: NumberFormatException) {
                    0.0
                }
                setValueInTextView.invoke()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun slider() {
        slider.addOnChangeListener { _, value, _ ->
            tipPercentage = value.toInt()
            setValueInTextView.invoke()
        }
    }
}

