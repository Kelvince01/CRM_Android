package com.timizatechnologies.crm.ui.accounts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskExecutors
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.gson.JsonElement
import com.timizatechnologies.crm.R
import com.timizatechnologies.crm.api_client.ApiClient
import com.timizatechnologies.crm.api_client.ApiInterface
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var btnresetpwd: Button
    lateinit var btnsendotp:Button
    lateinit var progressBar: ProgressBar
    lateinit var edtforgotpwd: EditText
    lateinit var edtotp:EditText
    lateinit var tiotp: TextInputLayout
    private lateinit var auth: FirebaseAuth

    lateinit var mobileno: String
    var ccode = "+254"
    lateinit var mVerificationId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        btnresetpwd = findViewById(R.id.btnresetpwd)
        btnsendotp = findViewById(R.id.btnsendotp)
        progressBar = findViewById(R.id.progressbar)
        edtforgotpwd = findViewById(R.id.edtforgotpwd)
        edtotp = findViewById(R.id.edtotp)
        tiotp = findViewById(R.id.tiotp)
        auth = FirebaseAuth.getInstance()

        mobileno = edtforgotpwd.getText().toString()

        btnsendotp.setOnClickListener(View.OnClickListener {
            val api: ApiInterface = ApiClient.client!!.create(ApiInterface::class.java)
            val call: Call<JsonElement?>? = api.mobileVerification(mobileno)
            call!!.enqueue(object : Callback<JsonElement?> {
                override fun onResponse(
                    call: Call<JsonElement?>?,
                    response: Response<JsonElement?>?
                ) {
                    try {
                        val `object` = JSONObject(response!!.body().toString())
                        val msg = `object`.getString("status")
                        if (msg == "200") {
                            mobileno = edtforgotpwd.getText().toString()
                            val code = ccode + mobileno
                            sendVerificationcode(code)
                            tiotp.setVisibility(View.VISIBLE)
                            btnsendotp.setVisibility(View.INVISIBLE)
                            btnresetpwd.setVisibility(View.VISIBLE)
                            progressBar.setVisibility(View.VISIBLE)
                        } else {
                            Toast.makeText(
                                this@ForgotPasswordActivity,
                                "Mobile No. Is not Registered",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<JsonElement?>?, t: Throwable?) {}
            })
        })

        btnresetpwd.setOnClickListener(View.OnClickListener {
            val otp = edtotp.getText().toString()
            mobileno = edtforgotpwd.getText().toString()
            val i = Intent(this@ForgotPasswordActivity, ResetPasswordActivity::class.java)
            i.putExtra("mobileno", mobileno)
            verifyVerificationcode(otp)
        })
    }

    private fun sendVerificationcode(code: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            code, 60, TimeUnit.SECONDS, this, mCallbacks
            //code, 60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD, mCallbacks
        )
    }

    var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(s: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(s, p1)
                mVerificationId = s
            }

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                val code: String = phoneAuthCredential.getSmsCode().toString()
                Log.e("Code>>", code + "")
                if (code != null) {
                    edtotp!!.setText(code)
                    verifyVerificationcode(code)
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@ForgotPasswordActivity, "" + e.message, Toast.LENGTH_SHORT).show()
            }
        }

    private fun verifyVerificationcode(otp: String) {
        val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(mVerificationId, otp)
        SigninwithphoneAuthCredential(credential)
    }

    private fun SigninwithphoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this@ForgotPasswordActivity, object : OnCompleteListener<AuthResult?> {
                override fun onComplete(task: Task<AuthResult?>) {
                    if (task.isSuccessful()) {
                        val i = Intent(this@ForgotPasswordActivity, ResetPasswordActivity::class.java)
                        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        i.putExtra("mobileno", mobileno)
                        startActivity(i)
                    } else {
                        var message = "Something went Wrong,we will fix it soon"
                        if (task.getException() is FirebaseAuthInvalidCredentialsException) {
                            message = "Invalid Code Entered"
                        }
                        val snackbar: Snackbar =
                            Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG)
                        snackbar.setAction("Dismiss", View.OnClickListener { })
                        snackbar.show()
                    }
                }
            })
    }
}
