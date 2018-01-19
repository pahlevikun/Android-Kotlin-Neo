package com.beehapps.e_dokterkudokter.system.retrofit

import fusion.neo.androidkotlinneo.sys.config.APIConfig

/**
 * Created by farhan on 1/9/18.
 */

object UtilsApi {
    val apiService: BaseApiService
        get() = RetrofitClient.getClient(APIConfig.END_POINT).create(BaseApiService::class.java)
}
