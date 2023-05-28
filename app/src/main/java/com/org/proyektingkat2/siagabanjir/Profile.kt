package com.org.proyektingkat2.siagabanjir

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.org.proyektingkat2.db.DatabaseHandler
import com.org.proyektingkat2.model.User
import com.google.android.material.textfield.TextInputEditText
import com.org.proyektingkat2.session.SessionManager
import com.org.proyektingkat2.siagabanjir.databinding.ProfileActivityBinding

class Profile : AppCompatActivity() {

    private lateinit var binding: ProfileActivityBinding
    private lateinit var user: User
    private lateinit var dbHandler: DatabaseHandler
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dbHandler = DatabaseHandler(this)
        sessionManager = SessionManager(this)
        val email = sessionManager.getEmail().toString() // Ganti dengan email user yang sesuai
        this.user = dbHandler.getUser(email)!!

        // Mengisi informasi terkini user ke dalam text field layout
        binding.editTextEmail.setText(user.email)
        binding.editTextNamaLengkap.setText(user.namaLengkap)
        binding.editTextNoTlp.setText(user.noTelepon)
        binding.editTextAlamat.setText(user.alamat)
        binding.buttonUpdate.setOnClickListener {
            updateUser()
        }
        binding.buttonUpdatePassword.setOnClickListener {
            updatePassword()
        }
    }

    private fun updateUser() {
        val updateEmail = binding.editTextEmail.text.toString()
        val updatedNamaLengkap = binding.editTextNamaLengkap.text.toString()
        val updatedNoTlp = binding.editTextNoTlp.text.toString()
        val updatedAlamat = binding.editTextAlamat.text.toString()


        // Update atribut user
        user.email = updateEmail
        user.namaLengkap = updatedNamaLengkap
        user.noTelepon = updatedNoTlp
        user.alamat = updatedAlamat

        // Update user dalam database
        val rowsAffected = dbHandler.updateUser(user)

        if (rowsAffected > 0) {
            // Update berhasil
            // Tambahkan tindakan yang diinginkan setelah update berhasil
            val dialogBuilder = MaterialAlertDialogBuilder(this)
                .setTitle("Update Berhasil!")
                .setMessage("Informasi akun anda berhasil diupdate")
                .setPositiveButton("OK", null)
            dialogBuilder.show()
        } else {
            // Update gagal
            // Tambahkan tindakan yang diinginkan jika update gagal
            Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updatePassword() {
        val currentPassword = binding.editTextCurrentPassword.text.toString()
        val newPassword = binding.editTextNewPassword.text.toString()
        val confirmNewPassword = binding.editTextConfirmNewPassword.text.toString()

        if (currentPassword == user.password) {
            if (isValidPassword(newPassword, confirmNewPassword)) {
                // Konfirmasi password terkini berhasil
                val rowsAffected = dbHandler.updatePassword(user.email, newPassword)

                if (rowsAffected > 0) {
                    // Update password berhasil
                    // Tambahkan tindakan yang diinginkan setelah update password berhasil
                    val dialogBuilder = MaterialAlertDialogBuilder(this)
                        .setTitle("Update password berhasil!")
                        .setMessage("Password akun anda berhasil diupdate")
                        .setPositiveButton("OK", null)
                    dialogBuilder.show()
                    binding.editTextCurrentPassword.text = null
                    binding.editTextNewPassword.text = null
                    binding.editTextConfirmNewPassword.text = null
                } else {
                    // Update password gagal
                    // Tambahkan tindakan yang diinginkan jika update password gagal

                    Toast.makeText(this, "Gagal update password", Toast.LENGTH_SHORT).show()
                }
            }


        } else {
            // Konfirmasi password terkini gagal
            binding.editTextCurrentPassword.error = "Password terkini salah"
            Toast.makeText(this, "Konfirmasi password lama salah", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidPassword(password: String, konfirmPassword: String): Boolean {
        return if (password.isNotEmpty() && password.length >= 6 && password == konfirmPassword) {
            true
        } else if (!password.isNotEmpty()){
            binding.editTextNewPassword.error =
                "Masukkan password baru dengan panjang minimal 6 karakter"
            false
        }
        else if(password.length < 6){
            binding.editTextNewPassword.error =
                "Password minimal 6 karakter"
            false
        }
        else{
            binding.editTextConfirmNewPassword.error = "Konfirmasi password salah"
            false
        }

    }
}




