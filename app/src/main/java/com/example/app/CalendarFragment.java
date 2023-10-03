package com.example.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment {

    private CalendarAdapter calendarAdapter;
    private Calendar currentCalendar;
    private Date selectedDate; // 选中的日期

    private TextView calendarTitle;
    private GridView calendarGridView;
    private LinearLayout scheduleLayout;
    private ScrollView scheduleScrollView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calender, container, false);

        // 初始化日历视图元件
        calendarTitle = view.findViewById(R.id.calendarTitle);
        calendarGridView = view.findViewById(R.id.calendarGridView);
        scheduleLayout = view.findViewById(R.id.scheduleLayout);
        scheduleScrollView = view.findViewById(R.id.scheduleScrollView);

        // 初始化日历
        currentCalendar = Calendar.getInstance();
        updateCalendarTitle();
        setupCalendarGrid();

        // 日历日期点击事件
        calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 处理日期点击事件
                selectedDate = (Date) parent.getItemAtPosition(position);
                calendarAdapter.setSelectedDate(selectedDate); // 设置选中的日期
                calendarAdapter.notifyDataSetChanged(); // 刷新日历视图

                // 显示滚动式画面
                showSchedule(selectedDate);
            }
        });

        return view;
    }

    // 更新日历标题
    private void updateCalendarTitle() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        calendarTitle.setText(dateFormat.format(currentCalendar.getTime()));
    }

    // 设置日历视图的 GridView
    private void setupCalendarGrid() {
        calendarAdapter = new CalendarAdapter();
        calendarGridView.setAdapter(calendarAdapter);

        currentCalendar.set(Calendar.DAY_OF_MONTH, 1); // 设置为当前月份的第一天
        calendarAdapter.setCalendar(currentCalendar);
    }

    // 日历视图的 Adapter
    private class CalendarAdapter extends BaseAdapter {
        private Calendar calendar;
        private List<Date> dateList; // 日期列表
        private Date selectedDate; // 选中的日期

        public void setCalendar(Calendar calendar) {
            this.calendar = calendar;
            this.dateList = calculateDateList(); // 根据日历计算日期列表
            notifyDataSetChanged();
        }

        public void setSelectedDate(Date selectedDate) {
            this.selectedDate = selectedDate;
        }

        private List<Date> calculateDateList() {
            List<Date> dates = new ArrayList<>();
            Calendar calendar = (Calendar) this.calendar.clone();
            calendar.set(Calendar.DAY_OF_MONTH, 1); // 设置为当前月份的第一天

            int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // 当月的天数

            // 计算日期列表
            for (int i = 0; i < maxDays; i++) {
                dates.add(calendar.getTime());
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }

            return dates;
        }

        @Override
        public int getCount() {
            if (dateList != null) {
                return dateList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (dateList != null && position >= 0 && position < dateList.size()) {
                return dateList.get(position);
            }
            return null;
        }

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

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_calendar, parent, false);
            }

            TextView dateTextView = convertView.findViewById(R.id.dateTextView);
            View dotView = convertView.findViewById(R.id.dotView);

            Date currentDate = (Date) getItem(position);
            if (currentDate != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);

                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                dateTextView.setVisibility(View.VISIBLE);
                dateTextView.setText(String.valueOf(dayOfMonth));

                // 判断是否是选中的日期，并设置红点的可见性
                if (selectedDate != null && isSameDay(currentDate, selectedDate)) {
                    dotView.setVisibility(View.VISIBLE); // 显示红点
                } else {
                    dotView.setVisibility(View.GONE); // 隐藏红点
                }
            }

            return convertView;
        }
    }

    // 判断两个日期是否是同一天
    private boolean isSameDay(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
                calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
    }

    // 显示滚动式画面
    private void showSchedule(Date date) {
        // 清空之前的内容
        scheduleLayout.removeAllViews();

        // 创建滚动式画面的内容布局
        LinearLayout contentLayout = new LinearLayout(getContext());
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams contentLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        // 设置滚动式画面的内容布局参数
        contentLayout.setLayoutParams(contentLayoutParams);

        // 设置小时的间隔
        int startHour = 0; // 起始小时
        int endHour = 24; // 结束小时
        int interval = 1; // 间隔（小时）
        int numHours = (endHour - startHour) / interval;

        // 添加每小时的时段布局
        for (int i = 0; i < numHours; i++) {
            LinearLayout timeSlotLayout = new LinearLayout(getContext());
            timeSlotLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams timeSlotLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            timeSlotLayout.setLayoutParams(timeSlotLayoutParams);

            // 添加时段的TextView
            for (int j = 0; j < 4; j++) {
                TextView timeSlotTextView = new TextView(getContext());
                LinearLayout.LayoutParams timeSlotTextViewParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1
                );
                timeSlotTextView.setLayoutParams(timeSlotTextViewParams);
                timeSlotTextView.setText(String.format("%02d:%02d", startHour + i * interval, j * 15));
                timeSlotLayout.addView(timeSlotTextView);
            }

            // 将时段布局添加到滚动式画面的内容布局中
            contentLayout.addView(timeSlotLayout);
        }

        // 将滚动式画面的内容布局添加到ScrollView中
        scheduleScrollView.removeAllViews();
        scheduleScrollView.addView(contentLayout);

        // 显示滚动式画面
        scheduleLayout.setVisibility(View.VISIBLE);
    }
}
