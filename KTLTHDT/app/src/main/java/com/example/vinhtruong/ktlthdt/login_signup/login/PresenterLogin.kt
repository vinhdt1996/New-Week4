package com.example.vinhtruong.ktlthdt.login_signup.login

import android.util.Log
import com.example.vinhtruong.ktlthdt.utils.Message
import com.example.vinhtruong.ktlthdt.utils.Constant
import com.facebook.AccessToken
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId

//  Listens to user actions from the UI (FragmentLogin), retrieves the data and updates
//  the UI as required.
class PresenterLogin(private var view: ViewLogin) : IPresenterLogin {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var mRootRef: DatabaseReference
    private lateinit var mUsersRef: DatabaseReference


//    (2) Xử lý đăng nhập
//    1. Nhấn nút đăng nhập thì thực hiện xử lý.
    override fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI
                        view.onLoginSuccess()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d("loginfb", "signInWithCredential:failure", task.exception)
                        view.onLoginFail(Message.MSG_LOGIN_FB_FAIL)
                    }
                }
    }

//    Handle when user login account with Email
//     (2) Xử lý đăng nhập
//    1. Nhấn nút đăng nhập thì thực hiện xử lý.
//    2. Xử lý check
//    a. Check hạng mục
        override fun handleLoginEmail(email: String, password: String) {
        mRootRef = FirebaseDatabase.getInstance().reference
        mUsersRef = mRootRef.child(Constant.REF_USERS)
//    a. Check hạng mục 2.1 2.2
        if(isFieldEmpty(email, password)){
            // If sign-up field empty, show a message to the user
            view.onLoginFail(Message.MSG_EMPTY_FIELD)
        }else{
//            3.Hàm sign in của Firebase
//            3.1  Input
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if(it.isSuccessful){
                    val uid = mAuth.currentUser!!.uid
                    val deviceToken = FirebaseInstanceId.getInstance().token
                    val userRef = mUsersRef.child(uid)
                    userRef.child(Constant.REF_USER_DEVICE_TOKEN).setValue(deviceToken)
                            .addOnCompleteListener{
                        // Sign in success, update UI
                        view.onLoginSuccess()
                    }
                }else{
//                    3.2 Output
                    handleAuthenticationException(it.exception!!)
                }
            }
        }

    }

    //    a. Check hạng mục 2.1 2.2 2.3
    private fun handleAuthenticationException(exception: Exception) {
        if(exception is FirebaseAuthException){
            when(exception.errorCode){
                Message.ERROR_INVALID_EMAIL -> view.onLoginFail(Message.MSG_INVALID_EMAIL)
                Message.ERROR_USER_NOT_FOUND -> view.onLoginFail(Message.MSG_USER_NOT_FOUND)
                Message.ERROR_WRONG_PASSWORD -> view.onLoginFail(Message.MSG_WRONG_PASSWORD)
            }

        }
    }
    /**
     * Check whether the login field is entered
     * @param email user's login email
     * @param password user's login password
     */
    private fun isFieldEmpty(email: String, password: String): Boolean{
        if(email.trim() == "" || password.trim() == ""){
            return true
        }
        return false
    }


}