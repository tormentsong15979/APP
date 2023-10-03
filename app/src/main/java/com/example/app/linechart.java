package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class linechart extends AppCompatActivity {

    int[] errorCounts = new int[12];
    LineChart chart ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linechart);

        LineChart chart = findViewById(R.id.line_chart);
// 設定圖表的描述
        Description desc = new Description();
        desc.setText("My Chart");
        chart.setDescription(desc);

// 啟用觸摸縮放和拖曳
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(true);

        // 創建一個空的 LineData 物件
        LineData data = new LineData();

// 為 LineData 物件添加一條線
        List<Entry> entries = new ArrayList<>();


      for (int i = 0; i < errorCounts.length; i++) {
            entries.add(new Entry(i, errorCounts[i]));
        }

/*
            entries.add(new Entry(0, 5));
            entries.add(new Entry(1, 10));
            entries.add(new Entry(2, 15));  // 修改為 2 月的位置
            entries.add(new Entry(3, 20));
            entries.add(new Entry(4, 25));
            entries.add(new Entry(5, 30));
            entries.add(new Entry(6, 35));
            entries.add(new Entry(7, 40));
            entries.add(new Entry(8, 45));
            entries.add(new Entry(9, 50));
            entries.add(new Entry(10, 55));
            entries.add(new Entry(11, 60));
*/


        LineDataSet dataSet = new LineDataSet(entries, "次數");
        dataSet.setValueTextSize(10f); // 設定線上數字大小為 20sp
        data.addDataSet(dataSet);

// 設定 X 軸的值和標籤
        List<String> labels = new ArrayList<>();
        labels.add("一月");
        labels.add("二月");
        labels.add("三月");
        labels.add("四月");
        labels.add("五月");
        labels.add("六月");
        labels.add("七月");
        labels.add("八月");
        labels.add("九月");
        labels.add("十月");
        labels.add("十一月");
        labels.add("十二月");

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setLabelRotationAngle(270f);
        xAxis.setTextSize(15f); // 設定文字大小為 14
        xAxis.setAxisMinimum(0f); // 將最小值設定為-0.5
        xAxis.setAxisMaximum(12f); // 將最大值設定為12.5
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // 設定x軸的位置為下方

        YAxis yAxis = chart.getAxisLeft(); // 取得左邊的 Y 軸
        yAxis.setAxisMinimum(0f); // 設置 Y 軸的最小值為 0
        yAxis.setAxisMaximum(50f); // 設置 Y 軸的最大值為 30
        yAxis.setGranularity(10f); // 設置標籤間隔為 10

        yAxis.setDrawLabels(true); // 啟用標籤顯示
        yAxis.setTextSize(15f);  //設定文字大小為 15
        yAxis = chart.getAxisRight(); // 取得右邊的 Y 軸
        yAxis.setDrawLabels(false); // 停用標籤顯示


        Legend legend = chart.getLegend();
        legend.setTextSize(16);

        chart.zoom(12f / 7f, 1f, 0f, 0f); // 将缩放比例修改为12/7
        chart.setDragOffsetX(40f); // 将拖拽偏移量设置为40

        chart.invalidate(); // 刷新图表
        chart.setData(data); // 设置图表数据

    }
    public void register(View view) {
        TextView month = findViewById(R.id.month1);
        TextView month1 = findViewById(R.id.month2);
        TextView month2 = findViewById(R.id.month3);
        TextView month3 = findViewById(R.id.month4);
        TextView month4 = findViewById(R.id.month5);
        TextView month5 = findViewById(R.id.month6);
        TextView month6 = findViewById(R.id.month7);
        TextView month7 = findViewById(R.id.month8);
        TextView month8 = findViewById(R.id.month9);
        TextView month9 = findViewById(R.id.month10);
        TextView month10 = findViewById(R.id.month11);
        TextView month11 = findViewById(R.id.month12);

        SharedPreferences sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", ""); // 從 login 頁面傳來的 email
        String password = sharedPreferences.getString("password", ""); // 從 login 頁面傳來的 password

        String fields = "username,phone,email,month";
        String URL = "http://0w0chen3060-2.ddns.net:7777/app_login/getErrorCount.php";

// 创建一个 StringRequest 对象，设置请求方式为 GET
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        errorCounts[i] = jsonArray.getInt(i);
                    }
                    // 將 errorCounts 數組用於繪製圖表
                    // ...

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // 處理從服務器返回的錯誤響應
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);

                return params;
            }
        };

// 将 StringRequest 对象添加到请求队列中
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
