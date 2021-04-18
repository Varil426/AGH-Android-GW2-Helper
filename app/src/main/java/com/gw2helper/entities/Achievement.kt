package com.gw2helper.entities

data class Achievement(val id: String, val done: Boolean = false, val currentProgress: Int = 0, val maxProgress: Int = 1) {
    var Name: String = ""
        set(value) {
            if (field == "") {
                field = value
            } else if (field != value){
                throw IllegalArgumentException("Value has been already set")
            }
        }

    var Points: Int = 0
        set(value) {
            if (field == 0) {
                field = value
            } else if (field != value) {
                throw IllegalArgumentException("Value has been already set")
            }
        }
}