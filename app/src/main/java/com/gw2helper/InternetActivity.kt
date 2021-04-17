package com.gw2helper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

abstract class InternetActivity : AppCompatActivity() {

    lateinit var queue: RequestQueue
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        queue = Volley.newRequestQueue(this)
    }
}