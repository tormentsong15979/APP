package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Birthday extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private TextView output;
    private Calendar dt = Calendar.getInstance();
    private Button msBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);
       TextView btn=findViewById(R.id.buton7);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        output = (TextView) findViewById(R.id.lblOutput);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // 取得當前年份
        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);

        // 設定限制日期
        Calendar limit = Calendar.getInstance();
        limit.set(currentYear - 100, 0, 1); // 從當前年份往前推100年開始不能選

        // 設定不允許選擇的日期
        Calendar disallow = Calendar.getInstance();
        disallow.set(currentYear - 5, 0, 1); // 從當前年份往前推5年開始不能選

        // 取得使用者所選擇的日期
        Calendar selected = Calendar.getInstance();
        selected.set(year, monthOfYear, dayOfMonth);

        // 檢查使用者所選擇的日期是否符合限制
        if (selected.after(now) || selected.before(limit) || selected.after(disallow)) {
            // 日期不在允許的範圍內，顯示提示對話框
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("請選擇" + limit.get(Calendar.YEAR) + "年1月1日到" + disallow.get(Calendar.YEAR) + "年12月31日之間的日期")
                    .setPositiveButton("確定", null)
                    .show();
            return;
        }

        // 日期符合限制，更新生日資訊
        String birthday = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
        output.setText(birthday);
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int h, int m) {
        output.setText("時間: " + h + ":" + m);
    }

    public void button_Click(View view) {
        DatePickerDialog dlg = new DatePickerDialog(this, this,
                dt.get(Calendar.YEAR),
                dt.get(Calendar.MONTH),
                dt.get(Calendar.DAY_OF_MONTH));
// 设置最小日期为100年前的今天
        Calendar minDate = Calendar.getInstance();
        minDate.add(Calendar.YEAR, -100);
        dlg.getDatePicker().setMinDate(minDate.getTimeInMillis());
        // 設定最大日期為今天
        Calendar maxDate = Calendar.getInstance();
        dlg.getDatePicker().setMaxDate(maxDate.getTimeInMillis());

        dlg.show();
    }




    public void register(View view) {

        TextView msbirthday = findViewById(R.id.lblOutput);
        msBtn = findViewById(R.id.button6);

        SharedPreferences sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", ""); // 從 login 頁面傳來的 email
        String password = sharedPreferences.getString("password", ""); // 從 login 頁面傳來的 password
        String mbirthday = msbirthday.getText().toString().trim(); // 要變更的電話號碼

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
                        editor.putString("birthday", mbirthday);
                        editor.apply();


                        // 在 TextView 上顯示修改後的電話號碼

                        Toast.makeText(Birthday.this, "日期修改成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // Update failed, handle the error
                        Toast.makeText(Birthday.this, reason, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error response from the server
                Toast.makeText(Birthday.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("email", email);
                data.put("password", password);
                data.put("field_type", "birthday");
                data.put("update_data", mbirthday);
                return data;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }}

