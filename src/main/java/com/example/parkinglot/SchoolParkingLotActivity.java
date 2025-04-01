package com.example.parkinglot;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.parkinglot.popupwindow.QRCodeResultPopup;
import com.example.parkinglot.util.GlobalVariable;
import com.example.parkinglot.util.Utils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Objects;

public class SchoolParkingLotActivity extends AppCompatActivity {
    private static final String TAG = SchoolParkingLotActivity.class.getSimpleName();

    private FrameLayout layParking1, layParking3, layParking4;
    private ImageView imgParking1, imgParking3, imgParking4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_parking_lot);

        // 제목 표시
        setTitle(getString(R.string.title_school_parking_lot));

        // 홈버튼(<-) 표시
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        this.layParking1 = findViewById(R.id.layParking1);
        this.layParking3 = findViewById(R.id.layParking3);
        this.layParking4 = findViewById(R.id.layParking4);
        this.imgParking1 = findViewById(R.id.imgParking1);
        this.imgParking3 = findViewById(R.id.imgParking3);
        this.imgParking4 = findViewById(R.id.imgParking4);

        findViewById(R.id.btnQRCode).setOnClickListener(view -> {
            // QR 코드 확인
            IntentIntegrator intentIntegrator = new IntentIntegrator(this);
            // QR 코드 인식시 소리
            intentIntegrator.setBeepEnabled(true);
            intentIntegrator.initiateScan();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            if(result != null) {
                if(result.getContents() == null) {
                    Toast.makeText(this, getString(R.string.msg_qr_code_scan_failure), Toast.LENGTH_LONG).show();
                } else {
                    // QR 코드 인식 성공
                    String code = result.getContents();

                    switch (code) {
                        case "A001":
                            if (GlobalVariable.parkingTimeMillis1 == 0) {
                                // 입차
                                this.layParking1.setBackgroundResource(R.color.red_icon_color);
                                this.imgParking1.setVisibility(View.VISIBLE);
                            } else {
                                // 출차
                                this.layParking1.setBackgroundResource(R.color.blue_icon_color);
                                this.imgParking1.setVisibility(View.GONE);
                            }
                            break;
                        case "A002":
                            // 주차공간 없음
                            break;
                        case "A003":
                            if (GlobalVariable.parkingTimeMillis3 == 0) {
                                // 입차
                                this.layParking3.setBackgroundResource(R.color.red_icon_color);
                                this.imgParking3.setVisibility(View.VISIBLE);
                            } else {
                                // 출차
                                this.layParking3.setBackgroundResource(R.color.blue_icon_color);
                                this.imgParking3.setVisibility(View.GONE);
                            }
                            break;
                        case "A004":
                            if (GlobalVariable.parkingTimeMillis4 == 0) {
                                // 입차
                                this.layParking4.setBackgroundResource(R.color.red_icon_color);
                                this.imgParking4.setVisibility(View.VISIBLE);
                            } else {
                                // 출차
                                this.layParking4.setBackgroundResource(R.color.blue_icon_color);
                                this.imgParking4.setVisibility(View.GONE);
                            }
                            break;
                        default:
                            return;
                    }

                    // QR 코드 결과 팝업창 호출
                    onPopupQRCodeResult(code);
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /* QR 코드 결과 팝업창 호출 */
    private void onPopupQRCodeResult(String code) {
        View popupView = View.inflate(this, R.layout.popup_qr_code_result, null);
        QRCodeResultPopup popup = new QRCodeResultPopup(popupView, code, System.currentTimeMillis());
        // Back 키 눌렸을때 닫기 위함
        popup.setFocusable(true);
        popup.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }
}
