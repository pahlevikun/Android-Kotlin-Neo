package fusion.neo.androidkotlinneo.presenter.implements

import android.app.Activity
import android.util.Log
import com.beehapps.e_dokterkudokter.presenter.interfaces.ServerCallback
import com.beehapps.e_dokterkudokter.system.retrofit.BaseApiService
import com.beehapps.e_dokterkudokter.system.retrofit.UtilsApi
import fusion.neo.androidkotlinneo.model.DataDetail
import fusion.neo.androidkotlinneo.presenter.interfaces.DetailInterface
import fusion.neo.androidkotlinneo.sys.config.APIConfig
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback

/**
 * Created by farhan on 1/20/18.
 */
class DetailPresenter : DetailInterface {
    override fun getData(context: Activity, token: String, id: String, callback: ServerCallback) {
        val mApiService: BaseApiService = UtilsApi.apiService
        mApiService.getDetail(token,id)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            callback.onSuccess(response.body()!!.string())
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        callback.onFailure(t)
                    }
                })
    }

    override fun parsingData(respon: String): DataDetail {
        var id = 0
        var image = ""
        var summary = ""
        var detail = ""
        try {
            val json = JSONObject(respon)
            id = json.getInt("id")
            image = json.getString("original_url")
            summary = json.getString("summary")
            detail = json.getString("detail")
        } catch (e: Exception) {
            Log.d(APIConfig.TAG, "EXCEPTION " + e)
        }
        return DataDetail(id, summary, detail, image)
    }
}