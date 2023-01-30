package com.example.trikotaproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.trikotaproject.authorizedcontract.AuthorizedUserStageNavigator
import com.example.trikotaproject.databinding.ActivityAuthorizedMainBinding
import com.example.trikotaproject.databinding.FragmentUserMyprofileBinding
import com.example.trikotaproject.ui.user.appointments.AppointmentsFragment
import com.example.trikotaproject.ui.user.other.myprofile.UpdateProfileFragment
import com.example.trikotaproject.ui.user.home.HomeFragment
import com.example.trikotaproject.ui.user.home.doctors.DoctorsFragment
import com.example.trikotaproject.ui.user.home.hospitals.HospitalsFragment
import com.example.trikotaproject.ui.user.other.OtherFragment
import com.example.trikotaproject.ui.user.other.myprofile.MyProfileFragment
import com.example.trikotaproject.ui.user.other.termsofuse.TermsOfUseFragment

class AuthorizedUserActivity : AppCompatActivity(), AuthorizedUserStageNavigator {
    private lateinit var binding: ActivityAuthorizedMainBinding
    private lateinit var profileBinding: FragmentUserMyprofileBinding
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var currentFragment: String
    private lateinit var toolbarBackButton: ImageView
    private lateinit var toolbarSettingsButton: ImageView

    private val HOME = "home"
    private val APPOINTMENTS = "appointments"
    private val OTHER = "other"
    private val DOCTORS = "doctors"
    private val HOSPITALS = "hospitals"
    private val MYPROFILE = "myprofile"
    private val TERMSOFUSE = "termsofuse"
    private val UPDATEPROFILE = "updateprofile"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizedMainBinding.inflate(layoutInflater)
        profileBinding = FragmentUserMyprofileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showHomePage()

        bottomNav = binding.navView
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
                HOSPITALS -> showHomePage()
                MYPROFILE -> showOtherPage()
                TERMSOFUSE -> showOtherPage()
                UPDATEPROFILE -> showMyProfile()
            }
        }

        toolbarSettingsButton = findViewById(R.id.toolbar_settings_button)
        toolbarSettingsButton.setOnClickListener {
            showUpdateProfile()
        }
        if(currentFragment == MYPROFILE) {
            toolbarSettingsButton.visibility = View.VISIBLE
        } else {
            toolbarSettingsButton.visibility = View.INVISIBLE
        }
    }

    override fun showTermsOfUse() {
        currentFragment = TERMSOFUSE
        launchFragment(TermsOfUseFragment())
    }

    override fun showMyProfile() {
        currentFragment = MYPROFILE
        launchFragment(MyProfileFragment())
    }

    override fun logOut() {
        val listIntent: Intent = Intent(this, UnauthorizedActivity::class.java)
        startActivity(listIntent)
    }

    override fun showHospitals() {
        currentFragment = HOSPITALS
        launchFragment(HospitalsFragment())
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

    override fun showUpdateProfile() {
        currentFragment = UPDATEPROFILE
        launchFragment(UpdateProfileFragment())
    }
}