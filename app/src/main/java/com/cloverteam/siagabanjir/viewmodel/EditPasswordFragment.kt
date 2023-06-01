package com.cloverteam.siagabanjir.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cloverteam.siagabanjir.databinding.FragmentEditPasswordBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.cloverteam.siagabanjir.db.DatabaseHandler
import com.cloverteam.siagabanjir.model.User
import com.cloverteam.siagabanjir.session.SessionManager

class EditPasswordFragment : Fragment() {
    private var _binding: FragmentEditPasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var user: User
    private lateinit var dbHandler: DatabaseHandler
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHandler = DatabaseHandler(requireContext())
        sessionManager = SessionManager(requireContext())
        val email = sessionManager.getEmail().toString() // Ganti dengan email user yang sesuai
        this.user = dbHandler.getUser(email)!!
        binding.buttonUpdatePassword.setOnClickListener {
            updatePassword()
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
                    val dialogBuilder = MaterialAlertDialogBuilder(requireContext())
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

                    val dialogBuilder = MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Update password gagal!")
                        .setMessage("Password akun anda gagal diupdate")
                        .setPositiveButton("OK", null)
                    dialogBuilder.show()
                }
            }


        } else {
            // Konfirmasi password terkini gagal
            binding.editTextCurrentPassword.error = "Password terkini salah"
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