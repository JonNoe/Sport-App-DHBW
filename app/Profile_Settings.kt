import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatTextView
import com.dhbw.dhbwsportapp.R

class Profile_Settings : Fragment() {

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
            Toast.makeText(activity, "Geburtstag eintragen", Toast.LENGTH_SHORT).show()
        }

        weightView.setOnClickListener {
            Toast.makeText(activity, "Gewicht eintragen", Toast.LENGTH_SHORT).show()
        }

        heightView.setOnClickListener {
            Toast.makeText(activity, "Größe eintragen", Toast.LENGTH_SHORT).show()
        }

        targetWeightView.setOnClickListener {
            Toast.makeText(activity, "Zielgewicht eintragen", Toast.LENGTH_SHORT).show()
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
                Toast.makeText(activity, "Bitte geben Sie gültige Werte für Gewicht und Größe ein", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
