package com.org.proyektingkat2.siagabanjir

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.org.proyektingkat2.db.DatabaseHandler
import com.org.proyektingkat2.session.SessionManager
import com.org.proyektingkat2.siagabanjir.databinding.LoginActivityBinding



class Login: AppCompatActivity() {
    private lateinit var binding: LoginActivityBinding
    private lateinit var databaseHandler: DatabaseHandler
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        databaseHandler = DatabaseHandler(this)

        val intent = intent
        val message = intent.getStringExtra("toast_message")
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()


        binding.login.setOnClickListener{
            val username = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()

            val user = databaseHandler.getUser(username)
            if (user != null && user.password == password) {
               databaseHandler.startSession(user)
                val intent = Intent(this, Home::class.java)
                startActivity(intent)
                //Toast.makeText(this, "LOGIN BERHASIL", Toast.LENGTH_SHORT).show()
            } else {
                // Login gagal, tampilkan pesan kesalahan
                Toast.makeText(this, "Username atau password salah", Toast.LENGTH_SHORT).show()
            }
        }


    }

}