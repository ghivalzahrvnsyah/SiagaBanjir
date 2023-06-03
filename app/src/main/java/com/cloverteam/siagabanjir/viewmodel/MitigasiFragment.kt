package com.cloverteam.siagabanjir.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloverteam.siagabanjir.MitigasiAdapter
import com.cloverteam.siagabanjir.databinding.FragmentMitigasiListBinding
import com.cloverteam.siagabanjir.model.Mitigasi

class MitigasiFragment: Fragment() {

    private lateinit var binding: FragmentMitigasiListBinding
    private lateinit var mitigasiAdapter: MitigasiAdapter
    private lateinit var mitigasiViewModel: MitigasiViewModel
    private lateinit var mitigasiList: List<Mitigasi>

    private val viewModel: MitigasiViewModel by lazy {
       ViewModelProvider(this)[MitigasiViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMitigasiListBinding.inflate(inflater, container, false)
        mitigasiAdapter = MitigasiAdapter()

        with(binding.mitigasiRecyclerView){
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mitigasiAdapter
            setHasFixedSize(true)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData().observe(viewLifecycleOwner) { mitigasiList ->
            mitigasiAdapter.updateData(mitigasiList)
        }
    }
}