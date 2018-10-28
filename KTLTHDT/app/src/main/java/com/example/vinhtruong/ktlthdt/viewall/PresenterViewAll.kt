package com.example.vinhtruong.ktlthdt.viewall

import com.example.vinhtruong.ktlthdt.model.Taxi

class PresenterViewAll : IPresenterViewAll {

    private var mModelViewAll: ModelViewAll? = null
    private var mViewViewAll: ViewViewAll? = null

    constructor(viewViewAll: ViewViewAll){
        this.mViewViewAll = viewViewAll
        mModelViewAll = ModelViewAll()
    }

    override fun fetch4SeaterTaxi() {
        mModelViewAll?.fetch4SeaterTaxi()
        mModelViewAll?.setCallBack4(object : ModelViewAll.ModelViewAll4SeatersCallBack{
            override fun onFetch4SeaterTaxiSuccess(listTaxi: ArrayList<Taxi>) {
                mViewViewAll?.onFetch4SeaterTaxiSuccess(listTaxi)
            }
        })
    }

    override fun fetch7SeaterTaxi() {
        mModelViewAll?.fetch7SeaterTaxi()
        mModelViewAll?.setCallBack7(object : ModelViewAll.ModelViewAll7SeatersCallBack{
            override fun onFetch7SeaterTaxiSuccess(listTaxi: ArrayList<Taxi>) {
                mViewViewAll?.onFetch7SeaterTaxiSuccess(listTaxi)
            }
        })
    }
}