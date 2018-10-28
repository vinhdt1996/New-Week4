package com.example.vinhtruong.ktlthdt.route

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.vinhtruong.ktlthdt.R
import com.example.vinhtruong.ktlthdt.login_signup.StartActivity
import com.example.vinhtruong.ktlthdt.model.ResultAddress
import com.example.vinhtruong.ktlthdt.model.ResultRoute
import com.example.vinhtruong.ktlthdt.model.User
import com.example.vinhtruong.ktlthdt.search.SearchActivity
import com.example.vinhtruong.ktlthdt.taxi.TaxiActivity
import com.example.vinhtruong.ktlthdt.utils.Constant
import com.example.vinhtruong.ktlthdt.utils.Constant.Companion.INITIAL_STROKE_WIDTH_PX
import com.example.vinhtruong.ktlthdt.utils.Constant.Companion.MY_PERMISSIONS_REQUEST_LOCATION
import com.example.vinhtruong.ktlthdt.utils.Constant.Companion.RADIUS_LARGE
import com.example.vinhtruong.ktlthdt.utils.Constant.Companion.STROKE_WIDTH
import com.example.vinhtruong.ktlthdt.utils.decodePoly
import com.github.ybq.android.spinkit.style.Circle
import com.google.android.gms.location.*
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_route.*
import kotlinx.android.synthetic.main.header_navigationview.view.*

class RouteActivity:
        AppCompatActivity(),
        View.OnClickListener
        ,NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback,
        ViewRoute {

    private lateinit var mMap: GoogleMap
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var mLocationRequest : LocationRequest
    private lateinit var mPresenterRoute: PresenterRoute
    private lateinit var mResultRoute: ResultRoute
    private lateinit var mAuth: FirebaseAuth
    private var mCurrentUser: FirebaseUser? = null

    companion object {
        var sDistance: Double? = null
        var sStartAddress: String? = null
        var sEndAddress: String? = null
    }

//    private lateinit var user: User

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)

        mAuth  = FirebaseAuth.getInstance()
        mCurrentUser = mAuth.currentUser
        mPresenterRoute = PresenterRoute(this)

        val mapFragment : SupportMapFragment? =
                supportFragmentManager.findFragmentById(R.id.mapView) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        bgRipple.startRippleAnimation()

        checkUserState()

        setupView()

    }

    private fun checkUserState() {
        mCurrentUser?.let {
            val infos = mCurrentUser!!.providerData
            for (ui: UserInfo in infos) {
                if (ui.providerId == EmailAuthProvider.PROVIDER_ID) {
                    mPresenterRoute.getEmailUser()
                } else if (ui.providerId == FacebookAuthProvider.PROVIDER_ID) {
                    setupUiWithFbUser()
                }
            }
        }
    }

    private fun setupUiWithFbUser() {
        val header = navigationView.getHeaderView(0)
            header.nameHeader.isClickable = false
            header.nameHeader.text = mCurrentUser!!.displayName
            header.emailHeader.text = mCurrentUser!!.email
            Glide.with(this).load(mCurrentUser!!.photoUrl.toString())
                    .apply(RequestOptions().placeholder(R.drawable.avatar_placeholder))
                    .into(header.imgHeader)
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnCancel -> {
                txtDestination.text = ""
            if (bgRipple.visibility == View.VISIBLE) {
                bgRipple.visibility = View.GONE
                btnFabRoute.visibility = View.GONE
            }
                mMap.clear()
            }
            R.id.btnFind -> {
                val origin: String = txtOrigin.text.toString()
                val destination: String = txtDestination.text.toString()

                if (origin == "")
                    Toast.makeText(this, "Origin location can not be empty", Toast.LENGTH_SHORT).show()
                else if (destination == "")
                    Toast.makeText(this, "Destination location can not be empty", Toast.LENGTH_SHORT).show()
                else if (origin != "" && destination != "") {
                    mMap.clear()
                    mPresenterRoute.startGetRoute(origin, destination)
                }
            }

            R.id.txtDestination -> {
                val intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                        .build(this@RouteActivity)
                startActivityForResult(intent, Constant.PLACE_AUTOCOMPLETE_REQUEST_CODE)
            }

            R.id.btnFabRoute -> {
                Log.d("AA","Click")
                val intent = Intent(this@RouteActivity, TaxiActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == Constant.PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlaceAutocomplete.getPlace(this, data)
                txtDestination.text = place?.name.toString()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        when(item.itemId){
            R.id.menuLogout -> {
                mAuth.signOut()
                val loginIntent = Intent(this@RouteActivity, StartActivity::class.java)
                startActivity(loginIntent)
                finish()
            }
            R.id.menuFare ->{
                var intent = Intent(this@RouteActivity, SearchActivity::class.java)
                startActivity(intent)

            }
        }
        return true
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap?) {

        showProgress(false)

        mMap = googleMap ?: return
        mMap!!.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json))

        mMap.setPadding(Constant.LEFT, Constant.TOP, Constant.RIGHT, Constant.BOTTOM)

        mLocationRequest = LocationRequest.create()
//        mLocationRequest.interval = 120000
//        mLocationRequest.fastestInterval = 120000
//        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
                mMap.isMyLocationEnabled = true
            } else {
                //Request Location Permission
                checkPermission()
            }
        }
        else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
            mMap.isMyLocationEnabled = true
        }


        btnCancel.setOnClickListener(this)

        btnFind.setOnClickListener (this)

        txtOrigin.setOnClickListener(this)

        txtDestination.setOnClickListener(this)
    }

    private var mLocationCallback : LocationCallback = object: LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult) {

            val locationList = locationResult.locations

            if (locationList.count() > 0) {
                //The last location in the list is the newest
                val location : Location = locationList[locationList.count() - 1]
                Log.i("MapsActivity", "Location: " + location.latitude + " " + location.longitude)

                //Move camera to last current location

                mMap.clear()

                val latLng = LatLng(location.latitude, location.longitude)
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15f)
                mMap.animateCamera(cameraUpdate)

                mPresenterRoute.startGetAddress(location.latitude.toString() + "," + location.longitude.toString())

                mMap.addCircle(
                        CircleOptions().apply {
                            center(latLng )
                            radius(RADIUS_LARGE)
                            strokeWidth(STROKE_WIDTH)
                            fillColor(ContextCompat.getColor(baseContext,R.color.colorCircleMap))
                            strokeColor(ContextCompat.getColor(baseContext,R.color.colorCircleMap))
                        })

            }
        }
    }


    /**
     * Function for handle response data -Address- when request's successful
     * @param result : Response data - Address
     */
    override fun onGetAddressSuccess(result: ResultAddress) {
        Log.d("Data Status","Return Address success!")

        if (result.status == "OK")
            txtOrigin.setText(result.results!![0].formattedAddress)
    }


    /**
     * Function for handle response data -Route- when request's successful
     * @param result : Response data - Route
     */
    override fun onGetRouteSuccess(result: ResultRoute) {

        Log.d("Data Status","Return route success!")

        if (result.status.toString() == "OK") {

            mResultRoute = result

            sDistance = (result.routes!![0].legs!![0].distance!!.value!!).toDouble() / 1000
            sStartAddress = mResultRoute.routes!![0].legs!![0].startAddress.toString()
            sEndAddress = mResultRoute.routes!![0].legs!![0].endAddress.toString()

            txtOrigin.text = sStartAddress
            txtDestination.text = sEndAddress

            drawRoute(mMap, mResultRoute.routes!![0].overviewPolyline!!.points!!.decodePoly())

//            drawerLayout.expandFab(this)

            if (bgRipple.visibility == View.GONE) {
                bgRipple.visibility = View.VISIBLE
                btnFabRoute.visibility = View.VISIBLE
            }
            bgRipple.startRippleAnimation()
            btnFabRoute.setOnClickListener(this)
        }
        else
            showError("Something wrong with request!")
    }

    /**
     * Function checking permission for ACCESS_FINE_LOCATION
     */
    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        MY_PERMISSIONS_REQUEST_LOCATION)
                Log.d("Permission access","First time")
            }
        } else {
            // Permission has already been granted
            Log.d("Permission access","Permission has already been granted")
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //Location Permission already granted
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
            mMap.isMyLocationEnabled = true
        }
    }

    /**
     * Function for setup listener for action buttons, actionbar
     */
    private fun setupView() {
        val circle = Circle()
        circle.color = ContextCompat.getColor(baseContext, R.color.colorPrimary)
        loader.indeterminateDrawable = circle

        showProgress(true)

        setSupportActionBar(toolBar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigationView.bringToFront()
        navigationView.setNavigationItemSelectedListener(this)

        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolBar,
                R.string.open,
                R.string.close
        ){}

        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }


    /**
     * Zooms a Route (given a List of LalLng) at the greatest possible zoom level, draw a direction
     * given location and set up marker
     *
     * @param googleMap: instance of GoogleMap
     * @param lstLatLngRoute: list of LatLng forming Route
     */
    private fun drawRoute(googleMap: GoogleMap, lstLatLngRoute: List<LatLng>) {

        val boundsBuilder = LatLngBounds.Builder()
        val lineBuilder = PolylineOptions()


        for (latLngPoint in lstLatLngRoute) {
            boundsBuilder.include(latLngPoint)

            // A geodesic polyline that goes form origin to destination.
            lineBuilder.apply {
                add(latLngPoint)
                width(INITIAL_STROKE_WIDTH_PX.toFloat())
                color(ContextCompat.getColor(baseContext, R.color.colorRed))
                geodesic(true)
            }

        }

        val routePadding = 0
        val latLngBounds = boundsBuilder.build()

        mMap.setPadding(Constant.LEFT, Constant.TOP, Constant.RIGHT, Constant.BOTTOM)

        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, routePadding))
        googleMap.addPolyline(lineBuilder)

        googleMap.addMarker(MarkerOptions().apply{
            position(lstLatLngRoute.last())
            title("Destination")
            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        })

    }

    override fun showProgress(isShow: Boolean) {
        loader.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onGetEmailUserSuccess(user: User) {
        val header = navigationView.getHeaderView(0)
        header.nameHeader.isClickable = false
        header.nameHeader.text = user.name
        header.emailHeader.text = user.email
        Glide.with(this).load(user.image)
                .apply(RequestOptions().placeholder(R.drawable.avatar_placeholder))
                .into(header.imgHeader)
    }
}
