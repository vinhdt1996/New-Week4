package com.example.vinhtruong.ktlthdt.taxi

import com.example.vinhtruong.ktlthdt.model.Taxi
import com.example.vinhtruong.ktlthdt.model.User
import java.util.ArrayList

interface ViewTaxi {
    abstract fun onGetList4SeatersSuccess(taxi4ArrayList: ArrayList<Taxi>)
    abstract fun onGetList7SeatersSuccess(taxi7ArrayList: ArrayList<Taxi>)
    abstract fun onGetUserSuccess(user: User)
}