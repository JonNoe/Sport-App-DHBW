package com.dhbw.dhbwsportapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import java.io.ByteArrayOutputStream

class ProfileFragment : Fragment() {

    private lateinit var editProfileName: AppCompatButton
    private var profileName: String = "Profile Name"
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var profileImageButton: ImageButton
    private lateinit var sharedPreferences: SharedPreferences
    private var profileImageBitmap: Bitmap? = null

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        sharedPreferences =
            requireContext().getSharedPreferences("ProfilePreferences", Context.MODE_PRIVATE)

        val editProfileButton: AppCompatButton = view.findViewById(R.id.Edit_Profile)
        editProfileName = view.findViewById(R.id.Profile_Name)
        profileImageButton = view.findViewById(R.id.profile_image_button)

        loadProfileData()

        editProfileButton.setOnClickListener {
            navigateToProfileSettings()
        }

        editProfileName.setOnClickListener {
            showChangeNameDialog()
        }

        profileImageButton.setOnClickListener {
            selectImageFromGallery()
        }

        return view
    }

    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            selectedImageUri?.let {
                val inputStream = requireContext().contentResolver.openInputStream(selectedImageUri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                profileImageBitmap = bitmap
                profileImageButton.setImageBitmap(bitmap)
                saveProfileImageBitmap(bitmap)
            }
        }
    }

    private fun showChangeNameDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val editText = EditText(requireContext())
        editText.hint = "Neuer Name"
        builder.setTitle("Name ändern")
            .setView(editText)
            .setPositiveButton("Ändern") { dialog, _ ->
                val newName = editText.text.toString()
                profileName = newName
                editProfileName.text = newName
                saveProfileName(newName)
                dialog.dismiss()
            }
            .setNegativeButton("Abbrechen") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }

    private fun saveProfileName(name: String) {
        with(sharedPreferences.edit()) {
            putString("profile_name", name)
            apply()
        }
    }

    private fun saveProfileImageBitmap(bitmap: Bitmap) {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val imageBytes = outputStream.toByteArray()

        with(sharedPreferences.edit()) {
            putString("profile_image", Base64.encodeToString(imageBytes, Base64.DEFAULT))
            apply()
        }
    }

    private fun loadProfileData() {
        profileName = sharedPreferences.getString("profile_name", "Profile Name") ?: "Profile Name"
        editProfileName.text = profileName

        val profileImageString = sharedPreferences.getString("profile_image", null)
        profileImageString?.let {
            val imageBytes = Base64.decode(it, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            profileImageBitmap = bitmap
            profileImageButton.setImageBitmap(bitmap)
        }

    }

    private fun navigateToProfileSettings() {
        val intent = Intent(requireContext(), ProfileActivity::class.java)
        startActivity(intent)
    }

}