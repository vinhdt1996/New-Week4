package com.example.vinhtruong.ktlthdt.search

import com.example.vinhtruong.ktlthdt.model.TaxiBrand

class PresenterSearch : IPresenterSearch {

    private var mViewSearch: ViewSearch? = null
    private var mModelSearch: ModelSearch? = null

    constructor(viewSeach: ViewSearch){
        this.mViewSearch = viewSeach
        mModelSearch = ModelSearch()
    }

    override fun filterTaxi(query: String) {
        mModelSearch?.filterTaxi(query)
        mModelSearch?.setCallback(object : ModelSearch.ModelSearchCalllback{
            override fun onFilterQuerySuccess(results: ArrayList<TaxiBrand>) {
                mViewSearch?.onFilterTaxiSuccess(results)
            }
        })
    }
}