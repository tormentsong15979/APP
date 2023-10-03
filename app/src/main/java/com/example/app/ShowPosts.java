package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class ShowPosts extends AppCompatActivity {

    // 声明一个成员变量用于保存email地址，但在onCreate方法中初始化
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showposts); // 確保使用正確的佈局文件

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 在这里添加跳转到其他 layout 的代码
                Intent intent = new Intent(ShowPosts.this, Post.class);
                startActivity(intent);
            }
        });

        // 在這裡初始化SharedPreferences和email
        SharedPreferences sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email", "");

        // 定義要訪問的URL
        String URL = "http://0w0chen3060-2.ddns.net:7777/app_login/getPosts.php"; // 更新URL以獲取貼文

        // 建立StringRequest來發送請求
        // 設置請求方法為POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    LinearLayout postList = findViewById(R.id.post_list);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject postObject = jsonArray.getJSONObject(i);
                        String username = postObject.getString("username");
                        String postText = postObject.getString("postText");
                        String postImageBase64 = postObject.getString("postImagePath"); // 接收Base64編碼的圖片

                        // 解碼Base64圖片
                        byte[] decodedString = Base64.decode(postImageBase64, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                        // 添加貼文到佈局
                        addPostToLayout(username, postText, decodedByte, postList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // 在這裡處理錯誤響應
            }
        }) {
            // 在這裡設置POST請求的參數
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);

                return params;
            }
        };

        // 創建RequestQueue和將StringRequest添加到Queue中
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    // 此方法負責動態添加貼文到LinearLayout中
    private void addPostToLayout(String username, String postText, Bitmap postImage, LinearLayout postList) {
        // 創建貼文的LinearLayout
        LinearLayout postLayout = new LinearLayout(this);
        postLayout.setOrientation(LinearLayout.VERTICAL);
        postLayout.setPadding(8, 8, 8, 8);

        // 創建用戶名TextView
        TextView usernameTextView = new TextView(this);
        usernameTextView.setText(username);
        usernameTextView.setTypeface(null, Typeface.BOLD);

        // 創建貼文文字TextView
        TextView postTextView = new TextView(this);
        postTextView.setText(postText);
        postTextView.setPadding(0, 4, 0, 4);

        // 創建貼文圖片ImageView
        ImageView postImageView = new ImageView(this);
        postImageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200));

        // 設置解碼後的圖片作為ImageView的源
        if (postImage != null) {
            postImageView.setImageBitmap(postImage);
        } else {
            postImageView.setImageResource(R.drawable.loding); // 使用占位符
        }

        // 將元素添加到貼文佈局
        postLayout.addView(usernameTextView);
        postLayout.addView(postTextView);
        postLayout.addView(postImageView);

        // 將貼文佈局添加到貼文列表
        postList.addView(postLayout);

        // 添加分隔線
        View separator = new View(this);
        separator.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
        separator.setBackgroundColor(Color.parseColor("#A0A0A0"));
        postList.addView(separator);
    }
}