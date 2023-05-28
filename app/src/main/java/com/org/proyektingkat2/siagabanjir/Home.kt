package com.org.proyektingkat2.siagabanjir

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.org.proyektingkat2.db.DatabaseHandler
import com.org.proyektingkat2.siagabanjir.databinding.HomeBinding


class Home: AppCompatActivity() {
    private lateinit var binding: HomeBinding
    private lateinit var databaseHandler: DatabaseHandler
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseHandler = DatabaseHandler(this)
        drawerLayout = binding.drawerLayout
        navigationView = binding.navigationView

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
                R.id.account -> {
                    drawerLayout.openDrawer(navigationView)
                    true
                }
                else -> false
            }
        }
    }
}