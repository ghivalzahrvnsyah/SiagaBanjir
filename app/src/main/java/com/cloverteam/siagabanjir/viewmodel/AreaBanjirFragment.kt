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
    private lateinit var binding: FragmentBanjirAreaBinding
    private lateinit var databaseHandler: DatabaseHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBanjirAreaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHandler = DatabaseHandler(requireContext())

        // Mengambil data laporan terakhir dengan status 3 dari Firebase
        databaseHandler.getBanjirData { banjirDataList ->
            if (banjirDataList != null) {
                val statusCode = banjirDataList.statusCode
                // Set gambar banjir berdasarkan statusCode
                when (statusCode) {
                    0 -> binding.imageBanjir.setImageResource(R.drawable.banjir_status_0)
                    1 -> binding.imageBanjir.setImageResource(R.drawable.banjir_status_1)
                    2 -> binding.imageBanjir.setImageResource(R.drawable.banjir_status_2)
                    3 -> binding.imageBanjir.setImageResource(R.drawable.banjir_status_3)
                    4 -> binding.imageBanjir.setImageResource(R.drawable.banjir_status_4)
                }
            } else {

            }
        }
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
