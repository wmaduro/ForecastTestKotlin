package com.wlmtest.forecasttestjava.view.main

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.wlmtest.forecasttestjava.R
import com.wlmtest.forecasttestjava.base.ActivityBase
import com.wlmtest.forecasttestjava.repository.ForecastTestJavaRepository
import com.wlmtest.forecasttestjava.view.forecast.ForecastFragment
import com.wlmtest.forecasttestjava.view.search.SearchResultFragment
import com.wlmtest.forecasttestjava.viewmodel.MainViewModelFactory
import com.wlmtest.forecasttestjava.viewmodel.MainViewModel

class MainActivity : ActivityBase() {


    lateinit var mainViewModel: MainViewModel
    private lateinit var etCurrentWeatherData: EditText


    /**
     * Get the current visible fragment
     *
     * @return
     */
    private val fragmentContainer: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.fragment_container)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this, MainViewModelFactory(ForecastTestJavaRepository.getInstance()))
            .get(MainViewModel::class.java)

        if (fragmentContainer == null) {

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, SearchResultFragment()).commit()

        }

        etCurrentWeatherData = findViewById<View>(R.id.etCurrentWeatherData) as EditText

        setUIListeners()
        setObservers()


        configGPS()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            10 -> configGPS()
        }
    }

    internal fun configGPS() {

        if (ActivityCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this@MainActivity,
                ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                request_permission()
            }
        } else {


            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val listener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    mainViewModel.findCurrentWeatherDataByGPSCoordinates(location)
                }

                override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {}

                override fun onProviderEnabled(s: String) {

                }

                override fun onProviderDisabled(s: String) {
                    val i = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(i)
                }
            }

            /**
             * Progress dialog while getting GPS information
             */
            showProgressDialog(R.string.check_gps_message)

            val myLooper = Looper.myLooper()
            val criteria = Criteria()
            criteria.accuracy = Criteria.ACCURACY_FINE
            criteria.powerRequirement = Criteria.POWER_HIGH

            locationManager.requestSingleUpdate(criteria, listener, myLooper)


            val GPS_TIMEOUT: Long = 5000
            val myHandler = Handler(myLooper)

            myHandler.postDelayed({
                setViewWhenGPSTimeout(locationManager, listener)
            }, GPS_TIMEOUT)


        }
    }

    private fun setViewWhenGPSTimeout(locationManager: LocationManager, listener: LocationListener) {
        locationManager.removeUpdates(listener)
        closeProgressDialog()
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun request_permission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this@MainActivity,
                ACCESS_COARSE_LOCATION
            )
        ) {

            Snackbar.make(
                findViewById(R.id.main_layout), "Please, enable your location.",
                Snackbar.LENGTH_LONG
            )
                .setAction("Enable") {
                    requestPermissions(
                        arrayOf(
                            ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ), 10
                    )
                }
                .show()
        } else {
            requestPermissions(arrayOf(ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 10)
        }
    }


    /**
     * @param fragment
     */
    private fun changeCurrentFragment(fragment: Fragment) {

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_container,
                fragment, null
            ).commit()


    }

    /**
     * Set all observers
     */
    private fun setObservers() {

        /**
         * Show the dialog when the internet is unavailable.
         */
        mainViewModel.showInternetUnavailableMessage.observe(this, Observer { showInternetUnavailableMessage() })

        mainViewModel.progressDialogLoadingData.observe(this, Observer { loading ->

                if (loading) {
                    showProgressDialog(R.string.loading_from_api_message)
                } else {
                    closeProgressDialog()
                }

        })


        /**
         * Dismiss the progress dialog when the app is initialized.
         */

        mainViewModel.appInitialized.observe(this, Observer { appInitialized ->
            closeProgressDialog()
        })

        /**
         * Switch between fragments and hide keyboard.
         */
        mainViewModel.fragmentOrchestrationHelper.observe(this, Observer { s ->
            if (s == MainOrchestrationHelper.SHOW_FORECAST_FRAGMENT) {

                changeCurrentFragment(ForecastFragment());

            } else if (s == MainOrchestrationHelper.SHOW_SEARCH_RESULT_FRAGMENT) {

                changeCurrentFragment(SearchResultFragment())

            }

            hideKeyboard(this@MainActivity)
        })

    }

    /**
     * Setup all view listeners.
     */
    @SuppressLint("ClickableViewAccessibility")
    private fun setUIListeners() {

        etCurrentWeatherData.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                findCurrentWeather()
                return@OnEditorActionListener true
            }
            false
        })

        etCurrentWeatherData.setOnTouchListener(View.OnTouchListener { v, event ->
            val DRAWABLE_RIGHT = 2

            if (event.action == MotionEvent.ACTION_UP) {

                val drawbleWidth =
                    etCurrentWeatherData.compoundDrawables[DRAWABLE_RIGHT].bounds.width() + etCurrentWeatherData.paddingRight

                if (event.rawX >= etCurrentWeatherData.right - drawbleWidth) {

                    findCurrentWeather()

                    return@OnTouchListener true
                }
            }
            false
        })
    }

    private fun findCurrentWeather() {

        if (etCurrentWeatherData.text.toString().length <= 2) {

            val toast = Toast.makeText(this, R.string.city_name_greather_than_3, Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return
        }

        mainViewModel.findCurrentWeatherDataByCityName(etCurrentWeatherData.text.toString())
    }

    fun hideKeyboard(activity: Activity) {
        val view = activity.findViewById<View>(android.R.id.content)
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


}

