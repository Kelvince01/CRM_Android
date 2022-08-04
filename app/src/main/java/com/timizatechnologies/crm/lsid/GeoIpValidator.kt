package com.timizatechnologies.crm.lsid

import com.timizatechnologies.crm.geoip.GeoIpProvider

class GeoIpValidator {
    lateinit var geoIpProvider: GeoIpProvider
    lateinit var user: User

    constructor(geoIpProvider: GeoIpProvider, user: User) {
        this.geoIpProvider = geoIpProvider
        this.user = user
    }

    fun addressConfirmationRequired(set:Set<String>, set2:Set<String>) : Boolean {
        var str:String = this.geoIpProvider.get()
        return this.user.getVerifiedCountry() == null &&
                set.contains(str) &&
                !set2.contains(str)
    }

    fun canBuyOrPlay(set:Set<String>, set2:Set<String>) : Boolean {
        var str:String = this.geoIpProvider.get()
        //return (set2.contains(str) || )
        return true
    }
}
