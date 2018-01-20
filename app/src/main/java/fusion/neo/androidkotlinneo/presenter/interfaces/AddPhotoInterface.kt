package fusion.neo.androidkotlinneo.presenter.interfaces

import android.app.Activity
import android.net.Uri
import com.beehapps.e_dokterkudokter.presenter.interfaces.ServerCallback
import fusion.neo.androidkotlinneo.model.Detail
import okhttp3.MultipartBody

/**
 * Created by farhan on 1/20/18.
 */
interface AddPhotoInterface {

    fun getRealPathFromURIPath(context: Activity,contentURI: Uri): String

    fun updatePhoto(context: Activity, data: Detail, id: String, callback: ServerCallback)

    fun uploadPhoto(context: Activity, file: MultipartBody.Part, callback: ServerCallback)

    fun isEmpty(file: MultipartBody.Part, summary: String, detail: String): Boolean
}