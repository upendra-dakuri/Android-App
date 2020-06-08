package com.example.logindemo;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.logindemo.jwt.JwtAuthenticationInterceptor;
import com.example.logindemo.model.Announcements;
import com.example.logindemo.network.FactoryAPI;
import com.example.logindemo.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Headers;

public class SecondActivity extends AppCompatActivity {

    private TextView user;
    private Button logout;
    String TAG = "second";
    private String token;
    ArrayAdapter adapter;
    ListView listView;
    @Headers("Content-Type: application/json")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        user = (TextView) findViewById(R.id.user);
        Intent intent = getIntent();
        String str = intent.getStringExtra("key");
        user.setText(str);
         token = intent.getStringExtra("jwttoken");
        getAppAnnouncements();
        logout = (Button) findViewById(R.id.logout);
         logout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 openActivity();
             }
         });
    }



    /*private void getAnnouncements() {
        int err = 0;
        if (err == 0) {
            getAppAnnouncements();

        } else {

            Toast.makeText(SecondActivity.this, "Enter Valid Credentials", Toast.LENGTH_SHORT).show();
        }
    }*/
    String REG = "Reg";
    private void getAppAnnouncements() {
     /*   System.out.println("getAppAnnouncements***");
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
              //  Request request = original.newBuilder().header("Authorization","xys").build();
                Request request1 = original.newBuilder().addHeader("Authorization","xys").build();
                return chain.proceed(request1);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();*/

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new JwtAuthenticationInterceptor(this::createJwtToken))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
      //  System.out.println("getAppAnnouncements2n" +client);
        FactoryAPI api = retrofit.create(FactoryAPI.class);
        Call<List<Announcements>> call = api.getAnnouncements();
        System.out.println("getAppAnnouncements3");
        call.enqueue(new Callback<List<Announcements>>() {
            @Override
            public void onResponse(Call<List<Announcements>> call, Response<List<Announcements>> response) {
                System.out.println("response printed "+response.body().get(0).getId());
                List<Announcements> announcement=response.body();
                List<String> announcementList = new ArrayList<>();
                int lengthofannouncements=announcement.size();
                for(int i=0;i<lengthofannouncements;i++) {
                    announcementList.add("Title : "+announcement.get(i).getTitle()+"\n"+"Description : "+announcement.get(i).getDescription()+"\n");
                }
                adapter = new ArrayAdapter<String>(SecondActivity.this, R.layout.activity_list, R.id.textview, announcementList);


                System.out.println("getAppAnnouncements4 is "+adapter);
                ListView listView = (ListView) findViewById(R.id.window_list);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener((parent, view, position, id) -> {
                    System.out.println("Clicked Position :"+position);
                    System.out.println("Clicked id :"+id);
                    System.out.println(announcementList.get(position));
                    openActivity3(announcement.get(position).getId(),token);
                });

            }

            @Override
            public void onFailure(Call<List<Announcements>> call, Throwable t) {
                System.out.println("getAppAnnouncements5 "+t);
            }
        });

    }

    private String createJwtToken() {
        return token;
    }

    /* private void logout() {
         finish();
     }*/
   private void openActivity() {
       Intent intent = new Intent(this,MainActivity.class);
       startActivity(intent);
   }
    private void openActivity3(String id,String token) {
        System.out.println("openActivity3 start"+id+  "token  "  +token);
        Intent intent = new Intent(this, ShowNotification.class);
        intent.putExtra("ID", id);
        //intent.putExtra("TOKEN",token);
        //System.out.println("openActivity3 end"+id+  "token  "  +token);
        startActivity(intent);
    }
}