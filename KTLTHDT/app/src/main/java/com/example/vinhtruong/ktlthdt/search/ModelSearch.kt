package com.example.vinhtruong.ktlthdt.search

import android.util.Log
import com.example.vinhtruong.ktlthdt.model.TaxiBrand
import com.example.vinhtruong.ktlthdt.utils.Constant.Companion.REF_TAXI
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ModelSearch{
    private val mRootRef = FirebaseDatabase.getInstance().reference
    private val mTaxiRef = mRootRef.child(REF_TAXI)
    private val mResults = ArrayList<TaxiBrand>()
    private var mCallback: ModelSearchCalllback? = null

    fun setCallback(callback: ModelSearchCalllback){
        this.mCallback = callback
    }

    fun filterTaxi(query: String){
        mTaxiRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var found: Boolean?
                for (ds in dataSnapshot.getChildren()) {
                    val brand = ds.getValue(TaxiBrand::class.java!!)
                    found = brand!!.name.toLowerCase().trim().contains(query.toLowerCase())
                    if (found!!) {
                        mResults.add(brand)
                        Log.d("AAA", "Model search: " + mResults.size)
                        mCallback?.onFilterQuerySuccess(mResults)
                    }

                }
            }
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    interface ModelSearchCalllback{
        fun onFilterQuerySuccess(results: ArrayList<TaxiBrand>)
    }
}