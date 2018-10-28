package com.example.vinhtruong.ktlthdt.route

import com.example.vinhtruong.ktlthdt.model.ResultAddress
import com.example.vinhtruong.ktlthdt.model.ResultRoute


interface RouteContract {

    interface View {

        fun showProgress(isShow: Boolean)

        fun showError(message: String)

        fun setPresenter(presenter: RouteContract.Presenter)

        fun onGetRouteSuccess(result: ResultRoute)

        fun onGetAddressSuccess(result: ResultAddress)
    }

    interface Presenter {

        fun startGetRoute(org: String, des: String)

        fun startGetAddress(latlng: String)
    }
}