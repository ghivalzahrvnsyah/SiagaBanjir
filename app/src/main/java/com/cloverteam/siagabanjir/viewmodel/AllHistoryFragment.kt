package com.cloverteam.siagabanjir.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloverteam.siagabanjir.databinding.FragmentAllHistoryBinding
import com.cloverteam.siagabanjir.db.DatabaseHandler
import com.cloverteam.siagabanjir.session.SessionManager

class AllHistoryFragment : Fragment() {
    private lateinit var binding: FragmentAllHistoryBinding
    private lateinit var reportsAdapter: AllReportsAdapter
    private lateinit var sessionManager: SessionManager
    private lateinit var databaseHandler: DatabaseHandler

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllHistoryBinding.inflate(inflater, container, false)

        // Inisialisasi adapter dan tetapkan ke RecyclerView
        reportsAdapter = AllReportsAdapter()
        binding.rvHistory.adapter = reportsAdapter

        // Tetapkan LinearLayoutManager ke RecyclerView
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mengambil data laporan dari DatabaseHandler
        sessionManager = SessionManager(requireContext())
        databaseHandler = DatabaseHandler(requireContext())

        val userId = sessionManager.getUserId() // Ubah sesuai dengan cara Anda mendapatkan email pengguna


        // Mengambil data laporan dari Firebase Realtime Database menggunakan DatabaseHandler
        if (userId != null) {
            databaseHandler.getReportsByUserId(userId) { reports ->
                reportsAdapter.setReports(reports)
            }
        }
    }

}
