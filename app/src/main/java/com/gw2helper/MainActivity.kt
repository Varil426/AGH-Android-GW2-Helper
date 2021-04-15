package com.gw2helper

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class MainActivity : InternetActivity() {

    lateinit var apiInput: TextView
    lateinit var confirmApiKeyButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiInput = findViewById(R.id.apiKeyInput)
        confirmApiKeyButton = findViewById(R.id.confirmApiKeyButton)

        confirmApiKeyButton.setOnClickListener { handleNewApiKey() }

        if (ApiHelper.getApiKeyFromSharedPreferences(this) != null) {
            verifyApiKeyRequest()
        }
    }

    private fun handleNewApiKey() {
        ApiHelper.saveApiKeyToSharedPreferences(this, apiInput.text.toString())
        verifyApiKeyRequest()
    }

    private fun verifyApiKeyRequest() {
        val request = JsonObjectRequest(
            Request.Method.GET,
            ApiHelper.buildUrl(ApiHelper.endpoints.tokeninfo, this),
            null,
            {response -> verifyApiKeyHandle(response)},
            {error -> ToastsHelper.makeToast(error.message, this)}
        )

        queue.add(request)
    }

    private fun verifyApiKeyHandle(response: JSONObject) {
        // TODO Check for needed permissions

        ToastsHelper.makeToast("Hi", this)
    }

}