package com.example.trikotaproject

import com.example.trikotaproject.ui.getstarted.userModel.UserLoginModel
import com.example.trikotaproject.ui.getstarted.userModel.UserModel
import com.example.trikotaproject.ui.home.doctors.model.DoctorModel
import com.google.gson.JsonObject
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

    @GET("doctors")
    fun getListOfDoctors(
        @Header("Authorization") authToken: String
    ): Call<List<DoctorModel>>
}