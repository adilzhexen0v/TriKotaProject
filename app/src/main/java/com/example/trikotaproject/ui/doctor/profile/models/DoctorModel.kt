package com.example.trikotaproject.ui.doctor.profile.models

import retrofit2.http.Multipart
import retrofit2.http.Part

data class DoctorModel(
    val _id: String,
    val docimg: Multipart
)