package com.gw2helper.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gw2helper.PersistedData
import com.gw2helper.R
import com.gw2helper.entities.Achievement
import com.gw2helper.persistency.FavoriteAchievement
import kotlin.concurrent.thread

class AchievementsListAdapter(private val context: Context, private val listOfAchievements: MutableList<Achievement>) : RecyclerView.Adapter<AchievementsListAdapter.AchievementView>() {

    private var fullListOfAchievements = mutableListOf<Achievement>()

    class AchievementView(val view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView = view.findViewById<TextView>(R.id.achievementTitleTextView)
        val pointsTextView = view.findViewById<TextView>(R.id.achievementPointsTextView)
        val doneCheckBox = view.findViewById<CheckBox>(R.id.achievementDoneCheckBox)
        val progressBar = view.findViewById<ProgressBar>(R.id.achivementProgressBar)
        val favoriteCheckBox = view.findViewById<CheckBox>(R.id.achievementAddToFavoritesCheckBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementView {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.view_achievement_row, parent, false)
        return AchievementView(view)
    }

    override fun onBindViewHolder(holder: AchievementView, position: Int) {
        val currentAchievement = listOfAchievements[position]

        holder.titleTextView.text = currentAchievement.Name
        holder.pointsTextView.text = currentAchievement.Points.toString()
        holder.doneCheckBox.isChecked = currentAchievement.done

        holder.progressBar.max = currentAchievement.maxProgress
        holder.progressBar.progress = currentAchievement.currentProgress

        holder.favoriteCheckBox.setOnCheckedChangeListener {_, value -> updateFavoriteAchievementValue(currentAchievement, value)}
        holder.favoriteCheckBox.isChecked = PersistedData.favoriteAchievements.find { persisted -> persisted.id == currentAchievement.id }?.isFavorite ?: false
    }

    private fun updateFavoriteAchievementValue(currentAchievement: Achievement, value: Boolean) {
        val persistedFavoriteAchievement = PersistedData.favoriteAchievements.find { persisted -> persisted.id == currentAchievement.id }
        if (persistedFavoriteAchievement != null) {
            persistedFavoriteAchievement.isFavorite = value
        } else {
            thread {
                val persistedAchievement = PersistedData.database.favoriteAchievementsDao().findById(currentAchievement.id)
                if (persistedAchievement != null) {
                    persistedAchievement.isFavorite = value
                    PersistedData.refreshPersistedData()
                } else {
                    PersistedData.database.favoriteAchievementsDao().insertAll(FavoriteAchievement(currentAchievement.id, value))
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listOfAchievements.size
    }

    fun filter(onlyFavorites: Boolean) {
        if (onlyFavorites) {
            fullListOfAchievements = listOfAchievements.toMutableList()
            listOfAchievements.clear()
            listOfAchievements.addAll(fullListOfAchievements.filter { achievement -> PersistedData.favoriteAchievements.find { persistent -> persistent.id == achievement.id }?.isFavorite ?: false })
        } else {
            listOfAchievements.clear()
            listOfAchievements.addAll(fullListOfAchievements)
        }
        notifyDataSetChanged()
    }

}