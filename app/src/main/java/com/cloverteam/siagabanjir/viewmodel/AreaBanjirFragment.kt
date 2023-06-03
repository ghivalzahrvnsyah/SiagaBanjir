package com.cloverteam.siagabanjir.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cloverteam.siagabanjir.R
import com.cloverteam.siagabanjir.databinding.FragmentBanjirAreaBinding
import com.cloverteam.siagabanjir.db.DatabaseHandler

class AreaBanjirFragment : Fragment() {
    private var _binding: FragmentBanjirAreaBinding? = null
    private val binding get() = _binding!!
    private lateinit var databaseHandler: DatabaseHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBanjirAreaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHandler = DatabaseHandler(requireContext())

        // Mengambil data laporan terakhir dengan status 3 dari Firebase
        databaseHandler.getLatestReportsWithStatus3 { reports ->
            if (reports.isNotEmpty()) {
                val area = reports[0].area

                // Set gambar banjir berdasarkan area laporan
                when (area) {
                    "RT-01", "RT-03" -> binding.imageBanjir.setImageResource(R.drawable.banjir_wilayah_2)
                    "RT-02", "RT-05" -> binding.imageBanjir.setImageResource(R.drawable.banjir_wilayah_1)
                    "RT-04", "RT-06" -> binding.imageBanjir.setImageResource(R.drawable.banjir_wilayah_3)
                    else -> binding.imageBanjir.setImageResource(R.drawable.banjir_wilayah_error)
                }
            } else {
                // No reports found with status 3
                // Set a default image or handle it as per your requirement
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import com.cloverteam.siagabanjir.R
//import com.cloverteam.siagabanjir.databinding.FragmentBanjirAreaBinding
//import com.cloverteam.siagabanjir.db.DatabaseHandler
//
//
//class AreaBanjirFragment : Fragment() {
//    private var _binding: FragmentBanjirAreaBinding? = null
//    private lateinit var databaseHandler: DatabaseHandler
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentBanjirAreaBinding.inflate(inflater, container, false)
//        val rootView = binding.root
//        databaseHandler = DatabaseHandler(requireContext())
//
//        // Lakukan inisialisasi atau manipulasi tampilan fragment di sini
//        // Misalnya, dapat mengakses view menggunakan binding.viewId
//
//        return rootView
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val report = databaseHandler.getLatestReportsWithStatus3()
//        val latestReports = databaseHandler.getLatestReportsWithStatus3()
//        for (report in latestReports) {
//            //binding.statusBanjir.setText(info_for, " ${report.area}")
//            if (report.area == "RT-01" || report.area == "RT-03"){
//                binding.imageBanjir.setImageResource(R.drawable.banjir_wilayah_2)
//            }
//            else if (report.area == "RT-02" || report.area == "RT-05"){
//                binding.imageBanjir.setImageResource(R.drawable.banjir_wilayah_1)
//            }
//            else {
//                binding.imageBanjir.setImageResource(R.drawable.banjir_wilayah_3)
//            }
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
