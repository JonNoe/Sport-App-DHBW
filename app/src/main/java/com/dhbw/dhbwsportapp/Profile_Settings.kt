package com.dhbw.dhbwsportapp

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*


class Profile_Settings : Fragment() {

    private fun saveData(key: String, value: String) {
        val sharedPreferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile__settings, container, false)

        val birthdayView: AppCompatTextView = view.findViewById(R.id.birthday_view)
        val weightView: AppCompatTextView = view.findViewById(R.id.weight_view)
        val heightView: AppCompatTextView = view.findViewById(R.id.height_view)
        val targetWeightView: AppCompatTextView = view.findViewById(R.id.target_weight_view)
        val genderSpinner: Spinner = view.findViewById(R.id.gender_spinner)
        val inputWeight: EditText = view.findViewById(R.id.input_weight)
        val inputHeight: EditText = view.findViewById(R.id.input_height)
        val calculateBmiButton: Button = view.findViewById(R.id.calculate_bmi_button)
        val bmiResult: TextView = view.findViewById(R.id.bmi_result)

        birthdayView.setOnClickListener {
            val datePickerDialog = DatePickerDialog(requireContext())
            datePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate.time)
                Toast.makeText(activity, "Geburtstag: $formattedDate", Toast.LENGTH_SHORT).show()
                saveData("birthday", formattedDate)
            }
            datePickerDialog.show()
        }

        weightView.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            val editText = EditText(requireContext())
            editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            builder.setTitle("Gewicht eintragen")
                .setView(editText)
                .setPositiveButton("OK") { dialog, _ ->
                    val weight = editText.text.toString().toDoubleOrNull()
                    if (weight != null) {
                        Toast.makeText(activity, "Gewicht: $weight", Toast.LENGTH_SHORT).show()
                        saveData("weight", weight.toString())
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
            val builder = AlertDialog.Builder(requireContext())
            val editText = EditText(requireContext())
            editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            builder.setTitle("Größe eintragen (in cm)")
                .setView(editText)
                .setPositiveButton("OK") { dialog, _ ->
                    val height = editText.text.toString().toDoubleOrNull()
                    if (height != null) {
                        Toast.makeText(activity, "Größe: $height cm", Toast.LENGTH_SHORT).show()
                        saveData("height", height.toString())
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
            val builder = AlertDialog.Builder(requireContext())
            val editText = EditText(requireContext())
            editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            builder.setTitle("Zielgewicht eintragen")
                .setView(editText)
                .setPositiveButton("OK") { dialog, _ ->
                    val targetWeight = editText.text.toString().toDoubleOrNull()
                    if (targetWeight != null) {
                        Toast.makeText(activity, "Zielgewicht: $targetWeight", Toast.LENGTH_SHORT).show()
                        saveData("target_weight", targetWeight.toString())
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Abbrechen") { dialog, _ ->
                    dialog.dismiss()
                }
            val dialog = builder.create()
            dialog.show()
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.gender_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            genderSpinner.adapter = adapter
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
                    activity,
                    "Bitte geben Sie gültige Werte für Gewicht und Größe ein",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return view
    }
}
