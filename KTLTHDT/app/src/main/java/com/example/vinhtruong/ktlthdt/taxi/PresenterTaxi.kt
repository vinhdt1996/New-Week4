package com.example.vinhtruong.ktlthdt.taxi

import android.util.Log

import com.example.vinhtruong.ktlthdt.model.Taxi
import com.example.vinhtruong.ktlthdt.model.User
import com.google.firebase.auth.FirebaseAuth
import java.util.ArrayList

class PresenterTaxi : IPresenterTaxi{

    private var mModelTaxi: ModelTaxi? = null
    private var mViewTaxi: ViewTaxi? = null
    private var mAuth = FirebaseAuth.getInstance()
    private var mCurrentUser = mAuth.currentUser

    constructor(viewTaxi: ViewTaxi?){
        this.mViewTaxi = viewTaxi
        mModelTaxi = ModelTaxi()
    }

    override fun getListTaxi() {
        mModelTaxi?.getList4SeaterTaxi()
        mModelTaxi?.getList7SeaterTaxi()

        mModelTaxi?.setCallback(object : ModelTaxi.ModelTaxiCallback {
            override fun onGetList4SeatersSuccess(taxi4ArrayList: ArrayList<Taxi>) {
                Log.d("taxi", "Presenter 4: " + taxi4ArrayList.size)
                mViewTaxi?.onGetList4SeatersSuccess(taxi4ArrayList)
            }
            override fun onGetList7SeatersSuccess(taxi7ArrayList: ArrayList<Taxi>) {
                Log.d("taxi", "Presenter 7: " + taxi7ArrayList.size)
                mViewTaxi?.onGetList7SeatersSuccess(taxi7ArrayList)
            }
        })
    }



}