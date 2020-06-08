/*
package com.example.logindemo.jwt;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.logindemo.MainActivity;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    SharedPreferences prefs;
    @Override
    public Response intercept(Chain chain)
            throws IOException {

        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String name = prefs.getString("name", "No name defined");//"No name defined" is the default value.
        int idName = prefs.getInt("idName", 0); //0 is the default value.

        PreferenceManager.getSharedPreferences();

        prefs=this.MainAc.getSharedPreferences("myPrefs",Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        Request request = chain.request();
        if(prefs!=null && prefs.hasToken()){//essentially checking if the prefs has a non null token
            request = request.newBuilder()
                    .addHeader("Authenticator", prefs.getToken())
                    .build();
        }
        Response response = chain.proceed(request);
        return response;
    }
}*/
