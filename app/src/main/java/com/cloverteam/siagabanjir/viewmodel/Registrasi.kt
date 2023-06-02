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

class Registrasi : AppCompatActivity() {
    private lateinit var binding: RegisterActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

                // Membuat objek User
                val user = User(
                    email = email,
                    namaLengkap = namaLengkap,
                    noTelepon = noTlp,
                    alamat = alamat,
                    password = password
                )

                // Membuat objek DatabaseHandler
                val databaseHandler = DatabaseHandler(this)

                // Memanggil fungsi addUser dengan callback
                databaseHandler.addUser(user) { isSuccess ->
                    // Sembunyikan ProgressBar setelah pengiriman selesai
                    binding.progressBar.visibility = View.GONE

                    if (isSuccess) {
                        // Pengiriman data berhasil
                        val message = "Registrasi Berhasil, Silahkan login"
                        showToast(message)

                        // Pindah ke halaman Login
                        val intent = Intent(this, Login::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Pengiriman data gagal
                        val message = "Gagal mengirim data ke database"
                        showToast(message)
                    }
                }
            } else {
                // Menampilkan pesan error
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
        // Validasi alamat tidak kosong
        return alamat.isNotEmpty()
    }

    private fun isValidPhone(phone: String): Boolean {
        // Validasi format nomor telepon menggunakan Regex
        return phone.isNotEmpty() && phone.matches(Regex("[0-9]+")) && phone.length >= 10
    }

    private fun isValidPassword(password: String, konfirmPassword: String): Boolean {
        // Validasi password tidak kosong, minimal 6 karakter, dan sesuai dengan konfirmasi password
        return password.isNotEmpty() && password.length >= 6 && password == konfirmPassword
    }

    private fun showToast(message: String) {
        // Menampilkan toast dengan pesan
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}


//ver-2
//import android.content.Intent
//import android.os.Bundle
//import android.util.Patterns
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.cloverteam.siagabanjir.databinding.RegisterActivityBinding
//import com.cloverteam.siagabanjir.model.User
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ktx.database
//import com.google.firebase.ktx.Firebase
//
//class Registrasi : AppCompatActivity() {
//    private lateinit var binding: RegisterActivityBinding
//    private lateinit var databaseReference: DatabaseReference
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = RegisterActivityBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val url = "https://siaga-banjir-6b3e7-default-rtdb.asia-southeast1.firebasedatabase.app"
//        //val url = "https://siaga-banjir-6b3e7-default-rtdb.asia-southeast1.firebasedatabase.app/"// Ganti dengan URL Database yang dikonfigurasi dalam proyek Firebase Anda
//        databaseReference = FirebaseDatabase.getInstance(url).reference.child("users")
//        //databaseReference = Firebase.database.reference.child("users")
//
//        binding.registrasi.setOnClickListener {
//            val email = binding.emailInput.text.toString()
//            val namaLengkap = binding.namaLengkapInput.text.toString()
//            val noTlp = binding.noTelpInput.text.toString()
//            val alamat = binding.alamatInput.text.toString()
//            val password = binding.passwordInput.text.toString()
//            val konfirmPassword = binding.konfirmPasswordInput.text.toString()
//
//            if (
//                isValidEmail(email) &&
//                isValidNamaLengkap(namaLengkap) &&
//                isValidPhone(noTlp) &&
//                isValidAlamat(alamat) &&
//                isValidPassword(password, konfirmPassword)
//            ) {
//                val userId = databaseReference.push().key
//                val user = User(
//                    id = userId ?: "",
//                    email = email,
//                    namaLengkap = namaLengkap,
//                    noTelepon = noTlp,
//                    alamat = alamat,
//                    password = password
//                )
//
//                if (userId != null) {
//                    databaseReference.child(userId).setValue(user)
//                        .addOnCompleteListener { task ->
//                            if (task.isSuccessful) {
//                                val message = "Registrasi Berhasil, Silahkan login"
//                                showToast(message)
//                                val intent = Intent(this, Login::class.java)
//                                startActivity(intent)
//                                finish()
//                            } else {
//                                val message = "Registrasi gagal"
//                                showToast(message)
//                            }
//                        }
//                } else {
//                    val message = "Registrasi gagal"
//                    showToast(message)
//                }
//            } else {
//                val message = "Mohon lengkapi data dengan benar"
//                showToast(message)
//            }
//        }
//
//        binding.doLogin.setOnClickListener {
//            val message = "Silahkan Login"
//            showToast(message)
//            val intent = Intent(this, Login::class.java)
//            startActivity(intent)
//            finish()
//        }
//    }
//
//    private fun isValidEmail(email: String): Boolean {
//        return if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            true
//        } else {
//            binding.emailInput.error = "Masukkan email dengan format yang benar"
//            false
//        }
//    }
//
//    private fun isValidNamaLengkap(namaLengkap: String): Boolean {
//        return if (namaLengkap.isNotEmpty() && !namaLengkap.matches(Regex(".*\\d.*")) && !namaLengkap.matches(Regex(".*[^\\p{L}\\s].*"))) {
//            true
//        } else {
//            binding.namaLengkapInput.error = "Nama lengkap tidak valid"
//            false
//        }
//    }
//
//    private fun isValidAlamat(alamat: String): Boolean {
//        return alamat.isNotEmpty()
//    }
//
//    private fun isValidPhone(phone: String): Boolean {
//        return phone.isNotEmpty() && phone.matches(Regex("[0-9]+")) && phone.length >= 10
//    }
//
//    private fun isValidPassword(password: String, konfirmPassword: String): Boolean {
//        return password.isNotEmpty() && password.length >= 6 && password == konfirmPassword
//    }
//
//    private fun showToast(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//    }
//}


//ver 1
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Patterns
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.cloverteam.siagabanjir.databinding.RegisterActivityBinding
//import com.cloverteam.siagabanjir.db.DatabaseHandler
//import com.cloverteam.siagabanjir.model.User
//import com.cloverteam.siagabanjir.session.SessionManager
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.FirebaseDatabase.*
//import com.google.firebase.database.ktx.database
//import com.google.firebase.ktx.Firebase
//
//
//class Registrasi : AppCompatActivity() {
//    private lateinit var binding: RegisterActivityBinding
//    private lateinit var databaseHandler: DatabaseHandler
//    private lateinit var sessionManager: SessionManager
//    private lateinit var database: DatabaseReference
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = RegisterActivityBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        //databaseHandler = DatabaseHandler(this)
//        val url = "https://siaga-banjir-6b3e7-default-rtdb.asia-southeast1.firebasedatabase.app"
//        database = FirebaseDatabase.getInstance(url).reference
//        //getInstance("https://console.firebase.google.com/project/siaga-banjir-6b3e7/database/siaga-banjir-6b3e7-default-rtdb/data/~2F")//getReferenceFromUrl("https://siaga-banjir-6b3e7-default-rtdb.asia-southeast1.firebasedatabase.app")
//        sessionManager = SessionManager(this)
//
//        if (sessionManager.isLoggedIn()) {
//            val intent = Intent(this, Home::class.java)
//            startActivity(intent)
//            finish() // Menutup Activity Login agar tidak dapat diakses lagi
//        }
//
//
//        binding.registrasi.setOnClickListener {
//            //val userId = 0
//            val email = binding.emailInput.text.toString()
//            val namaLengkap = binding.namaLengkapInput.text.toString()
//            val noTlp = binding.noTelpInput.text.toString()
//            val alamat = binding.alamatInput.text.toString()
//            val password = binding.passwordInput.text.toString()
//            val konfirmPassword = binding.konfirmPasswordInput.text.toString()
//            if (
//                isValidEmail(email) &&
//                isValidNamaLengkap(namaLengkap) &&
//                isValidPhone(noTlp) &&
//                isValidAlamat(alamat) &&
//                isValidPassword(password, konfirmPassword)
//            ) {
//                val userId = database.child("users").push().key
//                val user = User(
//                    id = userId ?: "",
//                    email,
//                    namaLengkap,
//                    noTlp,
//                    alamat,
//                    password)
//                //val userId = databaseHandler.addUser(user)
//
//                if (userId != null) {
//                    database.child("users").child(userId).setValue(user)
//                    val message = "Registrasi Berhasil, Silahkan login"
//                    val intent = Intent(this, Login::class.java)
//                    intent.putExtra("toast_message", message)
//                    startActivity(intent)
//                }
////                if (userId != -1L) {
////                    val message = "Registrasi Berhasil, Silahkan login"
////                    val intent = Intent(this, Login::class.java)
////                    intent.putExtra("toast_message", message)
////                    startActivity(intent)
//                else {
//                    Toast.makeText(this, "Registrasi gagal", Toast.LENGTH_SHORT).show()
//                }
//            } else {
//                Toast.makeText(this, "Mohon lengkapi data dengan benar", Toast.LENGTH_SHORT).show()
//
//            }
//        }
//        binding.doLogin.setOnClickListener {
//            val message = "Silahkan Login"
//            val intent = Intent(this, Login::class.java)
//            intent.putExtra("toast_message", message)
//            startActivity(intent)
//        }
//
//
//    }
//
//    private fun isValidEmail(email: String): Boolean {
//        return if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            val existingUser = databaseHandler.getUser(email)
//            if (existingUser != null) {
//                binding.emailInput.error = "Email sudah terdaftar"
//                false
//            } else {
//                true
//            }
//        } else {
//            binding.emailInput.error = "Masukkan email dengan format yang benar"
//            false
//        }
//    }
//
//
//    private fun isValidNamaLengkap(namaLengkap: String): Boolean {
//        return if (namaLengkap.isNotEmpty() && !namaLengkap.matches(Regex(".*\\d.*")) && !namaLengkap.matches(
//                Regex(".*[^\\p{L}\\s].*")
//            )
//        ) {
//            true
//        } else {
//            binding.namaLengkapInput.error = "Nama lengkap tidak valid"
//            false
//        }
//    }
//
//    private fun isValidAlamat(alamat: String): Boolean {
//        return if (alamat.isNotEmpty()) {
//            true
//        } else {
//            binding.alamatInput.error = "Harap masukkan alamat anda"
//            false
//        }
//    }
//
//    private fun isValidPhone(phone: String): Boolean {
//        return if (phone.isNotEmpty() && phone.matches(Regex("[0-9]+")) && phone.length >= 10) {
//            true
//        } else {
//            binding.noTelpInput.error = "Masukkan nomor telepon dengan format yang benar"
//            false
//        }
//    }
//
//    private fun isValidPassword(password: String, konfirmPassword: String): Boolean {
//        return if (password.isNotEmpty() && password.length >= 6 && password == konfirmPassword) {
//            true
//        } else if (!password.isNotEmpty()) {
//            binding.passwordInput.error =
//                "Masukkan password dengan panjang minimal 6 karakter"
//            false
//        } else if (password.length < 6) {
//            binding.passwordInput.error =
//                "Password minimal 6 karakter"
//            false
//        } else {
//            binding.konfirmPasswordInput.error = "Konfirmasi password salah"
//            false
//        }
//
//    }
//
//}


