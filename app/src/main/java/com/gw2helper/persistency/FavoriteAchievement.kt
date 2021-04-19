package com.gw2helper.persistency

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteAchievement(
    @PrimaryKey val id: String,
    var isFavorite: Boolean
)
