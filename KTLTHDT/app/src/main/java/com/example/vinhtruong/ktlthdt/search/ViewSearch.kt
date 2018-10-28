package com.example.vinhtruong.ktlthdt.search

import com.example.vinhtruong.ktlthdt.model.TaxiBrand

interface ViewSearch {
    fun onFilterTaxiSuccess(results: ArrayList<TaxiBrand>)
}