package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
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

public class gender extends AppCompatActivity {
    private Button mBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);

        ImageButton imageButton = findViewById(R.id.imageButton11);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView btn= findViewById(R.id.button9);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void register(View view) {

        mBtn= findViewById(R.id.button8);
        Spinner mspinner=findViewById(R.id.spinner);

        SharedPreferences sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", ""); // 從 login 頁面傳來的 email
        String password = sharedPreferences.getString("password", ""); // 從 login 頁面傳來的 password
        String mpinner = mspinner.getSelectedItem().toString();


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
                        editor.putString("gender", mpinner);
                        editor.apply();


                        // 在 TextView 上顯示修改後的電話號碼

                        Toast.makeText(gender.this, "性別修改成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // Update failed, handle the error
                        Toast.makeText(gender.this, reason, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error response from the server
                Toast.makeText(gender.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("email", email);
                data.put("password", password);
                data.put("field_type", "gender");
                data.put("update_data", mpinner);
                return data;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }}
