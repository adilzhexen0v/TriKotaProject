package com.example.trikotaproject.ui.home.doctors

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trikotaproject.authorizedcontract.navigator
import com.example.trikotaproject.databinding.FragmentHomeDoctorsBinding
import com.example.trikotaproject.ui.home.doctors.adapter.DoctorAdapter
import com.example.trikotaproject.ui.home.doctors.data.DoctorDatasource

class DoctorsFragment: Fragment() {

    private lateinit var binding: FragmentHomeDoctorsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeDoctorsBinding.inflate(inflater, container, false)

        /*
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://nosql-group-project-backend.onrender.com/")
            .build()
        val jsonApi = retrofitBuilder.create(JsonApi::class.java)

        val preferences = context?.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE)
        val token = preferences?.getString("TOKEN", "")!!

        try {
            val call = jsonApi.getListOfDoctors(token)
            call.enqueue(object : Callback<List<DoctorModel>> {
                override fun onResponse(call: Call<List<DoctorModel>>, response: Response<List<DoctorModel>>) {
                    try {
                        val json = JSONObject(Gson().toJson(response.body()))
                        val doctorsList = json.getJSONArray("doctors")
                        val str = StringBuilder()
                        for(i in 0 until doctorsList.length()) {
                            str.append(doctorsList[i]).append("/n/n")
                        }
                        /*
                        val doctorsRecyclerView = binding.recyclerView
                        doctorsRecyclerView.adapter = DoctorAdapter(container?.context, doctors)
                        */
                        binding.listOfDoctors.text = str.toString()

                    } catch (e: Exception) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<DoctorModel>>, t: Throwable) {
                    Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }

        */
        val doctorsRecyclerView = binding.recyclerView
        doctorsRecyclerView.adapter = DoctorAdapter(container?.context, DoctorDatasource.doctors)
        return binding.root
    }

    private fun onToMainMenuPressed() {
        navigator().showHomePage()
    }
}