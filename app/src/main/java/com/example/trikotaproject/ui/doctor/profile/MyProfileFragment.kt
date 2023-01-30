package com.example.trikotaproject.ui.doctor.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trikotaproject.databinding.FragmentDoctorMyprofileBinding

class MyProfileFragment: Fragment() {
    private lateinit var binding: FragmentDoctorMyprofileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDoctorMyprofileBinding.inflate(inflater, container, false)


        return binding.root
    }
}