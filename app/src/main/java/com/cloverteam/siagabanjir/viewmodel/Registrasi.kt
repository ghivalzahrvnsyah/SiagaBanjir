package com.cloverteam.siagabanjir.viewmodel


import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cloverteam.siagabanjir.databinding.RegisterActivityBinding
import com.cloverteam.siagabanjir.db.DatabaseHandler
import com.cloverteam.siagabanjir.model.User
import com.cloverteam.siagabanjir.session.SessionManager


class Registrasi : AppCompatActivity() {
    private lateinit var binding: RegisterActivityBinding
    private lateinit var databaseHandler: DatabaseHandler
    private lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHandler = DatabaseHandler(this)
        sessionManager = SessionManager(this)

        if (sessionManager.isLoggedIn()) {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish() // Menutup Activity Login agar tidak dapat diakses lagi
        }


        binding.registrasi.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val namaLengkap = binding.namaLengkapInput.text.toString()
            val noTlp = binding.noTelpInput.text.toString()
            val alamat = binding.alamatInput.text.toString()
            val password = binding.passwordInput.text.toString()
            val konfirmPassword = binding.konfirmPasswordInput.text.toString()
            if (
                isValidEmail(email) &&
                isValidNamaLengkap(namaLengkap) &&
                isValidPhone(noTlp) &&
                isValidAlamat(alamat) &&
                isValidPassword(password, konfirmPassword)
            ) {
                val user = User(0, email, namaLengkap, noTlp, alamat, password)
                val userId = databaseHandler.addUser(user)
                if (userId != -1L) {
                    val message = "Registrasi Berhasil, Silahkan login"
                    val intent = Intent(this, Login::class.java)
                    intent.putExtra("toast_message", message)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Registrasi gagal", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Mohon lengkapi data dengan benar", Toast.LENGTH_SHORT).show()

            }
        }
        binding.doLogin.setOnClickListener {
            val message = "Silahkan Login"
            val intent = Intent(this, Login::class.java)
            intent.putExtra("toast_message", message)
            startActivity(intent)
        }


    }

    private fun isValidEmail(email: String): Boolean {
        return if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            val existingUser = databaseHandler.getUser(email)
            if (existingUser != null) {
                binding.emailInput.error = "Email sudah terdaftar"
                false
            } else {
                true
            }
        } else {
            binding.emailInput.error = "Masukkan email dengan format yang benar"
            false
        }
    }


    private fun isValidNamaLengkap(namaLengkap: String): Boolean {
        return if (namaLengkap.isNotEmpty() && !namaLengkap.matches(Regex(".*\\d.*")) && !namaLengkap.matches(
                Regex(".*[^\\p{L}\\s].*")
            )
        ) {
            true
        } else {
            binding.namaLengkapInput.error = "Nama lengkap tidak valid"
            false
        }
    }

    private fun isValidAlamat(alamat: String): Boolean {
        return if (alamat.isNotEmpty()) {
            true
        } else {
            binding.alamatInput.error = "Harap masukkan alamat anda"
            false
        }
    }

    private fun isValidPhone(phone: String): Boolean {
        return if (phone.isNotEmpty() && phone.matches(Regex("[0-9]+")) && phone.length >= 10) {
            true
        } else {
            binding.noTelpInput.error = "Masukkan nomor telepon dengan format yang benar"
            false
        }
    }

    private fun isValidPassword(password: String, konfirmPassword: String): Boolean {
        return if (password.isNotEmpty() && password.length >= 6 && password == konfirmPassword) {
            true
        } else if (!password.isNotEmpty()) {
            binding.passwordInput.error =
                "Masukkan password dengan panjang minimal 6 karakter"
            false
        } else if (password.length < 6) {
            binding.passwordInput.error =
                "Password minimal 6 karakter"
            false
        } else {
            binding.konfirmPasswordInput.error = "Konfirmasi password salah"
            false
        }

    }

}


