package com.example.app;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PianoFragment extends Fragment {
    int[] errorCounts = new int[12];
    int[] ErrorCounts = new int[31];
    LineChart chart;
    BarChart Chart;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    // 在类的成员变量处定义 Entries 和 labels
    private List<Entry> Entries = new ArrayList<>();
    private List<String> labels = new ArrayList<>();
    public PianoFragment() {
        // Required empty public constructor
    }

    public static PianoFragment newInstance(String param1, String param2) {
        PianoFragment fragment = new PianoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private TextView output;
    private Button msBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_piano, container, false);


        chart = view.findViewById(R.id.line_chart);
        Chart = view.findViewById(R.id.Bar_chart);



        Button button = view.findViewById(R.id.button10);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                output = view.findViewById(R.id.textView32);

                // 获取当前的年份和月份
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);

                // 创建年份选择器对话框
                DatePickerDialog yearPickerDialog = new DatePickerDialog(requireContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar, null, year, 0, 1);
                yearPickerDialog.setTitle("選擇年份");
                yearPickerDialog.getDatePicker().findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE); // 隐藏日期部分
                yearPickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "下一步", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 获取选定的年份
                        DatePicker datePicker = yearPickerDialog.getDatePicker();
                        int selectedYear = datePicker.getYear();

                        // 创建月份选择器对话框
                        DatePickerDialog monthPickerDialog = new DatePickerDialog(requireContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                                // 处理选定的年份和月份
                                String birthday = String.format(Locale.getDefault(), "%04d-%02d", selectedYear, selectedMonth + 1);
                                output.setText(birthday);
                            }
                        }, selectedYear, month, 1); // 注意月份设置为初始值

                        monthPickerDialog.getDatePicker().findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE); // 隐藏日期部分
                        monthPickerDialog.setTitle("選擇月份");
                        monthPickerDialog.show();
                    }
                });

                yearPickerDialog.show();
            }
        });

        Button button66 = view.findViewById(R.id.button66); // 替换为你的按钮 ID
        button66.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView JanuaryTextView = view.findViewById(R.id.m1);      TextView FebruaryTextView = view.findViewById(R.id.m2);
                TextView MarchTextView = view.findViewById(R.id.m3);        TextView AprilTextView = view.findViewById(R.id.m4);
                TextView MayTextView = view.findViewById(R.id.m5);          TextView JuneTextView = view.findViewById(R.id.m6);
                TextView JulyTextView = view.findViewById(R.id.m7);         TextView AugustTextView = view.findViewById(R.id.m8);
                TextView SeptemberTextView = view.findViewById(R.id.m9);    TextView OctoberTextView = view.findViewById(R.id.m10);
                TextView NovemberTextView = view.findViewById(R.id.m11);    TextView DecemberTextView = view.findViewById(R.id.m12);
                TextView textView40 = view.findViewById(R.id.textView40);   TextView textView50 = view.findViewById(R.id.textView50);
                TextView textView52 = view.findViewById(R.id.textView52);   TextView textView54 = view.findViewById(R.id.textView54);
                TextView textView56 = view.findViewById(R.id.textView56);   TextView textView58 = view.findViewById(R.id.textView58);
                TextView textView59 = view.findViewById(R.id.textView59);   TextView textView62 = view.findViewById(R.id.textView62);
                TextView textView64 = view.findViewById(R.id.textView64);   TextView textView66 = view.findViewById(R.id.textView66);
                TextView textView68 = view.findViewById(R.id.textView68);   TextView textView70 = view.findViewById(R.id.textView70);

                textView40.setVisibility(View.GONE);    textView50.setVisibility(View.GONE);
                textView52.setVisibility(View.GONE);    textView54.setVisibility(View.GONE);
                textView52.setVisibility(View.GONE);    textView56.setVisibility(View.GONE);
                textView58.setVisibility(View.GONE);    textView59.setVisibility(View.GONE);
                textView62.setVisibility(View.GONE);    textView64.setVisibility(View.GONE);
                textView66.setVisibility(View.GONE);    textView68.setVisibility(View.GONE);
                textView70.setVisibility(View.GONE);

                TextView textView31 = view.findViewById(R.id.textView31);   TextView textView33 = view.findViewById(R.id.textView33);
                TextView textView35 = view.findViewById(R.id.textView35);   TextView textView37 = view.findViewById(R.id.textView37);
                TextView textView39 = view.findViewById(R.id.textView39);   TextView textView41 = view.findViewById(R.id.textView41);
                TextView textView43 = view.findViewById(R.id.textView43);   TextView textView45 = view.findViewById(R.id.textView45);   TextView textView42 = view.findViewById(R.id.textView42);
                TextView textView49 = view.findViewById(R.id.textView49);   TextView textView47 = view.findViewById(R.id.textView47);
                TextView textView48 = view.findViewById(R.id.textView48);   TextView textView36 = view.findViewById(R.id.textView36);
                TextView textView80 = view.findViewById(R.id.textView80);   TextView textView82 = view.findViewById(R.id.textView82);
                TextView textView85 = view.findViewById(R.id.textView85);   TextView textView38 = view.findViewById(R.id.textView38);
                TextView textView88 = view.findViewById(R.id.textView88);   TextView textView90 = view.findViewById(R.id.textView90);
                TextView textView92 = view.findViewById(R.id.textView92);   TextView textView94 = view.findViewById(R.id.textView94);
                TextView textView96 = view.findViewById(R.id.textView96);   TextView textView100 = view.findViewById(R.id.textView100);
                TextView textView98 = view.findViewById(R.id.textView98);   TextView textView102 = view.findViewById(R.id.textView102);
                TextView textView106 = view.findViewById(R.id.textView106); TextView textView104 = view.findViewById(R.id.textView104);
                TextView textView34 = view.findViewById(R.id.textView34);   TextView textView73 = view.findViewById(R.id.textView73);
                TextView textView75 = view.findViewById(R.id.textView75);   TextView textView77 = view.findViewById(R.id.textView77);

                TextView month1 = view.findViewById(R.id.month1);
                TextView month2 = view.findViewById(R.id.month2);
                TextView month3 = view.findViewById(R.id.month3);
                TextView month4 = view.findViewById(R.id.month4);
                TextView month5 = view.findViewById(R.id.month5);
                TextView month6 = view.findViewById(R.id.month6);
                TextView month7 = view.findViewById(R.id.month7);
                TextView month8 = view.findViewById(R.id.month8);
                TextView month9 = view.findViewById(R.id.month9);
                TextView month10 = view.findViewById(R.id.month10);
                TextView month11 = view.findViewById(R.id.month11);
                TextView month12 = view.findViewById(R.id.month12);
                TextView month13 = view.findViewById(R.id.month13);
                TextView month14 = view.findViewById(R.id.month14);
                TextView month15 = view.findViewById(R.id.month15);
                TextView month16 = view.findViewById(R.id.month16);
                TextView month17 = view.findViewById(R.id.month17);
                TextView month18 = view.findViewById(R.id.month18);
                TextView month19 = view.findViewById(R.id.month19);
                TextView month20 = view.findViewById(R.id.month20);
                TextView month21 = view.findViewById(R.id.month21);
                TextView month22 = view.findViewById(R.id.month22);
                TextView month23 = view.findViewById(R.id.month23);
                TextView month24 = view.findViewById(R.id.month24);
                TextView month25 = view.findViewById(R.id.month25);
                TextView month26 = view.findViewById(R.id.month26);
                TextView month27 = view.findViewById(R.id.month27);
                TextView month28 = view.findViewById(R.id.month28);
                TextView month29 = view.findViewById(R.id.month29);
                TextView month30 = view.findViewById(R.id.month30);
                TextView month31 = view.findViewById(R.id.month31);

                Button button10= view.findViewById(R.id.button10);
                Button button11= view.findViewById(R.id.button11);
                TextView textView32 = view.findViewById(R.id.textView32);
                button10.setVisibility(View.VISIBLE);
                button11.setVisibility(View.VISIBLE);
                textView32.setVisibility(View.VISIBLE);

                month1.setVisibility(View.VISIBLE);
                month2.setVisibility(View.VISIBLE);
                month3.setVisibility(View.VISIBLE);
                month4.setVisibility(View.VISIBLE);
                month5.setVisibility(View.VISIBLE);
                month6.setVisibility(View.VISIBLE);
                month7.setVisibility(View.VISIBLE);
                month8.setVisibility(View.VISIBLE);
                month9.setVisibility(View.VISIBLE);
                month10.setVisibility(View.VISIBLE);
                month11.setVisibility(View.VISIBLE);
                month12.setVisibility(View.VISIBLE);
                month13.setVisibility(View.VISIBLE);
                month14.setVisibility(View.VISIBLE);
                month15.setVisibility(View.VISIBLE);
                month16.setVisibility(View.VISIBLE);
                month17.setVisibility(View.VISIBLE);
                month18.setVisibility(View.VISIBLE);
                month19.setVisibility(View.VISIBLE);
                month20.setVisibility(View.VISIBLE);
                month21.setVisibility(View.VISIBLE);
                month22.setVisibility(View.VISIBLE);
                month23.setVisibility(View.VISIBLE);
                month24.setVisibility(View.VISIBLE);
                month25.setVisibility(View.VISIBLE);
                month26.setVisibility(View.VISIBLE);
                month27.setVisibility(View.VISIBLE);
                month28.setVisibility(View.VISIBLE);
                month29.setVisibility(View.VISIBLE);
                month30.setVisibility(View.VISIBLE);
                month31.setVisibility(View.VISIBLE);


                textView34.setVisibility(View.VISIBLE);
                textView73.setVisibility(View.VISIBLE);
                textView75.setVisibility(View.VISIBLE);
                textView104.setVisibility(View.VISIBLE);
                textView77.setVisibility(View.VISIBLE);
                textView106.setVisibility(View.VISIBLE);
                textView98.setVisibility(View.VISIBLE);
                textView100.setVisibility(View.VISIBLE);
                textView102.setVisibility(View.VISIBLE);
                textView31.setVisibility(View.VISIBLE);
                textView33.setVisibility(View.VISIBLE);
                textView35.setVisibility(View.VISIBLE);
                textView37.setVisibility(View.VISIBLE);
                textView39.setVisibility(View.VISIBLE);
                textView41.setVisibility(View.VISIBLE);
                textView38.setVisibility(View.VISIBLE);
                textView88.setVisibility(View.VISIBLE);
                textView90.setVisibility(View.VISIBLE);
                textView92.setVisibility(View.VISIBLE);
                textView94.setVisibility(View.VISIBLE);
                textView96.setVisibility(View.VISIBLE);
                textView43.setVisibility(View.VISIBLE);
                textView45.setVisibility(View.VISIBLE);
                textView42.setVisibility(View.VISIBLE);
                textView49.setVisibility(View.VISIBLE);
                textView47.setVisibility(View.VISIBLE);
                textView48.setVisibility(View.VISIBLE);
                textView36.setVisibility(View.VISIBLE);
                textView80.setVisibility(View.VISIBLE);
                textView82.setVisibility(View.VISIBLE);
                textView85.setVisibility(View.VISIBLE);
// Set visibility to GONE for each TextView
                JanuaryTextView.setVisibility(View.GONE);
                FebruaryTextView.setVisibility(View.GONE);
                MarchTextView.setVisibility(View.GONE);
                AprilTextView.setVisibility(View.GONE);
                MayTextView.setVisibility(View.GONE);
                JuneTextView.setVisibility(View.GONE);
                JulyTextView.setVisibility(View.GONE);
                AugustTextView.setVisibility(View.GONE);
                SeptemberTextView.setVisibility(View.GONE);
                OctoberTextView.setVisibility(View.GONE);
                NovemberTextView.setVisibility(View.GONE);
                DecemberTextView.setVisibility(View.GONE);
                textView40.setVisibility(View.GONE);}
        });

// 在点击按钮的点击事件中更新折线图和发起网络请求
        Button msBtn = view.findViewById(R.id.button11);
        msBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取选定的日期
                String selectedDate = output.getText().toString();
                Description desc = new Description();
                desc.setText("My Chart");
                chart.setDescription(desc);
                chart.setDragOffsetX(40f);

                chart.setTouchEnabled(true);
                chart.setDragEnabled(true);
                chart.setScaleEnabled(true);
                chart.setPinchZoom(true);

                Entries.clear();
                labels.clear();


                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                String email = sharedPreferences.getString("email", "");

                // 发起网络请求，将选定的日期发送到后端
                String URL = "http://0w0chen3060-2.ddns.net:7777/app_login/getErrorCount4.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    // 解析后端返回的数据
                                    JSONArray jsonArray = new JSONArray(response);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String date = jsonObject.getString("date");
                                        int errorCount = jsonObject.getInt("error_count");

                                        // 将日期和错误次数添加到 Entries 和 labels 中
                                        Entries.add(new Entry(i, errorCount));
                                        labels.add(date);
                                    }

                                    // 更新折线图
                                    updateChart();

// 更新 TextView 的文本内容
                                    int[] monthTextViewIds = {R.id.month1, R.id.month2, R.id.month3,R.id.month4,R.id.month5,R.id.month6
                                                             ,R.id.month7,R.id.month8,R.id.month9,R.id.month10,R.id.month11,R.id.month12
                                                             ,R.id.month13,R.id.month14,R.id.month15,R.id.month16,R.id.month17,R.id.month18
                                                             ,R.id.month19,R.id.month20,R.id.month21,R.id.month22,R.id.month23,R.id.month24
                                                             ,R.id.month25,R.id.month26,R.id.month27,R.id.month28,R.id.month29,R.id.month30,R.id.month31}; // Add more IDs as needed

                                    for (int i = 0; i < monthTextViewIds.length && i < Entries.size(); i++) {
                                        Entry entry = Entries.get(i);
                                        int errorCount = (int) entry.getY();

                                        TextView monthTextView = view.findViewById(monthTextViewIds[i]);
                                        monthTextView.setText(String.valueOf(errorCount));
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // 处理从服务器返回的错误响应
                                if (error instanceof NetworkError) {
                                    // 网络连接错误
                                    Toast.makeText(requireContext(), "网络连接错误", Toast.LENGTH_SHORT).show();
                                } else if (error instanceof ServerError) {
                                    // 服务器错误
                                    Toast.makeText(requireContext(), "服务器错误", Toast.LENGTH_SHORT).show();
                                } else if (error instanceof AuthFailureError) {
                                    // 身份验证失败
                                    Toast.makeText(requireContext(), "身份验证失败", Toast.LENGTH_SHORT).show();
                                } else if (error instanceof ParseError) {
                                    // 数据解析错误
                                    Toast.makeText(requireContext(), "数据解析错误", Toast.LENGTH_SHORT).show();
                                } else if (error instanceof NoConnectionError) {
                                    // 没有网络连接
                                    Toast.makeText(requireContext(), "没有网络连接", Toast.LENGTH_SHORT).show();
                                } else if (error instanceof TimeoutError) {
                                    // 请求超时
                                    Toast.makeText(requireContext(), "请求超时", Toast.LENGTH_SHORT).show();
                                } else {
                                    // 其他错误
                                    Toast.makeText(requireContext(), "发生错误", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", email);
                        params.put("selectedDate", selectedDate);

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
                requestQueue.add(stringRequest);
            }

            private void updateChart() {
                LineDataSet dataSet = new LineDataSet(Entries, "次數");
                dataSet.setValueTextSize(10f);
                LineData data = new LineData(dataSet);

                // 使用正确的 LineChart 对象
                chart.setData(data);

                XAxis xAxis = chart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
                xAxis.setAxisMaximum(labels.size() - 1);

                chart.invalidate();

                // 默认显示折线图
                chart.setVisibility(View.VISIBLE);
                Chart.setVisibility(View.GONE);
            }
        });

        Button button1 = view.findViewById(R.id.linechart);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Description desc = new Description();
                desc.setText("My Chart");
                chart.setDescription(desc);
                chart.setDragOffsetX(40f);
// Get the current date
                Calendar calendar = Calendar.getInstance();
                int currentYear = calendar.get(Calendar.YEAR);
                int currentMonth = calendar.get(Calendar.MONTH) + 1; // Months are 0-based, so add 1 to get the correct month

                // Format the current date in "yyyy-MM" format
                String selectedDate = String.format(Locale.getDefault(), "%04d-%02d", currentYear, currentMonth);
                chart.setTouchEnabled(true);
                chart.setDragEnabled(true);
                chart.setScaleEnabled(true);
                chart.setPinchZoom(true);

                Entries.clear();
                labels.clear();


                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                String email = sharedPreferences.getString("email", "");

                // 发起网络请求，将选定的日期发送到后端
                String URL = "http://0w0chen3060-2.ddns.net:7777/app_login/getErrorCount4.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    // 解析后端返回的数据
                                    JSONArray jsonArray = new JSONArray(response);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String date = jsonObject.getString("date");
                                        int errorCount = jsonObject.getInt("error_count");


                                        // 将日期和错误次数添加到 Entries 和 labels 中
                                        Entries.add(new Entry(i, errorCount));
                                        labels.add(date);

                                    }

                                    // 更新折线图
                                    updateChart();

// 更新 TextView 的文本内容
                                    int[] monthTextViewIds = {R.id.month1, R.id.month2, R.id.month3,R.id.month4,R.id.month5,R.id.month6
                                            ,R.id.month7,R.id.month8,R.id.month9,R.id.month10,R.id.month11,R.id.month12
                                            ,R.id.month13,R.id.month14,R.id.month15,R.id.month16,R.id.month17,R.id.month18
                                            ,R.id.month19,R.id.month20,R.id.month21,R.id.month22,R.id.month23,R.id.month24
                                            ,R.id.month25,R.id.month26,R.id.month27,R.id.month28,R.id.month29,R.id.month30,R.id.month31}; // Add more IDs as needed

                                    for (int i = 0; i < monthTextViewIds.length && i < Entries.size(); i++) {
                                        Entry entry = Entries.get(i);
                                        int errorCount = (int) entry.getY();

                                        TextView monthTextView = view.findViewById(monthTextViewIds[i]);
                                        monthTextView.setText(String.valueOf(errorCount));
                                    }
                                    // Hide the TextViews
                                    TextView JanuaryTextView = view.findViewById(R.id.m1);      TextView FebruaryTextView = view.findViewById(R.id.m2);
                                    TextView MarchTextView = view.findViewById(R.id.m3);        TextView AprilTextView = view.findViewById(R.id.m4);
                                    TextView MayTextView = view.findViewById(R.id.m5);          TextView JuneTextView = view.findViewById(R.id.m6);
                                    TextView JulyTextView = view.findViewById(R.id.m7);         TextView AugustTextView = view.findViewById(R.id.m8);
                                    TextView SeptemberTextView = view.findViewById(R.id.m9);    TextView OctoberTextView = view.findViewById(R.id.m10);
                                    TextView NovemberTextView = view.findViewById(R.id.m11);    TextView DecemberTextView = view.findViewById(R.id.m12);
                                    TextView textView40 = view.findViewById(R.id.textView40);   TextView textView50 = view.findViewById(R.id.textView50);
                                    TextView textView52 = view.findViewById(R.id.textView52);   TextView textView54 = view.findViewById(R.id.textView54);
                                    TextView textView56 = view.findViewById(R.id.textView56);   TextView textView58 = view.findViewById(R.id.textView58);
                                    TextView textView59 = view.findViewById(R.id.textView59);   TextView textView62 = view.findViewById(R.id.textView62);
                                    TextView textView64 = view.findViewById(R.id.textView64);   TextView textView66 = view.findViewById(R.id.textView66);
                                    TextView textView68 = view.findViewById(R.id.textView68);   TextView textView70 = view.findViewById(R.id.textView70);

                                    textView40.setVisibility(View.GONE);    textView50.setVisibility(View.GONE);
                                    textView52.setVisibility(View.GONE);    textView54.setVisibility(View.GONE);
                                    textView52.setVisibility(View.GONE);    textView56.setVisibility(View.GONE);
                                    textView58.setVisibility(View.GONE);    textView59.setVisibility(View.GONE);
                                    textView62.setVisibility(View.GONE);    textView64.setVisibility(View.GONE);
                                    textView66.setVisibility(View.GONE);    textView68.setVisibility(View.GONE);
                                    textView70.setVisibility(View.GONE);

                                    TextView textView31 = view.findViewById(R.id.textView31);   TextView textView33 = view.findViewById(R.id.textView33);
                                    TextView textView35 = view.findViewById(R.id.textView35);   TextView textView37 = view.findViewById(R.id.textView37);
                                    TextView textView39 = view.findViewById(R.id.textView39);   TextView textView41 = view.findViewById(R.id.textView41);
                                    TextView textView43 = view.findViewById(R.id.textView43);   TextView textView45 = view.findViewById(R.id.textView45);   TextView textView42 = view.findViewById(R.id.textView42);
                                    TextView textView49 = view.findViewById(R.id.textView49);   TextView textView47 = view.findViewById(R.id.textView47);
                                    TextView textView48 = view.findViewById(R.id.textView48);   TextView textView36 = view.findViewById(R.id.textView36);
                                    TextView textView80 = view.findViewById(R.id.textView80);   TextView textView82 = view.findViewById(R.id.textView82);
                                    TextView textView85 = view.findViewById(R.id.textView85);   TextView textView38 = view.findViewById(R.id.textView38);
                                    TextView textView88 = view.findViewById(R.id.textView88);   TextView textView90 = view.findViewById(R.id.textView90);
                                    TextView textView92 = view.findViewById(R.id.textView92);   TextView textView94 = view.findViewById(R.id.textView94);
                                    TextView textView96 = view.findViewById(R.id.textView96);   TextView textView100 = view.findViewById(R.id.textView100);
                                    TextView textView98 = view.findViewById(R.id.textView98);   TextView textView102 = view.findViewById(R.id.textView102);
                                    TextView textView106 = view.findViewById(R.id.textView106); TextView textView104 = view.findViewById(R.id.textView104);
                                    TextView textView34 = view.findViewById(R.id.textView34);   TextView textView73 = view.findViewById(R.id.textView73);
                                    TextView textView75 = view.findViewById(R.id.textView75);   TextView textView77 = view.findViewById(R.id.textView77);

                                    TextView month1 = view.findViewById(R.id.month1);
                                    TextView month2 = view.findViewById(R.id.month2);
                                    TextView month3 = view.findViewById(R.id.month3);
                                    TextView month4 = view.findViewById(R.id.month4);
                                    TextView month5 = view.findViewById(R.id.month5);
                                    TextView month6 = view.findViewById(R.id.month6);
                                    TextView month7 = view.findViewById(R.id.month7);
                                    TextView month8 = view.findViewById(R.id.month8);
                                    TextView month9 = view.findViewById(R.id.month9);
                                    TextView month10 = view.findViewById(R.id.month10);
                                    TextView month11 = view.findViewById(R.id.month11);
                                    TextView month12 = view.findViewById(R.id.month12);
                                    TextView month13 = view.findViewById(R.id.month13);
                                    TextView month14 = view.findViewById(R.id.month14);
                                    TextView month15 = view.findViewById(R.id.month15);
                                    TextView month16 = view.findViewById(R.id.month16);
                                    TextView month17 = view.findViewById(R.id.month17);
                                    TextView month18 = view.findViewById(R.id.month18);
                                    TextView month19 = view.findViewById(R.id.month19);
                                    TextView month20 = view.findViewById(R.id.month20);
                                    TextView month21 = view.findViewById(R.id.month21);
                                    TextView month22 = view.findViewById(R.id.month22);
                                    TextView month23 = view.findViewById(R.id.month23);
                                    TextView month24 = view.findViewById(R.id.month24);
                                    TextView month25 = view.findViewById(R.id.month25);
                                    TextView month26 = view.findViewById(R.id.month26);
                                    TextView month27 = view.findViewById(R.id.month27);
                                    TextView month28 = view.findViewById(R.id.month28);
                                    TextView month29 = view.findViewById(R.id.month29);
                                    TextView month30 = view.findViewById(R.id.month30);
                                    TextView month31 = view.findViewById(R.id.month31);

                                    Button button10= view.findViewById(R.id.button10);
                                    Button button11= view.findViewById(R.id.button11);
                                    TextView textView32 = view.findViewById(R.id.textView32);
                                    button10.setVisibility(View.GONE);
                                    button11.setVisibility(View.GONE);
                                    textView32.setVisibility(View.GONE);

                                    month1.setVisibility(View.VISIBLE);
                                    month2.setVisibility(View.VISIBLE);
                                    month3.setVisibility(View.VISIBLE);
                                    month4.setVisibility(View.VISIBLE);
                                    month5.setVisibility(View.VISIBLE);
                                    month6.setVisibility(View.VISIBLE);
                                    month7.setVisibility(View.VISIBLE);
                                    month8.setVisibility(View.VISIBLE);
                                    month9.setVisibility(View.VISIBLE);
                                    month10.setVisibility(View.VISIBLE);
                                    month11.setVisibility(View.VISIBLE);
                                    month12.setVisibility(View.VISIBLE);
                                    month13.setVisibility(View.VISIBLE);
                                    month14.setVisibility(View.VISIBLE);
                                    month15.setVisibility(View.VISIBLE);
                                    month16.setVisibility(View.VISIBLE);
                                    month17.setVisibility(View.VISIBLE);
                                    month18.setVisibility(View.VISIBLE);
                                    month19.setVisibility(View.VISIBLE);
                                    month20.setVisibility(View.VISIBLE);
                                    month21.setVisibility(View.VISIBLE);
                                    month22.setVisibility(View.VISIBLE);
                                    month23.setVisibility(View.VISIBLE);
                                    month24.setVisibility(View.VISIBLE);
                                    month25.setVisibility(View.VISIBLE);
                                    month26.setVisibility(View.VISIBLE);
                                    month27.setVisibility(View.VISIBLE);
                                    month28.setVisibility(View.VISIBLE);
                                    month29.setVisibility(View.VISIBLE);
                                    month30.setVisibility(View.VISIBLE);
                                    month31.setVisibility(View.VISIBLE);


                                    textView34.setVisibility(View.VISIBLE);
                                    textView73.setVisibility(View.VISIBLE);
                                    textView75.setVisibility(View.VISIBLE);
                                    textView104.setVisibility(View.VISIBLE);
                                    textView77.setVisibility(View.VISIBLE);
                                    textView106.setVisibility(View.VISIBLE);
                                    textView98.setVisibility(View.VISIBLE);
                                    textView100.setVisibility(View.VISIBLE);
                                    textView102.setVisibility(View.VISIBLE);
                                    textView31.setVisibility(View.VISIBLE);
                                    textView33.setVisibility(View.VISIBLE);
                                    textView35.setVisibility(View.VISIBLE);
                                    textView37.setVisibility(View.VISIBLE);
                                    textView39.setVisibility(View.VISIBLE);
                                    textView41.setVisibility(View.VISIBLE);
                                    textView38.setVisibility(View.VISIBLE);
                                    textView88.setVisibility(View.VISIBLE);
                                    textView90.setVisibility(View.VISIBLE);
                                    textView92.setVisibility(View.VISIBLE);
                                    textView94.setVisibility(View.VISIBLE);
                                    textView96.setVisibility(View.VISIBLE);
                                    textView43.setVisibility(View.VISIBLE);
                                    textView45.setVisibility(View.VISIBLE);
                                    textView42.setVisibility(View.VISIBLE);
                                    textView49.setVisibility(View.VISIBLE);
                                    textView47.setVisibility(View.VISIBLE);
                                    textView48.setVisibility(View.VISIBLE);
                                    textView36.setVisibility(View.VISIBLE);
                                    textView80.setVisibility(View.VISIBLE);
                                    textView82.setVisibility(View.VISIBLE);
                                    textView85.setVisibility(View.VISIBLE);
// Set visibility to GONE for each TextView
                                    JanuaryTextView.setVisibility(View.GONE);
                                    FebruaryTextView.setVisibility(View.GONE);
                                    MarchTextView.setVisibility(View.GONE);
                                    AprilTextView.setVisibility(View.GONE);
                                    MayTextView.setVisibility(View.GONE);
                                    JuneTextView.setVisibility(View.GONE);
                                    JulyTextView.setVisibility(View.GONE);
                                    AugustTextView.setVisibility(View.GONE);
                                    SeptemberTextView.setVisibility(View.GONE);
                                    OctoberTextView.setVisibility(View.GONE);
                                    NovemberTextView.setVisibility(View.GONE);
                                    DecemberTextView.setVisibility(View.GONE);
                                    textView40.setVisibility(View.GONE);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // 处理从服务器返回的错误响应
                                if (error instanceof NetworkError) {
                                    // 网络连接错误
                                    Toast.makeText(requireContext(), "网络连接错误", Toast.LENGTH_SHORT).show();
                                } else if (error instanceof ServerError) {
                                    // 服务器错误
                                    Toast.makeText(requireContext(), "服务器错误", Toast.LENGTH_SHORT).show();
                                } else if (error instanceof AuthFailureError) {
                                    // 身份验证失败
                                    Toast.makeText(requireContext(), "身份验证失败", Toast.LENGTH_SHORT).show();
                                } else if (error instanceof ParseError) {
                                    // 数据解析错误
                                    Toast.makeText(requireContext(), "数据解析错误", Toast.LENGTH_SHORT).show();
                                } else if (error instanceof NoConnectionError) {
                                    // 没有网络连接
                                    Toast.makeText(requireContext(), "没有网络连接", Toast.LENGTH_SHORT).show();
                                } else if (error instanceof TimeoutError) {
                                    // 请求超时
                                    Toast.makeText(requireContext(), "请求超时", Toast.LENGTH_SHORT).show();
                                } else {
                                    // 其他错误
                                    Toast.makeText(requireContext(), "发生错误", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("email", email);
                        params.put("selectedDate", selectedDate);

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
                requestQueue.add(stringRequest);
            }

            private void updateChart() {
                LineDataSet dataSet = new LineDataSet(Entries, "次數");
                dataSet.setValueTextSize(10f);
                LineData data = new LineData(dataSet);

                // 使用正确的 LineChart 对象
                chart.setData(data);

                XAxis xAxis = chart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
                xAxis.setAxisMaximum(labels.size() - 1);

                chart.invalidate();

                // 默认显示折线图
                chart.setVisibility(View.VISIBLE);
                Chart.setVisibility(View.GONE);
            }
        });

        Button barchart = view.findViewById(R.id.barchart);
        barchart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set chart description
                Description desc = new Description();
                desc.setText("My Bar Chart");
                Chart.setDescription(desc);

                // Enable touch gestures
                Chart.setTouchEnabled(true);
                Chart.setDragEnabled(true);
                Chart.setScaleEnabled(true);
                Chart.setPinchZoom(true);

                // Create an empty BarData object
                BarData data = new BarData();

                // Add a bar to the BarData object
                List<BarEntry> entries = new ArrayList<>();


                for (int i = 0; i < errorCounts.length; i++) {
                    entries.add(new BarEntry(i, errorCounts[i]));
                }


                BarDataSet dataSet = new BarDataSet(entries, "次數");
                dataSet.setValueTextSize(10f);
                dataSet.setColor(Color.BLUE);
                data.addDataSet(dataSet);

                // Set X-axis values and labels
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

                XAxis xAxis = Chart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setGranularity(1f);

                YAxis yAxis = Chart.getAxisLeft();
                yAxis.setTextSize(15f);

                Chart.getAxisRight().setEnabled(false);

                Chart.invalidate();
                Chart.setData(data);
                // 默认显示折线图
                chart.setVisibility(View.GONE);
                Chart.setVisibility(View.VISIBLE);

                TextView JanuaryTextView = view.findViewById(R.id.m1);
                TextView FebruaryTextView = view.findViewById(R.id.m2);
                TextView MarchTextView = view.findViewById(R.id.m3);
                TextView AprilTextView = view.findViewById(R.id.m4);
                TextView MayTextView = view.findViewById(R.id.m5);
                TextView JuneTextView = view.findViewById(R.id.m6);
                TextView JulyTextView = view.findViewById(R.id.m7);
                TextView AugustTextView = view.findViewById(R.id.m8);
                TextView SeptemberTextView = view.findViewById(R.id.m9);
                TextView OctoberTextView = view.findViewById(R.id.m10);
                TextView NovemberTextView = view.findViewById(R.id.m11);
                TextView DecemberTextView = view.findViewById(R.id.m12);

                String fields = "January,February,March,April,May,June,July,August,September,October,November,December";
                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                String email = sharedPreferences.getString("email", "");

                String URL = "http://0w0chen3060-2.ddns.net:7777/app_login/getErrorCount.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                errorCounts[i] = jsonArray.getInt(i);
                            }
                            updateChart();

                            String January = jsonArray.getString(0);
                            String February = jsonArray.getString(1);
                            String March = jsonArray.getString(2);
                            String April = jsonArray.getString(3);
                            String May = jsonArray.getString(4);
                            String June = jsonArray.getString(5);
                            String July = jsonArray.getString(6);
                            String August = jsonArray.getString(7);
                            String September = jsonArray.getString(8);
                            String October = jsonArray.getString(9);
                            String November = jsonArray.getString(10);
                            String December = jsonArray.getString(11);

                            JanuaryTextView.setText(January);
                            FebruaryTextView.setText(February);
                            MarchTextView.setText(March);
                            AprilTextView.setText(April);
                            MayTextView.setText(May);
                            JuneTextView.setText(June);
                            JulyTextView.setText(July);
                            AugustTextView.setText(August);
                            SeptemberTextView.setText(September);
                            OctoberTextView.setText(October);
                            NovemberTextView.setText(November);
                            DecemberTextView.setText(December);
                            TextView textView40 = view.findViewById(R.id.textView40);
                            TextView textView50 = view.findViewById(R.id.textView50);
                            TextView textView52 = view.findViewById(R.id.textView52);
                            TextView textView54 = view.findViewById(R.id.textView54);
                            TextView textView56 = view.findViewById(R.id.textView56);
                            TextView textView58 = view.findViewById(R.id.textView58);
                            TextView textView59 = view.findViewById(R.id.textView59);
                            TextView textView62 = view.findViewById(R.id.textView62);
                            TextView textView64 = view.findViewById(R.id.textView64);
                            TextView textView66 = view.findViewById(R.id.textView66);
                            TextView textView68 = view.findViewById(R.id.textView68);
                            TextView textView70 = view.findViewById(R.id.textView70);


                        // Show the TextViews
                        JanuaryTextView.setVisibility(View.VISIBLE);
                        FebruaryTextView.setVisibility(View.VISIBLE);
                        MarchTextView.setVisibility(View.VISIBLE);
                        AprilTextView.setVisibility(View.VISIBLE);
                        MayTextView.setVisibility(View.VISIBLE);
                        JuneTextView.setVisibility(View.VISIBLE);
                        JulyTextView.setVisibility(View.VISIBLE);
                        AugustTextView.setVisibility(View.VISIBLE);
                        SeptemberTextView.setVisibility(View.VISIBLE);
                        OctoberTextView.setVisibility(View.VISIBLE);
                        NovemberTextView.setVisibility(View.VISIBLE);
                        DecemberTextView.setVisibility(View.VISIBLE);
                            textView40.setVisibility(View.VISIBLE);
                            textView50.setVisibility(View.VISIBLE);
                            textView52.setVisibility(View.VISIBLE);
                            textView54.setVisibility(View.VISIBLE);
                            textView52.setVisibility(View.VISIBLE);
                            textView56.setVisibility(View.VISIBLE);
                            textView58.setVisibility(View.VISIBLE);
                            textView59.setVisibility(View.VISIBLE);
                            textView62.setVisibility(View.VISIBLE);
                            textView64.setVisibility(View.VISIBLE);
                            textView66.setVisibility(View.VISIBLE);
                            textView68.setVisibility(View.VISIBLE);
                            textView70.setVisibility(View.VISIBLE);

                            TextView textView31 = view.findViewById(R.id.textView31);
                            TextView textView33 = view.findViewById(R.id.textView33);
                            TextView textView35 = view.findViewById(R.id.textView35);
                            TextView textView37 = view.findViewById(R.id.textView37);
                            TextView textView39 = view.findViewById(R.id.textView39);
                            TextView textView41 = view.findViewById(R.id.textView41);
                            TextView textView43 = view.findViewById(R.id.textView43);
                            TextView textView45 = view.findViewById(R.id.textView45);
                            TextView textView42 = view.findViewById(R.id.textView42);
                            TextView textView49 = view.findViewById(R.id.textView49);
                            TextView textView47 = view.findViewById(R.id.textView47);
                            TextView textView48 = view.findViewById(R.id.textView48);
                            TextView textView36 = view.findViewById(R.id.textView36);
                            TextView textView80 = view.findViewById(R.id.textView80);
                            TextView textView82 = view.findViewById(R.id.textView82);
                            TextView textView85 = view.findViewById(R.id.textView85);
                            TextView textView38 = view.findViewById(R.id.textView38);
                            TextView textView88 = view.findViewById(R.id.textView88);
                            TextView textView90 = view.findViewById(R.id.textView90);
                            TextView textView92 = view.findViewById(R.id.textView92);
                            TextView textView94 = view.findViewById(R.id.textView94);
                            TextView textView96 = view.findViewById(R.id.textView96);
                            TextView textView100 = view.findViewById(R.id.textView100);
                            TextView textView98 = view.findViewById(R.id.textView98);
                            TextView textView102 = view.findViewById(R.id.textView102);
                            TextView textView106 = view.findViewById(R.id.textView106);
                            TextView textView104 = view.findViewById(R.id.textView104);
                            TextView textView34 = view.findViewById(R.id.textView34);
                            TextView textView73 = view.findViewById(R.id.textView73);
                            TextView textView75 = view.findViewById(R.id.textView75);
                            TextView textView77 = view.findViewById(R.id.textView77);

                            TextView month1 = view.findViewById(R.id.month1);
                            TextView month2 = view.findViewById(R.id.month2);
                            TextView month3 = view.findViewById(R.id.month3);
                            TextView month4 = view.findViewById(R.id.month4);
                            TextView month5 = view.findViewById(R.id.month5);
                            TextView month6 = view.findViewById(R.id.month6);
                            TextView month7 = view.findViewById(R.id.month7);
                            TextView month8 = view.findViewById(R.id.month8);
                            TextView month9 = view.findViewById(R.id.month9);
                            TextView month10 = view.findViewById(R.id.month10);
                            TextView month11 = view.findViewById(R.id.month11);
                            TextView month12 = view.findViewById(R.id.month12);
                            TextView month13 = view.findViewById(R.id.month13);
                            TextView month14 = view.findViewById(R.id.month14);
                            TextView month15 = view.findViewById(R.id.month15);
                            TextView month16 = view.findViewById(R.id.month16);
                            TextView month17 = view.findViewById(R.id.month17);
                            TextView month18 = view.findViewById(R.id.month18);
                            TextView month19 = view.findViewById(R.id.month19);
                            TextView month20 = view.findViewById(R.id.month20);
                            TextView month21 = view.findViewById(R.id.month21);
                            TextView month22 = view.findViewById(R.id.month22);
                            TextView month23 = view.findViewById(R.id.month23);
                            TextView month24 = view.findViewById(R.id.month24);
                            TextView month25 = view.findViewById(R.id.month25);
                            TextView month26 = view.findViewById(R.id.month26);
                            TextView month27 = view.findViewById(R.id.month27);
                            TextView month28 = view.findViewById(R.id.month28);
                            TextView month29 = view.findViewById(R.id.month29);
                            TextView month30 = view.findViewById(R.id.month30);
                            TextView month31 = view.findViewById(R.id.month31);

                            month1.setVisibility(View.GONE);
                            month2.setVisibility(View.GONE);
                            month3.setVisibility(View.GONE);
                            month4.setVisibility(View.GONE);
                            month5.setVisibility(View.GONE);
                            month6.setVisibility(View.GONE);
                            month7.setVisibility(View.GONE);
                            month8.setVisibility(View.GONE);
                            month9.setVisibility(View.GONE);
                            month10.setVisibility(View.GONE);
                            month11.setVisibility(View.GONE);
                            month12.setVisibility(View.GONE);
                            month13.setVisibility(View.GONE);
                            month14.setVisibility(View.GONE);
                            month15.setVisibility(View.GONE);
                            month16.setVisibility(View.GONE);
                            month17.setVisibility(View.GONE);
                            month18.setVisibility(View.GONE);
                            month19.setVisibility(View.GONE);
                            month20.setVisibility(View.GONE);
                            month21.setVisibility(View.GONE);
                            month22.setVisibility(View.GONE);
                            month23.setVisibility(View.GONE);
                            month24.setVisibility(View.GONE);
                            month25.setVisibility(View.GONE);
                            month26.setVisibility(View.GONE);
                            month27.setVisibility(View.GONE);
                            month28.setVisibility(View.GONE);
                            month29.setVisibility(View.GONE);
                            month30.setVisibility(View.GONE);
                            month31.setVisibility(View.GONE);


                            textView34.setVisibility(View.GONE);
                            textView73.setVisibility(View.GONE);
                            textView75.setVisibility(View.GONE);
                            textView104.setVisibility(View.GONE);
                            textView77.setVisibility(View.GONE);
                            textView106.setVisibility(View.GONE);
                            textView98.setVisibility(View.GONE);
                            textView100.setVisibility(View.GONE);
                            textView102.setVisibility(View.GONE);
                            textView31.setVisibility(View.GONE);
                            textView33.setVisibility(View.GONE);
                            textView35.setVisibility(View.GONE);
                            textView37.setVisibility(View.GONE);
                            textView39.setVisibility(View.GONE);
                            textView41.setVisibility(View.GONE);
                            textView38.setVisibility(View.GONE);
                            textView88.setVisibility(View.GONE);
                            textView90.setVisibility(View.GONE);
                            textView92.setVisibility(View.GONE);
                            textView94.setVisibility(View.GONE);
                            textView96.setVisibility(View.GONE);
                            textView43.setVisibility(View.GONE);
                            textView45.setVisibility(View.GONE);
                            textView42.setVisibility(View.GONE);
                            textView49.setVisibility(View.GONE);
                            textView47.setVisibility(View.GONE);
                            textView48.setVisibility(View.GONE);
                            textView36.setVisibility(View.GONE);
                            textView80.setVisibility(View.GONE);
                            textView82.setVisibility(View.GONE);
                            textView85.setVisibility(View.GONE);

                            Button button10= view.findViewById(R.id.button10);
                            Button button11= view.findViewById(R.id.button11);
                            TextView textView32 = view.findViewById(R.id.textView32);
                            button10.setVisibility(View.GONE);
                            button11.setVisibility(View.GONE);
                            textView32.setVisibility(View.GONE);
}
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Handle error response from the server
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("fields", fields);
                        params.put("email", email);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
                requestQueue.add(stringRequest);


            }

            private void updateChart() {
                List<Entry> entries = new ArrayList<>();

                for (int i = 0; i < errorCounts.length; i++) {
                    entries.add(new Entry(i, errorCounts[i]));
                }

                LineDataSet dataSet = new LineDataSet(entries, "次數");
                dataSet.setValueTextSize(10f);
                LineData data = new LineData(dataSet);


                chart.setData(data);
                chart.invalidate();

            }
        });
            return view;
    }}

