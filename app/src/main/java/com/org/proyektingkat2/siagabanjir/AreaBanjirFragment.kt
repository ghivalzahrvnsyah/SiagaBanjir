package com.org.proyektingkat2.siagabanjir

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.org.proyektingkat2.db.DatabaseHandler
import com.org.proyektingkat2.siagabanjir.databinding.FragmentBanjirAreaBinding


class AreaBanjirFragment : Fragment() {
    private var _binding: FragmentBanjirAreaBinding? = null
    private lateinit var databaseHandler: DatabaseHandler
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBanjirAreaBinding.inflate(inflater, container, false)
        val rootView = binding.root
        databaseHandler = DatabaseHandler(requireContext())

        // Lakukan inisialisasi atau manipulasi tampilan fragment di sini
        // Misalnya, dapat mengakses view menggunakan binding.viewId

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val report = databaseHandler.getLatestReportsWithStatus3()
        val latestReports = databaseHandler.getLatestReportsWithStatus3()
        for (report in latestReports) {
            //binding.statusBanjir.setText(info_for, " ${report.area}")
            if (report.area == "RT-01" || report.area == "RT-03"){
                binding.imageBanjir.setImageResource(R.drawable.banjir_wilayah_2)
            }
            else if (report.area == "RT-02" || report.area == "RT-05"){
                binding.imageBanjir.setImageResource(R.drawable.banjir_wilayah_1)
            }
            else {
                binding.imageBanjir.setImageResource(R.drawable.banjir_wilayah_3)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
