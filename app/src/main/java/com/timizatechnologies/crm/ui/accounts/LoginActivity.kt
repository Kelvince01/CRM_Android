package com.timizatechnologies.crm.ui.accounts

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.timizatechnologies.crm.R
import com.timizatechnologies.crm.ui.main.DashboardActivity
import org.json.JSONObject
import java.util.*


class LoginActivity : AppCompatActivity() {
    private var userNameEdt: EditText? =  null
    private  var passwordEdt: EditText? = null
    private lateinit var loginBtn: Button
    private lateinit var facebookLoginBtn: LoginButton
    private var swipeRight: TextView? = null
    private lateinit var mAuth: FirebaseAuth
    private var loadingPB: ProgressBar? = null
    private val EMAIL = "email"
    private lateinit var callbackManager : CallbackManager

    private lateinit var signInButton: SignInButton
    private lateinit var googleSignInClient: GoogleSignInClient
    private val TAG = "mainTag"
    private lateinit var btnSignOut: Button
    private val RESULT_CODE_SINGIN = 999

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired

        callbackManager = CallbackManager.Factory.create();

        // initializing all our variables.
        userNameEdt = findViewById(R.id.et_email);
        passwordEdt = findViewById(R.id.et_password);
        loginBtn = findViewById(R.id.btn_login);
        swipeRight = findViewById(R.id.swipeRight);
        mAuth = FirebaseAuth.getInstance();
        loadingPB = findViewById(R.id.idPBLoading);
        facebookLoginBtn = findViewById(R.id.btn_login_facebook);
        facebookLoginBtn.setReadPermissions(Arrays.asList(EMAIL));
        facebookLoginBtn.setReadPermissions(EMAIL, "public_profile");

        /*btn_login.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }*/

        /*LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                fun onSuccess(loginResult: LoginResult) {
                    // App code
                    // actual login
                    //LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
                }

                override fun onCancel() {
                    // App code
                }

                override fun onError(exception: FacebookException) {
                    // App code
                }
            })*/

        // Callback registration
        facebookLoginBtn.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(result: LoginResult?) {
                Log.d(TAG, "facebook:onSuccess:" + result);

                val graphRequest = GraphRequest.newMeRequest(result?.accessToken){ `object`, response ->
                    getFacebookData(`object`)
                }
                val parameters = Bundle()
                parameters.putString("fields", "id,email,birthday,gender,name")
                graphRequest.parameters = parameters
                graphRequest.executeAsync()

                handleFacebookAccessToken(result!!.accessToken)
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel");
                Toast.makeText(this@LoginActivity,
                    "Cancelled",
                    Toast.LENGTH_SHORT).show()
            }

            override fun onError(exception: FacebookException) {
                Log.d(TAG, "facebook:onError", exception);
                Toast.makeText(this@LoginActivity, "$exception", Toast.LENGTH_SHORT).show()
            }
        })

        // adding on click listener for our login button.
        loginBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // hiding our progress bar.
                loadingPB!!.visibility = View.VISIBLE
                // getting data from our edit text on below line.
                val email: String = userNameEdt!!.text.toString()
                val password: String = passwordEdt!!.text.toString()
                // on below line validating the text input.
                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter your credentials..",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                // on below line we are calling a sign in method and passing email and password to it.
                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(object : OnCompleteListener<AuthResult?> {
                        override fun onComplete(@NonNull task: Task<AuthResult?>) {
                            // on below line we are checking if the task is success or not.
                            if (task.isSuccessful()) {
                                // on below line we are hiding our progress bar.
                                loadingPB!!.visibility = View.GONE
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Login Successful..",
                                    Toast.LENGTH_SHORT
                                ).show()
                                // on below line we are opening our mainactivity.
                                val i = Intent(this@LoginActivity, DashboardActivity::class.java)
                                startActivity(i)
                                finish()
                            } else {
                                // hiding our progress bar and displaying a toast message.
                                loadingPB!!.visibility = View.GONE
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Please enter valid user credentials..",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
            }
        })

        signInButton = findViewById(R.id.btn_login_google);
        //btnSignOut = findViewById(R.id.Sign_out);
        //btnSignOut.setVisibility(View.INVISIBLE);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        //Attach a onClickListener
        signInButton.setOnClickListener(View.OnClickListener { signInM() })

        //Attach a onClickListener
        /*btnSignOut.setOnClickListener(View.OnClickListener {
            googleSignInClient.signOut()
            btnSignOut.setVisibility(View.INVISIBLE)
            signInButton.setVisibility(View.VISIBLE)
            Toast.makeText(this@LoginActivity, "you are logged out", Toast.LENGTH_LONG).show()
        })*/

        swipeRight!!.setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
            finish()
        }
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = mAuth.currentUser

                    startActivity(Intent(this, DashboardActivity::class.java))
                    finish()
                    UpdateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    UpdateUI(null)
                }
            }
    }

    private fun getFacebookData(jsonObject: JSONObject?) {
        val profilePic = "https://graph.facebook.com/${jsonObject
            ?.getString("id")}/picture?width=500&height=500"
        /*Glide.with(this)
            .load(profilePic)
            .into(binding.profilePic)

        val name = jsonObject?.getString("name")
        val birthday = jsonObject?.getString("birthday")
        val gender = jsonObject?.getString("gender")
        val email = jsonObject?.getString("email")

        binding.userName.text = "Name: ${name}"
        binding.userEmail.text = "Email: ${email}"
        binding.userBDay.text = "Birthday: ${birthday}"
        binding.userGender.text = "Gender: ${gender}"*/
    }

    //when the signIn Button is clicked then start the signIn Intent
    private fun signInM() {
        val singInIntent: Intent = googleSignInClient.getSignInIntent()
        startActivityForResult(singInIntent, RESULT_CODE_SINGIN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // The activity result pass back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode === RESULT_CODE_SINGIN) {        //just to verify the code
            //create a Task object and use GoogleSignInAccount from Intent and write a separate method to handle singIn Result.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun FirebaseGoogleAuth(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        //here we are checking the Authentication Credential and checking the task is successful or not and display the message
        //based on that.
        mAuth.signInWithCredential(credential).addOnCompleteListener(
            this
        ) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this@LoginActivity, "successful", Toast.LENGTH_LONG).show()
                val firebaseUser = mAuth.currentUser
                UpdateUI(firebaseUser!!)
            } else {
                Toast.makeText(this@LoginActivity, "Failed!", Toast.LENGTH_LONG).show()
                UpdateUI(null)
            }
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {

        //we use try catch block because of Exception.
        try {
            signInButton.setVisibility(View.INVISIBLE)
            val account = task.getResult(ApiException::class.java)
            Toast.makeText(this@LoginActivity, "Signed In successfully", Toast.LENGTH_LONG).show()
            //SignIn successful now show authentication
            FirebaseGoogleAuth(account)
        } catch (e: ApiException) {
            e.printStackTrace()
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            Toast.makeText(this@LoginActivity, "SignIn Failed!!!", Toast.LENGTH_LONG).show()
            FirebaseGoogleAuth(null)
        }
    }

    //Inside UpdateUI we can get the user information and display it when required
    private fun UpdateUI(fUser: FirebaseUser?) {
        //btnSignOut.setVisibility(View.VISIBLE)

        //getLastSignedInAccount returned the account
        val account: GoogleSignInAccount = GoogleSignIn.getLastSignedInAccount(applicationContext)!!
        if (account != null) {
            val personName = account.displayName
            val personGivenName = account.givenName
            val personEmail = account.email
            val personId = account.id
            Toast.makeText(this@LoginActivity, "$personName  $personEmail", Toast.LENGTH_LONG).show()
        }
    }

    override fun onStart() {
        super.onStart()
        // in on start method checking if
        // the user is already sign in.
        //val user: FirebaseUser? = mAuth.currentUser
        //UpdateUI(user)
        /*if (user != null) {
            // if the user is not null then we are
            // opening a main activity on below line.
            val i = Intent(this@LoginActivity, DashboardActivity::class.java)
            startActivity(i)
            finish()
        }*/
    }
}
