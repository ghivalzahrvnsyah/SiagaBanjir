package com.cloverteam.siagabanjir

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloverteam.siagabanjir.databinding.ListItemMitigasiBinding
import com.cloverteam.siagabanjir.model.Mitigasi

class MitigasiAdapter: RecyclerView.Adapter<MitigasiAdapter.ViewHolder>() {

    private val data = mutableListOf<Mitigasi>()
    fun updateData(newData: List<Mitigasi>){
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MitigasiAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemMitigasiBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MitigasiAdapter.ViewHolder, position: Int) {
        val mitigasi = data[position]
        holder.bind(mitigasi)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val binding: ListItemMitigasiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mitigasi: Mitigasi) {
            with(binding) {
               mitigasiName.text = mitigasi.mitigasi
            }
        }
    }

}