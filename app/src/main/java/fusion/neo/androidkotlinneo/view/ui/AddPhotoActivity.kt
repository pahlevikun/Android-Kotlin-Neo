@file:Suppress("DEPRECATION")

package fusion.neo.androidkotlinneo.view.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.beehapps.e_dokterkudokter.presenter.interfaces.ServerCallback
import fusion.neo.androidkotlinneo.R
import fusion.neo.androidkotlinneo.model.Detail
import fusion.neo.androidkotlinneo.presenter.implements.AddPhotoPresenter
import fusion.neo.androidkotlinneo.sys.config.APIConfig
import kotlinx.android.synthetic.main.activity_add_photo.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File


class AddPhotoActivity : AppCompatActivity() {

    private var file: File? = null
    private var fileToUpload: MultipartBody.Part? = null
    private val presenter = AddPhotoPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo)
        setSupportActionBar(toolbarAdd)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        toolbarAdd.title = getString(R.string.app_name)
        toolbarAdd.setTitleTextColor(resources.getColor(R.color.colorText2))

        imageViewAdd.setOnClickListener {
            val openGalleryIntent = Intent(Intent.ACTION_PICK)
            openGalleryIntent.type = "image/*"
            startActivityForResult(openGalleryIntent, APIConfig.REQUEST_GALLERY_CODE)
        }

        buttonUpload.setOnClickListener {
            if (!presenter.isEmpty(file!!, editTextSummary.text.toString(),
                    editTextDetail.text.toString())) {
                presenter.uploadPhoto(this@AddPhotoActivity, fileToUpload!!,
                        object : ServerCallback {
                            override fun onSuccess(response: String) {
                                Log.d(APIConfig.TAG, "JSON BERHASIL " + response)
                                try {
                                    val json = JSONObject(response)
                                    val id = json.getInt("id")
                                    postUpdate(id.toString(),
                                            Detail(editTextSummary.text.toString(),
                                                    editTextDetail.text.toString()))
                                } catch (e: Exception) {
                                    Toast.makeText(this@AddPhotoActivity,
                                            getString(R.string.toast_upload_photo_failed),
                                            Toast.LENGTH_SHORT).show()
                                    Log.d(APIConfig.TAG,"EXCEPTION "+e)
                                }
                            }

                            override fun onFailed(isFailed: Boolean) {
                                Toast.makeText(this@AddPhotoActivity,
                                        getString(R.string.toast_upload_photo_failed),
                                        Toast.LENGTH_SHORT).show()
                                Log.d(APIConfig.TAG,"ISFAILED")
                            }

                            override fun onFailure(throwable: Throwable) {
                                Toast.makeText(this@AddPhotoActivity, throwable.toString(),
                                        Toast.LENGTH_SHORT).show()
                                Log.d(APIConfig.TAG,"FAILURE "+throwable)
                            }
                        })
            } else {
                Toast.makeText(this@AddPhotoActivity, getString(R.string.toast_upload_photo),
                        Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        val uri = data.data
        val filePath = presenter.getRealPathFromURIPath(this@AddPhotoActivity, uri)
        file = File(filePath)
        val mFile = RequestBody.create(MediaType.parse("image/*"), file!!)
        fileToUpload = MultipartBody.Part.createFormData("file", file!!.name, mFile)
        imageViewAdd.setImageBitmap(BitmapFactory.decodeFile(file!!.absolutePath))
    }

    fun postUpdate(id: String, detail: Detail) {
        presenter.updatePhoto(this@AddPhotoActivity, detail, id, object : ServerCallback {
            override fun onSuccess(response: String) {
                val intent = Intent(this@AddPhotoActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }

            override fun onFailed(isFailed: Boolean) {

            }

            override fun onFailure(throwable: Throwable) {

            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finish()
    }

}
