package com.example.vinhtruong.ktlthdt.login_signup.login

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.vinhtruong.ktlthdt.utils.Constant.Companion.REF_USERS
import com.example.vinhtruong.ktlthdt.R
import com.example.vinhtruong.ktlthdt.route.RouteActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import java.util.*

/**
 * Main UI for the login screen.
 */
class FragmentLogin : Fragment(), View.OnClickListener, ViewLogin, View.OnFocusChangeListener {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mRootRef: DatabaseReference
    private lateinit var mUsersRef: DatabaseReference
    private lateinit var mCallBackManager: CallbackManager
    private lateinit var mPresenter: PresenterLogin

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_login,container,false)

        mAuth = FirebaseAuth.getInstance()
        mRootRef = FirebaseDatabase.getInstance().reference
        mUsersRef = mRootRef.child(REF_USERS)
        mCallBackManager = CallbackManager.Factory.create()
        mPresenter = PresenterLogin(this)

        mView.btnFbLogin.setOnClickListener(this)

        mView.btnEmailLogin.setOnClickListener(this)

        mView.txtLoginGuest.setOnClickListener(this)

        mView.txtLoginGuest.setOnClickListener(this)

        mView.edtEmaillogin.onFocusChangeListener = this

        mView.edtPasslogin.onFocusChangeListener = this

        registerCallbackLoginManager()

        return mView
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnFbLogin -> LoginManager.getInstance().logInWithReadPermissions(activity!!,
                    Arrays.asList("email", "public_profile"))
            R.id.btnEmailLogin -> {
                val email = edtEmaillogin.text.toString().trim()
                val password = edtPasslogin.text.toString().trim()
                mPresenter.handleLoginEmail(email,password)
            }
            R.id.txtLoginGuest -> sendToRoute()
        }
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        when(v?.id){
            R.id.edtEmaillogin -> {
                edtEmaillogin.setText(edtEmaillogin.text.toString().trim())
            }
            R.id.edtPasslogin -> {
                edtPasslogin.setText(edtPasslogin.text.toString().trim())
            }
        }
    }


//      Register callback for Facebook Login Manager
    private fun registerCallbackLoginManager() {
        LoginManager.getInstance().registerCallback(mCallBackManager,
                object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d("loginfb", "facebook:onSuccess:$loginResult")
                mPresenter.handleFacebookAccessToken(loginResult.accessToken)
            }
            override fun onCancel() {
                Log.d("loginfb", "facebook:onCancel")
            }
            override fun onError(error: FacebookException) {
                Log.d("loginfb", "facebook:onError", error)
            }
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mCallBackManager.onActivityResult(requestCode, resultCode, data)
    }



//     4. Di chuyển sang màn hình Map
    private fun sendToRoute() {
        val intent = Intent(activity, RouteActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

//      Display message after login failed
    override fun onLoginFail(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }


//      Handle after successful login
    override fun onLoginSuccess() {

        sendToRoute()
    }
}