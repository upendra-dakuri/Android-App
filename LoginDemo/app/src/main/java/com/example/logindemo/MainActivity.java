package com.example.logindemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;
import okhttp3.ResponseBody;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.logindemo.model.LoginData;
import com.example.logindemo.network.FactoryAPI;
import com.example.logindemo.utils.Constants;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.widget.EditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    private EditText usernameetext;
    private EditText passwordetext;
    private Button login;
    private static final String CHANNEL_ID = "NotificationChannel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        System.out.println("refreshedToken " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "NotificationChannel";
            String description = "description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        System.out.println("Token :" + FirebaseInstanceId.getInstance().getToken());

        usernameetext = (EditText) findViewById(R.id.UserName);
        passwordetext = (EditText) findViewById(R.id.Password);
        login = (Button) findViewById(R.id.login);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(usernameetext.getText().toString(), passwordetext.getText().toString());
            }
        });
    }

    private void login(String username, String password) {
    int error=0;
        if (username.equalsIgnoreCase("")) {
            //Toast.makeText(MainActivity.this, "Enter username", Toast.LENGTH_SHORT).show();
            error=++error;
            usernameetext.setError("Enter Username");

        }
        if (password.equalsIgnoreCase("")) {
            //Toast.makeText(MainActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
            passwordetext.setError("Enter Password");
            error=++error;
        }

        if (error == 0) {
            System.out.println("api calling "+error);
            loginProcess(username, password);

        }/* else {

            Toast.makeText(MainActivity.this, "Enter Valid Credentials", Toast.LENGTH_SHORT).show();
        }*/
    }

    String TAG = "Reg";

    private void loginProcess(String username, String password) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FactoryAPI api = retrofit.create(FactoryAPI.class);
        LoginData data = new LoginData(username, password);

            Call<LoginData> call = api.login(data);
            call.enqueue(new Callback<LoginData>() {

                @Override
                public void onResponse(Call<LoginData> call, retrofit2.Response<LoginData> response) {

                    if (null != response.body()) {
                        System.out.println("login respose "+response.body());
                        System.out.println("username "+response.body().getName());
                        System.out.println("jwt token "+response.body().getToken());

                       Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                        intent.putExtra("key", "Hello" + " " + response.body().getName());
                        intent.putExtra("jwttoken",  response.body().getToken());
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginData> call, Throwable t) {
                    Toast.makeText(MainActivity.this, " Username or Password", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void sendRegistrationToServer ( final String refreshedToken){

            String url = "http://192.168.56.1:8081/devicetoken";
            System.out.println("in url "+url);
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // view.setText("Login Success");
                    System.out.println("in success response");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Error in sending token "+error);
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("token", refreshedToken);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();
                    // params.put("Content-Type","application/json; charset=utf-8");
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }


}

