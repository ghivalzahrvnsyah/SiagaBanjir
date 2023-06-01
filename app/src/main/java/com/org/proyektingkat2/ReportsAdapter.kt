package com.org.proyektingkat2.siagabanjir

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.org.proyektingkat2.model.Report
import com.org.proyektingkat2.siagabanjir.databinding.ListItemHistoryBinding


class ReportsAdapter : RecyclerView.Adapter<ReportsAdapter.ViewHolder>() {
    private var reports: List<Report> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemHistoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reports[position])
    }

    override fun getItemCount(): Int {
        return reports.size
    }

    fun setReports(reports: List<Report>) {
        this.reports = reports
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ListItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(report: Report) {
            // Mengikat data laporan ke tampilan item di sini
            // Contoh:
            binding.tvAction.text = report.description
            binding.tvDate.text = report.date
            if (report.status == 2) {
                binding.ivIcon.setImageResource(R.drawable.status_2)
                binding.tvStatus.text = "Telas dikonfirmasi"

            } else if (report.status == 3) {
                binding.ivIcon.setImageResource(R.drawable.status_3)
                binding.tvStatus.text = "Report valid"
            } else {
                binding.ivIcon.setImageResource(R.drawable.status_1)
                binding.tvStatus.text = "Belum terkonfirmasi"
            }
        }
    }
}
