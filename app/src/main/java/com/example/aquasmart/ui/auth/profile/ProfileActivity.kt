package com.example.aquasmart.ui.auth.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.aquasmart.API.ApiConfig
import com.example.aquasmart.R
import com.example.aquasmart.databinding.ActivityProfileBinding
import com.example.aquasmart.databinding.CustomLogoutDialogBinding
import com.example.aquasmart.ui.auth.Login.LoginActivity
import com.example.aquasmart.ui.auth.profile.ProfileViewModel
import com.example.aquasmart.ui.auth.profile.ProfileViewModelFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        loadProfileImage()
        val token = getAuthToken()

        val factory = ProfileViewModelFactory(application, ApiConfig.apiService)
        profileViewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)

        // Memantau data profil yang diterima dari API
        profileViewModel.profileData.observe(this, { profileResponse ->
            profileResponse?.let {
                // Menampilkan data profil lainnya seperti nama, email, dan nomor telepon
                binding.nameProfile.setText(it.data.name)
                binding.emailProfile.setText(it.data.email)
                binding.phoneProfile.setText(it.data.phoneNumber)
                binding.birthdateProfile.setText(it.data.dateBirth)

                // Menampilkan gambar profil dari URL jika ada
                val imageUrl = it.data.profilePicture
                Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(binding.imageView)
            }
        })

        profileViewModel.errorMessage.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
        profileViewModel.getProfile("Bearer $token")

        binding.btnPickImage.setOnClickListener {
            openImagePicker()
        }
        binding.btnLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }
    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imagePickerLauncher.launch(intent)
    }

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedImageUri = data?.data
                selectedImageUri?.let {
                    Log.d("ImagePicker", "Selected URI: $it")
                    binding.imageView.setImageURI(it)
                    val file = uriToFile(it, applicationContext)
                    saveProfileImage(file)
                    loadProfileImage()
                }
            }
        }

    private fun uriToFile(uri: Uri, context: Context): File {
        val contentResolver = context.contentResolver
        val file = File(context.cacheDir, "profile_picture.jpg")
        try {
            contentResolver.openInputStream(uri)?.use { inputStream ->
                FileOutputStream(file).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }

    private fun saveProfileImage(file: File) {
        val sharedPref = getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("profile_picture_path", file.absolutePath)
            apply()
        }
    }

    private fun loadProfileImage() {
        val sharedPref = getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
        val imagePath = sharedPref.getString("profile_picture_path", null)

        imagePath?.let {
            val file = File(it)
            if (file.exists()) {
                Glide.with(this)
                    .load(file)
                    .placeholder(R.drawable.placeholder_image) // Gambar placeholder saat loading
                    .error(R.drawable.error_image) // Gambar error jika gagal
                    .into(binding.imageView)
            }
        }
    }

    private fun getAuthToken(): String {
        val sharedPref = getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
        return sharedPref.getString("auth_token", "") ?: ""
    }

    private fun showLogoutConfirmationDialog() {
        val dialogView = layoutInflater.inflate(R.layout.custom_logout_dialog, null)
        val binding = CustomLogoutDialogBinding.bind(dialogView)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)

        val alertDialog = builder.create()
        binding.btnYes.setOnClickListener {
            logout()
            alertDialog.dismiss()
        }
        binding.btnNo.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    private fun logout() {
        val sharedPref = getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            remove("auth_token")
            remove("profile_picture_path")
            apply()
        }

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
