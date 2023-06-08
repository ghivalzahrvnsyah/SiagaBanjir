package com.cloverteam.siagabanjir.viewmodel

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cloverteam.siagabanjir.databinding.RegisterActivityBinding
import com.cloverteam.siagabanjir.db.DatabaseHandler
import com.cloverteam.siagabanjir.model.User
import com.cloverteam.siagabanjir.session.SessionManager

class Registrasi : AppCompatActivity() {
    private lateinit var binding: RegisterActivityBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var databaseHandler: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)
        if (sessionManager.isLoggedIn()) {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish() // Menutup Activity Login agar tidak dapat diakses lagi
        }
        databaseHandler = DatabaseHandler(this)
        // Tombol Registrasi diklik
        binding.registrasi.setOnClickListener {
            // Mengambil input dari user
            val email = binding.emailInput.text.toString()
            val namaLengkap = binding.namaLengkapInput.text.toString()
            val noTlp = binding.noTelpInput.text.toString()
            val alamat = binding.alamatInput.text.toString()
            val password = binding.passwordInput.text.toString()
            val konfirmPassword = binding.konfirmPasswordInput.text.toString()

            // Validasi input pengguna
            if (
                isValidEmail(email) &&
                isValidNamaLengkap(namaLengkap) &&
                isValidPhone(noTlp) &&
                isValidAlamat(alamat) &&
                isValidPassword(password, konfirmPassword)
            ) {
                binding.progressBar.visibility = View.VISIBLE

                // Check if the email already exists in the database
                databaseHandler.getUserByEmail(email) { user ->
                    if (user == null) {
                        // Email is not found, proceed with registration
                        val newUser = User(
                            email = email,
                            namaLengkap = namaLengkap,
                            noTelepon = noTlp,
                            alamat = alamat,
                            password = password
                        )

                        databaseHandler.addUser(newUser) { isSuccess ->
                            binding.progressBar.visibility = View.GONE

                            if (isSuccess) {
                                sessionManager.createLoginSession(newUser.id)
                                val intent = Intent(this, Home::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                val message = "Gagal mengirim data ke database"
                                showToast(message)
                            }
                        }
                    } else {
                        // Email already exists in the database
                        binding.progressBar.visibility = View.GONE
                        val message = "Email sudah terdaftar"
                        binding.emailInput.error = message
                        showToast(message)
                    }
                }
            } else {
                val message = "Mohon lengkapi data dengan benar"
                showToast(message)
            }
        }

        // Tombol Login diklik
        binding.doLogin.setOnClickListener {
            // Menampilkan pesan dan pindah ke halaman Login
            val message = "Silahkan Login"
            showToast(message)
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        // Validasi format email menggunakan Regex
        return if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            true
        } else {
            // Menampilkan pesan error jika format email tidak valid
            binding.emailInput.error = "Masukkan email dengan format yang benar"
            false
        }
    }

    private fun isValidNamaLengkap(namaLengkap: String): Boolean {
        // Validasi nama lengkap tidak mengandung angka atau karakter khusus
        return if (namaLengkap.isNotEmpty() && !namaLengkap.matches(Regex(".*\\d.*")) && !namaLengkap.matches(Regex(".*[^\\p{L}\\s].*"))) {
            true
        } else {
            // Menampilkan pesan error jika nama lengkap tidak valid
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

    private fun showToast(message: String) {
        // Menampilkan toast dengan pesan
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}



