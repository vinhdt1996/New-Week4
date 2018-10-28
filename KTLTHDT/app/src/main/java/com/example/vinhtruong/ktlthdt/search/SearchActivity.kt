package com.example.vinhtruong.ktlthdt.search

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.example.vinhtruong.ktlthdt.R
import com.example.vinhtruong.ktlthdt.adapter.AdapterRecyclerSearch
import com.example.vinhtruong.ktlthdt.login_signup.StartActivity
import com.example.vinhtruong.ktlthdt.model.TaxiBrand
import com.example.vinhtruong.ktlthdt.route.RouteActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), ViewSearch, NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    private var mPresenterSearch: PresenterSearch? = null
    private val mAuth = FirebaseAuth.getInstance()
    private var searchView: SearchView? = null
    private var mResults: ArrayList<TaxiBrand> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setupView()

        eventOnClick()

        mPresenterSearch = PresenterSearch(this)

    }

    private fun eventOnClick() {
        navigationSearch.setNavigationItemSelectedListener(this)
    }

    private fun setupView() {
        setSupportActionBar(toolbarSearch)
        supportActionBar.apply {
            title = "Search Taxi"
            this!!.setHomeButtonEnabled(true)
            this!!.setDisplayHomeAsUpEnabled(true)
            this!!.setHomeAsUpIndicator(R.drawable.drawer)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val itSearch = menu?.findItem(R.id.menuSearch)
        searchView = itSearch?.actionView as SearchView

        searchView?.setOnQueryTextListener(this)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> drawerlayoutSearch.openDrawer(GravityCompat.START)
        }
        return true
    }

    override fun onFilterTaxiSuccess(results: ArrayList<TaxiBrand>) {
        mResults = results
        recyclerSearch.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = AdapterRecyclerSearch(results, this@SearchActivity)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerlayoutSearch.closeDrawer(GravityCompat.START)
        when(item.itemId){
            R.id.menuLogout -> {
                mAuth.signOut()
                val loginIntent = Intent(this@SearchActivity, StartActivity::class.java)
                startActivity(loginIntent)
                finish()
            }
            R.id.menuHome -> {
                var intent = Intent(this@SearchActivity, RouteActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        searchView?.setQuery("", false)
        searchView?.isIconified = true
        mResults.clear()
        mPresenterSearch?.filterTaxi(query!!)
        return false
    }
}
