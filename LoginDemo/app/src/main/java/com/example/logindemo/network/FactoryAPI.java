package com.example.logindemo.network;

import com.example.logindemo.model.Announcements;
import com.example.logindemo.model.LoginData;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FactoryAPI {


    @POST("login")
    Call<LoginData> login(@Body LoginData body);

   @GET("getAll")
   Call<List<Announcements>> getAnnouncements();

    @GET("announcement/{id}")
    Call<Announcements> getAnnouncementsById(@Path("id") String id);


}
