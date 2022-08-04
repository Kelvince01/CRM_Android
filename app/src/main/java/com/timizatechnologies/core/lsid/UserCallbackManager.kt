package com.timizatechnologies.core.lsid

interface UserCallbackManager {
    fun addUserCallback(userActionCallback:UserActionCallback)
    fun removeUserCallback(userActionCallback:UserActionCallback)
}
