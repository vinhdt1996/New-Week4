package com.example.vinhtruong.ktlthdt.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.vinhtruong.ktlthdt.login_signup.login.FragmentLogin
import com.example.vinhtruong.ktlthdt.login_signup.signup.FragmentSignup

class ViewPagerLoginAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position){
            0 -> FragmentLogin()
            1 -> FragmentSignup()
            else -> null
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Login"
            1 -> "Sign up"
            else -> null
        }
    }

    override fun getCount(): Int = 2
}