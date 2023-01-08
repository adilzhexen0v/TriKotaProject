package com.example.trikotaproject

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.trikotaproject.contract.Navigator
import com.example.trikotaproject.databinding.ActivityAuthorizedMainBinding
import com.example.trikotaproject.ui.appointments.AppointmentsFragment
import com.example.trikotaproject.ui.home.HomeFragment
import com.example.trikotaproject.ui.home.doctors.DoctorsFragment
import com.example.trikotaproject.ui.other.OtherFragment

class AuthorizedActivity : AppCompatActivity(), Navigator {
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var currentFragment: String

    private val HOME = "home"
    private val APPOINTMENTS = "appointments"
    private val OTHER = "other"

    override fun onCreate(savedInstanceState: Bundle?) {
        /*
        super.onCreate(savedInstanceState)

        binding = ActivityAuthorizedMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_appointments, R.id.navigation_other
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        */

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorized_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        launchFragment(HomeFragment())

        currentFragment = HOME

        bottomNav = findViewById(R.id.nav_view)
        bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home -> {
                    showHomePage()
                    true
                }
                R.id.navigation_appointments -> {
                    showAppointmentsPage()
                    true
                }
                else -> {
                    showOtherPage()
                    true
                }
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            when(currentFragment){
                HOME -> showHomePage()
                APPOINTMENTS -> showAppointmentsPage()
                OTHER -> showOtherPage()
            }
        }
        return true
    }

    private fun launchFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment_activity_main,fragment)
        transaction.commit()
    }

    override fun showDoctors(){
        launchFragment(DoctorsFragment())
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun goToHome() {
        launchFragment(HomeFragment())
    }

    override fun showHomePage() {
        launchFragment(HomeFragment())
        currentFragment = HOME
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun showAppointmentsPage() {
        launchFragment(AppointmentsFragment())
        currentFragment = APPOINTMENTS
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun showOtherPage() {
        launchFragment(OtherFragment())
        currentFragment = OTHER
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}