package com.timizatechnologies.core.config

import java.util.ArrayList

interface App {
    fun getAppIconDisabled() : MutableCollection<Boolean>
    //fun getCalenderRange() : MutableValueProvider<Int>
    fun getCalenderRange() : MutableCollection<Int>
    fun getDefaultLocale() : String
    fun getDefaultLocaleTTS(): String
    fun getDialogRemote(): String
    fun getGeoIp(): String
    fun getLanguage(): String
    fun getLanguageId(): Int
    fun getLeagueListInfoBoxUrl(): String
    fun getMidnightRefreshInterval(): Int
    fun getMyTeamsUpcomingDaysOffset(): Int
    fun getName() : String
    fun getPushNotificationTypes() : Map<String, List<Int>>
    fun getSearchMinQueryLength() : Int
    fun getStorageDirectory(): String
    fun getStorageDirectoryImage(): String
    fun setGeoIp()
}
