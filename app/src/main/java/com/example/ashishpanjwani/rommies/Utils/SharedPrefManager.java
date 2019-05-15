package com.example.ashishpanjwani.rommies.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    SharedPreferences sharedPreferences;
    Context mContext;

    //Shared Pref Mode
    int PRIVATE_MODE=0;

    //Shared Preferences File name
    private static final String PREF_NAME="sessionPref";
    private static final String IS_LOGIN="IsLoggedIn";
    SharedPreferences.Editor editor;

    public SharedPrefManager (Context context) {
        mContext=context;
        sharedPreferences=mContext.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor=sharedPreferences.edit();
    }

    public void saveIsLoggedIn(Context context,Boolean isLoggedIn) {
        mContext=context;
        sharedPreferences=mContext.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("IS_LOGGED_IN",isLoggedIn).apply();
        editor.commit();
    }

    public boolean getIsLoggedIn() {
        sharedPreferences=mContext.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        return sharedPreferences.getBoolean("IS_LOGGED_IN",false);
    }

    public void saveToken(Context context,String token) {
        mContext=context;
        sharedPreferences=mContext.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("ID_TOKEN",token).apply();
        editor.commit();
    }

    public String getUserToken() {

        sharedPreferences=mContext.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        return sharedPreferences.getString("ID_TOKEN","");
    }

    public void saveEmail(Context context,String email) {
        mContext=context;
        sharedPreferences=mContext.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("EMAIL",email);
        editor.commit();
    }

    public String getUserEmail() {
        sharedPreferences=mContext.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        return sharedPreferences.getString("EMAIL",null);
    }

    public void saveName(Context context,String name) {
        mContext=context;
        sharedPreferences=mContext.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("NAME",name);
        editor.commit();
    }

    public String getName() {
        sharedPreferences=mContext.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        return sharedPreferences.getString("NAME",null);
    }

    public void savePhoto(Context context,String photo) {
        mContext=context;
        sharedPreferences=mContext.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("PHOTO",photo);
        editor.commit();
    }

    public String getPhoto() {
        sharedPreferences=mContext.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        return sharedPreferences.getString("PHOTO",null);
    }

    public void clear() {
        editor.clear();
        editor.apply();
    }
}
