package com.cloverteam.siagabanjir.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloverteam.siagabanjir.ContactAdapter
import com.cloverteam.siagabanjir.databinding.FragmentContactListBinding
import com.cloverteam.siagabanjir.model.Contact

class ContactFragmentList: Fragment() {

    private lateinit var binding: FragmentContactListBinding
    private lateinit var  contactAdapter: ContactAdapter
    private lateinit var contactViewModel: ContactViewModel
    private lateinit var contactList: List<Contact>

    private val viewModel: ContactViewModel by lazy {
       ViewModelProvider(this)[ContactViewModel::class.java]
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactListBinding.inflate(inflater, container, false)
        contactAdapter = ContactAdapter()

        with(binding.contactRecyclerView){
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            layoutManager = LinearLayoutManager(requireContext())
            adapter = contactAdapter
            setHasFixedSize(true)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData().observe(viewLifecycleOwner) { contactList ->
            contactAdapter.updateData(contactList)
        }


    }


}