package com.example.trikotaproject.ui.getstarted

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trikotaproject.R
import com.example.trikotaproject.databinding.FragmentUserRegisterFirstBinding
import com.example.trikotaproject.unauthorizedcontract.navigator

class UserRegisterFragmentFirst: Fragment() {
    private lateinit var binding: FragmentUserRegisterFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserRegisterFirstBinding.inflate(layoutInflater)

        val preferences = context?.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE)
        val editor = preferences?.edit()

        binding.nextBtn.setOnClickListener {
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

            if (errorCounter == 0) {
                editor?.putString("NAME", binding.enterNameText.text.toString())
                editor?.putString("SURNAME", binding.enterSurnameText.text.toString())
                editor?.putString("EMAIL", binding.enterEmailText.text.toString())
                editor?.putString("PASSWORD", binding.enterPasswordText.text.toString())
                editor?.apply()
                navigator().showUserRegisterSecond()
            }
        }

        binding.moveLoginBtn.setOnClickListener {
            navigator().showUserLogin()
        }

        return binding.root
    }
}