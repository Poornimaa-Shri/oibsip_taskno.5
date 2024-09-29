package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView timerView;
    private Button startButton, stopButton, resetButton, resumeButton;
    private Handler handler;
    private long milliseconds = 0L;
    private boolean isRunning = false;
    private long startTime = 0L, updateTime = 0L, timeBuff = 0L, currentTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerView = findViewById(R.id.timerView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        resetButton = findViewById(R.id.resetButton);
        resumeButton = findViewById(R.id.resumeButton);
        handler = new Handler();
        showStartButtonOnly();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRunning) {
                    startTime = System.currentTimeMillis() - timeBuff;
                    handler.postDelayed(runnable, 0);
                    isRunning = true;
                    showControlButtons();
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRunning) {
                    timeBuff += currentTime;
                    handler.removeCallbacks(runnable);
                    isRunning = false;
                    resumeButton.setVisibility(View.VISIBLE);
                    stopButton.setVisibility(View.GONE);
                    startButton.setVisibility(View.GONE);
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRunning) {
                    // Stop the timer if it's running
                    handler.removeCallbacks(runnable);
                    isRunning = false;
                }

                resetTimer();
                showStartButtonOnly();
            }
        });

        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRunning) {
                    startTime = System.currentTimeMillis() - timeBuff;
                    handler.postDelayed(runnable, 0);
                    isRunning = true;
                    showControlButtons();
                }
            }
        });
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            currentTime = System.currentTimeMillis() - startTime;
            updateTime = timeBuff + currentTime;
            updateTimerView();
            handler.postDelayed(this, 0);
        }
    };
    private void updateTimerView() {
        int secs = (int) (updateTime / 1000);
        int mins = secs / 60;
        secs = secs % 60;
        int milliseconds = (int) (updateTime % 1000);
        timerView.setText(String.format("%02d:%02d:%03d", mins, secs, milliseconds));
    }
    private void resetTimer() {
        startTime = 0L;
        updateTime = 0L;
        timeBuff = 0L;
        currentTime = 0L;
        timerView.setText("00:00:00");
    }
    private void showStartButtonOnly() {
        startButton.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.GONE);
        resetButton.setVisibility(View.GONE);
        resumeButton.setVisibility(View.GONE);
    }
    private void showControlButtons() {
        startButton.setVisibility(View.GONE);
        stopButton.setVisibility(View.VISIBLE);
        resetButton.setVisibility(View.VISIBLE);
        resumeButton.setVisibility(View.GONE);
    }
}
