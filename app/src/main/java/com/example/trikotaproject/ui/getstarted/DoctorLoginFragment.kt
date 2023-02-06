package com.example.trikotaproject.ui.getstarted

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.trikotaproject.R
import com.example.trikotaproject.databinding.FragmentDoctorLoginBinding
import com.example.trikotaproject.jsonApi
import com.example.trikotaproject.ui.getstarted.doctorModel.DoctorLoginModel
import com.example.trikotaproject.unauthorizedcontract.navigator
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoctorLoginFragment:Fragment() {
    private lateinit var binding: FragmentDoctorLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDoctorLoginBinding.inflate(inflater, container, false)
        val preferences = context?.getSharedPreferences("DOCTOR_INFO", Context.MODE_PRIVATE)
        val editor = preferences?.edit()

        binding.loginBtn.setOnClickListener {
            binding.error.text = "Please, wait..."
            binding.error.setTextColor(resources.getColor(R.color.green_700))
            val email = binding.enterEmailText.text.toString()
            val  password = binding.enterPasswordText.text.toString()
            val call = jsonApi.loginDoctor(DoctorLoginModel(
                email, password
            ))
            call.enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    try {
                        if (response.code() == 400) {
                            val mess = "Incorrect email or password"
                            binding.error.text = mess
                            binding.error.setTextColor(resources.getColor(R.color.red))
                        } else {
                            val json = JSONObject(Gson().toJson(response.body()))
                            val token = json.getString("token")
                            editor?.putString("TOKEN", token)
                            val id = json.getString("_id")
                            editor?.putString("ID", id)
                            editor?.apply()
                            navigator().doctorLogIn()
                        }
                    } catch (e: Exception) {
                        binding.error.text = e.toString()
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    var toastMsg = t.message.toString()
                    if (t.message == "timeout") {
                        toastMsg = "The waiting time has been exceeded. Please repeat again!"
                    }
                    Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show()
                }
            })
        }

        binding.moveRegisterBtn.setOnClickListener {
            navigator().showDoctorRegisterFirst()
        }
        return binding.root
    }
}