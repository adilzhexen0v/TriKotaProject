package com.example.trikotaproject.contract

import androidx.fragment.app.Fragment

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {
    fun showHomePage()
    fun showAppointmentsPage()
    fun showOtherPage()
    fun showDoctors()
    fun showHospitals()
    fun showMyProfile()
    fun showTermsOfUse()
    fun logOut()
}