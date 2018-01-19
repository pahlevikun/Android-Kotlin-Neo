package com.beehapps.e_dokterkudokter.system.retrofit

import fusion.neo.androidkotlinneo.model.Login
import fusion.neo.androidkotlinneo.sys.config.APIConfig
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by farhan on 1/9/18.
 */

interface BaseApiService {
    @FormUrlEncoded
    @POST(APIConfig.AUTH)
    fun makeLogin(@Header("Content-Type") type: String,
                  @Body data: Login): Call<ResponseBody>

    @GET(APIConfig.DATA)
    fun getListPatient(@Header("Access-Toke") token: String): Call<ResponseBody>

}
