package com.timizatechnologies.crm.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Customer(
    var id: String? = null,
    var name: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var gender: String? = null,
    var city: String? = null,
    var address: String? = null,
    var created_date: Timestamp? = null,
    var update_date: Timestamp? = null,
    var photo: String? = null
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "name" to name,
            "email" to email,
            "phone" to phone,
            "gender" to gender,
            "city" to city,
            "address" to address,
            "created_date" to created_date,
            "update_date" to update_date,
            "photo" to photo,
        )
    }
}
