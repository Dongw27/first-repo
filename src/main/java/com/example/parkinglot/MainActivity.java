package com.example.parkinglot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.parkinglot.util.Constants;

public class MainActivity extends AppCompatActivity {

    private BackPressHandler backPressHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(R.string.app_name);

        findViewById(R.id.layNationalParkingLot).setOnClickListener(view -> {
            // 전국 주차장 현황
            Intent intent = new Intent(this, NationalParkingLotActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.laySchoolParkingLot).setOnClickListener(view -> {
            // 학교 주차장 현황
            Intent intent = new Intent(this, SchoolParkingLotActivity.class);
            startActivity(intent);
        });

        // 종료 핸들러
        this.backPressHandler = new BackPressHandler(this);
    }

    @Override
    public void onBackPressed() {
        this.backPressHandler.onBackPressed();
    }

    /* Back Press Class */
    private class BackPressHandler {
        private final Context context;
        private Toast toast;

        private long backPressedTime = 0;

        public BackPressHandler(Context context) {
            this.context = context;
        }

        public void onBackPressed() {
            if (System.currentTimeMillis() > this.backPressedTime + (Constants.LoadingDelay.LONG * 2)) {
                this.backPressedTime = System.currentTimeMillis();

                this.toast = Toast.makeText(this.context, R.string.msg_back_press_end, Toast.LENGTH_SHORT);
                this.toast.show();
                return;
            }

            if (System.currentTimeMillis() <= this.backPressedTime + (Constants.LoadingDelay.LONG * 2)) {
                // 종료
                moveTaskToBack(true);
                finish();
                this.toast.cancel();
            }
        }
    }
}