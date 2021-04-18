package com.gw2helper.utils

class ValueConverter {
    companion object {
        fun getFormatedDateFromSeconds(value: Long): String {
            var rest = value.toDouble()
            val days = Math.floor(rest/(60*60*24))
            rest -= days * (60*60*24)
            val hours = Math.floor(rest/(60*60))
            rest -= hours * (60*60)
            val minutes = Math.floor(rest/(60))
            rest -= minutes * (60)
            val seconds = Math.floor(rest)
            return "Days: ${days.toInt()}, Hours: ${hours.toInt()}, Minutes: ${minutes.toInt()}, Seconds: ${seconds.toInt()}"
        }
    }
}