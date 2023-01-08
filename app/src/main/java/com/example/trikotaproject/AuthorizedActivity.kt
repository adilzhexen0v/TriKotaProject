package com.example.trikotaproject

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.trikotaproject.contract.Navigator
import com.example.trikotaproject.ui.appointments.AppointmentsFragment
import com.example.trikotaproject.ui.home.HomeFragment
import com.example.trikotaproject.ui.home.doctors.DoctorsFragment
import com.example.trikotaproject.ui.other.OtherFragment

class AuthorizedActivity : AppCompatActivity(), Navigator {
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var currentFragment: String
    private lateinit var toolbarBackButton: ImageView

    private val HOME = "home"
    private val APPOINTMENTS = "appointments"
    private val OTHER = "other"
    private val DOCTORS = "doctors"

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

        showHomePage()

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


    private fun launchFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment_activity_main,fragment)
        transaction.commit()
        showOrHideBackButton()
    }

    private fun showOrHideBackButton(){
        toolbarBackButton = findViewById(R.id.toolbar_back_button)
        if(currentFragment != HOME && currentFragment != APPOINTMENTS && currentFragment != OTHER) {
            toolbarBackButton.visibility = View.VISIBLE
        } else {
            toolbarBackButton.visibility = View.INVISIBLE
        }
        toolbarBackButton.setOnClickListener {
            when(currentFragment){
                DOCTORS -> showHomePage()
            }
        }
    }

    override fun showDoctors(){
        currentFragment = DOCTORS
        launchFragment(DoctorsFragment())
    }

    override fun showHomePage() {
        currentFragment = HOME
        launchFragment(HomeFragment())
    }

    override fun showAppointmentsPage() {
        currentFragment = APPOINTMENTS
        launchFragment(AppointmentsFragment())
    }

    override fun showOtherPage() {
        currentFragment = OTHER
        launchFragment(OtherFragment())
    }
}