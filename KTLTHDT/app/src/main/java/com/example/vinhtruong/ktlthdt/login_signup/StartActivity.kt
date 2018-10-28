package com.example.vinhtruong.ktlthdt.login_signup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.example.vinhtruong.ktlthdt.R
import com.example.vinhtruong.ktlthdt.adapter.ViewPagerLoginAdapter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login_signup.*
import com.example.vinhtruong.ktlthdt.route.RouteActivity

class StartActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_signup)
        mAuth = FirebaseAuth.getInstance()
        setupView()
        checkUserState()
    }

    /**
    * Checks user's state. If the user is logged in then send to RouteActivity
    */
    private fun checkUserState() {
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            val intent = Intent(this@StartActivity, RouteActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }

    /**
     * Sets up view
     */
    private fun setupView() {
        var viewpagerAdapter = ViewPagerLoginAdapter(supportFragmentManager)
        viewpagerLogin.adapter = viewpagerAdapter
        tabLayoutLogin.setupWithViewPager(viewpagerLogin)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        try {
            for (fragment in supportFragmentManager.fragments) {
                fragment.onActivityResult(requestCode, resultCode, data)
                Log.d("Activity", "ON RESULT CALLED")
            }
        } catch (e: Exception) {
            Log.d("ERROR", e.toString())
        }

    }
}
