package com.gw2helper.tabsFragments

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.gw2helper.ApiHelper
import com.gw2helper.InternetActivity
import com.gw2helper.R
import com.gw2helper.ToastsHelper
import com.gw2helper.adapters.AchievementsListAdapter
import com.gw2helper.adapters.CharactersListAdapter
import com.gw2helper.entities.Achievement
import org.json.JSONArray
import java.lang.StringBuilder
import kotlin.math.ceil
import kotlin.math.min

class AchievementsFragment : Fragment(R.layout.fragment_achievements_screen) {

    private lateinit var achievementsRecyclerView: RecyclerView
    private lateinit var onlyFavoritesCheckBox: CheckBox

    private val listOfAchievements = mutableListOf<Achievement>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        achievementsRecyclerView = view.findViewById(R.id.achievementsRecyclerView)
        achievementsRecyclerView.adapter = AchievementsListAdapter(activity!!, listOfAchievements)
        achievementsRecyclerView.layoutManager = LinearLayoutManager(activity!!)

        onlyFavoritesCheckBox = view.findViewById(R.id.onlyFavoritesCheckBox)
        onlyFavoritesCheckBox.setOnCheckedChangeListener { _, isChecked -> (achievementsRecyclerView.adapter as AchievementsListAdapter)?.filter(isChecked) }

        getAchievementsListRequest()
    }

    private fun getAchievementsListRequest() {
        val url = ApiHelper.buildUrl(ApiHelper.endpoints.accountAchievements, activity!!)
        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            {response -> loadAchievementsList(response)},
            {error -> ToastsHelper.makeToast(error.toString(), activity!!)}
        )

        (activity as InternetActivity)?.queue.add(request)
    }

    private fun loadAchievementsList(response: JSONArray) {
        for (index in 0 until response.length()) {
            val currentAchievement = response.getJSONObject(index)
            val id = currentAchievement.getString("id")
            val done = currentAchievement.getBoolean("done")
            val currentProgress = if (currentAchievement.has("current")) currentAchievement.getInt("current") else (if (done) 1 else 0)
            val maxProgress = if (currentAchievement.has("max")) currentAchievement.getInt("max") else 1

            val achievement = Achievement(id, done, currentProgress, maxProgress)
            listOfAchievements.add(achievement)
        }
        achievementsRecyclerView.adapter?.notifyDataSetChanged()

        getAchievementsDetailsRequest()
    }

    private fun getAchievementsDetailsRequest() {
        val achievementGroupSize = 100.0
        for (groupOfAchievements in 0 until ceil(listOfAchievements.size / achievementGroupSize).toInt()) {
            val idsListBuilder = StringBuilder()
            for (achievementIndex in (groupOfAchievements * achievementGroupSize).toInt() until min(((groupOfAchievements+1) * achievementGroupSize).toInt(), listOfAchievements.size)) {
                val achievement = listOfAchievements[achievementIndex]
                idsListBuilder.append("${achievement.id},")
            }
            idsListBuilder.deleteCharAt(idsListBuilder.length-1)
            val params = mapOf(ApiHelper.params.ids to idsListBuilder.toString())
            val url = ApiHelper.buildUrl(ApiHelper.endpoints.achievements, params)
            val request = JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                {response -> loadAchievementsDetails(response)},
                {error -> ToastsHelper.makeToast(error.toString(), activity!!)}
            )

            (activity as InternetActivity)?.queue.add(request)
        }
    }

    private fun loadAchievementsDetails(response: JSONArray) {
        for (index in 0 until response.length()) {
            val currentAchievementDetails = response.getJSONObject(index)

            val id = currentAchievementDetails.getString("id")
            val name = currentAchievementDetails.getString("name")
            val tiers = currentAchievementDetails.getJSONArray("tiers")
            var points = 0
            for (tierIndex in 0 until tiers.length()) {
                val currentTier = tiers.getJSONObject(tierIndex)
                points += currentTier.getInt("points")
            }

            val currentAchievement = listOfAchievements.firstOrNull {achievement -> achievement.id == id }
            if (currentAchievement != null) {
                currentAchievement.Name = name
                currentAchievement.Points = points

                achievementsRecyclerView.adapter?.notifyItemChanged(listOfAchievements.indexOf(currentAchievement))
            }
        }
    }

}