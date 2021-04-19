package com.gw2helper.entities

import com.gw2helper.PersistedData
import com.gw2helper.persistency.FavoriteAchievement
import kotlin.concurrent.thread

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

    lateinit var favoriteAchievement: FavoriteAchievement
        private set

    init {
        thread {
            val fromDb = PersistedData.database.favoriteAchievementsDao().findById(id)
            favoriteAchievement = if (fromDb != null) {
                fromDb
            } else {
                val newFavoriteAchievement = FavoriteAchievement(id)
                PersistedData.database.favoriteAchievementsDao().insertAll(newFavoriteAchievement)
                newFavoriteAchievement
            }
        }
    }

}