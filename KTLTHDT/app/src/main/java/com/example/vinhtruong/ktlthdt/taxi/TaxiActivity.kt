package com.example.vinhtruong.ktlthdt.taxi

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.vinhtruong.ktlthdt.R
import com.example.vinhtruong.ktlthdt.adapter.AdapterRecyclerTaxi
import com.example.vinhtruong.ktlthdt.details.DetailsActivity
import com.example.vinhtruong.ktlthdt.login_signup.StartActivity
import com.example.vinhtruong.ktlthdt.model.Taxi
import com.example.vinhtruong.ktlthdt.model.User
import com.example.vinhtruong.ktlthdt.route.RouteActivity
import com.example.vinhtruong.ktlthdt.route.RouteActivity.Companion.sDistance
import com.example.vinhtruong.ktlthdt.route.RouteActivity.Companion.sEndAddress
import com.example.vinhtruong.ktlthdt.route.RouteActivity.Companion.sStartAddress
import com.example.vinhtruong.ktlthdt.utils.Constant
import com.example.vinhtruong.ktlthdt.utils.ItemClickSupport
import com.example.vinhtruong.ktlthdt.viewall.ViewAllActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_taxi.*
import kotlinx.android.synthetic.main.header_navigationview.view.*
import java.util.ArrayList

class TaxiActivity : AppCompatActivity(), ViewTaxi, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private var mPresenterTaxi: PresenterTaxi? = null
    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taxi)

        mPresenterTaxi = PresenterTaxi(this)
        mPresenterTaxi?.getListTaxi()

        setupView()
        eventsOnClick()
    }

    private fun eventsOnClick() {
        txtViewall4.setOnClickListener(this)
        txtViewall7.setOnClickListener(this)
        btnViewall4.setOnClickListener(this)
        btnViewall7.setOnClickListener(this)
        navigationTaxi.setNavigationItemSelectedListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.txtViewall4, R.id.btnViewall4 -> sendToViewall(Constant.TYPE_4_SEATER)
            R.id.txtViewall7, R.id.btnViewall7 -> sendToViewall(Constant.TYPE_7_SEATER)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerlayoutTaxi.closeDrawer(GravityCompat.START)
        when(item.itemId){
            R.id.menuLogout -> {
                mAuth.signOut()
                sendToLogin()
            }
            R.id.menuHome -> {
                val intent = Intent(this@TaxiActivity, RouteActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
            R.id.menuFare ->{
                Toast.makeText(this,"Search", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    private fun setupView() {
        txtYourLoctaxi.text = sStartAddress
        txtDestaxi.text = sEndAddress

        setSupportActionBar(toolbarTaxi)
        supportActionBar.apply {
            title = "Results"
            this!!.setHomeButtonEnabled(true)
            this!!.setDisplayHomeAsUpEnabled(true)
            this!!.setHomeAsUpIndicator(R.drawable.drawer)
        }
    }

    override fun onGetUserSuccess(user: User) {
        val header = navigationTaxi.getHeaderView(0)
        header.nameHeader.text = user.name
        header.emailHeader.text = user.email
        Glide.with(this).load(user.image)
                .apply(RequestOptions().placeholder(R.drawable.avatar_placeholder))
                .into(header.imgHeader)
        header.nameHeader.isClickable = user.id == Constant.DEFAULT_USER_ID
        header.nameHeader.setOnClickListener { sendToLogin() }
    }

    override fun onGetList4SeatersSuccess(taxi4ArrayList: ArrayList<Taxi>) {
        recycler4seaters.apply {
            layoutManager = LinearLayoutManager(this@TaxiActivity)
            adapter = AdapterRecyclerTaxi(taxi4ArrayList, this@TaxiActivity, sDistance, true)
        }
        val count = taxi4ArrayList.size - 3
        txtViewall4.text = "View all ($count)"

        ItemClickSupport.addTo(recycler4seaters).setOnItemClickListener { _, position, _ ->
            val taxiId = taxi4ArrayList[position].id
                sendToDetails(Constant.TYPE_4_SEATER, taxiId)
        }
    }

    override fun onGetList7SeatersSuccess(taxi7ArrayList: ArrayList<Taxi>) {
        recycler7seaters.apply {
            layoutManager = LinearLayoutManager(this@TaxiActivity)
            adapter = AdapterRecyclerTaxi(taxi7ArrayList, this@TaxiActivity, sDistance, true)
        }

        val count = taxi7ArrayList.size - 3
        txtViewall7.text = "View all ($count)"

        ItemClickSupport.addTo(recycler7seaters).setOnItemClickListener { _, position, _ ->
            val taxiId = taxi7ArrayList[position].id
            sendToDetails(Constant.TYPE_7_SEATER, taxiId)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawerlayoutTaxi.openDrawer(GravityCompat.START)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun sendToLogin() {
        val loginIntent = Intent(this@TaxiActivity, StartActivity::class.java)
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(loginIntent)
        finish()
    }

    private fun sendToDetails(type: Int, taxiId: Int) {
        val intent = Intent(this@TaxiActivity, DetailsActivity::class.java)
        intent.putExtra("type", type)
        intent.putExtra("taxiId", taxiId)
        startActivity(intent)
    }

    private fun sendToViewall(taxiType: Int) {
        val intent = Intent(this@TaxiActivity, ViewAllActivity::class.java)
        intent.putExtra("type", taxiType)
        startActivity(intent)
    }


}
