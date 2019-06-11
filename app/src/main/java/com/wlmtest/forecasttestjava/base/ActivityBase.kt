package com.wlmtest.forecasttestjava.base

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.wlmtest.forecasttestjava.R


open class ActivityBase : AppCompatActivity() {
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //progress bar
        progressDialog = ProgressDialog(this, R.style.progress_bar_theme)
        progressDialog.setCancelable(false)
    }

    protected fun showProgressDialog(idMessage: Int) {
        progressDialog.setMessage(getString(idMessage))
        progressDialog.show()
    }

    protected  fun closeProgressDialog(){
        progressDialog.dismiss()
    }


    protected fun showInternetUnavailableMessage() {

        runOnUiThread {
            val aBuilder = AlertDialog.Builder(this@ActivityBase, R.style.MyDialogTheme)
            aBuilder.setMessage(R.string.message_network_unavailable)
            aBuilder.setCancelable(true)

            aBuilder.setPositiveButton(
                "Ok"
            ) { dialog, id -> dialog.cancel() }

            val alert = aBuilder.create()
            alert.show()
        }

    }

}
