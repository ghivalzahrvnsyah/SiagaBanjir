package com.org.proyektingkat2.siagabanjir

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.org.proyektingkat2.db.DatabaseHandler
import com.org.proyektingkat2.session.SessionManager
import com.org.proyektingkat2.siagabanjir.databinding.LoginActivityBinding

class Login : AppCompatActivity() {
    private lateinit var binding: LoginActivityBinding
    private lateinit var databaseHandler: DatabaseHandler
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseHandler = DatabaseHandler(this)
        sessionManager = SessionManager(this)

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
            Toast.makeText(this, "-", Toast.LENGTH_SHORT)
        }

        binding.login.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            if (isValidEmail(email) && isValidPassword(password)) {
                val user = databaseHandler.getUser(email)
                if (user != null && user.password == password) {
                    sessionManager.createLoginSession(user.email)
                    val intent = Intent(this, Home::class.java)
                    startActivity(intent)
                    finish() // Menutup Activity Login agar tidak dapat diakses lagi
                } else {
                    // Login gagal, tampilkan pesan kesalahan
                    binding.emailInput.error = "Username atau password salah"
                    binding.passwordInput.text = null
                    Toast.makeText(this, "Username atau password salah", Toast.LENGTH_SHORT).show()
                }
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
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailInput.error = "Masukkan email valid!"
            false
        }
        else {
            binding.emailInput.error = "Masukkan email !"
            false
        }
    }

    private fun isValidPassword(password: String): Boolean {
        return if (password.isNotEmpty()) {
            true
        } else {
            binding.passwordInput.error =
                "Masukkan password !"
            false
        }
    }
}
