package com.example.vinhtruong.ktlthdt.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.vinhtruong.ktlthdt.R
import com.example.vinhtruong.ktlthdt.adapter.viewholder.ViewHolderListTaxi
import com.example.vinhtruong.ktlthdt.model.Taxi
import java.util.ArrayList

class AdapterRecyclerTaxi(private var taxiArrayList: ArrayList<Taxi>,
                          private var context: Context,
                          private var distance: Double?,
                          private var isLimit: Boolean)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.custom_row_taxi, parent, false)
        return ViewHolderListTaxi(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val taxi = taxiArrayList[position]
        val myViewHolder = holder as ViewHolderListTaxi
        val cost = cacuclateMoney(taxi.first4, taxi.after4)
        myViewHolder.bindTaxi(taxi, cost, context)
    }

    private fun cacuclateMoney(first4: Double, after4: Double): Double {
        var cost = 0.0

        cost = when {
            distance!! <= 1 -> first4
            distance!! <= 4 -> distance!! * first4
            else -> 4 * first4 + (distance!! - 4) * after4
        }
        return cost
    }

    override fun getItemCount(): Int {
        return if (isLimit)
            3
        else
            taxiArrayList.size
    }
}