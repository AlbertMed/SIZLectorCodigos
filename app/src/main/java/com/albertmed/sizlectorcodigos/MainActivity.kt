package com.albertmed.sizlectorcodigos

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.albertmed.sizlectorcodigos.data.UserRepository
import com.albertmed.sizlectorcodigos.data.datastore.SessionManager
import com.albertmed.sizlectorcodigos.data.network.RetrofitClient
import com.albertmed.sizlectorcodigos.databinding.ActivityMainBinding
import com.albertmed.sizlectorcodigos.ui.main.MainViewModel
import com.albertmed.sizlectorcodigos.util.ViewModelFactory
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory(
            UserRepository(
                RetrofitClient.instance,
                SessionManager(applicationContext)
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment, R.id.nav_scanner
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        setupObservers()
        setupDrawer()
    }

    private fun setupObservers() {
        val headerView = binding.navView.getHeaderView(0)
        val navHeaderName: TextView = headerView.findViewById(R.id.nav_header_name)
        val navHeaderDepartment: TextView = headerView.findViewById(R.id.nav_header_department)

        mainViewModel.userName.observe(this) { name ->
            navHeaderName.text = if (name != null) "Hola, $name" else "Invitado"
            updateMenuVisibility(name != null)
        }
        mainViewModel.userDepartment.observe(this) { department ->
            navHeaderDepartment.text = department ?: "Departamento"
            navHeaderDepartment.visibility = if (department.isNullOrBlank()) View.GONE else View.VISIBLE
        }
    }

    private fun updateMenuVisibility(isLoggedIn: Boolean) {
        val menu = binding.navView.menu
        menu.findItem(R.id.nav_home).isVisible = isLoggedIn
        menu.findItem(R.id.nav_logout).isVisible = isLoggedIn
        menu.findItem(R.id.nav_login).isVisible = !isLoggedIn
    }

    private fun setupDrawer() {
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            binding.drawerLayout.close()

            // Handle logout separately to clear session and back stack
            if (menuItem.itemId == R.id.nav_logout) {
                mainViewModel.logout()
                // Navigate to login and clear back stack
                findNavController(R.id.nav_host_fragment_content_main).navigate(
                    R.id.loginFragment,
                    null,
                    androidx.navigation.NavOptions.Builder()
                        .setPopUpTo(R.id.nav_graph, true)
                        .build()
                )
                return@setNavigationItemSelectedListener true
            }

            if (menuItem.itemId == R.id.nav_login) {
                // Siempre navega al login y limpia el back stack
                findNavController(R.id.nav_host_fragment_content_main).navigate(
                    R.id.loginFragment,
                    null,
                    androidx.navigation.NavOptions.Builder()
                        .setPopUpTo(R.id.nav_graph, true)
                        .build()
                )
                return@setNavigationItemSelectedListener true
            }

            // Let NavigationUI handle other selections
            return@setNavigationItemSelectedListener androidx.navigation.ui.NavigationUI.onNavDestinationSelected(
                menuItem,
                findNavController(R.id.nav_host_fragment_content_main)
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}