package com.example.vinhtruong.ktlthdt.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.vinhtruong.ktlthdt.R
import com.example.vinhtruong.ktlthdt.adapter.viewholder.ViewHolderSearch
import com.example.vinhtruong.ktlthdt.model.TaxiBrand

class AdapterRecyclerSearch(private var searchResults: ArrayList<TaxiBrand>,
                            private var context: Context)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.custom_row_search,parent,false)
        return ViewHolderSearch(view)
    }

    override fun getItemCount(): Int = searchResults.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var taxiBrand = searchResults[position]
        val viewHolderSearch = holder as ViewHolderSearch
        viewHolderSearch.bindTaxi(taxiBrand, context)
    }
}