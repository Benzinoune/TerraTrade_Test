package com.example.terratradetest

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat





class NumberLogin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_number_login)

        val drawable = ContextCompat.getDrawable(this, R.drawable.testiiing)
        val btnZero = findViewById<Button>(R.id.zero)
        val btnOne = findViewById<Button>(R.id.one)
        val btnTwo = findViewById<Button>(R.id.two)
        val btnThree = findViewById<Button>(R.id.three)
        val btnFour = findViewById<Button>(R.id.four)
        val btnFive = findViewById<Button>(R.id.five)
        val btnSix = findViewById<Button>(R.id.six)
        val btnSeven = findViewById<Button>(R.id.seven)
        val btnEight = findViewById<Button>(R.id.eight)
        val btnNine = findViewById<Button>(R.id.nine)
        val btnA = findViewById<Button>(R.id.a)             // Delete button
        val btnB = findViewById<Button>(R.id.b)                           // Verification button
        val phoneEditText = findViewById<EditText>(R.id.phoneEditText)
        val countrySpinner = findViewById<Spinner>(R.id.mySpinner)

        val countryList = listOf("+212", "+33", "+1")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, countryList)
        countrySpinner.adapter = adapter

        val colorApp = getColor(R.color.appColor)

        // Set up phone number input
        phoneEditText.apply {
            isFocusableInTouchMode = true
            showSoftInputOnFocus = false
        }

        val numberButtons = listOf(
            btnZero, btnOne, btnTwo, btnThree, btnFour,
            btnFive, btnSix, btnSeven, btnEight, btnNine
        )

        // Add number to EditText when number button is clicked, with max length check
        numberButtons.forEach { button ->
            button.setOnClickListener {
                if (phoneEditText.text.length < 9) {
                    phoneEditText.append(button.text)
                }
                button.setBackgroundDrawable(drawable)
                button.setTextColor(Color.WHITE)
                Handler(Looper.getMainLooper()).postDelayed({
                    button.setBackgroundColor(Color.WHITE)
                    button.setTextColor(colorApp)
                }, 70)
                checkPhoneNumberValidity(countrySpinner, phoneEditText, btnB)
            }
        }

        // Delete last character when 'a' is clicked
        btnA.setOnClickListener {
            val currentText = phoneEditText.text.toString()
            if (currentText.isNotEmpty()) {
                phoneEditText.setText(currentText.dropLast(1))
            }
            btnA.setBackgroundDrawable(drawable)
            btnA.setTextColor(Color.WHITE)
            Handler(Looper.getMainLooper()).postDelayed({
                btnA.setBackgroundColor(Color.WHITE)
                btnA.setTextColor(colorApp)
            }, 70)
            checkPhoneNumberValidity(countrySpinner, phoneEditText, btnB)

        }

        // Verification button (b)
        btnB.setOnClickListener {
            if (btnB.isClickable) {
                startActivity(Intent(this, Welcome::class.java))
            }
        }
    }

    private fun checkPhoneNumberValidity(countrySpinner: Spinner, phoneEditText: EditText, btnB: Button) {
        val countryCode = countrySpinner.selectedItem.toString()
        val isStart = when(countryCode) {
            "+212" -> phoneEditText.text.startsWith('6')
            "+33" -> phoneEditText.text.startsWith('8')
            "+1" -> phoneEditText.text.startsWith('1')
            else -> false
        }

         if (isStart && phoneEditText.text.length == 9) {
             btnB.isClickable = true
             btnB.background = ContextCompat.getDrawable(this, R.drawable.b) // Set button B background color to red
        }
        else {
             btnB.isClickable = false
             btnB.setBackgroundColor(Color.WHITE)
            btnB.setTextColor(getColor(R.color.appColor))
        }


    }
}

