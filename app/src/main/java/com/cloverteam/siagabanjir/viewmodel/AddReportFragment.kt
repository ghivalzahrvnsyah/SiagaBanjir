package com.cloverteam.siagabanjir.viewmodel

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.cloverteam.siagabanjir.R
import com.cloverteam.siagabanjir.db.DatabaseHandler
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.cloverteam.siagabanjir.model.Report
import com.cloverteam.siagabanjir.session.SessionManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.*

class AddReportFragment : Fragment() {
    private lateinit var descriptionInputEditText: TextInputEditText
    private lateinit var areaSpinner: AutoCompleteTextView
    private lateinit var addReportButton: MaterialButton
    private lateinit var database: DatabaseHandler
    private lateinit var sessionManager: SessionManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_report, container, false)

        descriptionInputEditText = view.findViewById(R.id.descriptionInputEditText)
        areaSpinner = view.findViewById(R.id.areaSpinner)
        addReportButton = view.findViewById(R.id.addReportButton)
        sessionManager = SessionManager(requireContext())
        database = DatabaseHandler(requireContext())
        setupAreaSpinner()
        addReportButton.setOnClickListener {
            addReport()
        }

        return view
    }

    private fun setupAreaSpinner() {
        val areas = arrayOf("RT-01", "RT-02", "RT-03", "RT-04", "RT-05", "RT-06")
        val areaAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, areas)
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        areaSpinner.setAdapter(areaAdapter)
    }

    private fun addReport() {
        val description = descriptionInputEditText.text.toString()
        val area = areaSpinner.text.toString()
        val status = 1
        val userId = sessionManager.getUserId().toString()

        if (description.isEmpty() || area.isEmpty()) {
            Toast.makeText(requireContext(), "Harap lengkapi semua input", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())


        val report = Report(
            description = description,
            date = currentDate,
            area = area,
            status = status,
            userId = userId
        )
        //kirim Report
        addReport(report, userId)
    }

    private fun addReport(report: Report, userId: String) {
        MaterialAlertDialogBuilder(requireContext()).setTitle("Kirim informasi banjir ?")
            .setMessage("Informasi laporan anda akan dicheck dan divalidasi. dilarang melaporkan informasi palsu")
            .setPositiveButton("OK") { _, _ ->
                database.addReport(report, userId) { isSuccess ->
                    if (isSuccess) {
                        // Pengiriman data berhasil
                        showSuccessDialog()
                    } else {
                        // Pengiriman data gagal
                       showErrorDialog()
                    }
                }
            }.setNegativeButton("No") { _, _ ->
            }.show()
    }

    private fun showSuccessDialog() {
        MaterialAlertDialogBuilder(requireContext()).setTitle("Berhasil menambahkan report")
            .setMessage("Informasi laporan anda akan dicheck dan divalidasi")
            .setPositiveButton("OK") { _, _ ->
                // Tambahkan pemanggilan recreate() di dalam tindakan positif tombol OK
                val intent = Intent(context, Home::class.java)
                startActivity(intent)
            }.show()
    }

    private fun showErrorDialog() {
        Toast.makeText(requireContext(), "Update password gagal", Toast.LENGTH_SHORT).show()
    }
}


//import android.content.Intent
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.*
//import androidx.fragment.app.Fragment
//import com.cloverteam.siagabanjir.R
//import com.google.android.material.button.MaterialButton
//import com.google.android.material.dialog.MaterialAlertDialogBuilder
//import com.google.android.material.textfield.TextInputEditText
//import com.cloverteam.siagabanjir.db.DatabaseHandler
//import com.cloverteam.siagabanjir.model.Report
//import com.cloverteam.siagabanjir.session.SessionManager
//import java.text.SimpleDateFormat
//import java.util.*
//
//class AddReportFragment : Fragment() {
//    private lateinit var descriptionInputEditText: TextInputEditText
//    private lateinit var areaSpinner: AutoCompleteTextView
//    private lateinit var selectImageButton: Button
//    private lateinit var imagePreview: ImageView
//    private lateinit var addReportButton: MaterialButton
//    private lateinit var databaseHandler: DatabaseHandler
//    private lateinit var sessionManager: SessionManager
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_add_report, container, false)
//
//        descriptionInputEditText = view.findViewById(R.id.descriptionInputEditText)
//        areaSpinner = view.findViewById(R.id.areaSpinner)
//        addReportButton = view.findViewById(R.id.addReportButton)
//        databaseHandler = DatabaseHandler(requireContext())
//        sessionManager = SessionManager(requireContext())
//        setupAreaSpinner()
//        addReportButton.setOnClickListener {
//            addReport()
//        }
//
//        return view
//    }
//
//    private fun setupAreaSpinner() {
//        val areas = arrayOf("RT-01", "RT-02", "RT-03", "RT-04", "RT-05", "RT-06")
//        val areaAdapter =
//            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, areas)
//        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        areaSpinner.setAdapter(areaAdapter)
//    }
//
//    private fun addReport() {
//        val description = descriptionInputEditText.text.toString()
//        val area = areaSpinner.text.toString()
//        val status = 3
//        val userEmail = sessionManager.getEmail().toString()
//
//        // Validasi input
//        if (description.isEmpty() || area.isEmpty()) {
//            Toast.makeText(requireContext(), "Harap lengkapi semua input", Toast.LENGTH_SHORT)
//                .show()
//            return
//        }
//
//        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
//
//        val report = Report(0, description, currentDate, area, status, userEmail)
//
//        val reportId = databaseHandler.addReport(report, userEmail)
//
//        if (reportId > 0) {
//            val dialogBuilder = MaterialAlertDialogBuilder(requireContext())
//                .setTitle("Lapor banjir!")
//                .setMessage("Informasi laporan anda akan dicheck dan divalidasi. dilarang melaporkan informasi palsu")
//                .setPositiveButton("OK") { _, _ ->
//                    descriptionInputEditText.text?.clear()
//                    areaSpinner.text?.clear()
//                    val dialogBuilder2 = MaterialAlertDialogBuilder(requireContext())
//                        .setTitle("Report terkirim!")
//                        .setMessage("Informasi akan segera divalidasi")
//                        .setPositiveButton("OK") { _, _ ->
//                            val intent = Intent(context, Home::class.java)
//                            startActivity(intent)
//                        }
//                    dialogBuilder2.show()
//                }
//                .setNegativeButton("No") { _, _ ->
//                }
//            dialogBuilder.show()
//        } else {
//            Toast.makeText(requireContext(), "Gagal menambahkan laporan", Toast.LENGTH_SHORT).show()
//        }
//
//
//    }
//}
