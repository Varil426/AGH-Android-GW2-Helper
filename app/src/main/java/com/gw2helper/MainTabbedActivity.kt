package com.gw2helper

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.gw2helper.tabsFragments.AccountFragment
import com.gw2helper.tabsFragments.AchievementsFragment
import com.gw2helper.tabsFragments.CharactersFragment
import java.lang.IndexOutOfBoundsException

class MainTabbedActivity : InternetActivity() {

    private class TabsPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        private val tabs = arrayOf(AccountFragment(), CharactersFragment())

        override fun getCount(): Int {
            return tabs.size
        }

        override fun getItem(position: Int): Fragment {
            return if (position < count) tabs[position] else throw IndexOutOfBoundsException()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_tabbed)
        val tabsPagerAdapter = TabsPagerAdapter(supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = tabsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }
}