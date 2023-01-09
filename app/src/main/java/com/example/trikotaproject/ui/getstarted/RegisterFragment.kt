package com.example.trikotaproject.ui.getstarted

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trikotaproject.R
import com.example.trikotaproject.databinding.FragmentRegisterBinding
import com.example.trikotaproject.unauthorizedcontract.navigator

class RegisterFragment: Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)

        val preferences = context?.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE)
        val editor = preferences?.edit()

        binding.registerBtn.setOnClickListener {
            var errorCounter = 0
            if (binding.enterNameText.text.isEmpty()) {
                errorCounter++
                binding.nameError.text = getString(R.string.error)
            }
            if (binding.enterSurnameText.text.isEmpty()) {
                errorCounter++
                binding.surnameError.text = getString(R.string.error)
            }
            if (binding.enterEmailText.text.isEmpty()) {
                errorCounter++
                binding.emailError.text = getString(R.string.error)
            }
            if (binding.enterPasswordText.text.isEmpty()) {
                errorCounter++
                binding.passwordError.text = getString(R.string.error)
            }
            if (binding.enterDayText.text.isEmpty() || binding.enterMonthText.text.isEmpty() || binding.enterYearText.text.isEmpty()) {
                errorCounter++
                binding.dateError.text = getString(R.string.error)
            }

            if (errorCounter == 0) {
                editor?.putString("NAME", binding.enterNameText.text.toString())
                editor?.putString("SURNAME", binding.enterSurnameText.text.toString())
                editor?.putString("EMAIL", binding.enterEmailText.text.toString())
                editor?.putString("DAY", binding.enterDayText.text.toString())
                editor?.putString("MONTH", binding.enterMonthText.text.toString())
                editor?.putString("YEAR", binding.enterYearText.text.toString())
                val gender = when(binding.chooseGender.checkedRadioButtonId) {
                    R.id.male -> "Male"
                    else -> "Female"
                }
                editor?.putString("GENDER", gender)
                editor?.apply()
                navigator().logIn()
            }
        }



        return binding.root
    }
}