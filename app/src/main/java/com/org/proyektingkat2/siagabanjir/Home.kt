package com.org.proyektingkat2.siagabanjir

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.org.proyektingkat2.db.DatabaseHandler
import com.org.proyektingkat2.session.SessionManager
import com.org.proyektingkat2.siagabanjir.databinding.HomeBinding
import com.org.proyektingkat2.siagabanjir.databinding.RegisterActivityBinding

class Home: AppCompatActivity() {
    private lateinit var binding: HomeBinding
    private lateinit var databaseHandler: DatabaseHandler
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= HomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseHandler = DatabaseHandler(this)
        sessionManager = SessionManager(this)
        val email = sessionManager.getEmail()

        val getUser = databaseHandler.getUser(email.toString())
        if (getUser != null) {
            binding.nama.text = getUser.namaLengkap
            binding.email.text = getUser.email
            binding.notlp.text =getUser.noTelepon
            binding.alamat.text =getUser.alamat
        }

    }
}