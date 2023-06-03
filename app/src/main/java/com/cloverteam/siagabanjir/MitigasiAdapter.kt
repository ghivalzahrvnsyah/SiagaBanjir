package com.cloverteam.siagabanjir

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cloverteam.siagabanjir.databinding.ListItemMitigasiBinding
import com.cloverteam.siagabanjir.model.Mitigasi

class MitigasiAdapter: RecyclerView.Adapter<MitigasiAdapter.ViewHolder>() {

    private val data = mutableListOf<Mitigasi>()
    private var counter = 1

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Mendapatkan referensi ke TextView nomor
        val textViewNomor = holder.itemView.findViewById<TextView>(R.id.mitigasiId)

        // Mengatur nilai TextView dengan nomor berdasarkan posisi + 1
        textViewNomor.text = (counter + position).toString()
        holder.bind(data[position])
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