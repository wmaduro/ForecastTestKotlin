package com.wlmtest.forecasttestkotlin.base

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import com.wlmtest.forecasttestkotlin.R


open class ActivityBase : AppCompatActivity() {

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
