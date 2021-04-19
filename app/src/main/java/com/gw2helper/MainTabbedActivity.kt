package com.gw2helper

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.room.Room
import com.gw2helper.persistency.Database
import com.gw2helper.tabsFragments.AccountFragment
import com.gw2helper.tabsFragments.AchievementsFragment
import com.gw2helper.tabsFragments.CharactersFragment
import java.lang.IndexOutOfBoundsException
import kotlin.concurrent.thread

class MainTabbedActivity : InternetActivity() {

    private class TabsPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        private val tabs = arrayOf(AccountFragment(), CharactersFragment(), AchievementsFragment())

        override fun getCount(): Int {
            return tabs.size
        }

        override fun getItem(position: Int): Fragment {
            return if (position < count) tabs[position] else throw IndexOutOfBoundsException()
        }

    }

    override fun onBackPressed() {
        ToastsHelper.makeToast("Use LOG OUT button", applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_tabbed)

        PersistedData.loadPersistedData(Room.databaseBuilder(
            this,
            Database::class.java, "gw2helper-database"
        ).build())

        val tabsPagerAdapter = TabsPagerAdapter(supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = tabsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }
}