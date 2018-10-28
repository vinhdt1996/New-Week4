package com.example.vinhtruong.ktlthdt.route

import com.example.vinhtruong.ktlthdt.R.string.email
import com.example.vinhtruong.ktlthdt.R.string.password
import com.example.vinhtruong.ktlthdt.model.User
import com.example.vinhtruong.ktlthdt.utils.Constant
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserInfo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ModelRoute{
    private val mRootRef = FirebaseDatabase.getInstance().reference
    private val mUsersRef = mRootRef.child(Constant.REF_USERS)
    private val mAuth = FirebaseAuth.getInstance()
    private val mCurrentUser = mAuth.currentUser
    private var callback: ModelRouteCallBack? = null

    fun setCallback(callback: ModelRouteCallBack) {
        this.callback = callback
    }

    fun getEmailUser(){
        var user: User? = null
        var uid = mCurrentUser!!.uid
        var name =""
        var email  = ""
        var image  = ""
        var password  = ""
        mUsersRef.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                uid = mCurrentUser!!.uid
                email = mCurrentUser.email!!
                name = dataSnapshot.child("name").value!!.toString()
                image = dataSnapshot.child("image").value!!.toString()
                password = dataSnapshot.child("password").value!!.toString()
                user = User(uid, name, email, password, image, Constant.EMAIL_USER)
                callback?.onGetUserSucces(user!!)
            }
            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }
    interface ModelRouteCallBack {
        fun onGetUserSucces(user: User)
    }
}