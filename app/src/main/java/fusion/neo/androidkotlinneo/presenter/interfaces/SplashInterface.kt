package fusion.neo.androidkotlinneo.presenter.interfaces

import android.app.Activity

/**
 * Created by farhan on 1/19/18.
 */
interface SplashInterface {
    fun checkPermission(activity: Activity, PERMISSION: Array<String>)

    fun resultPermission(activity: Activity, requestCode: Int, grantResults: IntArray)
}