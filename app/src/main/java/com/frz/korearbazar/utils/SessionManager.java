package com.frz.korearbazar.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.frz.korearbazar.model.User;

import java.util.HashMap;


public class SessionManager {
    private static final String SHAER_FREF_NAME = "KOREAR_BAZAR";
    private SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;
    public Context context;



    public SessionManager(Context context) {
        this.context = context;
    }

    public void saveUser(User user){
        mPrefs = context.getSharedPreferences(SHAER_FREF_NAME,Context.MODE_PRIVATE);
        mEditor = mPrefs.edit();
        mEditor.putInt("id",user.getId());
        mEditor.putString("name",user.getName());
        mEditor.putString("email",user.getEmail());
        mEditor.putString("phone",user.getPhone());
        mEditor.putString("address",user.getAddress());
        mEditor.putBoolean("logged",true);
        mEditor.apply();
    }

    public boolean isLoggedIn(){
        mPrefs = context.getSharedPreferences(SHAER_FREF_NAME,Context.MODE_PRIVATE);
        return mPrefs.getBoolean("logged",false);
    }

    public User getUser(){
        mPrefs = context.getSharedPreferences(SHAER_FREF_NAME,Context.MODE_PRIVATE);
        return new User(mPrefs.getInt("id",-1),
                mPrefs.getString("name",null),
                mPrefs.getString("email",null),
                mPrefs.getString("phone",null),
                mPrefs.getString("address",null));

    }

    public void logOut(){
        mPrefs = context.getSharedPreferences(SHAER_FREF_NAME,Context.MODE_PRIVATE);
        mEditor = mPrefs.edit();
        mEditor.clear();
        mEditor.apply();
    }

}
