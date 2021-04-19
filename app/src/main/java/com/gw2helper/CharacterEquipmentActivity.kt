package com.gw2helper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.gw2helper.adapters.EquipmentListAdapter
import com.gw2helper.entities.Equipment
import org.json.JSONArray
import org.json.JSONObject
import java.lang.StringBuilder
import kotlin.math.min

class CharacterEquipmentActivity : InternetActivity() {

    private lateinit var equipmentRecyclerView: RecyclerView
    private lateinit var characterName: String

    private val equipmentList = mutableListOf<Equipment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_equipment)

        characterName = intent.extras?.getString("characterName") ?: ""

        equipmentRecyclerView = findViewById(R.id.equipmentRecyclerView)
        equipmentRecyclerView.adapter = EquipmentListAdapter(this, equipmentList)
        equipmentRecyclerView.layoutManager = LinearLayoutManager(this)

        getCharacterEquipmentRequest()
    }

    private fun getCharacterEquipmentRequest() {
        val url = "${ApiHelper.baseUrl}/${ApiHelper.endpoints.characters}/$characterName/equipment?${ApiHelper.params.access_token}=${ApiHelper.getApiKeyFromSharedPreferences(this)}"
        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {response -> loadCharacterEquipment(response)},
            {error -> ToastsHelper.makeToast(error.toString(), this)}
        )

        queue.add(request)
    }

    private fun loadCharacterEquipment(response: JSONObject) {
        val equipment = response.getJSONArray("equipment")
        for (index in 0 until equipment.length()) {
            val currentEquipment = equipment.getJSONObject(index)

            val id = currentEquipment.getString("id")
            val equipmentObject = Equipment(id)

            equipmentObject.slot = currentEquipment.getString("slot")
            if (currentEquipment.has("stats")) {
                val stats = currentEquipment.getJSONObject("stats")
                val attributes = stats.getJSONObject("attributes")
                for (attributeKey in attributes.keys()) {
                    val value = attributes.getInt(attributeKey)
                    equipmentObject.attributes[attributeKey] = value
                }
            }

            equipmentList.add(equipmentObject)
        }

        getEquipmentDetailsRequest()
    }

    private fun getEquipmentDetailsRequest() {
        val idsListBuilder = StringBuilder()
        for (equipment in equipmentList) {
            idsListBuilder.append("${equipment.id},")
        }
        idsListBuilder.deleteCharAt(idsListBuilder.length-1)
        val url = ApiHelper.buildUrl(ApiHelper.endpoints.items, mapOf(ApiHelper.params.ids to idsListBuilder.toString()))
        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            {response -> loadEquipmentDetails(response)},
            {error -> ToastsHelper.makeToast(error.toString(), this)}
        )

        queue.add(request)
    }

    private fun loadEquipmentDetails(response: JSONArray) {
        for (index in 0 until response.length()) {
            val currentItem = response.getJSONObject(index)

            val id = currentItem.getString("id")
            val equipment = equipmentList.find {x -> x.id == id}
            if (equipment != null) {
                equipment.name = currentItem.getString("name")
                equipment.level = currentItem.getInt("level")
                if (currentItem.has("icon")) {
                    equipment.icon = currentItem.getString("icon")
                }
                equipment.rarity = currentItem.getString("rarity")

                if (currentItem.has("details")) {
                    val details = currentItem.getJSONObject("details")
                    if (details.has("infix_upgrade")) {
                        val stats = details.getJSONObject("infix_upgrade")
                        if (stats.has("attributes")) {
                            val attributes = stats.getJSONArray("attributes")
                            for (attributeIndex in 0 until attributes.length()) {
                                val attribute = attributes.getJSONObject(attributeIndex)
                                val attributeKey = attribute.getString("attribute")
                                val value = attribute.getInt("modifier")
                                equipment.attributes[attributeKey] = value
                            }
                        }
                    }
                }

            }
        }

        equipmentRecyclerView.adapter?.notifyDataSetChanged()
    }
}