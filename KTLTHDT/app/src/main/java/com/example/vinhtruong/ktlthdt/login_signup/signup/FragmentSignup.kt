package com.example.vinhtruong.ktlthdt.login_signup.signup

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.vinhtruong.ktlthdt.R
import kotlinx.android.synthetic.main.fragment_signup.*
import kotlinx.android.synthetic.main.fragment_signup.view.*

/**
 * Main UI for the sign-up screen.
 */
class FragmentSignup : Fragment(), ViewSignup, View.OnClickListener, View.OnFocusChangeListener {

    private lateinit var mPresenterSignup: PresenterSignup

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_signup,container,false)

        mPresenterSignup = PresenterSignup(this)

        mView.btnSignupwithemail.setOnClickListener(this)

        mView.edtUsernamesignup.onFocusChangeListener = this

        mView.edtEmailsignup.onFocusChangeListener = this

        mView.edtPasssignup.onFocusChangeListener = this

        return mView
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnSignupwithemail -> {
                var userName =edtUsernamesignup.text.toString().trim()
                var email = edtEmailsignup.text.toString().trim()
                var password = edtPasssignup.text.toString().trim()
                mPresenterSignup.handleSignupWithEmail(userName, email, password)
            }
        }
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        when(v?.id){
            R.id.edtUsernamesignup -> {
                edtUsernamesignup.setText(edtUsernamesignup.text.toString().trim())
            }
            R.id.edtEmailsignup -> {
                edtEmailsignup.setText(edtEmailsignup.text.toString().trim())
            }
            R.id.edtPasssignup -> {
                edtPasssignup.setText(edtPasssignup.text.toString().trim())
            }
        }
    }

    /**
     * Display message after registration failed
     */
    override fun onRegisterUserFail(msg: String) {
        Toast.makeText(activity,msg,Toast.LENGTH_SHORT).show()
    }
    /**
     * Display message after successful registration
     */
    override fun onRegisterUserSuccess(msg: String) {
        Toast.makeText(activity,msg,Toast.LENGTH_SHORT).show()
    }
}