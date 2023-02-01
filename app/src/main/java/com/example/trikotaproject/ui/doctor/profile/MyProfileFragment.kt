package com.example.trikotaproject.ui.doctor.profile


import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.trikotaproject.R
import com.example.trikotaproject.authorizeddoctorcontact.navigator
import com.example.trikotaproject.databinding.FragmentDoctorMyprofileBinding
import com.example.trikotaproject.jsonApi
import com.example.trikotaproject.ui.doctor.profile.models.DoctorDeleteProfilePictureModel
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

/*
import com.example.trikotaproject.jsonApi
import com.google.gson.JsonObject
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import android.util.Base64
import android.widget.Toast
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
 */

class MyProfileFragment: Fragment() {
    private lateinit var binding: FragmentDoctorMyprofileBinding
    private lateinit var imageUri: Uri
    private lateinit var TOKEN: String
    private lateinit var ID: String
    private var HASIMAGE = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDoctorMyprofileBinding.inflate(inflater, container, false)
        roundProfilePicture()
        val preferences = context?.getSharedPreferences("DOCTOR_INFO", Context.MODE_PRIVATE)
        TOKEN = preferences?.getString("TOKEN", "")!!
        ID = preferences?.getString("ID", "")!!
        getDoctorData(TOKEN)
        binding.myProfileImageImg.setOnClickListener {
            alertUploadOrDelete()
        }
        return binding.root
    }

    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it!!
        binding.myProfileImageImg.setImageURI(imageUri)
        roundProfilePicture()
        val fileName = imageUri.path?.substring(16) + ".png"
        uploadImage(imageUri, fileName, ID)
        /*
        println("\n\n\n\n\n\n\n\n")
        println(imageUri.path)
        println(imageUri.toString().substring(16))
        println("\n\n\n\n\n\n\n\n")
         */
    }

    private fun uploadImage(uri: Uri, filename: String, userId: String) {
        val file = File(requireContext().filesDir, filename)
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        inputStream!!.copyTo(outputStream)
        val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
        val image = MultipartBody.Part.createFormData("docimg", file?.name, requestFile)

        val call = jsonApi.uploadDoctorProfilePicture(
            TOKEN,
            image,
            RequestBody.create(MediaType.parse("text/plain"), userId)
        )
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                try {
                    println("\n\n\n\n\n\n\n\n")
                    println("Body:")
                    println(JSONObject(Gson().toJson(response.body())).toString())
                    println("\n\n\n\n\n\n\n\n")
                    HASIMAGE = true
                } catch (e: Exception) {
                    Toast.makeText(context, "From catch:" + e.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(context, "From onFailure:" + t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getDoctorData(token: String) {
        val call = jsonApi.getDoctorProfileData(token)
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                try {
                    val json = JSONObject(Gson().toJson(response.body()))
                    val name = json.getString("name")
                    binding.myProfileNameText.text = name

                    val surname = json.getString("surname")
                    binding.myProfileSurnameText.text = surname

                    val email = json.getString("email")
                    binding.myProfileEmailText.text = email

                    val city = JSONObject(json.getString("hospitalId")).getString("city")
                    val hospital = JSONObject(json.getString("hospitalId")).getString("hospital")
                    val address = JSONObject(json.getString("hospitalId")).getString("address")
                    binding.myProfileHospitalText.text = "$hospital, $city, $address"

                    val occupation = JSONObject(json.getString("occupation")).getString("occupation")
                    binding.myProfileOccupationText.text = occupation

                    val experience = json.getString("experience")
                    binding.myProfileExperienceText.text = experience

                    var status = "For consideration"
                    try {
                        val statusCheck = json.getBoolean("access")
                        status = when(statusCheck) {
                            true -> "Accepted"
                            false -> "Denied"
                        }
                    } catch (e: Exception) {
                        println(e)
                    }
                    binding.myProfileStatusText.text = "Status: $status"

                    try {
                        val imgUrl = json.getString("imageUrl")
                        if (imgUrl != null) {
                            Glide.with(requireContext()).load("https://nosql-group-project-backend.onrender.com/uploads/$imgUrl").into(binding.myProfileImageImg)
                            HASIMAGE = true
                        }
                    } catch (e: Exception) {
                        println(e)
                    }

                } catch (e: Exception) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun alertUploadOrDelete() {
        val dialog = AlertDialog.Builder(context)
            .setCancelable(true)
            .setTitle("Profile picture")
            .setPositiveButton("Upload picture") {_, _ ->
                contract.launch("image/*")
            }
            .setNegativeButton("Delete picture") {_, _ ->
                if (HASIMAGE) {
                    deleteImage(TOKEN, ID)
                } else {
                    Toast.makeText(context, "You haven't profile picture yet", Toast.LENGTH_SHORT).show()
                }
            }
            .create()
        dialog.show()
    }

    private fun deleteImage(token: String, id: String) {
        val call = jsonApi.deleteDoctorProfilePicture(token, DoctorDeleteProfilePictureModel(id))
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                try {
                    Toast.makeText(context, "Your profile picture have deleted", Toast.LENGTH_SHORT).show()
                    navigator().showMyProfile()
                } catch (e: Exception) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun roundProfilePicture() {
        binding.myProfileImageImg.clipToOutline = true
        binding.myProfileImageImg.setBackgroundResource(R.drawable.round_img)
    }
}
    /*
    private fun galleryCheckPermission() {
        Dexter.withContext(requireContext()).withPermission(
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(object : PermissionListener {
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                gallery()
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                Toast.makeText(
                    requireContext(),
                    "You have denied the storage permission to select image",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: PermissionRequest?,
                p1: PermissionToken?
            ) {
                Toast.makeText(
                    requireContext(),
                    "3rd option",
                    Toast.LENGTH_LONG
                ).show()
            }
        }).onSameThread().check()
    }

    private fun gallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {


            println("\n\n\n\n\n\n\n\n")
            println("Data:")
            println(data)
            println("Data (data):")
            println(data?.dataString)
            println("Data (action):")
            println(data?.action)
            println("\n\n\n\n\n\n\n\n")

            val file = File("/media/" + data?.data?.path)
            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val body = MultipartBody.Part.createFormData("docimg", file?.name, requestFile)

            println("\n\n\n\n\n\n\n\n")
            println(file)
            println(file?.name)
            println(file?.absoluteFile)
            println(file?.absoluteFile?.isFile)
            println("\n\n\n\n\n\n\n\n")


        }
    }

    private fun uploadImage() {

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val docimg = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)

        val file = File(path)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val body = MultipartBody.Part.createFormData("docimg", file?.name, requestFile)

        val preferences = context?.getSharedPreferences("DOCTOR_INFO", Context.MODE_PRIVATE)
        val call = jsonApi.uploadDoctorProfilePicture(
            preferences?.getString("TOKEN", "")!!,
            body,
            RequestBody.create(MediaType.parse("multipart/form-data"), preferences?.getString("ID", "")!!),
        )
        call.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                try {
                    println("\n\n\n\n\n\n\n\n")
                    println(response.body().toString())
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

 */ */