package com.example.trikotaproject.ui.getstarted

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.trikotaproject.JsonApi
import com.example.trikotaproject.R
import com.example.trikotaproject.authorizedcontract.navigator
import com.example.trikotaproject.databinding.FragmentMyprofileUpdateBinding
import com.example.trikotaproject.ui.getstarted.userModel.UserUpdateModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UpdateProfileFragment: Fragment() {
    private lateinit var binding: FragmentMyprofileUpdateBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyprofileUpdateBinding.inflate(layoutInflater)

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://nosql-group-project-backend.onrender.com/")
            .build()
        val jsonApi = retrofitBuilder.create(JsonApi::class.java)

        val preferences = context?.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE)
        binding.updateBtn.setOnClickListener {
            val token = preferences?.getString("TOKEN", "")!!
            val id = preferences?.getString("ID", "")!!
            val name = binding.updateNameText.text.toString()
            val surname = binding.updateSurnameText.text.toString()
            val email = binding.updateEmailText.text.toString()
            val phone = binding.updatePhoneText.text.toString()
            val day = binding.updateDayText.text.toString()
            val month = binding.updateMonthText.text.toString()
            val year = binding.updateYearText.text.toString()
            val user = UserUpdateModel(
                id,
                name,
                surname,
                email,
                "$day-$month-$year",
                phone
            )
            val callUpdate = jsonApi.updateUserProfileData(token, user)
            callUpdate.enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    try {
                        navigator().showMyProfile()
                    } catch (e: Exception) {
                        Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        }

        return binding.root
    }
}