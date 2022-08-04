package com.timizatechnologies.core.lsid.socialLogin

import android.content.Intent

interface SocialLogin {
    fun login()
    fun logout()
    fun processActivityResult(i:Int, i2:Int, intent: Intent)
}
