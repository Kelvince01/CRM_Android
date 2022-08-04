package com.timizatechnologies.crm.ui.accounts

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.timizatechnologies.crm.R

class ResetPasswordActivity : AppCompatActivity() {
    var edtresetpwd: EditText? = null
    var edtresetcpwd:EditText? = null
    var btnreset: Button? = null
    var pb: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        edtresetpwd = findViewById(R.id.edtresetpwd)
        edtresetcpwd = findViewById(R.id.edtresetcpwd)
        btnreset = findViewById(R.id.btnreset)
        pb = findViewById(R.id.pb)

        /*btnreset.setOnClickListener(View.OnClickListener {
            if (edtresetpwd.getText().toString().isEmpty()) {
                edtresetpwd.setError("This Field Should not remain Empty")
            } else if (edtresetcpwd.getText().toString().isEmpty()) {
                edtresetcpwd.setError("This Field Should not remain Empty")
            } else if (edtresetcpwd.getText().toString() != edtresetpwd.getText().toString()) {
                Toast.makeText(this@ResetPasswordActivity, "Password Mismatch", Toast.LENGTH_SHORT).show()
            } else {
                pb.setVisibility(View.VISIBLE)
                reset()
            }
        })*/
    }

    private fun reset() {
        /*val password = edtresetpwd!!.text.toString()
        val apiInterface: ApiInterface = ApiClient.client!!.create(ApiInterface::class.java)
        val call: Call<JsonElement?>? =
            apiInterface.resetPassword(intent.getStringExtra("mobileno"), password)
        call!!.enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                try {
                    val `object` = JSONObject(response.body().toString())
                    val msg = `object`.getString("status")
                    if (msg == "200") {
                        Toast.makeText(
                            this@ResetPasswordActivity,
                            "Password Changed Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this@ResetPasswordActivity, LoginActivity::class.java))
                    } else {
                        Toast.makeText(
                            this@ResetPasswordActivity,
                            "Something Went Wrong Please Try Again" + Log.e(
                                "Error: ",
                                response.body().toString()
                            ),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {}
        })*/
    }
}
