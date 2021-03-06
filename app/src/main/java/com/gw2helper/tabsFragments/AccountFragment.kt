package com.gw2helper.tabsFragments

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.gw2helper.*
import com.gw2helper.entities.Account
import com.gw2helper.utils.ValueConverter
import org.json.JSONObject

class AccountFragment : Fragment(R.layout.fragment_account_screen) {

    private lateinit var nameTextView: TextView
    private lateinit var ageTextView: TextView
    private lateinit var fractalLevelTextView: TextView
    private lateinit var wvwLevelTextView: TextView
    private lateinit var logOutButton: Button

    private lateinit var account: Account

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameTextView = view.findViewById(R.id.account_name_text_view)
        ageTextView = view.findViewById(R.id.age_text_view)
        fractalLevelTextView = view.findViewById(R.id.fractal_level_text_view)
        wvwLevelTextView = view.findViewById(R.id.wvw_level_text_view)
        logOutButton = view.findViewById(R.id.logout_button)

        logOutButton.setOnClickListener { _ -> ApiHelper.forgetSharedPreferences(context!!) }

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

        (activity as InternetActivity)?.queue.add(request)
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


        val fractalLevel = response.getInt("fractal_level")
        val wvw_level = response.getInt("wvw_rank")
        account = Account(name, age, worldId, access, fractalLevel, wvw_level)

        // TODO
        //getWorldNameRequest()

        nameTextView.text = account.name
        ageTextView.text = ValueConverter.getFormatedDateFromSeconds(account.age)
        fractalLevelTextView.text = account.fractalLevel.toString()
        wvwLevelTextView.text = account.wvwRank.toString()
    }


}