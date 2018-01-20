package fusion.neo.androidkotlinneo.view.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.beehapps.e_dokterkudokter.presenter.interfaces.ServerCallback
import fusion.neo.androidkotlinneo.R
import fusion.neo.androidkotlinneo.presenter.implements.AddPhotoPresenter
import fusion.neo.androidkotlinneo.sys.config.APIConfig
import kotlinx.android.synthetic.main.activity_add_photo.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class AddPhotoActivity : AppCompatActivity() {

    private var fileToUpload: MultipartBody.Part? = null
    private val presenter = AddPhotoPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo)

        imageViewAdd.setOnClickListener {
            val openGalleryIntent = Intent(Intent.ACTION_PICK)
            openGalleryIntent.type = "image/*"
            startActivityForResult(openGalleryIntent, APIConfig.REQUEST_GALLERY_CODE)
        }

        buttonUpload.setOnClickListener {
            if (!presenter.isEmpty(fileToUpload!!, "", "")) {
                presenter.uploadPhoto(this@AddPhotoActivity, fileToUpload!!, object : ServerCallback {
                    override fun onSuccess(response: String) {

                    }

                    override fun onFailed(isFailed: Boolean) {

                    }

                    override fun onFailure(throwable: Throwable) {

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
        val file = File(filePath)
        val mFile = RequestBody.create(MediaType.parse("image/*"), file)
        fileToUpload = MultipartBody.Part.createFormData("file", file.name, mFile)
        imageViewAdd.setImageBitmap(BitmapFactory.decodeFile(file.absolutePath))
    }

}
