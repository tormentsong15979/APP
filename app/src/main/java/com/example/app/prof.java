package com.example.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class prof extends AppCompatActivity {

    private EditText etPassword,etPassword1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof);

        ImageButton imageButton = findViewById(R.id.imageButton5);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
        public void register(View view) {


            etPassword = findViewById(R.id.et_ConformPassword1);
            etPassword1 = findViewById(R.id.et_ConfirmPassword);

            String password0 = etPassword.getText().toString();
            String password1 = etPassword1.getText().toString();

            if (password0.length() == 0) {
                Toast.makeText(prof.this, "新密碼不能為空", Toast.LENGTH_SHORT).show();
                return;
            } else if (4 > password0.length()) {
                Toast.makeText(prof.this, "新密碼長度應在4-18字之間", Toast.LENGTH_SHORT).show();
                return;
            } else if (password0.length() >= 18) {
                Toast.makeText(prof.this, "新密碼長度應在4-18字之間", Toast.LENGTH_SHORT).show();
                return;
            } else if (password1.length() == 0) {
                Toast.makeText(prof.this, "重複密碼不能為空", Toast.LENGTH_SHORT).show();
                return;
            } else if (4 > password1.length()) {
                Toast.makeText(prof.this, "重複密碼長度應在4-18字之間", Toast.LENGTH_SHORT).show();
                return;
            } else if (password1.length() >= 18) {
                Toast.makeText(prof.this, "重複密碼長度應在4-18字之間", Toast.LENGTH_SHORT).show();
                return;
            } else if (!password0.equals(password1)) {
                // 密碼不相等，顯示 Toast 訊息提示使用者
                Toast.makeText(prof.this, "新密碼與重複密碼不同，請重新輸入", Toast.LENGTH_SHORT).show();
                return;
            }
//            TextView mEmailTextView = view.findViewById(R.id.text_view_email);
            EditText mOldPasswordTextView = findViewById(R.id.et_RegisterPassword1);//舊密碼
            EditText mchangePassword = findViewById(R.id.et_ConformPassword1);//新密碼
            EditText mconfirmchangePassword = findViewById(R.id.et_ConfirmPassword);//重複密碼

            SharedPreferences sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
            String email = sharedPreferences.getString("email", "");//從login那邊來的資料
            String password = sharedPreferences.getString("password", "");//從login那邊來的資料
            String changepassword = mchangePassword.getText().toString().trim();//設定新密碼變數
            String confirmchangepassword = mconfirmchangePassword.getText().toString().trim();//設定重複密碼變數


            String URL = "http://0w0chen3060-2.ddns.net:7777/app_login/update_user_info2.php";//後端網址


            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//                Log.d("res", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String reason = jsonObject.getString("reason");
                        if (status.equals("success")) {
                            // 修改成功
                            // 保存新的密码到 SharedPreferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("password", changepassword);
                            editor.apply();

                            // 在 TextView 上顯示修改後的密碼
                            mOldPasswordTextView.setText(changepassword);
                            mchangePassword.setText("");
                            mconfirmchangePassword.setText("");
                            Toast.makeText(prof.this, "密码修改成功", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(prof.this, LoginActivity.class));

        } else {
                            // Update failed, handle the error
                            Toast.makeText(prof.this, reason, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error response from the server
                    Toast.makeText(prof.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("email", email);
                    data.put("password", password);
                    data.put("field_type", "password");
                    data.put("update_data", changepassword);
                    data.put("original_password", password);
                    return data;
                    //再來傳給後端的原本是3個參數：email、password、fields
                    //保留email、password
                    //把fields刪掉，然後加2個參數：
                    //field_type  (這個是修改的是什麼資料，例如"username")
                    //update_data  (這個是修改後的內容，給使用者輸入資料)
                    //例如：
                    //data.put("email", email);
                    //data.put("password", password);
                    //data.put("field_type", "username");
                    //data.put("update_data", 這裡是存輸入框資料的變數);
                    //
                    //若是修改密碼的話，要再加1個參數：
                    //original_password  (這個是原始密碼，也是給使用者輸入)
                    //也就是說修改密碼總共會有5個參數，例如：
                    //data.put("email", email);
                    //data.put("password", password);
                    //data.put("field_type", "password");
                    //data.put("update_data", 你懂的 存輸入框資料的變數);
                    //data.put("original_password", 這裡是從原始密碼輸入框來的資料);
                    //
                    //
                    //之後你需要改啥資料，都只需要改
                    //data.put("field_type", "這個地方，注意""要保留");
                    //
                    //目前能修改的是email、username、password這三個
                    //還需要啥跟我說
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

//            Button btn = findViewById(R.id.button3);
//            btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    startActivity(new Intent(prof.this, LoginActivity.class));
//                }
//            });
//            finish();
//            return ;
        }
}