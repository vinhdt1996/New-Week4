package com.example.vinhtruong.ktlthdt.login_signup.signup

interface IPresenterSignup {
    fun handleSignupWithEmail(email: String, password: String, userName: String)
}