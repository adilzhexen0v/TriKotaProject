package com.example.trikotaproject.authorizeddoctorcontact

import androidx.fragment.app.Fragment

fun Fragment.navigator(): AuthorizedDoctorStageNavigator {
    return requireActivity() as AuthorizedDoctorStageNavigator
}

interface AuthorizedDoctorStageNavigator {
    fun showHomePage()
    fun showAppointmentsPage()
    fun showMyProfile()
}