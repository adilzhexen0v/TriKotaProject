package com.example.trikotaproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import com.example.trikotaproject.databinding.ActivityUnauthorizedMainBinding
import com.example.trikotaproject.ui.getstarted.*
import com.example.trikotaproject.unauthorizedcontract.UnauthorizedStageNavigator

class UnauthorizedActivity: AppCompatActivity(), UnauthorizedStageNavigator {
    private lateinit var toolbarBackButton: ImageView
    private lateinit var binding: ActivityUnauthorizedMainBinding
    private lateinit var currentFragment: String
    private val DOCTORREGISTERFIRST = "DoctorRegisterFirst"
    private val DOCTORREGISTERSECOND = "DoctorRegisterSecond"
    private val DOCTORLOGIN = "DoctorLogin"
    private val USERREGISTERFIRST = "UserRegisterFirst"
    private val USERREGISTERSECOND = "UserRegisterSecond"
    private val USERLOGIN = "UserLogin"
    private val GETSTARTED = "GetStarted"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUnauthorizedMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showGetStarted()
    }
    private fun showOrHideBackButton(){
        toolbarBackButton = findViewById(R.id.back_btn)
        if(currentFragment == GETSTARTED) {
            toolbarBackButton.visibility = View.INVISIBLE
        } else {
            toolbarBackButton.visibility = View.VISIBLE
        }
        toolbarBackButton.setOnClickListener {
            when(currentFragment){
                USERLOGIN -> showGetStarted()
                USERREGISTERFIRST -> showGetStarted()
                USERREGISTERSECOND -> showUserRegisterFirst()
                DOCTORREGISTERFIRST -> showGetStarted()
                DOCTORREGISTERSECOND -> showDoctorRegisterFirst()
            }
        }
    }
    private fun launchFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment_unauthorized_activity_main,fragment)
        transaction.commit()
        showOrHideBackButton()
    }

    override fun userLogIn() {
        val listIntent: Intent = Intent(this, AuthorizedUserActivity::class.java)
        startActivity(listIntent)
    }

    override fun doctorLogIn() {
        val listIntent: Intent = Intent(this, AuthorizedDoctorActivity::class.java)
        startActivity(listIntent)
    }

    override fun showGetStarted(){
        currentFragment = GETSTARTED
        launchFragment(GetStartedFragment())
    }

    override fun showUserRegisterFirst() {
        currentFragment = USERREGISTERFIRST
        launchFragment(UserRegisterFragmentFirst())
    }

    override fun showUserRegisterSecond(){
        currentFragment = USERREGISTERSECOND
        launchFragment(UserRegisterFragmentSecond())
    }

    override fun showUserLogin() {
        currentFragment = USERLOGIN
        launchFragment(UserLoginFragment())
    }

    override fun showDoctorRegisterFirst() {
        currentFragment = DOCTORREGISTERFIRST
        launchFragment(DoctorRegisterFragmentFirst())
    }

    override fun showDoctorRegisterSecond() {
        currentFragment = DOCTORREGISTERSECOND
        launchFragment(DoctorRegisterFragmentSecond())
    }

    override fun showDoctorLogin() {
        currentFragment = DOCTORLOGIN
        launchFragment(DoctorLoginFragment())
    }
}