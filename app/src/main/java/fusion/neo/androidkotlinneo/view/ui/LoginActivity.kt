package fusion.neo.androidkotlinneo.view.ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.beehapps.e_dokterkudokter.presenter.interfaces.ServerCallback
import fusion.neo.androidkotlinneo.R
import fusion.neo.androidkotlinneo.presenter.implements.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*

@Suppress("DEPRECATION")
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
                        hideDialog()
                        if (presenter.isSuccess(response)) {
                            if (!presenter.saveSession(this@LoginActivity, response)) {
                                Snackbar.make(coordinatorLogin, getString(R.string.toast_login_failed_session), Snackbar.LENGTH_SHORT).show()
                            } else {
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                finish()
                            }
                        }
                    }

                    override fun onFailed(isFailed: Boolean) {
                        hideDialog()
                        editTextLoginEmail.text.clear()
                        editTextLoginPassword.text.clear()
                        Snackbar.make(coordinatorLogin, getString(R.string.toast_login_failed), Snackbar.LENGTH_LONG).show()
                    }

                    override fun onFailure(throwable: Throwable) {
                        hideDialog()
                        Snackbar.make(coordinatorLogin, getString(R.string.toast_login_connection), Snackbar.LENGTH_LONG).show()
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
