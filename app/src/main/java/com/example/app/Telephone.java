package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class Telephone extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telephone);

        ImageButton imageButton = findViewById(R.id.imageButton3);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void register(View view) {
        EditText mtelephone = findViewById(R.id.editTextTextPassword); // 目前的電話號碼
        EditText mchangetelephone = findViewById(R.id.editTextTextPassword2); // 新的電話號碼

        SharedPreferences sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", ""); // 從 login 頁面傳來的 email
        String password = sharedPreferences.getString("password", ""); // 從 login 頁面傳來的 password
        String changetelephone = mchangetelephone.getText().toString().trim(); // 要變更的電話號碼

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
                        // 保存新的電話號碼到 SharedPreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("phone", changetelephone);
                        editor.apply();

                        // 在 TextView 上顯示修改後的電話號碼
                        mtelephone.setText(changetelephone);
                        mchangetelephone.setText("");
                        Toast.makeText(Telephone.this, "電話修改成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // Update failed, handle the error
                        Toast.makeText(Telephone.this, reason, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error response from the server
                Toast.makeText(Telephone.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("email", email);
                data.put("password", password);
                data.put("field_type", "phone");
                data.put("update_data", changetelephone);
                    return data;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

}}
