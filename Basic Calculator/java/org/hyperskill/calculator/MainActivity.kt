package org.hyperskill.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.math.floor

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonListener(R.id.button0, "0")
        buttonListener(R.id.button1, "1")
        buttonListener(R.id.button2, "2")
        buttonListener(R.id.button3, "3")
        buttonListener(R.id.button4, "4")
        buttonListener(R.id.button5, "5")
        buttonListener(R.id.button6, "6")
        buttonListener(R.id.button7, "7")
        buttonListener(R.id.button8, "8")
        buttonListener(R.id.button9, "9")

        operatorButton(R.id.addButton,"+")
        operatorButton(R.id.multiplyButton,"*")
        operatorButton(R.id.divideButton,"/")
        minus()

        equal()
        dot()
        clear()

    }

    private var cache: Double = 0.0
    private var lastOperation: String = "+"

    private fun operatorButton(buttonId: Int, operator: String) {
        val button: Button = findViewById(buttonId)
        val editText: EditText = findViewById(R.id.editText)
        button.setOnClickListener {
            cache = editText.text.toString().toDouble()

            if (isFractional.invoke(cache)) editText.hint = cache.toInt().toString()
            else editText.hint = cache.toString()

            lastOperation = operator
            editText.setText("0")
        }
    }


    private fun minus() {
        val button: Button = findViewById(R.id.subtractButton)
        val editText: EditText = findViewById(R.id.editText)
        button.setOnClickListener {
            if (editText.text.toString() == "0") editText.setText("-")
            else {
                cache = editText.text.toString().toDouble()

                if (isFractional.invoke(cache)) editText.hint = cache.toInt().toString()
                else editText.hint = cache.toString()

                lastOperation = "-"
                editText.setText("0")
            }
        }
    }

    private fun equal() {
        val button: Button = findViewById(R.id.equalButton)
        val editText: EditText = findViewById(R.id.editText)
        button.setOnClickListener {
            val result: Double = when(lastOperation) {
                "+" -> cache + editText.text.toString().toDouble()
                "-" -> cache - editText.text.toString().toDouble()
                "*" -> cache * editText.text.toString().toDouble()
                "/" -> cache / editText.text.toString().toDouble()
                else -> 0.0
            }
            editText.hint = result.toString()
            if (isFractional.invoke(result)) editText.setText(result.toInt().toString())
            else editText.setText(result.toString())
        }
    }

    private fun clear() {
        val button: Button = findViewById(R.id.clearButton)
        val editText: EditText = findViewById(R.id.editText)
        button.setOnClickListener {
            cache = 0.0
            editText.hint = "0"
            editText.setText("")
        }
    }

    private fun dot() {
        val button: Button = findViewById(R.id.dotButton)
        val editText: EditText = findViewById(R.id.editText)
        button.setOnClickListener {
            if (editText.text.toString() == "-") editText.setText("-0.")
            if (!editText.text.toString().contains(".")) editText.append(".")
        }
    }

    private fun buttonListener(buttonId: Int, number: String) {
        val button: Button = findViewById(buttonId)
        val editText: EditText = findViewById(R.id.editText)
        button.setOnClickListener {
            when (editText.text.toString()) {
                "0" -> editText.setText(number)
                "-0" -> editText.setText("-$number")
                else -> editText.append(number)
            }
        }
    }

    private val isFractional: (Double) -> Boolean = { number: Double -> number == floor(number) }
}