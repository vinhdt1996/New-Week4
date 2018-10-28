package com.example.vinhtruong.ktlthdt.route

interface IPresenerRoute {
    fun startGetRoute(org: String, des: String)

    fun startGetAddress(latlng: String)

    fun getEmailUser()
}