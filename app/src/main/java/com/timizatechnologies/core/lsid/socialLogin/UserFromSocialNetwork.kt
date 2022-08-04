package com.timizatechnologies.core.lsid.socialLogin

final class UserFromSocialNetwork {
    private final lateinit var email: String
    private final lateinit var nickname: String
    private final lateinit var providerString: String
    private final lateinit var token: String
    private final lateinit var userId: String

    constructor(email: String, nickname: String,providerString: String,token: String, userId: String) {
        this.email = email
        this.nickname = nickname
        this.providerString = providerString
        this.token = token
        this.userId = userId
    }

    override fun toString(): String {
        return "UserFromSocialNetwork(email='$email', nickname='$nickname', providerString='$providerString', token='$token', userId='$userId')"
    }
}
