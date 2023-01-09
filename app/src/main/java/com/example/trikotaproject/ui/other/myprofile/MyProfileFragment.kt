package com.example.trikotaproject.ui.other.myprofile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trikotaproject.databinding.FragmentMyprofileBinding

class MyProfileFragment: Fragment() {
    private var _binding: FragmentMyprofileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyprofileBinding.inflate(inflater, container, false)

        val preferences = context?.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE)
        val name = preferences?.getString("NAME", "")
        val surname = preferences?.getString("SURNAME", "")
        val email = preferences?.getString("EMAIL", "")
        val day = preferences?.getString("DAY", "")
        val month = preferences?.getString("MONTH", "")
        val year = preferences?.getString("YEAR", "")
        val gender = preferences?.getString("GENDER", "")
        binding.myProfileNameText.text = name
        binding.myProfileSurnameText.text = surname
        binding.myProfileEmailText.text = email
        binding.myProfileDateText.text = "$day-$month-$year"
        binding.myProfileGenderText.text = gender
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}