package com.timizatechnologies.crm.models

import android.net.Uri
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var picture: Uri,
    var name: String,
    var id: String,
    var email: String,
    var permissions: String
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "name" to name,
            "email" to email,
            "permissions" to permissions,
            "picture" to picture,
        )
    }
}
