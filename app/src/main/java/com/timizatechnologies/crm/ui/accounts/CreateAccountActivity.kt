package com.timizatechnologies.crm.ui.accounts

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.timizatechnologies.crm.BuildConfig
import com.timizatechnologies.crm.ui.main.DashboardActivity
import com.timizatechnologies.crm.R
import kotlinx.android.synthetic.main.activity_create_account.*
import java.io.File
import java.util.regex.Pattern


class CreateAccountActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private var uri: Uri? = null
    //Our widgets
    private lateinit var btnCapture: Button
    private lateinit var btnChoose : Button
    //Our constants
    private val CAPTURE_PHOTO = 1
    private val CHOOSE_PHOTO = 2
    private var nameEdt: EditText? =  null
    private var emailEdt: EditText? =  null
    private var phoneEdt: EditText? =  null
    private  var passwordEdt: EditText? = null
    private var createAccountBtn: Button? = null
    private var swipeLeft: TextView? = null
    private lateinit var mAuth: FirebaseAuth
    private var loadingPB: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        // initializing all our variables.
        initializeWidgets()

        imageView.setOnClickListener{showBottomSheetDialog()}

        btn_register.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }

        // adding click listener for register button.
        createAccountBtn!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                // hiding our progress bar.
                loadingPB!!.visibility = View.VISIBLE

                // getting data from our edit text.
                val userName: String = nameEdt!!.text.toString()
                val pwd = passwordEdt!!.text.toString()
                val cnfPwd = passwordEdt!!.text.toString()
                val phone: String = phoneEdt!!.text.toString()
                val email: String = emailEdt!!.text.toString()

                // checking if the password and confirm password is equal or not.
                if (pwd != cnfPwd) {
                    Toast.makeText(
                        this@CreateAccountActivity,
                        "Please check both having same password..",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (TextUtils.isEmpty(email) && TextUtils.isEmpty(pwd) && TextUtils.isEmpty(
                        cnfPwd
                    )
                ) {

                    // checking if the text fields are empty or not.
                    Toast.makeText(
                        this@CreateAccountActivity,
                        "Please enter your credentials..",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    // on below line we are creating a new user by passing email and password.
                    mAuth.createUserWithEmailAndPassword(email, pwd)
                        .addOnCompleteListener(object : OnCompleteListener<AuthResult?> {
                            override fun onComplete(@NonNull task: Task<AuthResult?>) {
                                // on below line we are checking if the task is success or not.
                                if (task.isSuccessful()) {

                                    // in on success method we are hiding our progress bar and opening a login activity.
                                    loadingPB!!.visibility = View.GONE
                                    Toast.makeText(
                                        this@CreateAccountActivity,
                                        "User Registered..",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val i = Intent(this@CreateAccountActivity, LoginActivity::class.java)
                                    startActivity(i)
                                    finish()
                                } else {

                                    // in else condition we are displaying a failure toast message.
                                    loadingPB!!.visibility = View.GONE
                                    Toast.makeText(
                                        this@CreateAccountActivity,
                                        "Fail to register user..",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        })
                }
            }
        })

        swipeLeft!!.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun ValidateData() {
        /*if (edtname.getText().toString().trim().isEmpty()) {
            edtname.setError("Enter Your Name")
        } else if (!isValidEmaillId(edtemail.getText().toString().trim())) {
            edtemail.setError("Enter valid Email")
        } else if (edtpwd.getText().toString().length() > 16) {
            edtpwd.setError("Password length should not exist more than 16 character")
        } else if (edtpwd.getText().toString().trim().isEmpty() && edtcpwd.getText().toString()
                .trim().isEmpty()
        ) {
            edtpwd.setError("Enter Password")
            edtcpwd.setError("Enter password")
            tpwd.setPasswordVisibilityToggleDrawable(View.INVISIBLE)
        } else if (!edtpwd.getText().toString().equals(edtcpwd.getText().toString())) {
            Toast.makeText(this@SignUp, "Password Mismatch Error", Toast.LENGTH_SHORT).show()
        } else if (edtmobile.getText().toString().trim().isEmpty()) {
            edtmobile.setError("Enter Mobile No.")
        } else if (edtmobile.getText().length() < 10) {
            edtmobile.setError("Invalid Mobile No.")
        } else if (edtaddress.getText().toString().trim().isEmpty()) {
            edtaddress.setError("Address Field should not remain empty")
        } else if (edtcaddress.getText().toString().trim().isEmpty()) {
            edtcaddress.setError("Company Address field Should not remain empty")
        } else {
            signuppb.setVisibility(View.VISIBLE)
            DataPost()
        }*/
    }

    private fun initializeWidgets() {
        imageView = findViewById(R.id.img)
        nameEdt = findViewById(R.id.et_name);
        phoneEdt = findViewById(R.id.et_phone);
        emailEdt = findViewById(R.id.et_email);
        passwordEdt = findViewById(R.id.et_password);
        createAccountBtn = findViewById(R.id.btn_register);
        swipeLeft = findViewById(R.id.swipeLeft);
        mAuth = FirebaseAuth.getInstance();
        loadingPB = findViewById(R.id.idPBLoading);
    }

    private fun checkPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            false
        } else true
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CAMERA),
            1
        )
    }

    private fun isValidEmaillId(email: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_image_dialog_layout)
        val choose = bottomSheetDialog.findViewById<Button>(R.id.choose_btn)
        val take = bottomSheetDialog.findViewById<Button>(R.id.take_btn)
        val cancel = bottomSheetDialog.findViewById<Button>(R.id.btn_cancel)

        take?.setOnClickListener{
            //check permission at runtime
            val checkSelfPermission = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA)
            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED){
                //Requests permissions to be granted to this application at runtime
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.CAMERA), 1)
            } else{
                capturePhoto();
            }

            bottomSheetDialog.dismiss();
        }
        choose?.setOnClickListener{
            //check permission at runtime
            val checkSelfPermission = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED){
                //Requests permissions to be granted to this application at runtime
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            } else{
                openGallery()
            }

            bottomSheetDialog.dismiss();
        }
        cancel?.setOnClickListener{ bottomSheetDialog.dismiss(); }

        bottomSheetDialog.show()

        bottomSheetDialog.setOnDismissListener {
            // Instructions on bottomSheetDialog Dismiss
        }
    }

    private fun show(message: String) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }
    private fun capturePhoto(){
        val capturedImage = File(externalCacheDir, "My_Captured_Photo.jpg")
        if(capturedImage.exists()) {
            capturedImage.delete()
        }
        capturedImage.createNewFile()
        uri = if(Build.VERSION.SDK_INT >= 24){
            FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider",
                capturedImage)
        } else {
            Uri.fromFile(capturedImage)
        }

        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(intent, CAPTURE_PHOTO)
    }
    private fun openGallery(){
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent, CHOOSE_PHOTO)
    }
    private fun renderImage(imagePath: String?){
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            imageView.setImageBitmap(bitmap)
        }
        else {
            show("ImagePath is null")
        }
    }
    @SuppressLint("Range")
    private fun getImagePath(uri: Uri?, selection: String?): String {
        var path: String? = null
        val cursor = contentResolver.query(uri!!, null, selection, null, null )
        if (cursor != null){
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path!!
    }

    private fun handleImageOnKitkat(data: Intent?) {
        var imagePath: String? = null
        val uri = data!!.data
        //DocumentsContract defines the contract between a documents provider and the platform.
        if (DocumentsContract.isDocumentUri(this, uri)){
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri!!.authority){
                val id = docId.split(":")[1]
                val selsetion = MediaStore.Images.Media._ID + "=" + id
                imagePath = getImagePath(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    selsetion)
            }
            else if ("com.android.providers.downloads.documents" == uri.authority){
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse(
                        "content://downloads/public_downloads"), java.lang.Long.valueOf(docId))
                imagePath = getImagePath(contentUri, null)
            }
        }
        else if ("content".equals(uri!!.scheme, ignoreCase = true)){
            imagePath = getImagePath(uri, null)
        }
        else if ("file".equals(uri.scheme, ignoreCase = true)){
            imagePath = uri.path
        }
        renderImage(imagePath)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantedResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantedResults)
        when(requestCode){
            1 ->
                if (grantedResults.isNotEmpty() && grantedResults.get(0) ==
                    PackageManager.PERMISSION_GRANTED){
                    openGallery()
                }else {
                    show("Unfortunately You are Denied Permission to Perform this Operataion.")
                }
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            CAPTURE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    val bitmap = BitmapFactory.decodeStream(
                        getContentResolver().openInputStream(uri!!))
                    imageView.setImageBitmap(bitmap)
                }
            CHOOSE_PHOTO ->
                if (resultCode == Activity.RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitkat(data)
                    }
                }
        }
    }
}
