package com.gw2helper.tabsFragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.gw2helper.ApiHelper
import com.gw2helper.InternetActivity
import com.gw2helper.R
import com.gw2helper.ToastsHelper
import com.gw2helper.entities.Account
import org.json.JSONObject

class AccountFragment : Fragment(R.layout.fragment_account_screen) {

    private lateinit var nameTextView: TextView
    private lateinit var ageTextView: TextView
    private lateinit var fractalLevelTextView: TextView
    private lateinit var wvwLevelTextView: TextView
    private lateinit var guildsTextView: TextView

    private lateinit var account: Account

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameTextView = view.findViewById(R.id.account_name_text_view)
        ageTextView = view.findViewById(R.id.age_text_view)
        fractalLevelTextView = view.findViewById(R.id.fractal_level_text_view)
        wvwLevelTextView = view.findViewById(R.id.wvw_level_text_view)
        guildsTextView = view.findViewById(R.id.guilds_text_view)

        getAccountInfoRequest()
    }

    private fun getAccountInfoRequest() {
        val url = ApiHelper.buildUrl(ApiHelper.endpoints.account, activity!!)
        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {response -> loadAccountData(response)},
            {error -> ToastsHelper.makeToast(error.toString(), activity!!)}
        )

        val myActivity = activity as? InternetActivity
        myActivity?.queue?.add(request)
    }

    private fun loadAccountData(response: JSONObject) {
        val name = response.getString("name")
        val age = response.getLong("age")
        val worldId = response.getString("world")

        val accessArray = response.getJSONArray("access")
        val access = mutableListOf<String>()
        for (index in 0 until accessArray.length()) {
            access.add(accessArray[index].toString())
        }

        val guildsArray = response.getJSONArray("guilds")
        val guilds = mutableListOf<String>()
        for (index in 0 until guildsArray.length()) {
            guilds.add(guildsArray[index].toString())
        }

        val fractalLevel = response.getInt("fractal_level")
        val wvw_level = response.getInt("wvw_rank")
        account = Account(name, age, worldId, guilds, access, fractalLevel, wvw_level)

        // TODO
        //getGuildsNamesRequest()
        //getWorldNameRequest()

        nameTextView.text = account.name
        ageTextView.text = account.getFormatedDate()
        fractalLevelTextView.text = account.fractalLevel.toString()
        wvwLevelTextView.text = account.wvwRank.toString()
    }

}