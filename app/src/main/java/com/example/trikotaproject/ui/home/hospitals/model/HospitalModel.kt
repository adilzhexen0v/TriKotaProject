package com.example.trikotaproject.ui.home.hospitals.model

import androidx.annotation.DrawableRes

data class HospitalModel(
    @DrawableRes val image: Int,
    val hospital: String,
    val city: String,
    val address: String,
    val time: String,
    val phone: String
)