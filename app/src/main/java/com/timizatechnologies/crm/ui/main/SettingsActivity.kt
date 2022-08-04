package com.timizatechnologies.crm.ui.main

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import com.timizatechnologies.crm.R

class SettingsActivity : AppCompatActivity() {
    //var view: View? = null
    var tvempname: TextView? = null
    var tvempid:TextView? = null
    var tvempbal:TextView? = null
    var edtempmobile: EditText? = null
    var edtempname:EditText? = null
    var edtememail:EditText? = null
    var edtempdesignation:EditText? = null
    var edtempaddress:EditText? = null
    var ivemppic: ImageView? = null
    var ivprofile:android.widget.ImageView? = null
    var btnupdate: Button? = null
    var btndelete:android.widget.Button? = null

    var preferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvempname = findViewById(R.id.tvempname)
        tvempid = findViewById(R.id.tvempid)
        tvempbal = findViewById(R.id.tvempbal)

        edtempmobile = findViewById(R.id.edtempmobile)
        edtempname = findViewById(R.id.edtempname)
        edtememail = findViewById(R.id.edtememail)
        edtempdesignation = findViewById(R.id.edtempdesignation)
        edtempaddress = findViewById(R.id.edtempaddress)

        ivemppic = findViewById(R.id.ivemppic)

        btnupdate = findViewById(R.id.btnupdate)
        btndelete = findViewById(R.id.btndelete)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }
}
