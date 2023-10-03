package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Selfintroduction extends AppCompatActivity {

    private EditText mselfintrod;
    private Button msaveBtn;
    private SharedPreferences sharedPreferences;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfintroduction);

        ImageButton imageButton = findViewById(R.id.imageButton6);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mselfintrod = findViewById(R.id.self);
        msaveBtn = findViewById(R.id.button2);

        sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email", ""); // 從 login 頁面傳來的 email
        password = sharedPreferences.getString("password", ""); // 從 login 頁面傳來的 password

        msaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selfintrod = mselfintrod.getText().toString().trim();

                String URL = "http://0w0chen3060-2.ddns.net:7777/app_login/update_user_info2.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            String reason = jsonObject.getString("reason");
                            if (status.equals("success")) {
                                // 修改成功
                                // 保存新的自我介紹到 SharedPreferences
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("Introduction", selfintrod);
                                editor.apply();

                                TextView btn=findViewById(R.id.button2);
                                btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        finish();
                                    }
                                });


                                Toast.makeText(Selfintroduction.this, "自我介紹修改成功", Toast.LENGTH_SHORT).show();
                            } else {
                                // 修改失敗，處理錯誤
                                Toast.makeText(Selfintroduction.this, reason, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 處理從伺服器返回的錯誤響應
                        Toast.makeText(Selfintroduction.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> data = new HashMap<>();
                        data.put("email", email);
                        data.put("password", password);
                        data.put("field_type", "Introduction");
                        data.put("update_data", selfintrod);

                        return data;
                    }
                };
                ImageButton imageButton = findViewById(R.id.imageButton6);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(Selfintroduction.this, ProfileFragment.class);
                        startActivity(intent);
                    }
                });


                        RequestQueue requestQueue = Volley.newRequestQueue(Selfintroduction.this);
                        requestQueue.add(stringRequest);
            }});
    }}
