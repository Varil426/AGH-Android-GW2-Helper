package com.gw2helper

import android.app.Activity
import android.content.Context
import java.lang.StringBuilder

class ApiHelper private constructor() {

    companion object {

        const val baseUrl = "https://api.guildwars2.com/v2"

        val params = Params

        val endpoints = Endpoints

        fun getApiKeyFromSharedPreferences(context: Context): String? {
            val sharedPreferences = context.getSharedPreferences(context.getString(R.string.apiPreferences), Context.MODE_PRIVATE)
            return sharedPreferences.getString(context.getString(R.string.apiKeyKey), null)
        }

        fun saveApiKeyToSharedPreferences(context: Context, apiKey: String) {
            val sharedPref = context.getSharedPreferences(context.getString(R.string.apiPreferences), Context.MODE_PRIVATE) ?: return
            with (sharedPref.edit()) {
                putString(context.getString(R.string.apiKeyKey), apiKey)
                apply()
            }
        }

        fun buildUrl(endpoint: String, context: Context): String {
            return "$baseUrl/$endpoint?${params.access_token}=${getApiKeyFromSharedPreferences(context)}"
        }

        fun buildUrl(endpoint: String, params: Map<String, String>, context: Context): String {
            val urlBuilder = StringBuilder(buildUrl(endpoint, context))
            for (param in params) {
                urlBuilder.append("&${param.key}=${param.value}")
            }
            return urlBuilder.toString()
        }

        object Params {
            const val access_token = "access_token"
        }

        object Endpoints {
            const val tokeninfo = "tokeninfo"
        }
    }

}