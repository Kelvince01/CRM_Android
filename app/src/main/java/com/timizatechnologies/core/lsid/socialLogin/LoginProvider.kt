package com.timizatechnologies.core.lsid.socialLogin

//import androidx.appcompat.app.

interface LoginProvider {
    fun getButtonResId() : Int
    fun getProviderClientId() : String
    fun getLoginSocialInstance() : SocialLogin
    fun init(socialLogin: SocialLogin)
    fun isAvailable(): Boolean
}
