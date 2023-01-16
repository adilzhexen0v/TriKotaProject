package com.example.trikotaproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.trikotaproject.databinding.ActivityUnauthorizedMainBinding
import com.example.trikotaproject.ui.getstarted.GetStartedFragment
import com.example.trikotaproject.ui.getstarted.LoginFragment
import com.example.trikotaproject.ui.getstarted.RegisterFragmentFirst
import com.example.trikotaproject.ui.getstarted.RegisterFragmentSecond
import com.example.trikotaproject.unauthorizedcontract.UnauthorizedStageNavigator

class UnauthorizedActivity: AppCompatActivity(), UnauthorizedStageNavigator {

    private lateinit var binding: ActivityUnauthorizedMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUnauthorizedMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showGetStarted()
    }

    private fun launchFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment_unauthorized_activity_main,fragment)
        transaction.commit()
    }

    override fun logIn() {
        val listIntent: Intent = Intent(this, AuthorizedActivity::class.java)
        startActivity(listIntent)
    }

    override fun showGetStarted(){
        launchFragment(GetStartedFragment())
    }

    override fun showRegisterFirst() {
        launchFragment(RegisterFragmentFirst())
    }

    override fun showRegisterSecond(){
        launchFragment(RegisterFragmentSecond())
    }

    override fun showLogin() {
        launchFragment(LoginFragment())
    }
}