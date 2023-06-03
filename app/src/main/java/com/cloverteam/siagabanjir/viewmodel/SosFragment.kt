package com.cloverteam.siagabanjir.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloverteam.siagabanjir.SosAdapter
import com.cloverteam.siagabanjir.databinding.FragmentSosBinding
import com.cloverteam.siagabanjir.db.DatabaseHandler
import com.cloverteam.siagabanjir.session.SessionManager

class SosFragment : Fragment() {
    private lateinit var binding: FragmentSosBinding
    private lateinit var sosAdapter: SosAdapter
    private lateinit var sessionManager: SessionManager
    private lateinit var databaseHandler: DatabaseHandler

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSosBinding.inflate(inflater, container, false)

        // Inisialisasi adapter dan tetapkan ke RecyclerView
        sosAdapter = SosAdapter()
        binding.rvSos.adapter = sosAdapter

        // Tetapkan LinearLayoutManager ke RecyclerView
        binding.rvSos.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mengambil data laporan dari DatabaseHandler
        sessionManager = SessionManager(requireContext())
        databaseHandler = DatabaseHandler(requireContext())

        val userId = sessionManager.getUserId() // Ubah sesuai dengan cara Anda mendapatkan email pengguna
        databaseHandler.getSosNumber { sosNumbers ->
            sosAdapter.setSosNumbers(sosNumbers)
        }
    }

}
