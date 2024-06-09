package com.dhbw.dhbwsportapp

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import java.text.SimpleDateFormat
import java.util.Locale

class ProfileActivity : AppCompatActivity() {

    private fun saveData(key: String, value: String) {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    private fun loadData(key: String): String? {
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val birthdayView: AppCompatTextView = findViewById(R.id.birthday_view)
        val weightView: AppCompatTextView = findViewById(R.id.weight_view)
        val heightView: AppCompatTextView = findViewById(R.id.height_view)
        val targetWeightView: AppCompatTextView = findViewById(R.id.target_weight_view)
        val genderSpinner: Spinner = findViewById(R.id.gender_spinner)
        val inputWeight: EditText = findViewById(R.id.input_weight)
        val inputHeight: EditText = findViewById(R.id.input_height)
        val calculateBmiButton: Button = findViewById(R.id.calculate_bmi_button)
        val bmiResult: TextView = findViewById(R.id.bmi_result)

        // Load saved data and update views
        loadData("birthday")?.let { birthdayView.text = it }
        loadData("weight")?.let { weightView.text = it }
        loadData("height")?.let { heightView.text = it }
        loadData("target_weight")?.let { targetWeightView.text = it }

        // Setup gender spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.gender_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            genderSpinner.adapter = adapter
        }

        loadData("gender")?.let {
            val genderArray = resources.getStringArray(R.array.gender_array)
            val genderIndex = genderArray.indexOf(it)
            if (genderIndex >= 0) {
                genderSpinner.setSelection(genderIndex)
            }
        }

        birthdayView.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this)
            datePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate.time)
                saveData("birthday", formattedDate)
                birthdayView.text = formattedDate // Update the TextView
            }
            datePickerDialog.show()
        }

        weightView.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val editText = EditText(this)
            editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            builder.setTitle("Gewicht eintragen")
                .setView(editText)
                .setPositiveButton("OK") { dialog, _ ->
                    val weight = editText.text.toString().toDoubleOrNull()
                    if (weight != null) {
                        saveData("weight", weight.toString())
                        weightView.text = weight.toString() // Update the TextView
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Abbrechen") { dialog, _ ->
                    dialog.dismiss()
                }
            val dialog = builder.create()
            dialog.show()
        }

        heightView.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val editText = EditText(this)
            editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            builder.setTitle("Größe eintragen (in cm)")
                .setView(editText)
                .setPositiveButton("OK") { dialog, _ ->
                    val height = editText.text.toString().toDoubleOrNull()
                    if (height != null) {
                        saveData("height", height.toString())
                        heightView.text = height.toString() // Update the TextView
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Abbrechen") { dialog, _ ->
                    dialog.dismiss()
                }
            val dialog = builder.create()
            dialog.show()
        }

        targetWeightView.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val editText = EditText(this)
            editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            builder.setTitle("Zielgewicht eintragen")
                .setView(editText)
                .setPositiveButton("OK") { dialog, _ ->
                    val targetWeight = editText.text.toString().toDoubleOrNull()
                    if (targetWeight != null) {
                        saveData("target_weight", targetWeight.toString())
                        targetWeightView.text = targetWeight.toString() // Update the TextView
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Abbrechen") { dialog, _ ->
                    dialog.dismiss()
                }
            val dialog = builder.create()
            dialog.show()
        }

        genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedGender = parent.getItemAtPosition(position).toString()
                saveData("gender", selectedGender)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        calculateBmiButton.setOnClickListener {
            val weight = inputWeight.text.toString().toDoubleOrNull()
            val heightCm = inputHeight.text.toString().toDoubleOrNull()

            if (weight != null && heightCm != null) {
                val heightM = heightCm / 100
                val bmi = weight / (heightM * heightM)
                bmiResult.text = String.format("Ihr BMI: %.2f", bmi)
            } else {
                Toast.makeText(
                    this,
                    "Bitte geben Sie gültige Werte für Gewicht und Größe ein",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}