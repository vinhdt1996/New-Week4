package com.example.vinhtruong.ktlthdt.details

import com.example.vinhtruong.ktlthdt.model.Taxi

interface ViewDetails {
    fun onFetchTaxiDetailsSuccess(taxi: Taxi, cost: Double)
}