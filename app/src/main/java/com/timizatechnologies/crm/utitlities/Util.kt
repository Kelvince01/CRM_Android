package com.timizatechnologies.crm.utitlities

import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

object Util {
    fun MakePrettyDate(getCreatedTime: String?): String {
        val formatter =
            SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ", Locale.US)
        val pos = ParsePosition(0)
        val then = formatter.parse(getCreatedTime, pos).time
        val now = Date().time
        val seconds = (now - then) / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        var friendly: String? = null
        var num: Long = 0
        if (days > 0) {
            num = days
            friendly = "$days day"
        } else if (hours > 0) {
            num = hours
            friendly = "$hours hour"
        } else if (minutes > 0) {
            num = minutes
            friendly = "$minutes minute"
        } else {
            num = seconds
            friendly = "$seconds second"
        }
        if (num > 1) {
            friendly += "s"
        }
        return "$friendly ago"
    }
}
