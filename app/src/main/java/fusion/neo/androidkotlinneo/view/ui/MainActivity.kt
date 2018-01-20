@file:Suppress("DEPRECATION")

package fusion.neo.androidkotlinneo.view.ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.beehapps.e_dokterkudokter.presenter.interfaces.ServerCallback
import com.beehapps.e_dokterkudokter.system.util.SessionManager
import fusion.neo.androidkotlinneo.R
import fusion.neo.androidkotlinneo.model.DataHome
import fusion.neo.androidkotlinneo.presenter.implements.MainPresenter
import fusion.neo.androidkotlinneo.view.adapter.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false
    private var adapter: MainAdapter? = null
    private var layoutManager: LinearLayoutManager? = null
    private var loading: ProgressDialog? = null

    private val presenter = MainPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setHomeButtonEnabled(false)
        toolbar.title = getString(R.string.app_name)
        toolbar.setTitleTextColor(resources.getColor(R.color.colorText2))

        loadData()
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

    private fun loadData() {
        val sessionManager = SessionManager(this@MainActivity)
        loading = ProgressDialog.show(this, getString(R.string.progress_loading),
                getString(R.string.progress_getting), false, false)
        presenter.getData(this, sessionManager.accessToken, object : ServerCallback {
            override fun onSuccess(response: String) {
                hideDialog()
                val arrayList = presenter.parsingData(response)
                setAdapter(arrayList)
            }

            override fun onFailed(isFailed: Boolean) {
                hideDialog()
                Toast.makeText(this@MainActivity, getString(R.string.toast_data_load_failed),
                        Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(throwable: Throwable) {
                hideDialog()
                Toast.makeText(this@MainActivity, getString(R.string.toast_data_load_failed),
                        Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setAdapter(arraData: ArrayList<DataHome>) {
        adapter = MainAdapter(this@MainActivity, arraData)
        layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL,
                false)
        recyclerView.layoutManager = layoutManager
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.adapter = adapter
        adapter!!.notifyDataSetChanged()
    }

    private fun hideDialog() {
        if (loading!!.isShowing)
            loading!!.dismiss()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        return when (id) {
            R.id.action_add -> {
                startActivity(Intent(this, AddPhotoActivity::class.java))
                true
            }
            R.id.action_refresh -> {
                loadData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
}
