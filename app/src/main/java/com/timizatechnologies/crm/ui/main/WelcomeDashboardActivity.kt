package com.timizatechnologies.crm.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.timizatechnologies.crm.R
import com.timizatechnologies.crm.ui.accounts.CreateAccountActivity
import com.timizatechnologies.crm.ui.accounts.LoginActivity
//import com.timizatechnologies.crm.helpers.PrefManager
import kotlinx.android.synthetic.main.activity_welcome_dashboard.*


class WelcomeDashboardActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_dashboard)

        /*val prefManager = PrefManager(applicationContext)
        if (prefManager.isFirstTimeLaunch()) {
            prefManager.setFirstTimeLaunch(false)
            startActivity(Intent(this@WelcomeDashboardActivity, WelcomeActivity::class.java))
            finish()
        }*/

        mAuth = FirebaseAuth.getInstance();

        btn_login_welcome.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        btn_create_account_welcome.setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()

        // in on start method checking if
        // the user is already sign in.
        val user: FirebaseUser? = mAuth.currentUser
        //UpdateUI(user)
        if (user != null) {
            // if the user is not null then we are
            // opening a main activity on below line.
            val i = Intent(this@WelcomeDashboardActivity, DashboardActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}
