package com.gw2helper.entities

import org.json.JSONObject

data class Equipment(val id: String) {
    var slot: String = ""
        set(value) {
            if (field == "") {
                field = value
            }
        }

    var name: String = ""
        set(value) {
            if (field == "") {
                field = value
            }
        }

    var level: Int = 1
        set(value) {
            if (field == 1) {
                field = value
            }
        }

    var icon: String? = null
        set(value) {
            if (field == null) {
                field = value
            }
        }

    val attributes = mutableMapOf<String, Int>()

    var rarity: String? = null
        set(value) {
            if (field == null) {
                field = value
            }
        }
}