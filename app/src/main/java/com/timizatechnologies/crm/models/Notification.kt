package com.timizatechnologies.crm.models

import java.util.*

class Notification {
    var title: String? = null
    var is_new: Boolean? = null
    var created_date: Date? = null
    var type: String? = null
    var description: String? = null
    var icon: String? = null

    constructor() {}

    constructor(
        title: String?,
        is_new: Boolean?,
        created_date: Date?,
        type: String?,
        description: String?,
        icon: String?
    ) {
        this.title = title
        this.is_new = is_new
        this.created_date = created_date
        this.type = type
        this.description = description
        this.icon = icon
    }

}
