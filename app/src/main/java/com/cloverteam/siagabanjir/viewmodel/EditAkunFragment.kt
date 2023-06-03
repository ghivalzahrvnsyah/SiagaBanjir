package com.cloverteam.siagabanjir.viewmodel

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.cloverteam.siagabanjir.databinding.FragmentEditAkunBinding
import com.cloverteam.siagabanjir.db.DatabaseHandler
import com.cloverteam.siagabanjir.model.User
import com.cloverteam.siagabanjir.session.SessionManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditAkunFragment : Fragment() {
    private var _binding: FragmentEditAkunBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbHandler: DatabaseHandler
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditAkunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHandler = DatabaseHandler(requireContext())
        sessionManager = SessionManager(requireContext())
        val email = sessionManager.getUserId().toString() // Ganti dengan email user yang sesuai
        dbHandler.getUser(email) { user ->
            user?.let {
                // Update the UI with the user data
                binding.editTextEmail.setText(it.email)
                binding.editTextNamaLengkap.setText(it.namaLengkap)
                binding.editTextNoTlp.setText(it.noTelepon)
                binding.editTextAlamat.setText(it.alamat)

                // Set a click listener for the update button
                binding.buttonUpdate.setOnClickListener { _ ->
                    updateUserAccount(it)
                }
            }
        }
    }

    private fun updateUserAccount(user: User) {
        // Reset error messages
        binding.editTextEmail.error = null
        binding.editTextNamaLengkap.error = null
        binding.editTextNoTlp.error = null
        binding.editTextAlamat.error = null

        // Get the input values
        val email = binding.editTextEmail.text.toString().trim()
        val namaLengkap = binding.editTextNamaLengkap.text.toString().trim()
        val noTlp = binding.editTextNoTlp.text.toString().trim()
        val alamat = binding.editTextAlamat.text.toString().trim()

        // Validate input
        var isValid = true

        if (email.isEmpty()) {
            binding.editTextEmail.error = "Email tidak boleh kosong"
            isValid = false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextEmail.error = "Masukkan email yang valid"
            isValid = false
        }

        if (namaLengkap.isEmpty()) {
            binding.editTextNamaLengkap.error = "Masukkan nama lengap anda"
            isValid = false
        }
        if (namaLengkap.matches(Regex(".*\\d.*"))) {
            binding.editTextNamaLengkap.error = "Masukkan nama dengan format yang benar"
            isValid = false
        }
        if(namaLengkap.matches(Regex(".*[^\\p{L}\\s].*"))){
            binding.editTextNamaLengkap.error = "Masukkan nama dengan format yang benar"
            isValid = false

        }

        if (noTlp.isEmpty()) {
            binding.editTextNoTlp.error = "Masukkan nomor telepon"
            isValid = false
        }
        if (!noTlp.matches(Regex("[0-9]+")) && noTlp.length < 10) {
            binding.editTextNoTlp.error = "Format nomor telepon tidak valid"
            isValid = false
        }

        if (alamat.isEmpty()) {
            binding.editTextAlamat.error = "Alamat is required"
            isValid = false
        }

        if (!isValid) {
            return
        }

        val updatedUser = User(
            user.id,
            email,
            namaLengkap,
            noTlp,
            alamat,
            user.password // Assuming the password is not editable in this screen
        )

       updateAccountUser(updatedUser)
    }
    private fun updateAccountUser(user: User) {
        MaterialAlertDialogBuilder(requireContext()).setTitle("Perbaharui informasi akun ?")
            .setMessage("Anda akan memperbaharui profile anda")
            .setPositiveButton("OK") { _, _ ->
                dbHandler.updateUserAccount(user) { success ->
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
        MaterialAlertDialogBuilder(requireContext()).setTitle("Update berhasil")
            .setMessage("Update berhasil erhasil diperbaharui")
            .setPositiveButton("OK") { _, _ ->
                // Tambahkan pemanggilan recreate() di dalam tindakan positif tombol OK
                requireActivity().recreate()
            }.show()
    }
    private fun showErrorDialog() {
        Toast.makeText(requireContext(), "Update gagal", Toast.LENGTH_SHORT).show()
    }

}


