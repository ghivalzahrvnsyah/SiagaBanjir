package com.cloverteam.siagabanjir.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.cloverteam.siagabanjir.R
import com.cloverteam.siagabanjir.databinding.FragmentHomeBinding
import com.cloverteam.siagabanjir.db.DatabaseHandler
import com.cloverteam.siagabanjir.session.SessionManager

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var databaseHandler: DatabaseHandler

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext())
        databaseHandler = DatabaseHandler(requireContext())

        val session = sessionManager.getUserId().toString()
        databaseHandler.getUser(session) { user ->
            user?.let {
                binding.userName.text = user.namaLengkap
            }
        }
        databaseHandler.getLatestReportsWithStatus3 {
                reportList ->
            if (reportList != null) {
                for(report in reportList){
                    binding.statusBanjir.text = "Banjir sedang berlangsung di ${report.area}"
                }
            }
        }

        // Tombol Lapor
        binding.btnLapor.setOnClickListener {
            val addReportFragment = AddReportFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.fragmentContainer, addReportFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        // Menu Laporan Warga
        binding.menuLaporanWarga.setOnClickListener {
            val historyFragment = HistoryFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.fragmentContainer, historyFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        // Menu Wilayah Banjir
        binding.menuWilayahBanjir.setOnClickListener {
            val wilayahFragment = AreaBanjirFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.fragmentContainer, wilayahFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        // Mendapatkan data laporan terbaru dengan status 3 dari Firebase Realtime Database menggunakan DatabaseHandler

    }
}



