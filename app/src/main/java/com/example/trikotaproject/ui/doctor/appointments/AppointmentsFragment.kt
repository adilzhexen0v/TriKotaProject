package com.example.trikotaproject.ui.doctor.appointments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trikotaproject.databinding.FragmentDoctorAppointmentsBinding

class AppointmentsFragment: Fragment() {
    private lateinit var binding: FragmentDoctorAppointmentsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDoctorAppointmentsBinding.inflate(inflater, container, false)
        return binding.root
    }
}