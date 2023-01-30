package com.example.trikotaproject.ui.user.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trikotaproject.authorizedcontract.navigator
import com.example.trikotaproject.databinding.FragmentUserHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentUserHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.showHospitalsBtn.setOnClickListener {
            navigator().showHospitals()
        }
        binding.showDoctorsBtn.setOnClickListener {
            navigator().showDoctors()
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}