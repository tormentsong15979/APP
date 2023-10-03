package com.example.app;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import android.preference.PreferenceManager;

public class MetronomeFragment extends Fragment {

    private Button startButton;
    private Button stopButton;
    private MediaPlayer mediaPlayer;
    private Handler handler;
    private Runnable playBeep;
    private int intervalMillis;

    private SeekBar speedSeekBar;
    private TextView speedText;
    private EditText speedEditText;

    private SharedPreferences sharedPreferences;

    private class ViewHolder {
        public final SeekBar speedSeekBar;
        public final TextView speedText;
        public final EditText speedEditText;

        public ViewHolder(View view) {
            speedSeekBar = view.findViewById(R.id.speed_seekbar);
            speedText = view.findViewById(R.id.speed_text);
            speedEditText = view.findViewById(R.id.speed_edittext);
        }
    }

    public MetronomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_metronome, container, false);
        ViewHolder viewHolder = new ViewHolder(view);

        speedSeekBar = viewHolder.speedSeekBar;
        speedText = viewHolder.speedText;
        speedEditText = viewHolder.speedEditText;

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        // 獲取對節拍聲音的MediaPlayer引用
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.ten_hr);
        // 設置循環播放
        mediaPlayer.setLooping(true);

        // 初始化 handler 和 playBeep 變數
        handler = new Handler();
        playBeep = new Runnable() {
            @Override
            public void run() {
                // 播放節拍聲音
                mediaPlayer.start();

                // 延遲 intervalMillis 毫秒後再次調用此 Runnable
                handler.postDelayed(this, intervalMillis);
            }
        };

        // 初始化速度（預設值為 120）
        intervalMillis = 60000 / 120;
        speedSeekBar.setProgress(120);
        speedText.setText("120");

        // 從Shared Preferences中讀取先前保存的節拍值
        int savedTempo = sharedPreferences.getInt("tempo", 120);
        speedSeekBar.setProgress(savedTempo);
        speedText.setText(String.valueOf(savedTempo));

        // 監聽速度 EditText
        speedEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int speed = Integer.parseInt(s.toString().trim());
                    // 將速度限制在0到240的範圍內
                    speed = Math.max(speed, 0);
                    speed = Math.min(speed, 240);

                    // 更新速度 SeekBar 和文字
                    speedSeekBar.setProgress(speed);
                    speedText.setText(String.valueOf(speed));

                    // 更新 intervalMillis
                    intervalMillis = 60000 / speed;

                    // 將節拍值保存到Shared Preferences
                    sharedPreferences.edit().putInt("tempo", speed).apply();
                } catch (NumberFormatException e) {
                    // 如果使用者輸入無法解析為整數，則忽略它
                }
            }
        });

        // 監聽速度 SeekBar
        speedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 如果是使用者觸發的進度變化
                if (fromUser) {
                    int speed = progress;

                    // 更新速度 EditText 的內容
                    speedEditText.setText(String.valueOf(speed));

                    if (mediaPlayer != null) {
                        // 計算新的播放間隔時間
                        intervalMillis = 1000 * 60 / speed;
                        // 計算新的播放頻率
                        float playbackSpeed = (float) speed / 60.0f;
                        // 設置新的播放頻率
                        mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(playbackSpeed));
                        // 取消現有的 Handler 執行的任務
                        handler.removeCallbacks(playBeep);
                        // 在新的間隔時間之後，執行 Handler 中的任務
                        handler.postDelayed(playBeep, intervalMillis);

                        // 將節拍值保存到Shared Preferences
                        sharedPreferences.edit().putInt("tempo", speed).apply();
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // 找到開始和停止按鈕並設置它們的點擊監聽器
        startButton = view.findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 獲取顯示的速度值
                int speed = Integer.parseInt(speedText.getText().toString());

                // 設置新的播放間隔時間
                intervalMillis = 1000 * 60 / speed;
                // 計算新的播放頻率
                float playbackSpeed = (float) speed / 60.0f;
                // 設置新的播放頻率
                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(playbackSpeed));

                // Start the metronome
                handler.post(playBeep);
            }
        });

        stopButton = view.findViewById(R.id.stop_button);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 停止節拍器
                handler.removeCallbacks(playBeep);
                mediaPlayer.pause();
            }
        });

        return view;
    }
}
