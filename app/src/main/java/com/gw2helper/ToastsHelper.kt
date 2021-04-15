package com.gw2helper

import android.content.Context
import android.widget.Toast

class ToastsHelper {
    companion object {

        fun makeToast(message: String?, context: Context ) {
            Toast.makeText(context, message ?: "ERROR", Toast.LENGTH_LONG).show()
        }

    }
}