package com.gw2helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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

        fun forgetSharedPreferences(context: Context){
            val sharedPreferences = context.getSharedPreferences("API", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.remove("apiKey")
            editor.commit()
            val intent = Intent(context, LauncherActivity::class.java)
            context.startActivity(intent)
        }

        fun buildUrl(endpoint: String): String {
            return "$baseUrl/$endpoint"
        }

        fun buildUrl(endpoint: String, params: Map<String, String>): String {
            val urlBuilder = StringBuilder(buildUrl(endpoint))
            urlBuilder.append("?")
            for (param in params) {
                urlBuilder.append("${param.key}=${param.value}&")
            }
            urlBuilder.deleteCharAt(urlBuilder.length-1)
            return urlBuilder.toString()
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
            const val ids = "ids"
        }

        object Endpoints {
            const val tokeninfo = "tokeninfo"
            const val account = "account"
            const val characters = "characters"
            const val accountAchievements = "account/achievements"
            const val achievements = "achievements"
        }
    }

}