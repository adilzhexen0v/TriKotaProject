package com.example.trikotaproject.ui.user.home.hospitals.data

import com.example.trikotaproject.R
import com.example.trikotaproject.ui.user.home.hospitals.model.HospitalModel

object HospitalDatasource {
    val hospitals: List<HospitalModel> = listOf(
        HospitalModel(
            R.drawable.greenclinic,
            "Green Clinic",
            "Astana",
            "Mangilik El, 17/1",
            "08:00-21:00",
            "+7 (702) 892-85-13"
        ),
        HospitalModel(
            R.drawable.alanclinic,
            "Alan Clinic",
            "Astana",
            "Turan, 55/3",
            "07:00-19:00",
            "+7 (775) 648-82-47"
        ),
        HospitalModel(
            R.drawable.abylaiclinic,
            "Abylai Clinic",
            "Almaty",
            "Sauran, 43/1",
            "07:00-21:00",
            "+7 (705) 397-65-97"
        )
    )
}