package com.example.vinhtruong.ktlthdt.viewall

import com.example.vinhtruong.ktlthdt.model.Taxi

interface ViewViewAll {
    fun onFetch4SeaterTaxiSuccess(listTaxi: ArrayList<Taxi>)
    fun onFetch7SeaterTaxiSuccess(listTaxi: ArrayList<Taxi>)
}