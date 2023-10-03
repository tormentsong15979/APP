package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private Button imageRecognitionButton;
    private Button realtimeRecognitionButton;
    private LinearLayout imageLayout;
    private ImageView image1;
    private ImageView image2;
    private FrameLayout pageContainer;

    private boolean isImageLayoutVisible = false;
    private Button handButton;
    private Button bodyButton;
    private Button operatebutton;

    private Button postButton;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageRecognitionButton = view.findViewById(R.id.hand_button);
        realtimeRecognitionButton = view.findViewById(R.id.body_button);
        imageLayout = view.findViewById(R.id.image_layout);
        image1 = view.findViewById(R.id.image1);
        image2 = view.findViewById(R.id.image2);
        pageContainer = view.findViewById(R.id.page_container);
        handButton = view.findViewById(R.id.hand_button);
        bodyButton = view.findViewById(R.id.body_button);
        postButton = view.findViewById(R.id.post_button);
        operatebutton = view.findViewById(R.id.operate_button);

        imageRecognitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageLayout.setVisibility(View.VISIBLE);
                image1.setVisibility(View.VISIBLE);
                image2.setVisibility(View.GONE);
            }
        });

        realtimeRecognitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageLayout.setVisibility(View.VISIBLE);
                image1.setVisibility(View.GONE);
                image2.setVisibility(View.VISIBLE);
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowPosts.class);
                startActivity(intent);
            }
        });

        operatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), operateActivity.class);
                startActivity(intent);
            }
        });

//        return view;
        Button button7 = view.findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个意图，以跳转到 MainActivity
                Intent intent = new Intent(getActivity(), MainActivitymove.class);

                // 启动 MainActivity
                startActivity(intent);
            }
        });
    }

    public void onImageRecognitionButtonClick(View view) {
        image1.setVisibility(View.VISIBLE);
        image2.setVisibility(View.GONE);
    }

    public void onRealtimeRecognitionButtonClick(View view) {
        image1.setVisibility(View.GONE);
        image2.setVisibility(View.VISIBLE);
    }

}
