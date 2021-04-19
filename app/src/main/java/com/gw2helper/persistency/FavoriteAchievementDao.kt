package com.gw2helper.persistency

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteAchievementDao {
    @Query("SELECT * FROM FavoriteAchievement")
    fun getAll(): List<FavoriteAchievement>

    @Query("SELECT * FROM FavoriteAchievement WHERE id IN (:favoriteAchievementsIds)")
    fun loadAllByIds(favoriteAchievementsIds: Array<String>): List<FavoriteAchievement>

    @Query("SELECT * FROM FavoriteAchievement WHERE id LIKE :id LIMIT 1")
    fun findById(id: String): FavoriteAchievement

    @Insert
    fun insertAll(vararg favoriteAchievements: FavoriteAchievement)

    @Delete
    fun delete(favoriteAchievement: FavoriteAchievement)
}