package com.example.vinhtruong.ktlthdt.route

import com.example.vinhtruong.ktlthdt.model.ResultAddress
import com.example.vinhtruong.ktlthdt.model.ResultRoute
import com.example.vinhtruong.ktlthdt.model.User
import com.example.vinhtruong.ktlthdt.utils.RetrofitUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PresenterRoute(private var view: ViewRoute) : IPresenerRoute {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mCurrentUser: FirebaseUser
    private lateinit var mModelRoute: ModelRoute

    //Instance of interface created for Retrofit API calls
    private val service by lazy {
        //Initializing Retrofit stuff
        RetrofitUtil.builderGoogleService()
    }

    /**
     * Service to get data of address from Google Map database
     * @param latlng : The location need to geocode
     */
    override fun startGetAddress(latlng: String) {
        view.showProgress(true)
        service.getAddress(latlng).enqueue(object : Callback<ResultAddress>{
            override fun onResponse(call: Call<ResultAddress>, response: Response<ResultAddress>) {
                var resultAddress: ResultAddress = response.body()!!
                view.showProgress(false)
                view.onGetAddressSuccess(resultAddress)
            }

            override fun onFailure(call: Call<ResultAddress>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    /**
     * Service to get data of route from Google Map database
     * @param org : The origin place
     * @param des : The destination place
     */
    override fun startGetRoute(org: String, des: String) {
        service.getRoute(org, des).enqueue(object : Callback<ResultRoute>{
            override fun onResponse(call: Call<ResultRoute>, response: Response<ResultRoute>) {
                var resultRoute: ResultRoute = response.body()!!
                view.showProgress(false)
                view.onGetRouteSuccess(resultRoute)
            }

            override fun onFailure(call: Call<ResultRoute>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    override fun getEmailUser() {
        mModelRoute = ModelRoute()
        mAuth = FirebaseAuth.getInstance()
        mCurrentUser = mAuth.currentUser!!
        mModelRoute.getEmailUser()
        mModelRoute!!.setCallback(object : ModelRoute.ModelRouteCallBack {
            override fun onGetUserSucces(user: User) {
                view.onGetEmailUserSuccess(user)
            }
        })
    }
}