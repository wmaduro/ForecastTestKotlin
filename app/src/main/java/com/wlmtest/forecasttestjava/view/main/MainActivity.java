package com.wlmtest.forecasttestjava.view.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.snackbar.Snackbar;
import com.wlmtest.forecasttestjava.R;
import com.wlmtest.forecasttestjava.base.ActivityBase;
import com.wlmtest.forecasttestjava.repository.ForecastTestJavaRepository;
import com.wlmtest.forecasttestjava.view.search.SearchResultFragment;
import com.wlmtest.forecasttestjava.viewmodel.MainViewModeFactory;
import com.wlmtest.forecasttestjava.viewmodel.MainViewModel;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

public class MainActivity extends ActivityBase {


    private MainViewModel mainViewModel;
    private EditText etCurrentWeatherData;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //progress bar
        this.progressDialog = new ProgressDialog(this, R.style.progress_bar_theme);
        this.progressDialog.setCancelable(false);


        this.mainViewModel = ViewModelProviders.of(this, new MainViewModeFactory(new ForecastTestJavaRepository())).get(MainViewModel.class);

        if (this.getFragmentContainer() == null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, new SearchResultFragment()).commit();

        }

        etCurrentWeatherData = (EditText) findViewById(R.id.etCurrentWeatherData);

        setUIListeners();
        setObservers();


        configGPS();
    }

    private void showProgressDialog(int idMessage) {
        this.progressDialog.setMessage(getString(idMessage));
        this.progressDialog.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 10:
                configGPS();
                break;
            default:
                break;
        }
    }

    void configGPS() {


        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this,
                ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                request_permission();
            }
        } else {


            final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            final LocationListener listener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    mainViewModel.findCurrentWeatherDataByGPSCoordinates(location);
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {
                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {
                    Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(i);
                }
            };

            /**
             * Progress dialog while getting GPS information
             */
            showProgressDialog(R.string.check_gps_message);

            Looper myLooper = Looper.myLooper();
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setPowerRequirement(Criteria.POWER_HIGH);

            locationManager.requestSingleUpdate(criteria, listener, myLooper);


            final Handler myHandler = new Handler(myLooper);
            myHandler.postDelayed(new Runnable() {
                public void run() {

                    setViewWhenGPSTimeout(locationManager, listener);

                }
            }, 10000);


        }
    }

    private void setViewWhenGPSTimeout(LocationManager locationManager, LocationListener listener) {

        locationManager.removeUpdates(listener);
        progressDialog.dismiss();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void request_permission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                ACCESS_COARSE_LOCATION)) {

            Snackbar.make(findViewById(R.id.main_layout), "Please, enable your location.",
                    Snackbar.LENGTH_LONG)
                    .setAction("Enable", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            requestPermissions(new String[]{ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 10);
                        }
                    })
                    .show();
        } else {
            requestPermissions(new String[]{ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 10);
        }
    }


    /**
     * @param fragment
     */
    private void changeCurrentFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,
                        fragment, null).commit();


    }

    /**
     * Set all observers
     */
    private void setObservers() {

        /**
         * Show the dialog when the internet is unavailable.
         */
        this.mainViewModel.getShowInternetUnavailableMessage().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                showInternetUnavailableMessage();
            }
        });


        /**
         * Dismiss the progress dialog when the app is initialized.
         */

        this.mainViewModel.getAppInitialized().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean appInitialized) {
                if (appInitialized) {
                    progressDialog.dismiss();
                }
            }
        });

        /**
         * Switch between fragments and hide keyboard.
         */
        this.mainViewModel.getFragmentOrchestrationHelperMutableLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals(MainOrchestrationHelper.SHOW_FORECAST_FRAGMENT)) {

//                    changeCurrentFragment(new ForecastFragment());

                } else if (s.equals(MainOrchestrationHelper.SHOW_SEARCH_RESULT_FRAGMENT)) {

                    changeCurrentFragment(new SearchResultFragment());

                }

                hideKeyboard(MainActivity.this);
            }
        });

    }

    /**
     * Setup all view listeners.
     */
    @SuppressLint("ClickableViewAccessibility")
    private void setUIListeners() {

        etCurrentWeatherData.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    findCurrentWeather();
                    return true;
                }
                return false;
            }
        });

        this.etCurrentWeatherData.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {

                    int drawbleWidth =
                            etCurrentWeatherData.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width() +
                                    etCurrentWeatherData.getPaddingRight();

                    if (event.getRawX() >= (etCurrentWeatherData.getRight() - drawbleWidth)) {

                        findCurrentWeather();

                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void findCurrentWeather() {

        if (etCurrentWeatherData.getText().toString().length() <= 2) {

            Toast toast = Toast.makeText(this, R.string.city_name_greather_than_3, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        }

        mainViewModel.findCurrentWeatherDataByCityName(etCurrentWeatherData.getText().toString());
    }

    public void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Get the current visible fragment
     *
     * @return
     */
    private Fragment getFragmentContainer() {
        return this.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    }

    public MainViewModel getMainViewModel() {
        return mainViewModel;
    }


}

