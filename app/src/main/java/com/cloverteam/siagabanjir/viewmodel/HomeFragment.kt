package com.cloverteam.siagabanjir.viewmodel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.cloverteam.siagabanjir.R
import com.cloverteam.siagabanjir.databinding.FragmentHomeBinding
import com.cloverteam.siagabanjir.db.DatabaseHandler
import com.cloverteam.siagabanjir.session.SessionManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var databaseHandler: DatabaseHandler
    private lateinit var databaseReference: DatabaseReference
    private lateinit var reportListener: ValueEventListener
    private lateinit var notificationManager: NotificationManager
    private val channelId = "siagabanjir_channel"
    private var statusUser = ""
    private var engineStatus = false
    private var modeGetter = 0

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
        notificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val session = sessionManager.getUserId().toString()
        databaseHandler.getUser(session) { user ->
            user?.let {
                binding.userName.text = user.namaLengkap
                if (user.status == "1") {
                    binding.buttonPumpEngine.visibility = View.VISIBLE
                    binding.engineStatus.visibility = View.VISIBLE
                    binding.engineStatusTextView.visibility = View.VISIBLE
                } else {
                    binding.buttonPumpEngine.visibility = View.GONE
                    binding.engineStatus.visibility = View.GONE
                    binding.engineStatusTextView.visibility = View.GONE
                }
            }
        }

        // Uncomment the code to fetch Banjir data
        databaseHandler.getBanjirData { banjirDataList ->
            if (banjirDataList != null) {
                // Handle the Banjir data as needed
                //for (banjirData in banjirDataList) {
                // Process each BanjirData object
                val floatLevel = when (banjirDataList.statusCode) {
                    0 -> "0 cm"
                    1 -> "4 cm"
                    2 -> "8 cm"
                    3 -> "12 cm"
                    4 -> "16 cm"
                    // Add more cases as needed
                    else -> "Unknown Status: Your default message for unknown status"
                }

                val activeFloatArea = when (banjirDataList.statusCode) {
                    0 -> "Tidak ada"
                    1 -> "Tidak ada"
                    2 -> "RT05 - RT02"
                    3 -> "RT03 - RT01"
                    4 -> "RT06 - RT04"
                    // Add more cases as needed
                    else -> "Unknown Status: Your default message for unknown status"
                }

                val onGoingFloatArea = when (banjirDataList.statusCode) {
                    0 -> "Tidak ada"
                    1 -> "RT05 - RT02"
                    2 -> "RT03 - RT01"
                    3 -> "RT06 - RT04"
                    4 -> "Semua Area RW 10"
                    // Add more cases as needed
                    else -> "Unknown Status: Your default message for unknown status"
                }
                val statusFloat = when (banjirDataList.statusCode) {
                    0 -> "Normal"
                    1 -> "Debit air meningkat"
                    2 -> "Waspada!"
                    3 -> "Siaga!"
                    4 -> "Awas!"
                    // Add more cases as needed
                    else -> "Unknown Status: Your default message for unknown status"
                }
                when (banjirDataList.statusCode) {
                    0 -> {
                        binding.statusFloat.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_circle_normal,
                            0
                        )
                        binding.engineStatus.text = "Non-aktif"
                    }

                    1 -> binding.statusFloat.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.ic_circle_normal,
                        0
                    )

                    2 -> binding.statusFloat.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.ic_circle_warning,
                        0
                    )

                    3 -> binding.statusFloat.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.ic_circle_alert,
                        0
                    )

                    4 -> {
                        binding.statusFloat.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_circle_danger,
                            0
                        )
                        binding.engineStatus.text = "Aktif"
                    }
                    // Add more cases as needed
                    else -> binding.statusFloat.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_circle_danger,
                        0,
                        0,
                        0
                    )
                }
                when (banjirDataList.statusCode) {
                    0 -> engineStatus = false
                    4 -> engineStatus = true
                    // Add more cases as needed
                    else -> "Unknown Status: Your default message for unknown status"
                }

//                if(engineStatus){
//                    binding.engineStatus.text = "Aktif"
//                }
//                else{
//                    binding.engineStatus.text = "Non-aktif"
//                }

                when (banjirDataList.statusCode) {
                    0 -> binding.segmenTengah.setBackgroundResource(R.drawable.bg_gradient_blue)
                    1 -> binding.segmenTengah.setBackgroundResource(R.drawable.bg_gradient_blue_green)
                    2 -> binding.segmenTengah.setBackgroundResource(R.drawable.bg_gradient_yellow)
                    3 -> binding.segmenTengah.setBackgroundResource(R.drawable.bg_gradient_orange)
                    4 -> binding.segmenTengah.setBackgroundResource(R.drawable.bg_gradient_red)

                    else -> binding.segmenTengah.setBackgroundColor(R.drawable.bg_gradient_blue)
                }
                binding.waterHeight.text = floatLevel
                binding.activeFloat.text = activeFloatArea
                binding.onGoingFloat.text = onGoingFloatArea
                binding.statusFloat.text = statusFloat
                //binding.statusBanjir.text = message
                //}
            } else {
                // Handle case when there is no Banjir data

            }
        }

        binding.startEngineButton.setOnClickListener {
            databaseHandler.getBanjirData { banjirDataList ->
                if (banjirDataList != null) {
                    if (banjirDataList.statusCode == 0) {
                        binding.startEngineButton.setOnClickListener {
                            binding.engineStatus.text = "Non-aktif"
                            showSnackbar(binding.root, "Ditolak, Air dalam kondisi aman!!!")
                        }
                    } else {
                        binding.startEngineButton.setOnClickListener {
                            databaseHandler.setRelayMode(1) { success ->

                                if (success) {
                                    binding.engineStatus.text = "Aktif"
                                    showSnackbar(binding.root, "Engine telah diaktifkan.")
                                } else {
                                    showSnackbar(binding.root, "Gagal mengaktifkan engine.")
                                }
                            }
                        }
                    }

                }
            }

        }

        //binding.stopEngineButton.setOnClickListener {

        databaseHandler.getBanjirData { banjirDataList ->
            if (banjirDataList != null) {
                if (banjirDataList.statusCode == 4) {
                    binding.stopEngineButton.setOnClickListener {
                        binding.engineStatus.text = "Aktif"
                        showSnackbar(binding.root, "Ditolak, Air dalam kondisi awas!!!")
                    }
                } else {
                    binding.stopEngineButton.setOnClickListener {
                        databaseHandler.setRelayMode(0) { success ->

                            if (success) {
                                binding.engineStatus.text = "Non-aktif"
                                showSnackbar(binding.root, "Engine telah dinon-aktifkan.")
                            } else {
                                showSnackbar(binding.root, "Gagal menon-aktifkan engine.")
                            }
                        }
                    }
                }
            }
        }

        databaseHandler.getBanjirData { banjirDataList ->
            if (banjirDataList != null) {
                when (banjirDataList.statusCode) {
                    1 -> showNotification("Waspada Banjir", "RT 2 & 5 berpotensi terdampak banjir")
                    2 -> showNotification("Waspada Banjir", "RT 2 & 5 terdampak banjir & RT 1 & 3 berpotensi terdampak banjir")
                    3 -> showNotification("Waspada Banjir", "RT 1, 2, 3, 5 terdampak & RT 4 & 6 berpotensi terdampak banjir")
                    4 -> showNotification("Darurat Banjir", "Seluruh RW 10 terendam banjir")
                }
            }
        }

        // }


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

    private fun showNotification(title: String, message: String) {
        val builder = NotificationCompat.Builder(requireContext(), channelId)
            .setSmallIcon(R.drawable.logo_siaga_banjir)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "SiagaBanjir Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
            builder.setChannelId(channelId)
        }

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    // Contoh panggilan untuk menampilkan Snackbar
    fun showSnackbar(view: View, message: String) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }


}





