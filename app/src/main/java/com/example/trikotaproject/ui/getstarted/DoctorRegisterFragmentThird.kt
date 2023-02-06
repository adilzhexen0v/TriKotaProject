package com.example.trikotaproject.ui.getstarted

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.trikotaproject.databinding.FragmentDoctorRegisterThirdBinding
import com.example.trikotaproject.jsonApi
import com.example.trikotaproject.unauthorizedcontract.navigator
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class DoctorRegisterFragmentThird: Fragment() {
    private lateinit var binding: FragmentDoctorRegisterThirdBinding
    private lateinit var cvUri: Uri
    private lateinit var TOKEN: String
    private lateinit var ID: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDoctorRegisterThirdBinding.inflate(inflater, container, false)
        val preferences = context?.getSharedPreferences("DOCTOR_INFO", Context.MODE_PRIVATE)
        TOKEN = preferences?.getString("TOKEN", "")!!
        ID = preferences?.getString("ID", "")!!
        binding.cvUploadBtn.setOnClickListener {
            contract.launch("application/pdf")
        }
        binding.registerBtn.setOnClickListener {
            navigator().doctorLogIn()
        }
        return binding.root
    }

    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        cvUri = it!!
        println("\n\n\n\n\n")
        println(cvUri.path)
        println("\n\n\n\n")
        val fileName = cvUri.path?.substring(16) + ".pdf"
        uploadCV(cvUri, fileName, ID)
        binding.cvError.text = "CV is uploaded"
    }

    private fun uploadCV(uri: Uri, filename: String, doctorId: String) {
        val file = File(requireContext().filesDir, filename)
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream!!.copyTo(outputStream)
        val requestFile = RequestBody.create(MediaType.parse("application/pdf"), file)
        val image = MultipartBody.Part.createFormData("cv", file?.name, requestFile)

        val call = jsonApi.uploadDoctorCV(
            TOKEN,
            image,
            RequestBody.create(MediaType.parse("text/plain"), doctorId)
        )
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                try {
                    println("\n\n\n\n\n\n\n\n")
                    println("Body:")
                    println(JSONObject(Gson().toJson(response.body())).toString())
                    println("\n\n\n\n\n\n\n\n")
                } catch (e: Exception) {
                    Toast.makeText(context, "From catch:" + e.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(context, "From onFailure:" + t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }
}