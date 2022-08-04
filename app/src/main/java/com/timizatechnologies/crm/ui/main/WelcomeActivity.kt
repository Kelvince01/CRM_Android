package com.timizatechnologies.crm.ui.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.timizatechnologies.crm.Fragments.welcome.Welcome1Fragment
import com.timizatechnologies.crm.Fragments.welcome.Welcome2Fragment
import com.timizatechnologies.crm.Fragments.welcome.Welcome3Fragment
import com.timizatechnologies.crm.Fragments.welcome.Welcome4Fragment
import com.timizatechnologies.crm.R
import com.timizatechnologies.crm.extensions.IntroSliderAdapter
import com.timizatechnologies.crm.helpers.PrefManager
import kotlinx.android.synthetic.main.activity_welcome.*


class WelcomeActivity : AppCompatActivity() {
    private val fragmentList = ArrayList<Fragment>()
    private var prefManager: PrefManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Checking for first time launch - before calling setContentView()
        prefManager = PrefManager(this);
        if (!prefManager!!.isFirstTimeLaunch) {
            launchHomeScreen();
            finish();
        }

        // making the status bar transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        setContentView(R.layout.activity_welcome)

        /*PreferenceManager.getDefaultSharedPreferences(this).apply {
            // Check if we need to display our OnboardingSupportFragment
            if (!getBoolean(Welcome4Fragment.COMPLETED_ONBOARDING_PREF_NAME

                    , false)) {
                // The user hasn't seen the OnboardingSupportFragment yet, so show it
                startActivity(Intent(this@WelcomeActivity, WelcomeActivity::class.java))
            }
        }*/

        val pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE)
        if (pref.getBoolean("activity_executed", false)) {
            val intent = Intent(this, WelcomeDashboardActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val ed: SharedPreferences.Editor = pref.edit()
            ed.putBoolean("activity_executed", true)
            ed.commit()
        }

        val adapter = IntroSliderAdapter(this)
        vpIntroSlider.adapter = adapter
        fragmentList.addAll(listOf(
            Welcome1Fragment(), Welcome2Fragment(), Welcome3Fragment(), Welcome4Fragment()
        ))
        adapter.setFragmentList(fragmentList)
        indicatorLayout.setIndicatorCount(adapter.itemCount)
        indicatorLayout.selectCurrentPosition(0)
        registerListeners()
    }

    private fun launchHomeScreen() {
        prefManager!!.isFirstTimeLaunch = false
        startActivity(Intent(this@WelcomeActivity, WelcomeDashboardActivity::class.java))
        finish()
    }

    private fun registerListeners() {
        vpIntroSlider.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                indicatorLayout.selectCurrentPosition(position)
                if (position < fragmentList.lastIndex) {
                    tvSkip.visibility = View.VISIBLE
                    tvNext.text = "Next"
                } else {
                    tvSkip.visibility = View.GONE
                    tvNext.text = "Get Started"
                }
            }
        })
        tvSkip.setOnClickListener {
            startActivity(Intent(this, WelcomeDashboardActivity::class.java))
            finish()
        }

        tvNext.setOnClickListener {
            val position = vpIntroSlider.currentItem
            if (position < fragmentList.lastIndex) {
                vpIntroSlider.currentItem = position + 1
            } else {
                launchHomeScreen();
            }
        }
    }
}
