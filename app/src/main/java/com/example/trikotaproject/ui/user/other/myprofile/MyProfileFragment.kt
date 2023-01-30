package com.example.trikotaproject.ui.user.other.myprofile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.trikotaproject.databinding.FragmentUserMyprofileBinding
import com.example.trikotaproject.jsonApi
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyProfileFragment: Fragment() {
    private var _binding: FragmentUserMyprofileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserMyprofileBinding.inflate(inflater, container, false)
        /*
        binding.myProfileImageImg.setImageResource(R.drawable.profilelogo)
        binding.myProfileImageImg.clipToOutline = true
        binding.myProfileImageImg.setBackgroundResource(R.drawable.round_img)
         */
        val preferences = context?.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE)
        val token = preferences?.getString("TOKEN", "")!!

        try {
            val call = jsonApi.getUserProfileData(token)
            call.enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    try {
                        val json = JSONObject(Gson().toJson(response.body()))
                        val name = json.getString("name")
                        val surname = json.getString("surname")
                        val email = json.getString("email")
                        val dateOfBirth = json.getString("dateOfBirth")
                        val gender = json.getString("gender")
                        val phone = json.getString("phoneNumber")
                        binding.myProfileNameText.text = name
                        binding.myProfileSurnameText.text = surname
                        binding.myProfileEmailText.text = email
                        binding.myProfilePhoneText.text = phone
                        binding.myProfileDateText.text = dateOfBirth
                        binding.myProfileGenderText.text =  gender

                    } catch (e: Exception) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}