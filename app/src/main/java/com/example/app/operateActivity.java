package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class operateActivity extends AppCompatActivity {
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operate);

        viewPager = findViewById(R.id.viewPager);

        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.img);
        imageList.add(R.drawable.img_1);
        imageList.add(R.drawable.img_2);

        List<String> descriptionList = new ArrayList<>();
        descriptionList.add("1.端正坐好，保持背部挺直並放鬆肩膀");
        descriptionList.add("手指與琴鍵平行，不要在鍵盤邊緣懸停");
        descriptionList.add("折指");

        MyAdapter myAdapter = new MyAdapter(imageList,descriptionList);
        viewPager.setAdapter(myAdapter);
    }
}