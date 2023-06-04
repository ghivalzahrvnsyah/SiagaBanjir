package com.cloverteam.siagabanjir

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloverteam.siagabanjir.databinding.ListAllItemHistoryBinding
import com.cloverteam.siagabanjir.db.DatabaseHandler
import com.cloverteam.siagabanjir.model.Report

class AllReportsAdapter : RecyclerView.Adapter<AllReportsAdapter.ViewHolder>() {
    private var reports: List<Report> = emptyList()
    private lateinit var databaseHandler: DatabaseHandler

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListAllItemHistoryBinding.inflate(inflater, parent, false)
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

    inner class ViewHolder(private val binding: ListAllItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            val context = binding.root.context // Get the context from the root view
            databaseHandler = DatabaseHandler(context) // Initialize with the appropriate context Or initialize it using the appropriate constructor
        }

        fun bind(report: Report) {
            // Mengikat data laporan ke tampilan item di sini
            // Contoh:

            databaseHandler.getUser(report.userId) { user ->
                if (user != null)
                    binding.tvFrom.text = "Oleh : ${user.namaLengkap}"
            }
            binding.tvArea.text = "Melaporkan banjir di Area [ ${report.area} ]"
            binding.tvDate.text = report.date
            if (report.status == 4) {
                binding.tvStatus.text = "Informasi telah usai"
            } else if (report.status == 3) {

                binding.tvStatus.text = "Informasi banjir valid"
            }
        }
    }
}
