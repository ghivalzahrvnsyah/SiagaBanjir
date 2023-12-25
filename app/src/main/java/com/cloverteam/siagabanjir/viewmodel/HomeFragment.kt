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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var databaseHandler: DatabaseHandler
    private lateinit var databaseReference: DatabaseReference
    private lateinit var reportListener: ValueEventListener

    companion object {
        private const val CHANNEL_ID = "siagabanjir_channel"
        private const val NOTIFICATION_ID = 123
        private const val PREF_LAST_REPORT_ID = "last_report_id"
    }

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
        // Uncomment the code to fetch Banjir data
        databaseHandler.getBanjirData { banjirDataList ->
            if (banjirDataList != null) {
                // Handle the Banjir data as needed
                //for (banjirData in banjirDataList) {
                    // Process each BanjirData object
                    val message = when (banjirDataList.statusCode) {
                        0 -> "Tidak terjadi peninggkan debit air"
                        1 -> "Debit air menaik"
                        2 -> "Waspada!, Banjir wilayah RT05-RT02"
                        3 -> "Siaga!, Banjir wilayah RT03-RT01"
                        4 -> "Awas!, Banjir wilayah RT04-RT06"
                        // Add more cases as needed
                        else -> "Unknown Status: Your default message for unknown status"
                    }
                    when (banjirDataList.statusCode) {
                        0 -> binding.indicator.setImageResource(R.drawable.circle_status_0)
                        1 -> binding.indicator.setImageResource(R.drawable.circle_status_2)
                        2 -> binding.indicator.setImageResource(R.drawable.circle_status_2)
                        3 -> binding.indicator.setImageResource(R.drawable.circle_status_3)
                        4 -> binding.indicator.setImageResource(R.drawable.circle_status_4)
                        // Add more cases as needed
                        else -> binding.indicator.setImageResource(R.drawable.circle_status_4)
                    }
                    binding.statusBanjir.text = message
                //}
            } else {
                // Handle case when there is no Banjir data

            }
        }


//        databaseHandler.getLatestReportsWithStatus3 { reportList ->
//            if (reportList.isNotEmpty()) {
//                val latestReport = reportList.first() // Mendapatkan laporan terbaru
//                val message = "Banjir sedang berlangsung di ${latestReport.area}"
//                binding.statusBanjir.text = message
//                binding.indicator.setImageResource(R.drawable.circle_status_1)
//            } else {
//                binding.statusBanjir.text = "Tidak ada laporan banjir hari ini."
//                binding.indicator.setImageResource(R.drawable.circle_status_3)
//            }
//        }


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
            val Allhistory = AllHistoryFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.fragmentContainer, Allhistory)
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

        // Menu Sos
        binding.menuNoSos.setOnClickListener {
            val sosFragment = SosFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.fragmentContainer, sosFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        binding.menuMitigasi.setOnClickListener {
            val mitigasiFragment = MitigasiFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.fragmentContainer, mitigasiFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }


    }


}



