package com.example.trikotaproject.ui.user.home.doctors

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.trikotaproject.authorizedcontract.navigator
import com.example.trikotaproject.databinding.FragmentUserHomeDoctorsBinding
import com.example.trikotaproject.jsonApi
import com.example.trikotaproject.ui.user.home.doctors.adapter.DoctorAdapter
import com.example.trikotaproject.ui.user.home.doctors.model.DoctorModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoctorsFragment: Fragment() {

    private lateinit var binding: FragmentUserHomeDoctorsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserHomeDoctorsBinding.inflate(inflater, container, false)

        val preferences = context?.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE)
        val token = preferences?.getString("TOKEN", "")!!
        Glide.with(requireContext()).load("https://thumbs.gfycat.com/CorruptOldfashionedGuineapig-max-1mb.gif").into(binding.loading)
        try {
            val call = jsonApi.getListOfDoctors(token)
            call.enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    try {
                        val json = JSONObject(Gson().toJson(response.body()))
                        val doctorsJsonArray = json.getJSONArray("doctors")
                        val doctorsList: MutableList<DoctorModel> = mutableListOf()
                        for (i in 0 until doctorsJsonArray.length()) {
                            val name = doctorsJsonArray.getJSONObject(i).getString("name")
                            val surname = doctorsJsonArray.getJSONObject(i).getString("surname")
                            val occupation = JSONObject(
                                doctorsJsonArray
                                    .getJSONObject(i)
                                    .getString("occupation")
                            ).getString("occupation")
                            val experience = doctorsJsonArray.getJSONObject(i).getString("experience")
                            val hospitalName = JSONObject(
                                doctorsJsonArray
                                    .getJSONObject(i)
                                    .getString("hospitalId")
                            ).getString("hospital")
                            val hospitalCity = JSONObject(
                                doctorsJsonArray
                                    .getJSONObject(i)
                                    .getString("hospitalId")
                            ).getString("city")
                            val hospitalAddress = JSONObject(
                                doctorsJsonArray
                                    .getJSONObject(i)
                                    .getString("hospitalId")
                            ).getString("address")
                            var imageUrl = "empty"
                            try {
                                imageUrl = doctorsJsonArray.getJSONObject(i).getString("imageUrl")
                            } catch (e: Exception) {
                                println(e)
                            }
                            val doctor = DoctorModel (
                                "$name $surname",
                                    occupation,
                                    experience,
                                "$hospitalName, $hospitalCity, $hospitalAddress",
                                imageUrl
                                    )
                            doctorsList.add(doctor)
                        }
                        val doctorsRecyclerView = binding.recyclerView
                        doctorsRecyclerView.adapter = DoctorAdapter(container?.context, doctorsList)
                        binding.loading.visibility = View.INVISIBLE
                    } catch (e: Exception) {
                        Toast.makeText(context, "From catch: " + e.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Toast.makeText(context, "From onFailure: " + t.message.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun onToMainMenuPressed() {
        navigator().showHomePage()
    }
}