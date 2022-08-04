package com.timizatechnologies.crm.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.timizatechnologies.crm.R
import com.timizatechnologies.crm.ui.*
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        //(DashboardActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(true)
        //(DashboardActivity() as AppCompatActivity).supportActionBar?.title = "Products"

        cv_products.setOnClickListener {
            startActivity(Intent(this, ProductsActivity::class.java))
            finish()
        }

        cv_orders.setOnClickListener {
            startActivity(Intent(this, OrdersActivity::class.java))
            finish()
        }

        cv_analytics.setOnClickListener {
            startActivity(Intent(this, AnalyticsActivity::class.java))
            finish()
        }

        cv_customers.setOnClickListener {
            startActivity(Intent(this, CustomersActivity::class.java))
            finish()
        }

        cv_sales.setOnClickListener {
            //startActivity(Intent(this, SalesActivity::class.java))
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        cv_tasks.setOnClickListener {
            startActivity(Intent(this, TasksActivity::class.java))
            finish()
        }
    }
}
