package fusion.neo.androidkotlinneo.presenter.implements

import android.app.Activity
import android.util.Log
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

    override fun saveToDB(activity: Activity, response: String) {
        try {
            val jObj = JSONObject(response)
            Log.d("HASIL RESPON", "" + jObj)
            val success = jObj.getBoolean("success")
            if (success) {
                val message = jObj.getJSONObject("message")
                val token = message.getString("token")
                val profile = message.getJSONObject("profile")
                val id = profile.getInt("id")
                val name = profile.getString("name")
                val email = profile.getString("email")
                val created_at = profile.getString("created_at")
                val updated_at = profile.getString("updated_at")
                val avatar = profile.getString("avatar")
                val role = profile.getInt("role")
                val phone = profile.getString("phone")
                val address = profile.getString("address")
                val id_province = profile.getString("id_province")
                val id_city = profile.getString("id_city")
                val id_district = profile.getString("id_district")
                val id_village = profile.getString("id_village")
                val lat = profile.getString("lat")
                val lon = profile.getString("lon")
                val status = profile.getString("status")
                val deleted_at = profile.getString("deleted_at")

                val session = SessionManager(activity)
                session.startSession()

                Log.d("HASIL", "SESI " + session.isSession)
            }
        } catch (e: Exception) {

        }
    }

    override fun performLogin(context: Activity, email: String, password: String,
                              callback: ServerCallback) {
        val mApiService: BaseApiService = UtilsApi.apiService

        mApiService.makeLogin("application/json", Login(email,password))
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