package com.dhbw.dhbwsportapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton

class ProfileFragment : Fragment() {

    private lateinit var editProfileName: AppCompatButton
    private var profileName: String = "Profile Name"
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var profileImageButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val editProfileButton: AppCompatButton = view.findViewById(R.id.Edit_Profile)
        editProfileName = view.findViewById(R.id.Profile_Name)
        editProfileName.text = profileName

        profileImageButton = view.findViewById(R.id.profile_image_button) // Hier wird profileImageButton initialisiert

        editProfileButton.setOnClickListener {
            Toast.makeText(activity, "Edit Profile gedrückt", Toast.LENGTH_SHORT).show()
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
            val selectedImageUri: Uri? = data.data
            selectedImageUri?.let {
                profileImageButton.setImageURI(it)
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
                dialog.dismiss()
            }
            .setNegativeButton("Abbrechen") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }
}
