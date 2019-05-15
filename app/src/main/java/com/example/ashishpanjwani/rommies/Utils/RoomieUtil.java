package com.example.ashishpanjwani.rommies.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.ashishpanjwani.rommies.R;

public class RoomieUtil {

    private Context mContext = null;

    //Public Constructor that takes mContext for later use
    public RoomieUtil(Context con) {
        mContext=con;
    }

    /**
     * Encode user email to use it as a firebase key (Firebase does not allow "." in the key name)
     * Encoded email is also used as "userEmail",list and item "owner" value
     */

    public static String encodeEmail(String userEmail) {
        return userEmail.replace(".",",");
    }

    //This is a method to check if the device internet connection is currently on
    public boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo=connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void hideSoftKeyboard(@Nullable Activity activity) {
        if(activity!=null) {
            View currentFocus=activity.getCurrentFocus();
            if(currentFocus!=null) {
                InputMethodManager inputMethodManager = (InputMethodManager) activity
                        .getSystemService(Activity.INPUT_METHOD_SERVICE);
                if(inputMethodManager!=null) {
                    inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(),0);
                }
            }
        }
    }
}
