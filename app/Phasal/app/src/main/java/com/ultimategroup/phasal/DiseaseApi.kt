package com.ultimategroup.phasal

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface DiseaseApi {
    @Multipart
    @POST("upload/")
    fun uploadFile(@Part file: MultipartBody.Part?): Call<RequestBody?>?

    companion object {
        const val DJANGO_SITE = "https://abhijitacharya.pythonanywhere.com/"
    }
}