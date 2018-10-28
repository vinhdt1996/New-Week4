package com.example.vinhtruong.ktlthdt.login_signup.login

import com.facebook.AccessToken

interface IPresenterLogin {
    fun handleFacebookAccessToken(token: AccessToken)

    fun handleLoginEmail(email: String, password: String)
}