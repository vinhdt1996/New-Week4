package com.example.vinhtruong.ktlthdt.details

import com.example.vinhtruong.ktlthdt.model.Taxi

class PresenterDetails : IPresenterDetails{

    private var mViewDetails: ViewDetails? = null
    private var mModelDetails: ModelDetails? = null

    constructor(viewDetails: ViewDetails){
        this.mViewDetails = viewDetails
        mModelDetails = ModelDetails()
    }

    override fun getTaxiDetails(taxiType: Int, taxiId: Int, distance: Double) {
        mModelDetails?.getTaxiDetails(taxiType,taxiId)

        mModelDetails?.setCallback(object : ModelDetails.ModelDetailsCallback{
            override fun onGetTaxiDetailsSuccess(taxi: Taxi) {
                val first4km = taxi.first4
                val after4km = taxi.after4
                val cost = cacuclateMoney(first4km!!, after4km!!, distance)
                mViewDetails?.onFetchTaxiDetailsSuccess(taxi, cost)
            }
        })
    }
    private fun cacuclateMoney(first4: Double, after4: Double, distance: Double): Double {
        return when {
            distance <= 1 -> first4
            distance <= 4 -> distance * first4
            else -> 4 * first4 + (distance - 4) * after4
        }
    }
}