package com.org.proyektingkat2.siagabanjir

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.org.proyektingkat2.db.DatabaseHandler
import com.org.proyektingkat2.session.SessionManager
import com.org.proyektingkat2.siagabanjir.databinding.HomeBinding

class Home : AppCompatActivity() {
    private lateinit var binding: HomeBinding
    private lateinit var databaseHandler: DatabaseHandler
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)
        databaseHandler = DatabaseHandler(this)

        if(!sessionManager.isLoggedIn()){
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        drawerLayout = binding.drawerLayout

        navigationView = binding.navigationView
        val headerView = navigationView.getHeaderView(0)
        val textViewNamaLengkap = headerView.findViewById<TextView>(R.id.namaLengkap)
        val textViewEmail = headerView.findViewById<TextView>(R.id.email)


        val session = sessionManager.getEmail().toString()
        val user = databaseHandler.getUser(session)
        if (user != null) {
            textViewNamaLengkap.text = user.namaLengkap
            textViewEmail.text = user.email
        }


        // Set up ActionBarDrawerToggle
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Set navigation item selected listener
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val fragment = HomeFragment()
                    replaceFragment(fragment)
                    true
                }
                R.id.history -> {
                    val fragment = HistoryFragment()
                    replaceFragment(fragment)
                    true
                }
                R.id.notifikasi -> {
                    val fragment = NotifikasiFragment()
                    replaceFragment(fragment)
                    true
                }
                R.id.account -> {
                    drawerLayout.openDrawer(navigationView)
                    true
                }
                else -> false
            }
        }
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    val fragment = HomeFragment()
                    replaceFragment(fragment)
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.histori -> {
                    val fragment = HistoryFragment()
                    replaceFragment(fragment)
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.notifikasi -> {
                    val fragment = NotifikasiFragment()
                    replaceFragment(fragment)
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.menu_account_settings -> {
                    val fragment = EditAkunFragment()
                    replaceFragment(fragment)
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.menu_password_settings -> {
                    val fragment = EditPasswordFragment()
                    replaceFragment(fragment)
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.menu_logout -> {
                    sessionManager.logout()
                    val dialogBuilder = MaterialAlertDialogBuilder(this)
                        .setTitle("Keluar aplikasi!")
                        .setMessage("Anda akan keluar aplikasi?")
                        .setPositiveButton("OK") { _, _ ->
                            // Tambahkan pemanggilan recreate() di dalam tindakan positif tombol OK
                            recreate()
                            val message = "Anda telah keluar"
                            val intent = Intent(this, Login::class.java)
                            intent.putExtra("toast_message", message)
                            startActivity(intent)
                        }
                        .setNegativeButton("No") { _, _ ->
                            // Tindakan yang ingin Anda lakukan ketika pengguna memilih tombol "No"
                            // Misalnya, Anda dapat menutup dialog atau mengabaikan tindakan ini
                        }
                    dialogBuilder.show()
                    true
                }
                else -> false
            }
        }

        // Set default fragment
        val defaultFragment = HomeFragment()
        replaceFragment(defaultFragment)


    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
//import android.os.Bundle
//import androidx.appcompat.app.ActionBarDrawerToggle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.drawerlayout.widget.DrawerLayout
//import com.google.android.material.navigation.NavigationView
//import com.org.proyektingkat2.db.DatabaseHandler
//import com.org.proyektingkat2.siagabanjir.databinding.HomeBinding
//
//
//class Home: AppCompatActivity() {
//    private lateinit var binding: HomeBinding
//    private lateinit var databaseHandler: DatabaseHandler
//    private lateinit var drawerLayout: DrawerLayout
//    private lateinit var navigationView: NavigationView
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = HomeBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        databaseHandler = DatabaseHandler(this)
//        drawerLayout = binding.drawerLayout
//        navigationView = binding.navigationView
//
//        // Set up ActionBarDrawerToggle
//        val toggle = ActionBarDrawerToggle(
//            this,
//            drawerLayout,
//            R.string.navigation_drawer_open,
//            R.string.navigation_drawer_close
//        )
//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()
//
//        // Set navigation item selected listener
//        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.account -> {
//                    drawerLayout.openDrawer(navigationView)
//                    true
//                }
//                else -> false
//            }
//        }
//    }
//}