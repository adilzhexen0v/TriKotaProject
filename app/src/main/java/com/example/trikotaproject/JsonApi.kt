package com.example.trikotaproject

import com.example.trikotaproject.ui.doctor.profile.models.DoctorDeleteProfilePictureModel
import com.example.trikotaproject.ui.getstarted.doctorModel.DoctorLoginModel
import com.example.trikotaproject.ui.getstarted.doctorModel.DoctorRegisterModel
import com.example.trikotaproject.ui.getstarted.userModel.UserLoginModel
import com.example.trikotaproject.ui.getstarted.userModel.UserModel
import com.example.trikotaproject.ui.getstarted.userModel.UserUpdateModel
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface JsonApi {
    @POST("user/register")
    fun registerUser(
        @Body userData: UserModel
    ): Call<JsonObject>

    @POST("user/login")
    fun loginUser(
        @Body userData: UserLoginModel
    ): Call<JsonObject>

    @GET("user/myprofile")
    fun getUserProfileData(
        @Header("Authorization") authToken: String
    ): Call<JsonObject>

    @POST("user/update")
    fun updateUserProfileData(
        @Header("Authorization") authToken: String,
        @Body userData: UserUpdateModel
    ): Call<JsonObject>

    @GET("doctors")
    fun getListOfDoctors(
        @Header("Authorization") authToken: String
    ): Call<JsonObject>

    @GET("hospitals")
    fun getListOfHospitals(
    ): Call<JsonObject>

    @POST("doctor/register")
    fun registerDoctor(
        @Body doctorData: DoctorRegisterModel
    ): Call<JsonObject>

    @POST("doctor/login")
    fun loginDoctor(
        @Body doctorData: DoctorLoginModel
    ): Call<JsonObject>

    @GET("doctor/myprofile")
    fun getDoctorProfileData(
        @Header("Authorization") authToken: String
    ): Call<JsonObject>

    @Multipart
    @POST("doctor/upload/profilepicture")
    fun uploadDoctorProfilePicture(
        @Header("Authorization") authToken: String,
        @Part image: MultipartBody.Part,
        @Part("id") id: RequestBody
    ): Call<JsonObject>

    @Multipart
    @POST("doctor/upload/cv")
    fun uploadDoctorCV(
        @Header("Authorization") authToken: String,
        @Part image: MultipartBody.Part,
        @Part("id") id: RequestBody
    ): Call<JsonObject>

    @POST("doctor/delete/profilepicture")
    fun deleteDoctorProfilePicture(
        @Header("Authorization") authToken: String,
        @Body doctorData: DoctorDeleteProfilePictureModel
    ): Call<JsonObject>
}