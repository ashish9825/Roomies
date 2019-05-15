package com.example.ashishpanjwani.rommies;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.ashishpanjwani.rommies.Utils.SharedPrefManager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import pugman.com.simplelocationgetter.SimpleLocationGetter;

public class SplashActivity extends AppCompatActivity {

    private double lat,lon;
    private String city;
    private SharedPrefManager sharedPrefManager;
    private final Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkLogin();
        /*Intent intent=new Intent(this, AutoActivity.class);
        startActivity(intent);
        finish();*/

    }

    private void transparentStatusAndNavigation() {
        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            );
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    private void setWindowFlag(final int bits, boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private void transparentStatus() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.transparent));
        }
    }

    public boolean checkLocationPermission() {

        String permission="android.permission.ACCESS_FINE_LOCATION";
        int res=this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to  login page
     * Else won't do anything
     */
    public void checkLogin() {

        /*//Check login status
        if (!this.isLoggedIn()) {

            //User is not logged in redirect him/her to LoginActivity
            Intent intent = new Intent(SplashActivity.this,LoginActivity.class);

            //Closing all the Activities
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            //Add new flag to start new Activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //Starting Login Activity
            startActivity(intent);
        }
        else {
            Intent intent=new Intent(SplashActivity.this, AutoActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }*/

        Intent activityIntent;

        sharedPrefManager = new SharedPrefManager(mContext);
        //go straight to AutoActivity if a token is stored
        if (!sharedPrefManager.getUserToken().equals("")) {
            activityIntent = new Intent(this,MainActivity.class);
        }
        else {
            activityIntent = new Intent(this,LoginActivity.class);
        }

        startActivity(activityIntent);
        finish();

    }

    /**
     * Quick check for login
     */
    public boolean isLoggedIn() {
        return getPreferences(mContext).getBoolean("IS_LOGGED_IN",false);
    }

    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
