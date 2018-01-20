package fusion.neo.androidkotlinneo.presenter.implements

import android.app.Activity
import android.util.Log
import com.beehapps.e_dokterkudokter.presenter.interfaces.ServerCallback
import com.beehapps.e_dokterkudokter.system.retrofit.BaseApiService
import com.beehapps.e_dokterkudokter.system.retrofit.UtilsApi
import fusion.neo.androidkotlinneo.model.DataHome
import fusion.neo.androidkotlinneo.presenter.interfaces.MainInterface
import fusion.neo.androidkotlinneo.sys.config.APIConfig
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback

/**
 * Created by farhan on 1/20/18.
 */
class MainPresenter : MainInterface {
    override fun getData(context: Activity, token: String, callback: ServerCallback) {
        val mApiService: BaseApiService = UtilsApi.apiService
        mApiService.getList(token)
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

    override fun parsingData(respon: String): ArrayList<DataHome> {
        var arrayList = ArrayList<DataHome>()
        try {
            val json = JSONArray(respon)
            (0 until json.length())
                    .map { json.getJSONObject(it) }
                    .forEach {
                        val id = it.getInt("id")
                        val thumbnail = it.getString("thumbnail_url")
                        if (it.has("summary")){
                            val summary = it.getString("summary")
                            arrayList.add(DataHome(id,summary,thumbnail))
                        }else{
                            arrayList.add(DataHome(id,"No Summary",thumbnail))
                        }
                    }
            Log.d(APIConfig.TAG, "SIZE " + arrayList.size + " ARRAYLIST " + arrayList)
        } catch (e: Exception) {
            Log.d(APIConfig.TAG, "EXCEPTION " + e)
        }
        return arrayList
    }
}