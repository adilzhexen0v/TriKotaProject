package com.example.trikotaproject.ui.getstarted

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trikotaproject.JsonApi
import com.example.trikotaproject.R
import com.example.trikotaproject.databinding.FragmentRegisterBinding
import com.example.trikotaproject.ui.getstarted.userModel.UserModel
import com.example.trikotaproject.unauthorizedcontract.navigator
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class RegisterFragment: Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)

//        val preferences = context?.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE)
//        val editor = preferences?.edit()

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://nosql-group-project-backend.onrender.com/")
            .build()
        val jsonApi = retrofitBuilder.create(JsonApi::class.java)
        /*
        val gender = when(binding.chooseGender.checkedRadioButtonId) {
            R.id.male -> "Male"
            else -> "Female"
        }
        */
//        val user = UserModel(
//            binding.enterNameText.text.toString(),
//            binding.enterSurnameText.text.toString(),
//            binding.enterEmailText.text.toString(),
//            binding.enterPasswordText.text.toString(),
//            "${binding.enterDayText.text}-${binding.enterMonthText.text}-${binding.enterYearText.text}",
//        )


        val user = UserModel(
            "Name",
            "Surname",
            "smthg@gmail.com",
            "Qwerty123!",
            "08-12-1999",
        )
        val day = binding.enterDayText.text.toString()
        val month = binding.enterMonthText.text.toString()
        val year = binding.enterYearText.text.toString()

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
                /*
                editor?.putString("NAME", binding.enterNameText.text.toString())
                editor?.putString("SURNAME", binding.enterSurnameText.text.toString())
                editor?.putString("EMAIL", binding.enterEmailText.text.toString())
                editor?.putString("DAY", binding.enterDayText.text.toString())
                editor?.putString("MONTH", binding.enterMonthText.text.toString())
                editor?.putString("YEAR", binding.enterYearText.text.toString())
                editor?.putString("GENDER", gender)
                editor?.apply()
                 */

                val call = jsonApi.registerUser(user)
                call.enqueue(object : Callback<JsonObject> {
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                        //val json = JSONObject(Gson().toJson(response.body()))
                        //val token = json.getString("token")
                        navigator().logIn()

                        //binding.error.text = "$day-$month-$year"
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