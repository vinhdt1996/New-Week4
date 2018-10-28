package com.example.vinhtruong.ktlthdt.details

import android.util.Log
import com.example.vinhtruong.ktlthdt.model.Taxi
import com.example.vinhtruong.ktlthdt.utils.Constant
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ModelDetails{
    private val mRootRef = FirebaseDatabase.getInstance().reference
    private val m4SeaterRef = mRootRef.child(Constant.REF_TAXI_DETAILS).child(Constant.REF_4_SEATER)
    private val m7SeaterRef = mRootRef.child(Constant.REF_TAXI_DETAILS).child(Constant.REF_7_SEATER)
    private var mTaxi: Taxi? = null
    private var mCallback: ModelDetailsCallback? = null

    fun setCallback(callback: ModelDetailsCallback) {
        this.mCallback = callback
    }

    fun getTaxiDetails(taxiType: Int, taxiId: Int){
        val sTaxiId = taxiId.toString()
        if (taxiType == 4) {
            m4SeaterRef.child(sTaxiId).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    mTaxi = dataSnapshot.getValue<Taxi>(Taxi::class.java!!)
                    mCallback?.onGetTaxiDetailsSuccess(mTaxi!!)
                }
                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
        } else if (taxiType == 7) {
            m7SeaterRef.child(sTaxiId).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    mTaxi = dataSnapshot.getValue<Taxi>(Taxi::class.java!!)
                    mCallback?.onGetTaxiDetailsSuccess(mTaxi!!)
                }
                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
        }
    }

    interface ModelDetailsCallback {
        fun onGetTaxiDetailsSuccess(taxi: Taxi)
    }
}