package com.example.trikotaproject.ui.getstarted

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trikotaproject.JsonApi
import com.example.trikotaproject.R
import com.example.trikotaproject.databinding.FragmentRegisterSecondBinding
import com.example.trikotaproject.ui.getstarted.userModel.UserModel
import com.example.trikotaproject.unauthorizedcontract.navigator
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class RegisterFragmentSecond: Fragment() {
    private lateinit var binding: FragmentRegisterSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterSecondBinding.inflate(layoutInflater)

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://nosql-group-project-backend.onrender.com/")
            .build()
        val jsonApi = retrofitBuilder.create(JsonApi::class.java)

        val preferences = context?.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE)
        val editor = preferences?.edit()
        binding.registerBtn.setOnClickListener {
            var errorCounter = 0

            val name = preferences?.getString("NAME", "")!!
            val surname = preferences?.getString("SURNAME", "")!!
            val email = preferences?.getString("EMAIL", "")!!
            val password = preferences?.getString("PASSWORD", "")!!
            val phoneNumber = binding.enterPhoneText.text.toString()
            val day = binding.enterDayText.text.toString()
            val month = binding.enterMonthText.text.toString()
            val year = binding.enterYearText.text.toString()
            val gender = when(binding.chooseGender.checkedRadioButtonId) {
                R.id.female -> "Female"
                else -> "Male"
            }

            val user = UserModel(
                name,
                surname,
                email,
                password,
                "$day-$month-$year",
                phoneNumber,
                gender
            )





            if (binding.enterPhoneText.text.isEmpty()) {
                errorCounter++
                binding.phoneError.text = getString(R.string.error)
            } else {
                binding.phoneError.text = ""
            }


            if (binding.enterDayText.text.isEmpty() || binding.enterMonthText.text.isEmpty() || binding.enterYearText.text.length != 4) {
                errorCounter++
                binding.dateError.text = getString(R.string.error)
            } else {
                binding.dateError.text = ""
            }

            if (errorCounter == 0) {
                val call = jsonApi.registerUser(user)
                call.enqueue(object : Callback<JsonObject> {
                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                       try {
                           val json = JSONObject(Gson().toJson(response.body()))
                           val token = json.getString("token")
                           editor?.putString("TOKEN", token)
                           editor?.apply()
                           binding.error.text = user.toString()
                           navigator().logIn()
                        } catch (e: Exception) {
                            binding.error.text = e.toString()
                        }
                    }

                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        binding.error.text = t.message.toString()
                    }
                })
            }
        }

        return binding.root
    }
}