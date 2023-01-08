package com.example.trikotaproject.ui.home.hospitals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trikotaproject.databinding.FragmentHomeHospitalsBinding
import com.example.trikotaproject.ui.home.hospitals.adapter.HospitalAdapter
import com.example.trikotaproject.ui.home.hospitals.data.HospitalDatasource

class HospitalsFragment: Fragment() {
    private lateinit var binding: FragmentHomeHospitalsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeHospitalsBinding.inflate(inflater, container, false)
        val doctorsRecyclerView = binding.recyclerView
        doctorsRecyclerView.adapter = HospitalAdapter(container?.context, HospitalDatasource.hospitals)
        return binding.root
    }
}