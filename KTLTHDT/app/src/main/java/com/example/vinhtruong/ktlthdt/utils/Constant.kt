package com.example.vinhtruong.ktlthdt.utils

import android.app.Application

class Constant {
    companion object {
        const val MSG_EMPTY_FIELD = "Please enter all required fields"
        const val MSG_LOGIN_SUCCESS = "You have successfully logged in"
        const val LOGIN_FAILED = "Login Failed"
//USER TYPES
        const val EMAIL_USER = "Email"
        const val FACEBOOK_USER = "Facebook"
        const val GUEST_USER = "Guest"
//USER FIELDS
        const val DEFAULT_USER_ID = "-1"
        const val DEFAULT_USER_NAME = "Login now"
        const val DEFAULT_USER_EMAIL = "Guest"
        const val DEFAULT_USER_IMAGE = "NONE"
//REFERENCES
        const val REF_USERS = "Users"
        const val REF_TAXI = "Taxi"
        const val REF_TAXI_DETAILS = "TaxiDetails"
        const val REF_4_SEATER = "4seats"
        const val REF_7_SEATER = "7seats"
//USER REFERENCES
        const val REF_USER_NAME = "name"
        const val REF_USER_IMAGE = "image"
        const val REF_USER_DEVICE_TOKEN = "deviceToken"
        const val REF_USER_EMAIL = "email"
        const val REF_USER_PASSWORD = "password"
        const val REF_USER_TYPE = "userType"
//TAXI ID
        const val ID_VINA_TAXI = 1
        const val ID_SAIGON_TOURIST = 2
        const val ID_MAI_LINH_TAXI = 3
        const val ID_TAXI_VINASUN = 4
        const val ID_TAXI_AIRPORT = 5
        const val ID_TAXI_FESTIVAL = 6
        const val ID_TAXI_HAPPY = 7
        const val ID_TAXI_FUTURE = 8
        const val ID_TAXI_SAVICO = 9
        const val ID_STAR_TAXI = 10
//TAXI TYPE
        const val TYPE_4_SEATER = 4
        const val TYPE_7_SEATER = 7

        const val PERMISSION_ALL = 1
        const val DEFAULT_ZOOM = 15
        const val PLACE_PICKER_REQUEST = 1
        const val PLACE_AUTOCOMPLETE_REQUEST_CODE = 2


         const val MY_PERMISSIONS_REQUEST_LOCATION = 99
         const val INITIAL_STROKE_WIDTH_PX = 5
         const val LEFT = 25
         const val RIGHT = 25
         const val TOP = 25
         const val BOTTOM = 325
         const val RADIUS_LARGE = 100.0
         const val STROKE_WIDTH = 1f
    }
}