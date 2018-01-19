package fusion.neo.androidkotlinneo.view.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import fusion.neo.androidkotlinneo.R
import fusion.neo.androidkotlinneo.presenter.implements.SplashPresenter

class SplashActivity : AppCompatActivity() {

    private var resume = false
    private var presenter: SplashPresenter? = null

    private val PERMISSIONS = arrayOf(android.Manifest.permission.INTERNET,
            android.Manifest.permission.ACCESS_NETWORK_STATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        presenter = SplashPresenter()
        presenter!!.checkPermission(this, PERMISSIONS)

    }

    public override fun onResume() {
        super.onResume()
        if (resume) {
            presenter!!.checkPermission(this, PERMISSIONS)
        }
        resume = true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        presenter!!.resultPermission(this, requestCode, grantResults)
    }

    override fun onBackPressed() {}
}
