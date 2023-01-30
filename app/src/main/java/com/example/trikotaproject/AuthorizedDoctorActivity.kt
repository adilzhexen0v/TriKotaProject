package com.example.trikotaproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.trikotaproject.authorizeddoctorcontact.AuthorizedDoctorStageNavigator
import com.example.trikotaproject.databinding.ActivityAuthorizedDoctorMainBinding
import com.example.trikotaproject.ui.doctor.appointments.AppointmentsFragment
import com.example.trikotaproject.ui.doctor.home.HomeFragment
import com.example.trikotaproject.ui.doctor.profile.MyProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class AuthorizedDoctorActivity: AppCompatActivity(), AuthorizedDoctorStageNavigator {
    private lateinit var binding: ActivityAuthorizedDoctorMainBinding
    private lateinit var nav: BottomNavigationView
    private lateinit var currentFragment: String
    private val HOME = "home"
    private val APPOINTMENTS = "appointments"
    private val MYPROFILE = "myprofile"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizedDoctorMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nav = binding.navView
        nav.setOnItemSelectedListener {
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
                    showMyProfile()
                    true
                }
            }
        }

        showHomePage()
    }

    private fun launchFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment_activity_main,fragment)
        transaction.commit()
    }

    override fun showHomePage() {
        currentFragment = HOME
        launchFragment(HomeFragment())
    }

    override fun showAppointmentsPage() {
        currentFragment = APPOINTMENTS
        launchFragment(AppointmentsFragment())
    }

    override fun showMyProfile() {
        currentFragment = MYPROFILE
        launchFragment(MyProfileFragment())
    }
}