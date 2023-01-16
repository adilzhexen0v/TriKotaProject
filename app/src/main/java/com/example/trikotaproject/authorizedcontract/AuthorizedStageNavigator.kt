package com.example.trikotaproject.authorizedcontract

import androidx.fragment.app.Fragment

fun Fragment.navigator(): AuthorizedStageNavigator {
    return requireActivity() as AuthorizedStageNavigator
}

interface AuthorizedStageNavigator {
    fun showHomePage()
    fun showAppointmentsPage()
    fun showOtherPage()
    fun showDoctors()
    fun showHospitals()
    fun showMyProfile()
    fun showTermsOfUse()
    fun showUpdateProfile()
    fun logOut()
}