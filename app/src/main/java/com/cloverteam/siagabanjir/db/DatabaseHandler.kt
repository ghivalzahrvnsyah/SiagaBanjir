package com.cloverteam.siagabanjir.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.cloverteam.siagabanjir.model.Report
import com.cloverteam.siagabanjir.model.User

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 4
        private const val DATABASE_NAME = "siagabanjir.db"
        private const val TABLE_USERS = "users"
        private const val TABLE_REPORT = "report"
        private const val KEY_ID = "id"
        private const val KEY_NAMA_LENGKAP = "username"
        private const val KEY_NO_TLP = "nomor_telepon"
        private const val KEY_ALAMAT = "alamat"
        private const val KEY_PASSWORD = "password"
        private const val KEY_EMAIL = "email"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_DATE = "date"
        private const val KEY_AREA = "area"
        private const val KEY_STATUS = "status"
        private const val KEY_USER_EMAIL = "user_email"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUsersTable =
            "CREATE TABLE $TABLE_USERS ($KEY_ID INTEGER PRIMARY KEY, $KEY_EMAIL TEXT, $KEY_NAMA_LENGKAP TEXT, $KEY_NO_TLP TEXT, $KEY_ALAMAT TEXT, $KEY_PASSWORD TEXT)"
        db.execSQL(createUsersTable)

        val createReportTable =
            "CREATE TABLE $TABLE_REPORT ($KEY_ID INTEGER PRIMARY KEY, $KEY_DESCRIPTION TEXT, $KEY_DATE TEXT, $KEY_AREA TEXT, $KEY_STATUS INTEGER, $KEY_USER_EMAIL TEXT, FOREIGN KEY($KEY_USER_EMAIL) REFERENCES $TABLE_USERS($KEY_EMAIL))"
        db.execSQL(createReportTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_REPORT")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    // User related operations

    fun addUser(user: User): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_EMAIL, user.email)
        values.put(KEY_NAMA_LENGKAP, user.namaLengkap)
        values.put(KEY_NO_TLP, user.noTelepon)
        values.put(KEY_ALAMAT, user.alamat)
        values.put(KEY_PASSWORD, user.password)
        return db.insert(TABLE_USERS, null, values)
    }

    fun getUser(email: String): User? {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $KEY_EMAIL = ?"
        val cursor: Cursor = db.rawQuery(query, arrayOf(email))
        return if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
            val namaLengkap = cursor.getString(cursor.getColumnIndex(KEY_NAMA_LENGKAP))
            val noTlp = cursor.getString(cursor.getColumnIndex(KEY_NO_TLP))
            val alamat = cursor.getString(cursor.getColumnIndex(KEY_ALAMAT))
            val password = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD))
            User(id, email, namaLengkap, noTlp, alamat, password)
        } else {
            null
        }
    }

    fun updateUser(user: User): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NAMA_LENGKAP, user.namaLengkap)
        values.put(KEY_NO_TLP, user.noTelepon)
        values.put(KEY_ALAMAT, user.alamat)

        return db.update(TABLE_USERS, values, "$KEY_EMAIL = ?", arrayOf(user.email))
    }

    fun updatePassword(email: String, newPassword: String): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_PASSWORD, newPassword)
        return db.update(TABLE_USERS, values, "$KEY_EMAIL = ?", arrayOf(email))
    }

    fun deleteUser(user: User): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_USERS, "$KEY_EMAIL = ?", arrayOf(user.email))
    }

    fun getAllUsers(): List<User> {
        val userList = mutableListOf<User>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS"
        val cursor: Cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val email = cursor.getString(cursor.getColumnIndex(KEY_EMAIL))
                val namaLengkap = cursor.getString(cursor.getColumnIndex(KEY_NAMA_LENGKAP))
                val noTlp = cursor.getString(cursor.getColumnIndex(KEY_NO_TLP))
                val alamat = cursor.getString(cursor.getColumnIndex(KEY_ALAMAT))
                val password = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD))
                val user = User(id, email, namaLengkap, noTlp, alamat, password)
                userList.add(user)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return userList
    }

    // Report related operations

    fun addReport(report: Report, userEmail: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_DESCRIPTION, report.description)
        values.put(KEY_DATE, report.date)
        values.put(KEY_AREA, report.area)
        values.put(KEY_STATUS, report.status)
        values.put(KEY_USER_EMAIL, userEmail)
        return db.insert(TABLE_REPORT, null, values)
    }

    fun getAllReports(): List<Report> {
        val reportList = mutableListOf<Report>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_REPORT"
        val cursor: Cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val description = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))
                val date = cursor.getString(cursor.getColumnIndex(KEY_DATE))
                val area = cursor.getString(cursor.getColumnIndex(KEY_AREA))
                val status = cursor.getInt(cursor.getColumnIndex(KEY_STATUS))
                val userEmail = cursor.getString(cursor.getColumnIndex(KEY_USER_EMAIL))
                val report = Report(id, description, date, area, status, userEmail)
                reportList.add(report)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return reportList
    }

    fun getReportsByUserEmail(userEmail: String): List<Report> {
        val reportList = mutableListOf<Report>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_REPORT WHERE $KEY_USER_EMAIL = ? ORDER BY $KEY_ID DESC"
        val cursor: Cursor = db.rawQuery(query, arrayOf(userEmail))

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val description = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))
                val date = cursor.getString(cursor.getColumnIndex(KEY_DATE))
                val area = cursor.getString(cursor.getColumnIndex(KEY_AREA))
                val status = cursor.getInt(cursor.getColumnIndex(KEY_STATUS))
                val report = Report(id, description, date, area, status, userEmail)
                reportList.add(report)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return reportList
    }

    fun getLatestReportsWithStatus3(): List<Report> {
        val reportList = mutableListOf<Report>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_REPORT WHERE $KEY_STATUS = 3 ORDER BY $KEY_ID DESC LIMIT 1"
        val cursor: Cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val description = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))
                val date = cursor.getString(cursor.getColumnIndex(KEY_DATE))
                val area = cursor.getString(cursor.getColumnIndex(KEY_AREA))
                val status = cursor.getInt(cursor.getColumnIndex(KEY_STATUS))
                val userEmail = cursor.getString(cursor.getColumnIndex(KEY_USER_EMAIL))
                val report = Report(id, description, date, area, status, userEmail)
                reportList.add(report)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return reportList
    }

    fun updateReport(report: Report): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_DESCRIPTION, report.description)
        values.put(KEY_DATE, report.date)
        values.put(KEY_AREA, report.area)
        values.put(KEY_STATUS, report.status)

        return db.update(TABLE_REPORT, values, "$KEY_ID = ?", arrayOf(report.id.toString()))
    }

    fun deleteReport(report: Report): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_REPORT, "$KEY_ID = ?", arrayOf(report.id.toString()))
    }
}
