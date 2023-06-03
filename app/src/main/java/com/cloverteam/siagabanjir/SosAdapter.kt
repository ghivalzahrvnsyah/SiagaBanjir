package com.cloverteam.siagabanjir

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloverteam.siagabanjir.databinding.ListSosBinding
import com.cloverteam.siagabanjir.model.SosNumber

class SosAdapter : RecyclerView.Adapter<SosAdapter.ViewHolder>() {
    private var sosNumbers: List<SosNumber> = emptyList()

    // Fungsi lainnya

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sosNumbers[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListSosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return sosNumbers.size
    }

    fun setSosNumbers(sosNumbers: List<SosNumber>) {
        this.sosNumbers = sosNumbers
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ListSosBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(sosNumber: SosNumber) {
            binding.name.text = "${sosNumber.name} | ${sosNumber.status}"
            binding.phoneNumber.text =sosNumber.phone
            // Mengikat data laporan ke tampilan item di sini
            // Contoh:
            // binding.tvAction.text = report.description
            // binding.tvDate.text = report.date
            // if (report.status == 2) {
            //     binding.ivIcon.setImageResource(R.drawable.status_2)
            //     binding.tvStatus.text = "Telas dikonfirmasi"
            // } else if (report.status == 3) {
            //     binding.ivIcon.setImageResource(R.drawable.status_3)
            //     binding.tvStatus.text = "Report valid"
            // } else {
            //     binding.ivIcon.setImageResource(R.drawable.status_1)
            //     binding.tvStatus.text = "Belum terkonfirmasi"
            // }
        }
    }
}

//class SosAdapter : RecyclerView.Adapter<SosAdapter.ViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val binding = ListSosBinding.inflate(inflater, parent, false)
//        return ViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(reports[position])
//    }
//
//    override fun getItemCount(): Int {
//        return reports.size
//    }
//
//    fun setSos() {
//        notifyDataSetChanged()
//    }
//
//    inner class ViewHolder(private val binding: ListSosBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(report: Report) {
//            binding.name.text = "test"
////            // Mengikat data laporan ke tampilan item di sini
////            // Contoh:
////            binding.tvAction.text = report.description
////            binding.tvDate.text = report.date
////            if (report.status == 2) {
////                binding.ivIcon.setImageResource(R.drawable.status_2)
////                binding.tvStatus.text = "Telas dikonfirmasi"
////
////            } else if (report.status == 3) {
////                binding.ivIcon.setImageResource(R.drawable.status_3)
////                binding.tvStatus.text = "Report valid"
////            } else {
////                binding.ivIcon.setImageResource(R.drawable.status_1)
////                binding.tvStatus.text = "Belum terkonfirmasi"
////            }
//        }
//    }
//}
