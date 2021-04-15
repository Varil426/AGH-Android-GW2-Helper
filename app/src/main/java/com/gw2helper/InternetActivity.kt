package com.gw2helper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

abstract class InternetActivity : AppCompatActivity() {

    protected lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        queue = Volley.newRequestQueue(this)
    }
}