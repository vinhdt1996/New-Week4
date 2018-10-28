package com.example.vinhtruong.ktlthdt.adapter.viewholder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.vinhtruong.ktlthdt.R
import com.example.vinhtruong.ktlthdt.model.TaxiBrand
import kotlinx.android.synthetic.main.custom_row_search.view.*
import kotlinx.android.synthetic.main.header_navigationview.view.*

class ViewHolderSearch(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindTaxi(taxiBrand: TaxiBrand, context: Context){
        itemView.txtNameSearch.text = taxiBrand.name
        itemView.txtPhoneSearch.text = taxiBrand.phone

        Glide.with(context).load(taxiBrand.image)
                .apply(RequestOptions().placeholder(R.drawable.avatar_placeholder))
                .into(itemView.imgSearch)
    }
}