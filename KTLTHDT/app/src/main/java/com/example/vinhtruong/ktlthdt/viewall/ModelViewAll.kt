package com.example.vinhtruong.ktlthdt.viewall

import com.example.vinhtruong.ktlthdt.model.Taxi
import com.example.vinhtruong.ktlthdt.utils.Constant
import com.example.vinhtruong.ktlthdt.utils.Constant.Companion.REF_4_SEATER
import com.example.vinhtruong.ktlthdt.utils.Constant.Companion.REF_7_SEATER
import com.example.vinhtruong.ktlthdt.utils.Constant.Companion.REF_TAXI_DETAILS
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ModelViewAll {
    private val mRootRef = FirebaseDatabase.getInstance().reference
    private val m4SeaterRef = mRootRef.child(REF_TAXI_DETAILS).child(REF_4_SEATER)
    private val m7SeaterRef = mRootRef.child(REF_TAXI_DETAILS).child(REF_7_SEATER)
    private var mListTaxi: ArrayList<Taxi> = ArrayList()
    private var mCallback4: ModelViewAll4SeatersCallBack? = null
    private var mCallback7: ModelViewAll7SeatersCallBack? = null

    fun setCallBack4(callBack: ModelViewAll4SeatersCallBack) {
        this.mCallback4 = callBack
    }

    fun setCallBack7(callBack: ModelViewAll7SeatersCallBack) {
        this.mCallback7 = callBack
    }

    fun fetch4SeaterTaxi(){
        m4SeaterRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val taxi = snapshot.getValue(Taxi::class.java)
                    mListTaxi.add(taxi!!)
                    mCallback4?.onFetch4SeaterTaxiSuccess(mListTaxi)
                }
            }
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    fun fetch7SeaterTaxi(){
        m7SeaterRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val taxi = snapshot.getValue(Taxi::class.java)
                    mListTaxi.add(taxi!!)
                    mCallback7?.onFetch7SeaterTaxiSuccess(mListTaxi)
                }
            }
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    interface ModelViewAll4SeatersCallBack {
        fun onFetch4SeaterTaxiSuccess(listTaxi: ArrayList<Taxi>)
    }

    interface ModelViewAll7SeatersCallBack {
        fun onFetch7SeaterTaxiSuccess(listTaxi: ArrayList<Taxi>)
    }
}