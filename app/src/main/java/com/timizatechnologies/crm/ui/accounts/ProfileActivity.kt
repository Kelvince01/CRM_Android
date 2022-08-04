package com.timizatechnologies.crm.ui.accounts

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.facebook.drawee.view.SimpleDraweeView
import com.timizatechnologies.crm.R
import com.timizatechnologies.crm.accounts.callbacks.GetUserCallback
import com.timizatechnologies.crm.accounts.requests.UserRequest.makeUserRequest
import com.timizatechnologies.crm.models.User


class ProfileActivity : AppCompatActivity(), GetUserCallback.IGetUserResponse {
    private lateinit var mProfilePhotoView: SimpleDraweeView
    private lateinit var mName: TextView
    private lateinit var mId: TextView
    private lateinit var mEmail: TextView
    private lateinit var mPermissions: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        mProfilePhotoView = findViewById(R.id.profile_photo);
        mName = findViewById(R.id.name);
        mId = findViewById(R.id.id);
        mEmail = findViewById(R.id.email);
        mPermissions = findViewById(R.id.permissions);
    }

    override fun onResume() {
        super.onResume()
        makeUserRequest(GetUserCallback(this@ProfileActivity).callback)
    }

    override fun onCompleted(user: User) {
        mProfilePhotoView.setImageURI(user.picture);
        mName.setText(user.name);
        mId.setText(user.id);
        if (user.email == null) {
            mEmail.setText(R.string.no_email_perm);
            mEmail.setTextColor(Color.RED);
        } else {
            mEmail.setText(user.email);
            mEmail.setTextColor(Color.BLACK);
        }
        mPermissions.setText(user.permissions);
    }
}
