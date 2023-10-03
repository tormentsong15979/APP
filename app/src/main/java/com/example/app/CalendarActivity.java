package com.example.app;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.widget.BaseAdapter;
import android.view.ViewGroup;

public class CalendarActivity extends AppCompatActivity {

    private CalendarAdapter calendarAdapter;
    private Calendar currentCalendar;

    private TextView calendarTitle;
    private GridView calendarGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // 初始化日曆視圖元件
        calendarTitle = findViewById(R.id.calendarTitle);
        calendarGridView = findViewById(R.id.calendarGridView);

        // 初始化日曆
        currentCalendar = Calendar.getInstance();
        updateCalendarTitle();
        setupCalendarGrid();

        // 日曆日期點擊事件
        calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 在這裡處理日期點擊事件
                Date selectedDate = (Date) parent.getItemAtPosition(position);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String selectedDateStr = dateFormat.format(selectedDate);
                Toast.makeText(CalendarActivity.this, "Selected date: " + selectedDateStr, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 更新日曆標題
    private void updateCalendarTitle() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        calendarTitle.setText(dateFormat.format(currentCalendar.getTime()));
    }

    // 設置日曆視圖的 GridView
    private void setupCalendarGrid() {
        calendarAdapter = new CalendarAdapter();
        calendarGridView.setAdapter(calendarAdapter);

        currentCalendar.set(Calendar.DAY_OF_MONTH, 1); // 設置為當前月份的第一天
        calendarAdapter.setCalendar(currentCalendar);
    }

    // 日曆視圖的 Adapter
    private class CalendarAdapter extends BaseAdapter {
        private Calendar calendar;
        private List<Date> dateList; // 日期列表

        public void setCalendar(Calendar calendar) {
            this.calendar = calendar;
            this.dateList = calculateDateList(); // 根據日曆計算日期列表
            notifyDataSetChanged();
        }

        private List<Date> calculateDateList() {
            List<Date> dates = new ArrayList<>();
            Calendar calendar = (Calendar) this.calendar.clone();
            calendar.set(Calendar.DAY_OF_MONTH, 1); // 設置為當前月份的第一天

            int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // 當月的天數

            // 計算日期列表
            for (int i = 0; i < maxDays; i++) {
                dates.add(calendar.getTime());
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }

            return dates;
        }

        // 返回項目數量（日期列表的長度）
        @Override
        public int getCount() {
            if (dateList != null) {
                return dateList.size();
            }
            return 0;
        }

        // 返回該位置的項目數據（日期）
        @Override
        public Object getItem(int position) {
            if (dateList != null && position >= 0 && position < dateList.size()) {
                return dateList.get(position);
            }
            return null;
        }

        // 返回項目 ID
        @Override
        public long getItemId(int position) {
            Date date = (Date) getItem(position);
            if (date != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                return calendar.getTimeInMillis();
            }
            return 0;
        }

        // 實現自己的 Adapter
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 檢查是否重用了 convertView，如果沒有則創建新的
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.grid_item_calendar, parent, false);
            }

            TextView dateTextView = convertView.findViewById(R.id.dateTextView);

            // 獲取該位置的日期數據
            Date currentDate = (Date) getItem(position);
            if (currentDate != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);

                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                dateTextView.setVisibility(View.VISIBLE);
                dateTextView.setText(String.valueOf(dayOfMonth));
            }

            return convertView;
        }
    }
}
