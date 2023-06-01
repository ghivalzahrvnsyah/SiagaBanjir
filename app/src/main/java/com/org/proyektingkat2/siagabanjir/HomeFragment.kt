package com.org.proyektingkat2.siagabanjir

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.org.proyektingkat2.db.DatabaseHandler
import com.org.proyektingkat2.session.SessionManager
import com.org.proyektingkat2.siagabanjir.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var databaseHandler: DatabaseHandler
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databaseHandler = DatabaseHandler(requireContext())
        sessionManager = SessionManager(requireContext())
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userEmail = sessionManager.getEmail().toString()
        val user = databaseHandler.getUser(userEmail)

        if (user != null) {
            binding.userName.text = user.namaLengkap
        }

        binding.btnLapor.setOnClickListener {
            val addReportFragment = AddReportFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.fragmentContainer, addReportFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.menuLaporanWarga.setOnClickListener {
            val historyFragment = HistoryFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.fragmentContainer, historyFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        binding.menuWilayahBanjir.setOnClickListener {
            val wilayahFragment = AreaBanjirFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.fragmentContainer, wilayahFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        val latestReports = databaseHandler.getLatestReportsWithStatus3()
        for (report in latestReports) {
            binding.statusBanjir.text = "Banjir sedang berlangsung di ${report.area}"
        }


    }
}


