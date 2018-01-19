package fusion.neo.androidkotlinneo.presenter.implements

import android.app.Activity
import com.beehapps.e_dokterkudokter.presenter.interfaces.LoginInterface
import com.beehapps.e_dokterkudokter.presenter.interfaces.ServerCallback
import com.beehapps.e_dokterkudokter.system.retrofit.BaseApiService
import com.beehapps.e_dokterkudokter.system.retrofit.UtilsApi
import com.beehapps.e_dokterkudokter.system.util.SessionManager
import fusion.neo.androidkotlinneo.model.Login
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback

/**
 * Created by farhan on 12/23/17.
 */
class LoginPresenter : LoginInterface {

    override fun checkPassword(email: String, password: String): Boolean {
        return (email.isEmpty() || password.isEmpty())
    }

    override fun isSuccess(response: String): Boolean {
        var success = true
        try {
            val jObj = JSONObject(response)
            success = jObj.getBoolean("success")
        } catch (e: Exception) {

        }
        return success
    }

    override fun saveSession(activity: Activity, response: String) : Boolean {
        try {
            val jObj = JSONObject(response)
            val accessToken = jObj.getString("access_token")
            val session = SessionManager(activity)
            session.startSession(accessToken)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override fun performLogin(context: Activity, email: String, password: String,
                              callback: ServerCallback) {
        val mApiService: BaseApiService = UtilsApi.apiService

        mApiService.makeLogin("application/json", Login(email, password))
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

}