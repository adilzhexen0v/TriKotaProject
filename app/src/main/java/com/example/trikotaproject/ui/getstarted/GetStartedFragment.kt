package com.example.trikotaproject.ui.getstarted

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trikotaproject.unauthorizedcontract.navigator
import com.example.trikotaproject.databinding.FragmentGetstartedBinding

class GetStartedFragment: Fragment() {
    private lateinit var binding: FragmentGetstartedBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGetstartedBinding.inflate(layoutInflater)
        binding.getStartedBtn.setOnClickListener {
            navigator().showRegisterFirst()
        }
        return binding.root
    }
}