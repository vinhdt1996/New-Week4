package com.example.vinhtruong.ktlthdt.route

import com.example.vinhtruong.ktlthdt.model.ResultAddress
import com.example.vinhtruong.ktlthdt.model.ResultRoute
import com.example.vinhtruong.ktlthdt.model.User

interface ViewRoute {

    fun onGetEmailUserSuccess(user: User)

    fun showProgress(isShow: Boolean)

    fun showError(message: String)

    fun onGetRouteSuccess(result: ResultRoute)

    fun onGetAddressSuccess(result: ResultAddress)
}