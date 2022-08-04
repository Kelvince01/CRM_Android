package com.timizatechnologies.crm.accounts.callbacks

import com.facebook.GraphRequest
import com.facebook.GraphResponse


class PermissionCallback(caller: IPermissionResponse) {
    interface IPermissionResponse {
        fun onCompleted(response: GraphResponse?)
    }

    private val mCallback: GraphRequest.Callback
    val callback: GraphRequest.Callback
        get() = mCallback

    init {
        mCallback = object : GraphRequest.Callback {
            // Handled by PermissionsActivity
            override fun onCompleted(response: GraphResponse) {
                caller.onCompleted(response)
            }
        }
    }
}
