package com.example.trikotaproject.ui.home.doctors

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.trikotaproject.JsonApi
import com.example.trikotaproject.authorizedcontract.navigator
import com.example.trikotaproject.databinding.FragmentHomeDoctorsBinding
import com.example.trikotaproject.ui.home.doctors.adapter.DoctorAdapter
import com.example.trikotaproject.ui.home.doctors.data.DoctorDatasource
import com.example.trikotaproject.ui.home.doctors.model.DoctorModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DoctorsFragment: Fragment() {

    private lateinit var binding: FragmentHomeDoctorsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeDoctorsBinding.inflate(inflater, container, false)


        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://nosql-group-project-backend.onrender.com/")
            .build()
        val jsonApi = retrofitBuilder.create(JsonApi::class.java)

        val preferences = context?.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE)
        val token = preferences?.getString("TOKEN", "")!!

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
                                    .getJSONObject(0)
                                    .getString("hospitalId")
                            ).getString("hospital")
                            val hospitalCity = JSONObject(
                                doctorsJsonArray
                                    .getJSONObject(0)
                                    .getString("hospitalId")
                            ).getString("city")
                            val hospitalAddress = JSONObject(
                                doctorsJsonArray
                                    .getJSONObject(0)
                                    .getString("hospitalId")
                            ).getString("address")
                            val doctor = DoctorModel (
                                "$name $surname",
                                    occupation,
                                    experience,
                                "$hospitalName, $hospitalCity, $hospitalAddress"
                                    )
                            doctorsList.add(doctor)
                        }
                        val doctorsRecyclerView = binding.recyclerView
                        doctorsRecyclerView.adapter = DoctorAdapter(container?.context, doctorsList)
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


        val doctorsRecyclerView = binding.recyclerView
        doctorsRecyclerView.adapter = DoctorAdapter(container?.context, DoctorDatasource.doctors)
        return binding.root
    }

    private fun onToMainMenuPressed() {
        navigator().showHomePage()
    }
}