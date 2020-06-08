package com.example.logindemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.logindemo.R;
import com.example.logindemo.model.Announcements;
import com.example.logindemo.model.LoginData;
import com.example.logindemo.network.FactoryAPI;
import com.example.logindemo.utils.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowNotification extends AppCompatActivity {
    TextView notification;
    private ImageView imageView;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("in show notification on create method....");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notification);
        notification = (TextView) findViewById(R.id.notification);
        notification.setText("am into settext");
        imageView = (ImageView) findViewById(R.id.imageView);
        showNotification(getIntent().getStringExtra("ID"));
    }

    private void showNotification(String id) {
        System.out.println("in showNotification1");
        System.out.println("id is...."+id);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FactoryAPI api = retrofit.create(FactoryAPI.class);
        Call<Announcements> call = api.getAnnouncementsById(id);//5ec3632be9cabc0897d64763
        call.enqueue(new Callback<Announcements>() {
            @Override
            public void onResponse(Call<Announcements> call, Response<Announcements> response) {
                Announcements announcement=response.body();
                    //System.out.println("onResponse...."+response.body());
                String tag[]=announcement.getTags();
                StringBuilder tags=new StringBuilder();
                System.out.println("in lenght...."+tag.length);
                for(int i=0;i<tag.length;i++){
                    System.out.println("in array...."+tag[i]);


                        tags.append(tag[i]+",");

                }
                notification.setText("Date :"+DateFormat.getDateInstance().format(announcement.getDate())+"\n\nTitle :\n"+announcement.getTitle()+"\n\nSubject :\n"+announcement.getSubject()+
                        "\n\nDescription :\n"+announcement.getDescription()+"\n\nLink :\n"+announcement.getLink());
               if(announcement.getImage()!=null) {
                   String url = announcement.getImage().replace("localhost", "192.168.56.1");
                   Picasso.get().load(url).into(imageView);
               }
            }

            @Override
            public void onFailure(Call<Announcements> call, Throwable t) {
                System.out.println("onFailure...."+t);
            }
        });
    }
}
