package com.example.trikotaproject.ui.getstarted.doctorModel

data class DoctorRegisterModel(
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val occupation: String,
    val experience: String,
    val hospital: String,
    val city: String,
    val address: String
)