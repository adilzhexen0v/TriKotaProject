package com.example.trikotaproject.ui.getstarted

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.trikotaproject.R
import com.example.trikotaproject.databinding.FragmentDoctorRegisterSecondBinding
import com.example.trikotaproject.jsonApi
import com.example.trikotaproject.ui.getstarted.doctorModel.DoctorRegisterModel
import com.example.trikotaproject.ui.getstarted.hospitalModel.Hospital
import com.example.trikotaproject.unauthorizedcontract.navigator
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoctorRegisterFragmentSecond: Fragment() {
    private lateinit var binding: FragmentDoctorRegisterSecondBinding
    private lateinit var hospital: String
    private lateinit var city: String
    private lateinit var address: String

    private val EXIST = "exist"
    private val NEW = "new"
    private lateinit var currentState: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDoctorRegisterSecondBinding.inflate(layoutInflater)
        val spinner = binding.hospitalChoose
        currentState = EXIST
        showOrHideCorrectTypeOfHospital()
        val preferences = context?.getSharedPreferences("DOCTOR_INFO", Context.MODE_PRIVATE)
        val editor = preferences?.edit()
        val call = jsonApi.getListOfHospitals()
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                try {
                    val json = JSONObject(Gson().toJson(response.body()))
                    val hospitalsJsonArray = json.getJSONArray("hospitals")
                    var optionsArray = arrayOf<String>()
                    val optionsArrayTempList = optionsArray.toMutableList()
                    val optionsList = mutableListOf<Hospital>()
                    for (i in 0 until hospitalsJsonArray.length()) {
                        val name = hospitalsJsonArray.getJSONObject(i).getString("hospital")
                        val city = hospitalsJsonArray.getJSONObject(i).getString("city")
                        val address = hospitalsJsonArray.getJSONObject(i).getString("address")
                        optionsArrayTempList.add("$name, $city, $address")
                        optionsList.add(Hospital(name, city, address))
                    }
                    optionsArray = optionsArrayTempList.toTypedArray()
                    val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, optionsArray)
                    spinner.adapter = adapter
                    spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {
                            hospital = optionsList[p2].name
                            city = optionsList[p2].city
                            address = optionsList[p2].address
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            binding.currentOccupation.text = "Choose your hospital"
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "From catch: " + e.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(context, "From onFailure: " + t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
        binding.registerBtn.setOnClickListener {
            var errorCounter = 0

            val name = preferences?.getString("NAME", "")!!
            val surname = preferences?.getString("SURNAME", "")!!
            val email = preferences?.getString("EMAIL", "")!!
            val password = preferences?.getString("PASSWORD", "")!!
            val experienceYears = binding.enterExperienceYearsText.text.toString()
            val experienceMonths = binding.enterExperienceMonthText.text.toString()
            val occupation = binding.enterOccupationText.text.toString()

            if (currentState == NEW) run {
                hospital = binding.enterHospitalText.text.toString()
                city = binding.enterCityText.text.toString()
                address = binding.enterAddressText.text.toString()
            }

            if (binding.enterExperienceYearsText.text.isEmpty() || binding.enterExperienceMonthText.text.isEmpty()) {
                errorCounter++
                binding.experienceError.text = getString(R.string.error)
            }

            if (binding.enterOccupationText.text.isEmpty()) {
                errorCounter++
                binding.occupationError.text = getString(R.string.error)
            }

            if (currentState == NEW && (binding.enterHospitalText.text.isEmpty() || binding.enterAddressText.text.isEmpty() || binding.enterCityText.text.isEmpty())) {
                errorCounter++
                binding.hospitalError.text = getString(R.string.error)
            }

            val doctor = DoctorRegisterModel(
                name,
                surname,
                email,
                password,
                occupation,
                "$experienceYears years $experienceMonths months",
                hospital,
                city,
                address
            )

            if (errorCounter == 0) {
                val call = jsonApi.registerDoctor(doctor)
                call.enqueue(object : Callback<JsonObject> {
                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                        try {
                            val json = JSONObject(Gson().toJson(response.body()))
                            val token = json.getString("token")
                            editor?.putString("TOKEN", token)
                            val id = JSONObject(json.getString("doctorData")).getString("_id")
                            editor?.putString("ID", id)
                            editor?.apply()
                            //Toast.makeText(context, "Successful registration!", Toast.LENGTH_SHORT).show()
                            navigator().doctorLogIn()
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
        binding.existingHospital.setOnClickListener{
            showOrHideCorrectTypeOfHospital()
        }
        binding.newHospital.setOnClickListener {
            showOrHideCorrectTypeOfHospital()
        }
        return binding.root
    }

    private fun showOrHideCorrectTypeOfHospital() {
        currentState = when(binding.chooseTypeHospital.checkedRadioButtonId) {
            R.id.existing_hospital -> EXIST
            else -> NEW
        }
        if (currentState == EXIST) {
            binding.hospitalInputs.visibility = View.INVISIBLE
            binding.hospitalChoose.visibility = View.VISIBLE
        } else {
            binding.hospitalInputs.visibility = View.VISIBLE
            binding.hospitalChoose.visibility = View.INVISIBLE
        }
    }
}