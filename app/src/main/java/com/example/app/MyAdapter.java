package com.example.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import java.util.List;

public class MyAdapter extends PagerAdapter {
    private List<Integer> imageList;
    private List<String> descriptionList;

    public MyAdapter(List<Integer> imageList, List<String> descriptionList) {
        this.imageList = imageList;
        this.descriptionList = descriptionList;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.image_layout, container, false);
        ImageView image = view.findViewById(R.id.imageView);
        TextView descriptionTextView = view.findViewById(R.id.pageTextView);


        // 獲取描述
        String description = descriptionList.get(position);

        image.setImageResource(imageList.get(position));
        descriptionTextView.setText(description);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
