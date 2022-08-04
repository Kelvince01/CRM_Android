package com.timizatechnologies.crm.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.navigation.NavigationView
import com.timizatechnologies.crm.R
import com.timizatechnologies.crm.databinding.ActivityHome2Binding
import com.timizatechnologies.crm.ui.ActivityFeedActivity
import com.timizatechnologies.crm.ui.CustomersMapActivity
import com.timizatechnologies.crm.ui.OrdersActivity
import com.timizatechnologies.crm.ui.accounts.LoginActivity
import kotlinx.android.synthetic.main.activity_home.*

class Home2Activity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHome2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHome2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        //setSupportActionBar(binding.appBarMain.toolbar)
        setSupportActionBar(toolbar)

        /* //val drawerLayout: DrawerLayout = binding.drawerLayout
        val drawerLayout: DrawerLayout = drawer_layout
        //val navView: NavigationView = binding.navView
        val navView: NavigationView = nav_view
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_sales, R.id.nav_setting
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController) */

        /*val toggle = ActionBarDrawerToggle(
            this, drawer_layout, R.string.navigation_drawer_open,
            //this, drawer_layout, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close)*/

        //nav_view.setNavigationItemSelectedListener(this)

        /*val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        ///
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        //val navView: NavigationView = findViewById(R.id.nav_view)
        //val navController = findNavController(R.id.nav_host_fragment)*/

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home,
            R.id.nav_dashboard,
            R.id.nav_orders,
            R.id.nav_orders,
            R.id.nav_notifications,
            R.id.nav_activity,
            R.id.nav_logout
        ), drawerLayout)*/
        //setupActionBarWithNavController(navController, appBarConfiguration)
        //navView.setupWithNavController(navController)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the home action
                val i = Intent(this@Home2Activity, HomeActivity::class.java)
                startActivity(i)
                finish()
            }
            R.id.nav_dashboard -> {
                val i = Intent(this@Home2Activity, DashboardActivity::class.java)
                startActivity(i)
                finish()
            }
            R.id.nav_orders -> {
                val i = Intent(this@Home2Activity, OrdersActivity::class.java)
                startActivity(i)
                finish()
            }
            R.id.nav_notifications -> {
                val i = Intent(this@Home2Activity, CustomersMapActivity::class.java)
                startActivity(i)
                finish()
            }
            R.id.nav_activity -> {
                val i = Intent(this@Home2Activity, ActivityFeedActivity::class.java)
                startActivity(i)
                finish()
            }
            R.id.nav_setting -> {
                val i = Intent(this@Home2Activity, SettingsActivity::class.java)
                startActivity(i)
                finish()
            }
            R.id.nav_logout -> {
                // displaying a toast message on user logged out inside on click.
                Toast.makeText(applicationContext, "User Logged Out", Toast.LENGTH_LONG).show()
                // on below line we are signing out our user.
                //mAuth!!.signOut()
                // on below line we are opening our login activity.
                val i = Intent(this@Home2Activity, LoginActivity::class.java)
                startActivity(i)
                finish()
            }
            R.id.nav_sms -> {

            }
            R.id.nav_mail -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        //return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
        return true
    }
}
