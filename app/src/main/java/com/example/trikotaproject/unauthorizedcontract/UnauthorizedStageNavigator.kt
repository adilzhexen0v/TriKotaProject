package com.example.trikotaproject.unauthorizedcontract

import androidx.fragment.app.Fragment

fun Fragment.navigator(): UnauthorizedStageNavigator {
    return requireActivity() as UnauthorizedStageNavigator
}

interface UnauthorizedStageNavigator {
    fun showGetStarted()
    fun showRegisterFirst()
    fun showRegisterSecond()
    fun showLogin()
    fun logIn()
}