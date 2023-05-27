package com.org.proyektingkat2.siagabanjir

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.org.proyektingkat2.db.DatabaseHandler
import com.org.proyektingkat2.session.SessionManager
import com.org.proyektingkat2.siagabanjir.databinding.LoginActivityBinding
import com.org.proyektingkat2.siagabanjir.databinding.RegisterActivityBinding

class Home: AppCompatActivity() {
    private lateinit var binding: RegisterActivityBinding
    private lateinit var databaseHandler: DatabaseHandler
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= RegisterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}