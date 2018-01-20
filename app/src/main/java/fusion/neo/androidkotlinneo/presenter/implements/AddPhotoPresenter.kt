package fusion.neo.androidkotlinneo.presenter.implements

import android.app.Activity
import android.net.Uri
import android.provider.MediaStore
import com.beehapps.e_dokterkudokter.presenter.interfaces.ServerCallback
import com.beehapps.e_dokterkudokter.system.retrofit.BaseApiService
import com.beehapps.e_dokterkudokter.system.retrofit.UtilsApi
import com.beehapps.e_dokterkudokter.system.util.SessionManager
import fusion.neo.androidkotlinneo.model.Detail
import fusion.neo.androidkotlinneo.presenter.interfaces.AddPhotoInterface
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import java.io.File

/**
 * Created by farhan on 1/20/18.
 */
class AddPhotoPresenter : AddPhotoInterface {

    override fun getRealPathFromURIPath(context: Activity, contentURI: Uri): String {
        val cursor = context.contentResolver.query(contentURI, null, null,
                null, null)
        return if (cursor == null) {
            contentURI.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            cursor.getString(idx)
        }
    }

    override fun isEmpty(file: File, summary: String, detail: String): Boolean {
        return (file.length() < 1 || detail.isEmpty() || summary.isEmpty())
    }

    override fun updatePhoto(context: Activity, data: Detail, id: String, callback: ServerCallback) {
        val mApiService: BaseApiService = UtilsApi.apiService
        val sessionManager = SessionManager(context)

        mApiService.updatePhoto("application/json",
                sessionManager.accessToken, data, id)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            callback.onSuccess(response.body()!!.string())
                        } else {
                            callback.onFailed(response.body()!!.string())
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        callback.onFailure(t)
                    }
                })
    }

    override fun uploadPhoto(context: Activity, file: MultipartBody.Part, callback: ServerCallback) {
        val mApiService: BaseApiService = UtilsApi.apiService
        val sessionManager = SessionManager(context)

        mApiService.postPhoto(sessionManager.accessToken, file)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            callback.onSuccess(response.body()!!.string())
                        } else {
                            callback.onFailed(response.body()!!.string())
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        callback.onFailure(t)
                    }
                })
    }
}