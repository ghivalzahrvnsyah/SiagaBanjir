package com.cloverteam.siagabanjir

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloverteam.siagabanjir.databinding.ListItemContactBinding
import com.cloverteam.siagabanjir.model.Contact

class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    private val data = mutableListOf<Contact>()

    fun updateData(newData: List<Contact>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemContactBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = data[position]
        holder.bind(contact)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val binding: ListItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            with(binding) {
                contactName.text = contact.namaKontak
                contactNumber.text = contact.nomorKontak
            }
        }
    }
}
