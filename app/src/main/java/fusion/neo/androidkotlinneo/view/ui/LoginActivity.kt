@file:Suppress("DEPRECATION")

package fusion.neo.androidkotlinneo.view.ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.beehapps.e_dokterkudokter.presenter.interfaces.ServerCallback
import fusion.neo.androidkotlinneo.R
import fusion.neo.androidkotlinneo.presenter.implements.LoginPresenter
import fusion.neo.androidkotlinneo.sys.config.APIConfig
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private var username: String? = null
    private var password: String? = null
    private var doubleBackToExitPressedOnce = false
    private var loading: ProgressDialog? = null
    private val presenter: LoginPresenter = LoginPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonLogin.setOnClickListener {
            username = editTextLoginEmail.text.toString()
            password = editTextLoginPassword.text.toString().trim { it <= ' ' }
            if (presenter.checkPassword(username!!, password!!)) {
                Snackbar.make(coordinatorLogin, getString(R.string.toast_login_fill), Snackbar.LENGTH_SHORT).show()
            } else {
                loading = ProgressDialog.show(this, getString(R.string.progress_loading),
                        getString(R.string.progress_login), false, false)
                presenter.performLogin(this, username!!, password!!, object : ServerCallback {

                    override fun onSuccess(response: String) {
                        Log.d(APIConfig.TAG,"HASIL REQUEST "+response)
                        if (presenter.isSuccess(response)) {
                            hideDialog()
                            if (!presenter.saveSession(this@LoginActivity, response)) {
                                Toast.makeText(this@LoginActivity, getString(R.string.toast_login_failed_session),
                                        Toast.LENGTH_SHORT).show()
                            } else {
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            hideDialog()
                            Toast.makeText(this@LoginActivity, presenter.parsingMessage(response),
                                    Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailed(isFailed: String) {
                        Log.d(APIConfig.TAG,"HASIL FAILED ")
                        hideDialog()
                        editTextLoginEmail.text.clear()
                        editTextLoginPassword.text.clear()
                        Toast.makeText(this@LoginActivity, presenter.parsingMessage(isFailed),
                                Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(throwable: Throwable) {
                        Log.d(APIConfig.TAG,"HASIL FAILURE ")
                        hideDialog()
                        Toast.makeText(this@LoginActivity, getString(R.string.toast_login_connection),
                                Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, getString(R.string.app_login_double_back), Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }


    private fun hideDialog() {
        if (loading!!.isShowing)
            loading!!.dismiss()
    }


}
