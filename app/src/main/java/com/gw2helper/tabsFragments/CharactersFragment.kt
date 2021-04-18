package com.gw2helper.tabsFragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.gw2helper.ApiHelper
import com.gw2helper.InternetActivity
import com.gw2helper.R
import com.gw2helper.ToastsHelper
import com.gw2helper.adapters.CharactersListAdapter
import com.gw2helper.entities.Character
import org.json.JSONArray
import org.json.JSONObject

class CharactersFragment : Fragment(R.layout.fragment_characters_screen) {

    private lateinit var charactersRecyclerView: RecyclerView

    private val charactersNamesList = mutableListOf<String>()
    private val listOfCharacters = mutableListOf<Character>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        charactersRecyclerView = view.findViewById(R.id.charactersListRecyclerView)
        charactersRecyclerView.adapter = CharactersListAdapter(activity!!, listOfCharacters)
        charactersRecyclerView.layoutManager = LinearLayoutManager(activity!!)


        getCharactersListRequest()
    }

    private fun getCharactersListRequest() {
        val url = ApiHelper.buildUrl(ApiHelper.endpoints.characters, activity!!)
        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            {response -> loadCharactersList(response)},
            {error -> ToastsHelper.makeToast(error.toString(), activity!!)}
        )

        (activity as InternetActivity)?.queue.add(request)
    }

    private fun loadCharactersList(response: JSONArray) {
        for (index in 0 until response.length()) {
            charactersNamesList.add(response[index].toString())
        }

        getCharactersDataRequest()
    }

    private fun getCharactersDataRequest() {
        for (characterName in charactersNamesList) {
            val url = ApiHelper.buildUrl("${ApiHelper.endpoints.characters}/$characterName/core", activity!!)
            val request = JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                {response -> loadCharacterData(response)},
                {error -> ToastsHelper.makeToast(error.toString(), activity!!)}
            )

            (activity as InternetActivity)?.queue.add(request)
        }
    }

    private fun loadCharacterData(response: JSONObject) {
        val name = response.getString("name")
        val age =  response.getLong("age")
        val profession = response.getString("profession")
        val level = response.getInt("level")
        val deaths = response.getLong("deaths")

        listOfCharacters.add(Character(name, profession, level, age, deaths))

        charactersRecyclerView.adapter!!.notifyDataSetChanged()
    }

}