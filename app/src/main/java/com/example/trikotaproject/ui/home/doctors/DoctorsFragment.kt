package com.example.trikotaproject.ui.home.doctors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trikotaproject.authorizedcontract.navigator
import com.example.trikotaproject.databinding.FragmentHomeDoctorsBinding
import com.example.trikotaproject.ui.home.doctors.adapter.DoctorAdapter
import com.example.trikotaproject.ui.home.doctors.data.DoctorDatasource

class DoctorsFragment: Fragment() {

    private lateinit var binding: FragmentHomeDoctorsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeDoctorsBinding.inflate(inflater, container, false)
        val doctorsRecyclerView = binding.recyclerView
        doctorsRecyclerView.adapter = DoctorAdapter(container?.context, DoctorDatasource.doctors)
        return binding.root
    }

    private fun onToMainMenuPressed() {
        navigator().showHomePage()
    }
}