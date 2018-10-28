package com.example.vinhtruong.ktlthdt.login_signup.signup

import android.util.Log
import com.example.vinhtruong.ktlthdt.utils.Constant
import com.example.vinhtruong.ktlthdt.utils.Constant.Companion.MSG_EMPTY_FIELD
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private const val ERROR_EMAIL_ALREADY_IN_USE = "ERROR_EMAIL_ALREADY_IN_USE"
private const val ERROR_INVALID_EMAIL = "ERROR_INVALID_EMAIL"
private const val ERROR_WEAK_PASSWORD = "ERROR_WEAK_PASSWORD"
private const val MSG_EMAIL_EXISTS = "The email address is already in use by another account"
private const val MSG_EMAIL_BADLY_FORMATTED = "The email address is badly formatted"
private const val MSG_PASSWORD_INVALID = "Password should be at least 6 characters"
private const val MSG_SIGN_UP_SUCCESS = "You have successfully signed up"

/**
 * Listens to user actions from the UI (FragmentSignup), retrieves the data and updates
 * the UI as required.
 */
class PresenterSignup(private var view: ViewSignup) : IPresenterSignup {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mRootRef : DatabaseReference
    private lateinit var mUsersRef: DatabaseReference

    /**
     * Handle when user signs up account with email
     * @param userName user's sign-up display name
     * @param email user's sign-up email
     * @param password user's sign-up password
     */
    override fun handleSignupWithEmail(userName: String, email: String, password: String) {

        mAuth = FirebaseAuth.getInstance()
        mRootRef = FirebaseDatabase.getInstance().reference
        mUsersRef = mRootRef.child(Constant.REF_USERS)

        if(isFieldEmpty(userName, email, password)){
            // If sign-up field empty, show a message to the user
            view.onRegisterUserFail(MSG_EMPTY_FIELD)
        }else{
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if(it.isSuccessful){
                    val user: FirebaseUser = mAuth.currentUser!!
                    val uid: String = user!!.uid
                    val userRef = mUsersRef.child(uid)
                    val userHashMap =  HashMap<String,String>()
                    userHashMap[Constant.REF_USER_NAME] = userName
                    userHashMap[Constant.REF_USER_IMAGE] = Constant.DEFAULT_USER_IMAGE
                    userHashMap[Constant.REF_USER_EMAIL] = email
                    userHashMap[Constant.REF_USER_PASSWORD] = password
                    userHashMap[Constant.REF_USER_TYPE] = Constant.EMAIL_USER
                    userRef.setValue(userHashMap).addOnCompleteListener{
                        if(it.isSuccessful){
                            // Sign up success, display a message to the user.
                            view.onRegisterUserSuccess(MSG_SIGN_UP_SUCCESS)
                        }
                    }
                } else {
                    // If sign up fails, handle error
                    handleAuthenticationException(it.exception!!)
                }
            }
        }
    }

    /**
     * Handle error and display a message to the user if account registration fails
     */
    private fun handleAuthenticationException(exception: Exception) {
        if(exception is FirebaseAuthException){
            when(exception.errorCode){
                ERROR_EMAIL_ALREADY_IN_USE -> view.onRegisterUserFail(MSG_EMAIL_EXISTS)
                ERROR_INVALID_EMAIL -> view.onRegisterUserFail(MSG_EMAIL_BADLY_FORMATTED)
                ERROR_WEAK_PASSWORD -> view.onRegisterUserFail(MSG_PASSWORD_INVALID)
            }
        }
    }

    /**
     * Check whether the sign-up field is entered
     * @param name user's sign-up display name
     * @param email user's sign-up email
     * @param password user's sign-up password
     */
    private fun isFieldEmpty(name: String, email: String, password: String): Boolean{
        if(name.trim() == "" || email.trim() == "" || password.trim() == ""){
            return true
        }
        return false
    }
}