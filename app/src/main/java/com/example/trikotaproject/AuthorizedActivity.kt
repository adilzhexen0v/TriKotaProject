package com.example.trikotaproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.trikotaproject.authorizedcontract.AuthorizedStageNavigator
import com.example.trikotaproject.databinding.ActivityAuthorizedMainBinding
import com.example.trikotaproject.databinding.FragmentMyprofileBinding
import com.example.trikotaproject.ui.appointments.AppointmentsFragment
import com.example.trikotaproject.ui.home.HomeFragment
import com.example.trikotaproject.ui.home.doctors.DoctorsFragment
import com.example.trikotaproject.ui.home.hospitals.HospitalsFragment
import com.example.trikotaproject.ui.other.OtherFragment
import com.example.trikotaproject.ui.other.myprofile.MyProfileFragment
import com.example.trikotaproject.ui.other.termsofuse.TermsOfUseFragment

class AuthorizedActivity : AppCompatActivity(), AuthorizedStageNavigator {
    private lateinit var binding: ActivityAuthorizedMainBinding
    private lateinit var profileBinding: FragmentMyprofileBinding
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

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizedMainBinding.inflate(layoutInflater)
        profileBinding = FragmentMyprofileBinding.inflate(layoutInflater)
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
            }
        }

        toolbarSettingsButton = findViewById(R.id.toolbar_settings_button)
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
}