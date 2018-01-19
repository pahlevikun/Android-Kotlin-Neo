package fusion.neo.androidkotlinneo.presenter.implements

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.util.Log
import com.beehapps.e_dokterkudokter.system.util.SessionManager
import fusion.neo.androidkotlinneo.presenter.interfaces.SplashInterface
import fusion.neo.androidkotlinneo.sys.config.APIConfig
import fusion.neo.androidkotlinneo.view.ui.LoginActivity
import fusion.neo.androidkotlinneo.view.ui.MainActivity

/**
 * Created by farhan on 12/21/17.
 */
class SplashPresenter : SplashInterface {

    override fun checkPermission(activity: Activity, PERMISSIONS: Array<String>) {
        //if (isNetworkAvailable(activity) || isOnline!!) {
        if (Build.VERSION.SDK_INT >= 23) {
            Log.d(APIConfig.TAG, "IN IF Build.VERSION.SDK_INT >= 23")

            if (!hasPermissions(activity, *PERMISSIONS)) {
                Log.d(APIConfig.TAG, "IN IF hasPermissions")
                ActivityCompat.requestPermissions(activity, PERMISSIONS, APIConfig.REQUEST_PERMISSION)
            } else {
                Log.d(APIConfig.TAG, "IN ELSE hasPermissions")
                splashLanding(activity)
            }
        } else {
            Log.d(APIConfig.TAG, "IN ELSE  Build.VERSION.SDK_INT >= 23")
            splashLanding(activity)
        }
    }

    override fun resultPermission(activity: Activity, requestCode: Int, grantResults: IntArray) {
        when (requestCode) {
            APIConfig.REQUEST_PERMISSION -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(APIConfig.TAG, "PERMISSIONS grant")
                    splashLanding(activity)
                } else {
                    Log.d(APIConfig.TAG, "PERMISSIONS Denied")
                    val alert = android.support.v7.app.AlertDialog.Builder(activity)
                    alert.setTitle("Warning!")
                    alert.setMessage("Please give permission for Wifi Tester Monitor!")
                    alert.setCancelable(false)
                    alert.setPositiveButton("Yes",
                            object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface, which: Int) {
                                    // TODO Auto-generated method stub
                                    activity.finish()
                                    activity.startActivity(activity.intent)
                                }
                            })
                    alert.show()
                }
            }
        }
    }

    private fun splashLanding(activity: Activity) {
        val SPLASH_TIME_OUT = 2500

        val session = SessionManager(activity)
        Log.d("HASIL", "SESI " + session.isSession)

        if (session.isSession) {
            Handler().postDelayed(object : Thread() {
                override fun run() {
                    val intent = Intent(activity, MainActivity::class.java)
                    activity.startActivity(intent)
                    activity.finish()
                }
            }, SPLASH_TIME_OUT.toLong())
        } else {
            Handler().postDelayed(object : Thread() {
                override fun run() {
                    val intent = Intent(activity, LoginActivity::class.java)
                    activity.startActivity(intent)
                    activity.finish()
                }
            }, SPLASH_TIME_OUT.toLong())
        }
    }

    private fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }
}
