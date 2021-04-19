package com.gw2helper

import com.gw2helper.persistency.Database
import com.gw2helper.persistency.FavoriteAchievement
import kotlin.concurrent.thread

object PersistedData {

    lateinit var database: Database
        private set

    fun loadPersistedData(db: Database) {
        thread {
            database = db
        }
    }

}