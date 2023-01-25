package com.example.trikotaproject.unauthorizedcontract

import androidx.fragment.app.Fragment

fun Fragment.navigator(): UnauthorizedStageNavigator {
    return requireActivity() as UnauthorizedStageNavigator
}

interface UnauthorizedStageNavigator {
    fun showGetStarted()
    fun showUserRegisterFirst()
    fun showUserRegisterSecond()
    fun showUserLogin()
    fun userLogIn()
    fun showDoctorRegisterFirst()
    fun showDoctorRegisterSecond()
}