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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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


public class editprofile extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private TextView output;
    private Calendar dt = Calendar.getInstance();
    private Button msBtn,msaveBtn;
    private EditText etPassword,etPassword1,mselfintrod;
    private SharedPreferences sharedPreferences;
    private String email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        ImageButton imageButton = findViewById(R.id.imageButton7);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        output = (TextView) findViewById(R.id.textView29);
        mselfintrod = findViewById(R.id.self);

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



        etPassword = findViewById(R.id.editTextTextPersonName2);
        etPassword1 = findViewById(R.id.editTextTextPersonName3);

        String password0 = etPassword.getText().toString();
        String password1 = etPassword1.getText().toString();
        Spinner mspinner=findViewById(R.id.spinner);

        if (password0.length() == 0) {
            Toast.makeText(editprofile.this, "新密碼不能為空", Toast.LENGTH_SHORT).show();
            return;
        } else if (4 > password0.length()) {
            Toast.makeText(editprofile.this, "新密碼長度應在4-18字之間", Toast.LENGTH_SHORT).show();
            return;
        } else if (password0.length() >= 18) {
            Toast.makeText(editprofile.this, "新密碼長度應在4-18字之間", Toast.LENGTH_SHORT).show();
            return;
        } else if (password1.length() == 0) {
            Toast.makeText(editprofile.this, "重複密碼不能為空", Toast.LENGTH_SHORT).show();
            return;
        } else if (4 > password1.length()) {
            Toast.makeText(editprofile.this, "重複密碼長度應在4-18字之間", Toast.LENGTH_SHORT).show();
            return;
        } else if (password1.length() >= 18) {
            Toast.makeText(editprofile.this, "重複密碼長度應在4-18字之間", Toast.LENGTH_SHORT).show();
            return;
        } else if (!password0.equals(password1)) {
            // 密碼不相等，顯示 Toast 訊息提示使用者
            Toast.makeText(editprofile.this, "新密碼與重複密碼不同，請重新輸入", Toast.LENGTH_SHORT).show();
            return;
        }
//            TextView mEmailTextView = view.findViewById(R.id.text_view_email);
        EditText mOldPasswordTextView = findViewById(R.id.editTextTextPersonName);//舊密碼
        EditText mchangePassword = findViewById(R.id.editTextTextPersonName2);//新密碼
        EditText mconfirmchangePassword = findViewById(R.id.editTextTextPersonName3);//重複密碼
        EditText mchangetelephone = findViewById(R.id.editTextPhone); // 新的電話號碼
        TextView msbirthday = findViewById(R.id.textView29);

        SharedPreferences sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");//從login那邊來的資料
        String password = sharedPreferences.getString("password", "");//從login那邊來的資料
        String changepassword = mchangePassword.getText().toString().trim();//設定新密碼變數
        String confirmchangepassword = mconfirmchangePassword.getText().toString().trim();//設定重複密碼變數
        String changetelephone = mchangetelephone.getText().toString().trim(); // 要變更的電話號碼
        String mpinner = mspinner.getSelectedItem().toString();
        String selfintrod = mselfintrod.getText().toString().trim();
        String mbirthday = msbirthday.getText().toString().trim(); // 要變更的生日

        String URL = "http://0w0chen3060-2.ddns.net:7777/app_login/update_user_info.php";//後端網址


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
                        editor.putString("phone", changetelephone);
                        editor.putString("gender", mpinner);
                        editor.putString("birthday", mbirthday);
                        editor.putString("Introduction", selfintrod);
                        editor.apply();

                        // 在 TextView 上顯示修改後的密碼
                        mOldPasswordTextView.setText(changepassword);
                        mchangePassword.setText("");
                        mconfirmchangePassword.setText("");
                        mchangetelephone.setText("");

                        Toast.makeText(editprofile.this, "修改成功", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(prof.this, LoginActivity.class));

                    } else {
                        // Update failed, handle the error
                        Toast.makeText(editprofile.this, reason, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error response from the server
                Toast.makeText(editprofile.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("email", email);
                data.put("password", password);
          //密碼
                data.put("password", changepassword);
                data.put("original_password", password);
          //電話
                data.put("phone", changetelephone);
          //性別
                data.put("gender", mpinner);
          //生日1
                data.put("birthday", mbirthday);
          //自我介紹
                data.put("Introduction", selfintrod);
                return data;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}