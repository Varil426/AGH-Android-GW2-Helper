package com.gw2helper.persistency

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(FavoriteAchievement::class), version = 1)
abstract class Database : RoomDatabase() {
    abstract fun favoriteAchievementsDao(): FavoriteAchievementDao
}