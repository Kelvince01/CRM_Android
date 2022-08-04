package com.timizatechnologies.crm.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
class Product(
    var id: String? = null,
    var name: String? = null,
    var price: Double? = null,
    var created_date: Timestamp? = null,
    var update_date: Timestamp? = null,
    var description: String? = null,
    var photo: String? = null
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "name" to name,
            "price" to price,
            "description" to description,
            "created_date" to created_date,
            "update_date" to update_date,
            "photo" to photo,
        )
    }
}
