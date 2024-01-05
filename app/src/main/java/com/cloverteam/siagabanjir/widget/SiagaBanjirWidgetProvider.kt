package com.cloverteam.siagabanjir.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.cloverteam.siagabanjir.R
import com.cloverteam.siagabanjir.db.DatabaseHandler
import com.cloverteam.siagabanjir.viewmodel.MainActivity

class SiagaBanjirWidgetProvider : AppWidgetProvider() {

    companion object {
        fun updateAppWidget(
            context: Context,
            manager: AppWidgetManager,
            id: Int,
            data: String // Tambahkan parameter untuk menerima data
        ) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                context, 0, intent, 0
            )
            val views = RemoteViews(context.packageName, R.layout.widget_main)
            views.setOnClickPendingIntent(R.id.dataTextView, pendingIntent)

            // Tampilkan data yang sesuai di widget
            views.setTextViewText(R.id.dataTextView, data)

            manager.updateAppWidget(id, views)
        }
    }

    override fun onUpdate(
        context: Context?,
        manager: AppWidgetManager?,
        ids: IntArray?
    ) {
        if (context == null || manager == null || ids == null) return

        // Ambil data banjir terkini dari Firebase menggunakan DatabaseHandler
        val databaseHandler = DatabaseHandler(context)
        databaseHandler.getBanjirData { banjir ->
            if (banjir != null) {
                // Gunakan data banjir untuk memperbarui widget
                val statusBanjir = when (banjir.statusCode) {
                    0 -> "Normal"
                    1 -> "Waspada"
                    2 -> "Waspada"
                    3 -> "Siaga"
                    4 -> "Awas"
                    else -> "Tidak diketahui"
                }

                val ketinggian = when (banjir.statusCode) {
                    0 -> "0"
                    1 -> "4"
                    2 -> "8"
                    3 -> "12"
                    4 -> "16"
                    else -> "Tidak diketahui"
                }

                val berlangsung = when (banjir.statusCode) {
                    0 -> "Tidak ada"
                    1 -> "Tidak ada"
                    2 -> "RT05 - RT02"
                    3 -> "RT03 - RT01"
                    4 -> "RT06 - RT04"
                    else -> "Tidak diketahui"
                }

                val berpotensi = when (banjir.statusCode) {
                    0 -> "Tidak ada"
                    1 -> "RT05 - RT02"
                    2 -> "RT03 - RT01"
                    3 -> "RT06 - RT04"
                    4 -> "Semua Area RW 10"
                    else -> "Tidak diketahui"
                }

                val data = "Status Banjir: $statusBanjir\nKetinggian: $ketinggian cm" +
                        "\nBerlangsung: $berlangsung" +
                        "\nBerpotensi: $berpotensi"

                for (id in ids) {
                    updateAppWidget(context, manager, id, data)
                }
            }
        }
    }
}
