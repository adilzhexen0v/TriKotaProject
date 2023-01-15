package com.example.trikotaproject

import com.example.trikotaproject.ui.getstarted.userModel.UserModel
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface JsonApi {
    @POST("user/register")
    fun registerUser(
        @Body userData: UserModel
    ): Call<JsonObject>

    @GET("user/myprofile")
    fun getUserProfileData(
        @Header("token") authToken: String
    ): Call<UserModel>
}