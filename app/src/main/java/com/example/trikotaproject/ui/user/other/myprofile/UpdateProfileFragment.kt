package com.example.trikotaproject.ui.user.other.myprofile

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.trikotaproject.authorizedcontract.navigator
import com.example.trikotaproject.databinding.FragmentUserMyprofileUpdateBinding
import com.example.trikotaproject.jsonApi
import com.example.trikotaproject.ui.getstarted.userModel.UserUpdateModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateProfileFragment: Fragment() {
    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    private lateinit var binding: FragmentUserMyprofileUpdateBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserMyprofileUpdateBinding.inflate(layoutInflater)

        val preferences = context?.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE)
        val token = preferences?.getString("TOKEN", "")!!
        val id = preferences?.getString("ID", "")!!

        val call = jsonApi.getUserProfileData(token)
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                try {
                    val json = JSONObject(Gson().toJson(response.body()))
                    val name = json.getString("name")
                    val surname = json.getString("surname")
                    val email = json.getString("email")
                    val dateOfBirth = json.getString("dateOfBirth")
                    val phone = json.getString("phoneNumber")
                    binding.updateNameText.text = name.toEditable()
                    binding.updateSurnameText.text = surname.toEditable()
                    binding.updateEmailText.text = email.toEditable()
                    binding.updatePhoneText.text = phone.toEditable()
                    binding.updateDayText.text = dateOfBirth.toString().substring(0, 2).toEditable()
                    binding.updateMonthText.text = dateOfBirth.toString().substring(3, 5).toEditable()
                    binding.updateYearText.text = dateOfBirth.toString().substring(6).toEditable()
                } catch (e: Exception) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })


        binding.updateBtn.setOnClickListener {
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