@file:Suppress("DEPRECATION")

package fusion.neo.androidkotlinneo.view.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.beehapps.e_dokterkudokter.presenter.interfaces.ServerCallback
import com.beehapps.e_dokterkudokter.system.util.SessionManager
import com.squareup.picasso.Picasso
import fusion.neo.androidkotlinneo.R
import fusion.neo.androidkotlinneo.presenter.implements.DetailPresenter
import kotlinx.android.synthetic.main.activity_add_photo.*


class DetailActivity : AppCompatActivity() {

    private var loading: ProgressDialog? = null
    private val presenter = DetailPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo)
        setSupportActionBar(toolbarAdd)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        toolbarAdd.title = getString(R.string.app_view)
        toolbarAdd.setTitleTextColor(resources.getColor(R.color.colorText2))
        buttonUpload.visibility = View.GONE
        editTextSummary.isEnabled = false
        editTextDetail.isEnabled = false

        val id = intent.extras.getInt(getString(R.string.intentExtraIdMessage), 0)
        val sessionManager = SessionManager(this@DetailActivity)
        loading = ProgressDialog.show(this, getString(R.string.progress_loading),
                getString(R.string.progress_getting), false, false)
        presenter.getData(this@DetailActivity, sessionManager.accessToken, id.toString(),
                object : ServerCallback {
                    override fun onSuccess(response: String) {
                        hideDialog()
                        val data = presenter.parsingData(response)
                        editTextDetail.setText(data.detail)
                        editTextSummary.setText(data.summary)
                        Picasso.with(this@DetailActivity)
                                .load(data.image)
                                .into(imageViewAdd)
                    }

                    override fun onFailed(isFailed: Boolean) {
                        hideDialog()
                        finish()
                        Toast.makeText(this@DetailActivity, getString(R.string.toast_data_load_failed),
                                Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(throwable: Throwable) {
                        hideDialog()
                        finish()
                        Toast.makeText(this@DetailActivity, getString(R.string.toast_data_load_failed),
                                Toast.LENGTH_SHORT).show()
                    }
                })

    }


    private fun hideDialog() {
        if (loading!!.isShowing)
            loading!!.dismiss()
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
