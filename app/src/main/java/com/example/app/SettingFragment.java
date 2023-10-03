package com.example.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.app.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SettingFragment extends Fragment {

    private CalendarAdapter calendarAdapter;
    private Calendar currentCalendar;

    private TextView calendarTitle;
    private ImageButton previousMonthButton;
    private ImageButton nextMonthButton;
    private GridView calendarGridView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        // 初始化日曆視圖元件
        calendarTitle = view.findViewById(R.id.calendarTitle);
        previousMonthButton = view.findViewById(R.id.previousMonthButton);
        nextMonthButton = view.findViewById(R.id.nextMonthButton);
        calendarGridView = view.findViewById(R.id.calendarGridView);

        // 初始化日曆
        currentCalendar = Calendar.getInstance();
        updateCalendarTitle();
        setupCalendarGrid();

        // 上一個月按鈕點擊事件
        previousMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalendar.add(Calendar.MONTH, -1);
                updateCalendarTitle();
                calendarAdapter.setCalendar(currentCalendar);
            }
        });

        // 下一個月按鈕點擊事件
        nextMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalendar.add(Calendar.MONTH, 1);
                updateCalendarTitle();
                calendarAdapter.setCalendar(currentCalendar);
            }
        });

        // 日曆日期點擊事件
        calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 在這裡處理日期點擊事件
                // 可以根據你的需求顯示選擇的日期或設置排程提醒等功能
                // ...
            }
        });

        return view;
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
            this.dateList = calculateDateList(); // 根据日历计算日期列表
            notifyDataSetChanged();
        }

        private List<Date> calculateDateList() {
            List<Date> dates = new ArrayList<>();
            Calendar calendar = (Calendar) this.calendar.clone();
            calendar.set(Calendar.DAY_OF_MONTH, 1); // 設置為當前月份的第一天

            int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // 当月的天数

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
            // 檢查是否重用了 convertView，如果没有則創建新的
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_calendar, parent, false);
            }

            TextView dateTextView = convertView.findViewById(R.id.dateTextView);
            TextView weekdayTextView = convertView.findViewById(R.id.weekdayTextView);
            View dotView = convertView.findViewById(R.id.dotView);

            // 獲取該位置的日期數據
            Date currentDate = (Date) getItem(position);
            if (currentDate != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);

                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

                dateTextView.setVisibility(View.VISIBLE);
                dateTextView.setText(String.valueOf(dayOfMonth));

                String[] weekdays = {"日", "一", "二", "三", "四", "五", "六"};
                int dayIndex = (dayOfWeek + 5) % 7; // 轉換為星期日到星期六的索引
                weekdayTextView.setText(weekdays[dayIndex]);

                if (dayIndex == 0) {
                    // 如果是星期日，使用红色字體顯示
                    weekdayTextView.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.red));
                } else {
                    // 如果是其他工作日，使用默認的字體颜色
                    weekdayTextView.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.black));
                }
            }

            return convertView;
        }
    }
}
