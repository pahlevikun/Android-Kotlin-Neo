package fusion.neo.androidkotlinneo.presenter.interfaces

import android.app.Activity
import com.beehapps.e_dokterkudokter.presenter.interfaces.ServerCallback
import fusion.neo.androidkotlinneo.model.DataDetail

/**
 * Created by farhan on 1/20/18.
 */
interface DetailInterface {
    fun getData(context: Activity, token: String,  id:String,callback: ServerCallback)
    fun parsingData(respon: String): DataDetail
}