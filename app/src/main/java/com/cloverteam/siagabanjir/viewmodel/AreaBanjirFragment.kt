package com.cloverteam.siagabanjir.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cloverteam.siagabanjir.R
import com.cloverteam.siagabanjir.databinding.FragmentBanjirAreaBinding
import com.google.firebase.database.*

class AreaBanjirFragment : Fragment() {
    private var _binding: FragmentBanjirAreaBinding? = null
    private val binding get() = _binding!!
    private lateinit var databaseRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBanjirAreaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = "https://siaga-banjir-6b3e7-default-rtdb.asia-southeast1.firebasedatabase.app"
        databaseRef = FirebaseDatabase.getInstance(url).reference.child("reports")

        // Mengambil data laporan terakhir dengan status 3 dari Firebase
        databaseRef.orderByChild("status").equalTo(3.toDouble())
            .limitToLast(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val area = snapshot.child("area").value.toString()

                        // Set gambar banjir berdasarkan area laporan
                        when (area) {
                            "RT-01", "RT-03" -> binding.imageBanjir.setImageResource(R.drawable.banjir_wilayah_2)
                            "RT-02", "RT-05" -> binding.imageBanjir.setImageResource(R.drawable.banjir_wilayah_1)
                            else -> binding.imageBanjir.setImageResource(R.drawable.banjir_wilayah_3)
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Error handling jika gagal mengambil data
                    // Anda dapat menambahkan penanganan error sesuai kebutuhan aplikasi Anda
                }
            })
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
