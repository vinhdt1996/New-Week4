package com.example.vinhtruong.ktlthdt.taxi

import android.util.Log
import com.example.vinhtruong.ktlthdt.model.Taxi
import com.example.vinhtruong.ktlthdt.utils.Constant
import com.google.firebase.database.*
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

class ModelTaxi {
    private val mRootRef = FirebaseDatabase.getInstance().reference
    private val m4SeaterRef = mRootRef.child(Constant.REF_TAXI_DETAILS).child(Constant.REF_4_SEATER)
    private val m7SeaterRef = mRootRef.child(Constant.REF_TAXI_DETAILS).child(Constant.REF_7_SEATER)
    private var mCallBack: ModelTaxiCallback? = null

    fun setCallback(callback: ModelTaxiCallback) {
        this.mCallBack = callback
    }

    fun getList4SeaterTaxi(){
        val list4SeaterTaxi = ArrayList<Taxi>()
        m4SeaterRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val taxi = snapshot.getValue<Taxi>(Taxi::class.java!!)
                    list4SeaterTaxi.add(taxi!!)
                    Log.d("taxi", "Model 4: " + list4SeaterTaxi.size + "")
                    mCallBack?.onGetList4SeatersSuccess(list4SeaterTaxi)

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }
    fun getList7SeaterTaxi(){
        val list7SeaterTaxi = ArrayList<Taxi>()
        m7SeaterRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val taxi = snapshot.getValue(Taxi::class.java)
                    list7SeaterTaxi.add(taxi!!)
                    Log.d("taxi", "Model 7: " + list7SeaterTaxi.size + "")
                    mCallBack?.onGetList7SeatersSuccess(list7SeaterTaxi)

                }
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    interface ModelTaxiCallback {
        fun onGetList4SeatersSuccess(taxi4ArrayList: ArrayList<Taxi>)
        fun onGetList7SeatersSuccess(taxi7ArrayList: ArrayList<Taxi>)
    }

}