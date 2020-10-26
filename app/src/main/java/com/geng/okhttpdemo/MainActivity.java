package com.geng.okhttpdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Button btn1;
    private Button btn2;

    private String urlAddress = "http://api.heclouds.com/devices/598768194/datapoints?datastream_id=tur01";
    private static final String APIKEY = "ccOaQkJydurl7Frt0=jjvCbtrpM=";
    private String method = "l";


    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);

        okHttpClient = new OkHttpClient();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doGet();

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPost("22");

            }
        });

    }

    private void doPost(final String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FormBody formBody = new FormBody.Builder().add("aa", s).build();
                Request request = new Request.Builder().url(urlAddress).post(formBody).build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        System.out.println("Post Fail");
//                        Toast.makeText(MainActivity.this, "Post Fail!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        System.out.println(response.body().string());
                    }
                });
            }
        }).start();
    }

    public void personData(String result) {
        List<Member> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Member member = new Member();
                member.setName(object.getString("name"));
                member.setSex(object.getString("sex"));
                list.add(member);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (Member member : list) {
            System.out.println(member.toString());
        }
    }

    private void doGet() {
        final String geturl = urlAddress;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder().url(geturl).header("api-key", APIKEY).build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    System.out.println("didiid");
                    if (response.isSuccessful()) {
//                        System.out.println("didiid");
                        System.out.println(response.body().string());
//                        Toast.makeText(MainActivity.this, response.body().string(), Toast.LENGTH_SHORT).show();
                    } else {
                        System.out.println("Fall!");
//                        Toast.makeText(MainActivity.this, "fail", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}