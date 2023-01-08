package com.example.trikotaproject.ui.home.doctors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trikotaproject.contract.navigator
import com.example.trikotaproject.databinding.FragmentHomeDoctorsBinding

class DoctorsFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentHomeDoctorsBinding.inflate(inflater, container, false).apply {
        goBackBtn.setOnClickListener { onToMainMenuPressed() }
    }.root



    private fun onToMainMenuPressed() {
        navigator().goToHome()
    }
}