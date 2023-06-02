package com.cloverteam.siagabanjir.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
    private var getPassword = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHandler = DatabaseHandler(requireContext())
        sessionManager = SessionManager(requireContext())
        val email = sessionManager.getUserId().toString()
        dbHandler.getUser(email) { user ->
            user?.let {
                getPassword = it.password
                // Set a click listener for the update button
                binding.buttonUpdatePassword.setOnClickListener { _ ->
                    updatePassword(it)
                }
            }
        }
    }

    private fun updatePassword(user: User) {
        // Reset error messages
        binding.textInputLayoutCurrentPassword.error = null
        binding.textInputLayoutNewPassword.error = null
        binding.textInputLayoutConfirmNewPassword.error = null

        // Get the input values
        val currentPassword = binding.editTextCurrentPassword.text.toString().trim()
        val newPassword = binding.editTextNewPassword.text.toString().trim()
        val confirmNewPassword = binding.editTextConfirmNewPassword.text.toString().trim()

        // Validate input
        var isValid = true

        if (currentPassword.isEmpty()) {
            binding.textInputLayoutCurrentPassword.error = "Masukkan password lama"
            isValid = false
        }
        if (currentPassword != getPassword) {
            binding.textInputLayoutCurrentPassword.error = "Password lama tidak valid"
            isValid = false
        }
        if (newPassword.isEmpty()) {
            binding.textInputLayoutNewPassword.error = "Masukkan password baru"
            isValid = false
        }

        if (confirmNewPassword.isEmpty()) {
            binding.textInputLayoutConfirmNewPassword.error = "Konfirmasi password baru"
            isValid = false
        }
        if (confirmNewPassword != newPassword) {
            binding.textInputLayoutConfirmNewPassword.error = "Konfirmasi password salah"
            isValid = false
        }

        if (isValid) {
            val updatedUser = User(
                user.id,
                user.email,
                user.namaLengkap,
                user.noTelepon,
                user.alamat,
                newPassword // Assuming the password is not editable in this screen
            )
            updateUserPassword(updatedUser)

        }

    }

    private fun updateUserPassword(user: User) {
        MaterialAlertDialogBuilder(requireContext()).setTitle("Perbaharui password ?")
            .setMessage("Anda akan memperbaharui password anda")
            .setPositiveButton("OK") { _, _ ->
                dbHandler.updatePassword(user) { success ->
                    if (success) {
                        // Update successful
                        showSuccessDialog()
                    } else {
                        // Update failed
                        showErrorDialog()
                    }
                }
            }.setNegativeButton("No") { _, _ ->
            }.show()
    }
    private fun showSuccessDialog() {
        MaterialAlertDialogBuilder(requireContext()).setTitle("Update Password berhasil")
            .setMessage("Password anda berhasil diperbaharui")
            .setPositiveButton("OK") { _, _ ->
                // Tambahkan pemanggilan recreate() di dalam tindakan positif tombol OK
                requireActivity().recreate()
            }.show()
    }
    private fun showErrorDialog() {
        Toast.makeText(requireContext(), "Update password gagal", Toast.LENGTH_SHORT).show()
    }
}
