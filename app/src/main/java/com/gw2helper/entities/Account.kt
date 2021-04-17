package com.gw2helper.entities

import java.text.SimpleDateFormat

data class Account(val name: String, val age: Long, val worldId: String, val guildsIds: List<String>, val gameContentAccess: List<String>, val fractalLevel: Int, val wvwRank: Int) {

    fun getFormatedDate(): String {
        var rest = age.toDouble()
        val days = Math.floor(rest/(60*60*24))
        rest -= days * (60*60*24)
        val hours = Math.floor(rest/(60*60))
        rest -= hours * (60*60)
        val minutes = Math.floor(rest/(60))
        rest -= minutes * (60)
        val seconds = Math.floor(rest)
        return "Days: $days, Hours: $hours, Minutes: $minutes, Seconds: $seconds"
    }

}