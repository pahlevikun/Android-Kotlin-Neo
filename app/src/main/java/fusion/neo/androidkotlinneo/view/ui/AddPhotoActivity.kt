package fusion.neo.androidkotlinneo.view.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import fusion.neo.androidkotlinneo.R
import fusion.neo.androidkotlinneo.sys.config.APIConfig
import kotlinx.android.synthetic.main.activity_add_photo.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.provider.MediaStore
import android.app.Activity
import android.net.Uri
import android.util.Log
import com.beehapps.e_dokterkudokter.system.retrofit.BaseApiService
import com.beehapps.e_dokterkudokter.system.retrofit.UtilsApi
import com.beehapps.e_dokterkudokter.system.util.SessionManager
import fusion.neo.androidkotlinneo.model.Login
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import java.io.File


class AddPhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo)

        imageViewAdd.setOnClickListener {
            val openGalleryIntent = Intent(Intent.ACTION_PICK)
            openGalleryIntent.type = "image/*"
            startActivityForResult(openGalleryIntent, APIConfig.REQUEST_GALLERY_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        val uri = data.data
        val filePath = getRealPathFromURIPath(uri, this@AddPhotoActivity)
        val file = File(filePath)
        Log.d(APIConfig.TAG, "Filename " + file.name)
        val mFile = RequestBody.create(MediaType.parse("image/*"), file)
        val fileToUpload = MultipartBody.Part.createFormData("file", file.name, mFile)
        val mApiService: BaseApiService = UtilsApi.apiService
        val sessionManager = SessionManager(this@AddPhotoActivity)

        mApiService.postPhoto(sessionManager.accessToken, fileToUpload)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@AddPhotoActivity,"Test",Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(this@AddPhotoActivity,"Test",Toast.LENGTH_SHORT).show()
                    }
                })
    }

    private fun getRealPathFromURIPath(contentURI: Uri, activity: Activity): String {
        val cursor = activity.contentResolver.query(contentURI, null, null, null, null)
        return if (cursor == null) {
            contentURI.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            cursor.getString(idx)
        }
    }
}
