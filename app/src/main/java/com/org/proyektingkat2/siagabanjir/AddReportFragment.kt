package com.org.proyektingkat2.siagabanjir

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.org.proyektingkat2.db.DatabaseHandler
import com.org.proyektingkat2.model.Report
import com.org.proyektingkat2.session.SessionManager
import java.text.SimpleDateFormat
import java.util.*

class AddReportFragment : Fragment() {
    private lateinit var descriptionInputEditText: TextInputEditText
    private lateinit var areaSpinner: AutoCompleteTextView
    private lateinit var selectImageButton: Button
    private lateinit var imagePreview: ImageView
    private lateinit var addReportButton: MaterialButton
    private lateinit var databaseHandler: DatabaseHandler
    private lateinit var sessionManager: SessionManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_report, container, false)

        descriptionInputEditText = view.findViewById(R.id.descriptionInputEditText)
        areaSpinner = view.findViewById(R.id.areaSpinner)
        addReportButton = view.findViewById(R.id.addReportButton)
        databaseHandler = DatabaseHandler(requireContext())
        sessionManager = SessionManager(requireContext())
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
        val status = 3
        val userEmail = sessionManager.getEmail().toString()

        // Validasi input
        if (description.isEmpty() || area.isEmpty()) {
            Toast.makeText(requireContext(), "Harap lengkapi semua input", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        val report = Report(0, description, currentDate, area, status, userEmail)

        val reportId = databaseHandler.addReport(report, userEmail)

        if (reportId > 0) {
            val dialogBuilder = MaterialAlertDialogBuilder(requireContext())
                .setTitle("Lapor banjir!")
                .setMessage("Informasi laporan anda akan dicheck dan divalidasi. dilarang melaporkan informasi palsu")
                .setPositiveButton("OK") { _, _ ->
                    descriptionInputEditText.text?.clear()
                    areaSpinner.text?.clear()
                    val dialogBuilder2 = MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Report terkirim!")
                        .setMessage("Informasi akan segera divalidasi")
                        .setPositiveButton("OK") { _, _ ->
                            val intent = Intent(context, Home::class.java)
                            startActivity(intent)
                        }
                    dialogBuilder2.show()
                }
                .setNegativeButton("No") { _, _ ->
                }
            dialogBuilder.show()
        } else {
            Toast.makeText(requireContext(), "Gagal menambahkan laporan", Toast.LENGTH_SHORT).show()
        }


    }
}
