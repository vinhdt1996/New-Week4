package com.example.vinhtruong.ktlthdt.details

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.vinhtruong.ktlthdt.R
import com.example.vinhtruong.ktlthdt.model.Taxi
import com.example.vinhtruong.ktlthdt.route.RouteActivity
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_view_all.*
import java.text.DecimalFormat

class DetailsActivity : AppCompatActivity(), ViewDetails {

    private var mPresenterDetails: PresenterDetails? = null
    private var mPhoneNumber: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setupView()

        var type = intent.getIntExtra("type", 0)
        var taxiId = intent.getIntExtra("taxiId", 0)

        mPresenterDetails = PresenterDetails(this)
        mPresenterDetails!!.getTaxiDetails(type, taxiId, RouteActivity.sDistance!!)
    }

    private fun setupView() {
        setSupportActionBar(toolbarDetails)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        txtYourLocdetails.text = RouteActivity.sStartAddress
        txtDestaxidetails.text = RouteActivity.sEndAddress
        setSupportActionBar(toolbarDetails)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onFetchTaxiDetailsSuccess(taxi: Taxi, cost: Double) {
        val decimalFormat = DecimalFormat("#.#")
        supportActionBar!!.setTitle(taxi.name)
        txtDistance.text = decimalFormat.format(RouteActivity.sDistance) + " km"
        val decimalFormat2 = DecimalFormat("###,###")
        txtCost.text = decimalFormat2.format(cost)
        txtFirst4Details.text = decimalFormat2.format(taxi.first4)
        txtAfter4Details.text = decimalFormat2.format(taxi.after4)
        txtAboutDetails.text = taxi.about
        Glide.with(this).load(taxi.image).apply(RequestOptions().placeholder(R.drawable.louis)).into(imgLogoDetails)

        mPhoneNumber = taxi.phone
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_call, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuCall -> {
                val uri = "tel:" + mPhoneNumber?.trim { it <= ' ' }
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse(uri)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
