package com.beehapps.e_dokterkudokter.system.retrofit

import fusion.neo.androidkotlinneo.model.Login
import fusion.neo.androidkotlinneo.sys.config.APIConfig
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by farhan on 1/9/18.
 */

interface BaseApiService {
    @POST(APIConfig.AUTH)
    fun makeLogin(@Header("Content-Type") type: String,
                  @Body data: Login): Call<ResponseBody>

    @GET(APIConfig.DATA)
    fun getListPatient(@Header("Access-Token") token: String): Call<ResponseBody>


    @Multipart
    @POST(APIConfig.POST_IMAGE)
    fun postPhoto(@Header("Access-Token") token: String,
                  @Part file : MultipartBody.Part): Call<ResponseBody>
}
