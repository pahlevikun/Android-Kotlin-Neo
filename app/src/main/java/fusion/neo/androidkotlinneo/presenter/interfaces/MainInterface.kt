package fusion.neo.androidkotlinneo.presenter.interfaces

import android.app.Activity
import com.beehapps.e_dokterkudokter.presenter.interfaces.ServerCallback
import fusion.neo.androidkotlinneo.model.DataHome

/**
 * Created by farhan on 1/20/18.
 */
interface MainInterface {
    fun getData(context: Activity, token: String, callback: ServerCallback)
    fun parsingData(respon: String): ArrayList<DataHome>
}