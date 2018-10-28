package com.example.vinhtruong.ktlthdt.adapter.viewholder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.vinhtruong.ktlthdt.R
import com.example.vinhtruong.ktlthdt.model.Taxi
import kotlinx.android.synthetic.main.custom_row_search.view.*
import kotlinx.android.synthetic.main.custom_row_taxi.view.*
import java.text.DecimalFormat

class ViewHolderListTaxi(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bindTaxi(taxi: Taxi, cost: Double, context: Context) {
        itemView.txtNametaxi.text = taxi.name
        itemView.txtPhonetaxi.text = taxi.phone

        val decimalFormat = DecimalFormat("###,###")
        itemView.txtCost.text = decimalFormat.format(cost)

        Glide.with(context).load(taxi.image)
                .apply(RequestOptions().placeholder(R.drawable.avatar_placeholder))
                .into(itemView.imgLogo)
    }
}