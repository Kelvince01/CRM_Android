package com.timizatechnologies.crm.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.timizatechnologies.crm.R
import com.timizatechnologies.crm.ui.accounts.LoginActivity
import com.timizatechnologies.crm.ui.accounts.fragments.ProfileFragment
import com.timizatechnologies.crm.ui.fragments.SalesFragment
import com.timizatechnologies.crm.ui.main.fragments.AboutFragment
import com.timizatechnologies.crm.ui.main.fragments.HomeFragment
import com.timizatechnologies.crm.ui.main.fragments.NotificationFragment
import com.timizatechnologies.crm.ui.main.fragments.SettingsFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    lateinit var reviewManager: ReviewManager
    internal var reviewInfo: ReviewInfo? = null

    private lateinit var homeFragment: HomeFragment
    private lateinit var profileFragment: ProfileFragment
    private lateinit var notificationFragment: NotificationFragment
    private lateinit var settingsFragment: SettingsFragment
    private lateinit var salesFragment: SalesFragment
    private lateinit var aboutFragment: AboutFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mAuth = FirebaseAuth.getInstance()

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, 0, 0)

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        homeFragment = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        nav_view.setNavigationItemSelectedListener { menuItem ->

            menuItem.isChecked = false
            drawer_layout.closeDrawers()

            when (menuItem.itemId) {

                R.id.nav_home -> {
                    homeFragment = HomeFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, homeFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.nav_profile -> {
                    profileFragment = ProfileFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, profileFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.nav_notifications -> {
                    notificationFragment = NotificationFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, notificationFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.nav_sales -> {
                    salesFragment = SalesFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, salesFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.nav_setting -> {
                    settingsFragment = SettingsFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, settingsFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.nav_logout -> {
                    // displaying a toast message on user logged out inside on click.
                    Toast.makeText(applicationContext, "User Logged Out", Toast.LENGTH_LONG).show()
                    // on below line we are signing out our user.
                    mAuth!!.signOut()
                    // on below line we are opening our login activity.
                    val i = Intent(this@HomeActivity, LoginActivity::class.java)
                    startActivity(i)
                    finish()
                }

                R.id.nav_about -> {
                    aboutFragment = AboutFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, aboutFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
            }
            true
        }

        //getReviewInfo();
        //startReviewFlow();

        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // on below line we are inflating our menu
        // file for displaying our menu options.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(@NonNull item: MenuItem): Boolean {
        // adding a click listener for option selected on below line.
        return when (item.itemId) {
            R.id.nav_logout -> {
                // displaying a toast message on user logged out inside on click.
                Toast.makeText(applicationContext, "User Logged Out", Toast.LENGTH_LONG).show()
                // on below line we are signing out our user.
                mAuth!!.signOut()
                // on below line we are opening our login activity.
                val i = Intent(this@HomeActivity, LoginActivity::class.java)
                startActivity(i)
                finish()
                true
            }
            R.id.mi_settings -> {
                val i = Intent(this@HomeActivity, SettingsActivity::class.java)
                startActivity(i)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this@HomeActivity)
            builder.setTitle(R.string.app_name)
            builder.setIcon(R.mipmap.ic_launcher)
            builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton(
                    "Yes"
                ) { _, _ -> finish() }
                .setNegativeButton(
                    "No"
                ) { dialog, _ -> dialog.cancel() }
            val alert: AlertDialog = builder.create()
            alert.show()

            //super.onBackPressed()
        }
    }
}

fun HomeActivity.startReviewFlow() {
    if (reviewInfo != null) {
        val flow: Task<Void> = reviewManager.launchReviewFlow(this, reviewInfo!!)
        flow.addOnCompleteListener {
            Toast.makeText(
                applicationContext,
                "In App Rating complete",
                Toast.LENGTH_LONG
            ).show()
        }
    } else {
        Toast.makeText(applicationContext, "In App Rating failed", Toast.LENGTH_LONG).show()
    }
}

private fun HomeActivity.getReviewInfo() {
    reviewManager = ReviewManagerFactory.create(applicationContext)
    val manager: Task<ReviewInfo> = reviewManager.requestReviewFlow()
    manager.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            reviewInfo = task.result
        } else {
            Toast.makeText(
                applicationContext,
                "In App ReviewFlow failed to start",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
