package com.example.vinhtruong.ktlthdt.viewall

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.example.vinhtruong.ktlthdt.R
import com.example.vinhtruong.ktlthdt.adapter.AdapterRecyclerTaxi
import com.example.vinhtruong.ktlthdt.details.DetailsActivity
import com.example.vinhtruong.ktlthdt.model.Taxi
import com.example.vinhtruong.ktlthdt.route.RouteActivity
import com.example.vinhtruong.ktlthdt.utils.Constant
import com.example.vinhtruong.ktlthdt.utils.Constant.Companion.TYPE_4_SEATER
import com.example.vinhtruong.ktlthdt.utils.Constant.Companion.TYPE_7_SEATER
import com.example.vinhtruong.ktlthdt.utils.ItemClickSupport
import kotlinx.android.synthetic.main.activity_view_all.*

class ViewAllActivity : AppCompatActivity(), ViewViewAll {

    private var mPresenterViewAll: PresenterViewAll? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all)

        mPresenterViewAll = PresenterViewAll(this)
        setupView()
        val taxiType = intent.getIntExtra("type", 0)
        if (taxiType == 4)
            mPresenterViewAll?.fetch4SeaterTaxi()
        else if (taxiType == 7)
            mPresenterViewAll?.fetch7SeaterTaxi()
    }

    private fun setupView() {
        setSupportActionBar(toolbarViewall)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        txtYourLocViewall.text = RouteActivity.sStartAddress
        txtDesViewall.text = RouteActivity.sEndAddress
    }

    override fun onFetch4SeaterTaxiSuccess(listTaxi: ArrayList<Taxi>) {
        recyclerViewAll.apply {
            adapter = AdapterRecyclerTaxi(listTaxi,this@ViewAllActivity,RouteActivity.sDistance,false)
            layoutManager = LinearLayoutManager(this@ViewAllActivity)
        }
        txtResults.text = "${listTaxi.size} Results"
        supportActionBar!!.title = "4 Seaters Economy"

        ItemClickSupport.addTo(recyclerViewAll).setOnItemClickListener { _, position, _ ->
            val taxiId = listTaxi[position].id
            sendToDetails(TYPE_4_SEATER, taxiId)
        }
    }

    override fun onFetch7SeaterTaxiSuccess(listTaxi: ArrayList<Taxi>) {
        recyclerViewAll.apply {
            adapter = AdapterRecyclerTaxi(listTaxi,this@ViewAllActivity,RouteActivity.sDistance,false)
            layoutManager = LinearLayoutManager(this@ViewAllActivity)
        }
        txtResults.text = "${listTaxi.size} Results"
        supportActionBar!!.title = "7 Seaters Economy"

        ItemClickSupport.addTo(recyclerViewAll).setOnItemClickListener { _, position, _ ->
            val taxiId = listTaxi[position].id
            sendToDetails(TYPE_7_SEATER, taxiId)
        }
    }

    private fun sendToDetails(type: Int, taxiId: Int) {
        val intent = Intent(this@ViewAllActivity, DetailsActivity::class.java)
        intent.putExtra("type", type)
        intent.putExtra("taxiId", taxiId)
        startActivity(intent)
    }
}
