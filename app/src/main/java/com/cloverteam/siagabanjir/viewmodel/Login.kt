package com.cloverteam.siagabanjir.viewmodel

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cloverteam.siagabanjir.databinding.LoginActivityBinding
import com.cloverteam.siagabanjir.model.User
import com.cloverteam.siagabanjir.session.SessionManager
import com.google.firebase.database.*

class Login : AppCompatActivity() {
    private lateinit var binding: LoginActivityBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val progressBar = binding.progressBar
        sessionManager = SessionManager(this)
        database = FirebaseDatabase.getInstance().reference

        if (sessionManager.isLoggedIn()) {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish() // Menutup Activity Login agar tidak dapat diakses lagi
        }

        val intent = intent
        val message = intent.getStringExtra("toast_message")
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Selamat Datang", Toast.LENGTH_SHORT).show()
        }

        binding.login.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            if (isValidEmail(email) && isValidPassword(password)) {
                progressBar.visibility = View.VISIBLE // Tampilkan ProgressBar saat melakukan akses ke database
                val userQuery = database.child("users").orderByChild("email").equalTo(email)
                userQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        progressBar.visibility = View.GONE // Sembunyikan ProgressBar setelah akses database selesai
                        if (dataSnapshot.exists()) {
                            for (userSnapshot in dataSnapshot.children) {
                                val user = userSnapshot.getValue(User::class.java)
                                if (user != null && user.password == password) {
                                    sessionManager.createLoginSession(user.id)
                                    val intent = Intent(this@Login, Home::class.java)
                                    startActivity(intent)
                                    finish() // Menutup Activity Login agar tidak dapat diakses lagi
                                    return
                                }
                            }
                        }
                        // Login gagal, tampilkan pesan kesalahan
                        binding.emailInput.error = "Username atau password salah"
                        binding.passwordInput.text = null
                        Toast.makeText(this@Login, "Username atau password salah", Toast.LENGTH_SHORT).show()
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        progressBar.visibility = View.GONE // Sembunyikan ProgressBar saat terjadi gangguan jaringan
                        // Error saat membaca data dari Firebase Realtime Database
                        Toast.makeText(this@Login, "Terjadi kesalahan. Silakan coba lagi.", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

        binding.doRegistrasi.setOnClickListener {
            val intent = Intent(this, Registrasi::class.java)
            startActivity(intent)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            true
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInput.error = "Masukkan email valid!"
            false
        } else {
            binding.emailInput.error = "Masukkan email!"
            false
        }
    }

    private fun isValidPassword(password: String): Boolean {
        return if (password.isNotEmpty()) {
            true
        } else {
            binding.passwordInput.error =
                "Masukkan password!"
            false
        }
    }
}
