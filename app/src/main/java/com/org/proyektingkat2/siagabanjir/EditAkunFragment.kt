package com.org.proyektingkat2.siagabanjir

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.org.proyektingkat2.db.DatabaseHandler
import com.org.proyektingkat2.model.User
import com.org.proyektingkat2.session.SessionManager
import com.org.proyektingkat2.siagabanjir.databinding.FragmentEditAkunBinding

class EditAkunFragment : Fragment() {
    private var _binding: FragmentEditAkunBinding? = null
    private val binding get() = _binding!!
    private lateinit var user: User
    private lateinit var dbHandler: DatabaseHandler
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentEditAkunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbHandler = DatabaseHandler(requireContext())
        sessionManager = SessionManager(requireContext())
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
            val dialogBuilder = MaterialAlertDialogBuilder(requireContext())
                .setTitle("Update Berhasil!")
                .setMessage("Informasi akun anda berhasil diupdate")
                .setPositiveButton("OK") { _, _ ->
                    // Tambahkan pemanggilan recreate() di dalam tindakan positif tombol OK
                    requireActivity().recreate()
                }
            dialogBuilder.show()
        } else {
            // Update gagal
            // Tambahkan tindakan yang diinginkan jika update gagal
            val dialogBuilder = MaterialAlertDialogBuilder(requireContext())
                .setTitle("Update gagal!")
                .setMessage("Informasi akun anda gagal diupdate")
                .setPositiveButton("OK", null)
            dialogBuilder.show()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}