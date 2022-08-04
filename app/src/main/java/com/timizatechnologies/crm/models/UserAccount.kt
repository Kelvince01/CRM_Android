package com.timizatechnologies.crm.models

import java.util.*

class UserAccount {
    var name: String? = null
    var phone: Number? = null
    var email: String? = null
    var created_date: Date? = null
    var password: String? = null
    var photo: String? = null

    constructor() {}
    constructor(
        name: String?,
        phone: Number?,
        email: String?,
        created_date: Date?,
        password: String?,
        photo: String?
    ) {
        this.name = name
        this.phone = phone
        this.email = email
        this.created_date = created_date
        this.password = password
        this.photo = photo
    }
}
