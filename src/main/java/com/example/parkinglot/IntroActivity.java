package com.example.parkinglot;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.parkinglot.util.Constants;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class IntroActivity extends AppCompatActivity {
    //private static final String TAG = IntroActivity.class.getSimpleName();
    private static final String TAG = "ParkingLot";

    private boolean executed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 툴바 안보이게 하기 위함
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_intro);

        this.executed = true;

        // 인트로 화면을 1초동안 보여주고 메인으로 이동
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            this.executed = false;

            // 권한체크
            checkPermission();
        }, Constants.LoadingDelay.LONG);
    }

    @Override
    public void onBackPressed() {
        if (this.executed) {
            return;
        }

        moveTaskToBack(true);
        finish();
    }

    /* 권한 체크 */
    private void checkPermission() {
        // 권한 체크
        Dexter.withContext(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        // 권한 허용
                        goMain();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        // 권한 거부
                        /*
                        if (permissionDeniedResponse.isPermanentlyDenied()) {
                            // 완전히 거부했을 경우
                            Toast.makeText(IntroActivity.this, R.string.permission_rationale_app_use, Toast.LENGTH_SHORT).show();
                        }
                        */

                        Toast.makeText(IntroActivity.this, R.string.permission_rationale_app_use, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        // 권한 거부시 설정 다이얼로그 보여주기
                        showPermissionRationale(permissionToken);
                    }
                })
                .withErrorListener(dexterError -> {
                    // 권한설정 오류
                    Toast.makeText(this, R.string.msg_error, Toast.LENGTH_SHORT).show();
                }).check();
    }

    /* 만약 권한을 거절했을 경우, 다이얼로그 보여주기 */
    private void showPermissionRationale(PermissionToken token) {
        new AlertDialog.Builder(this)
                .setPositiveButton(R.string.dialog_allow, (dialog, which) -> {
                    // 다시 권한요청 후 거부했을 경우 (onPermissionRationaleShouldBeShown) 메서드가 다시 실행 안됨 (권한 설정 못함)
                    // 어플리케이션 설정에서 직접 권한설정을 해야함
                    token.continuePermissionRequest();
                })
                .setNegativeButton(R.string.dialog_deny, (dialog, which) -> {
                    // 권한 요청 취소
                    token.cancelPermissionRequest();
                })
                .setCancelable(false)
                .setMessage(R.string.permission_rationale_app_use)
                .show();
    }

    /* 메인으로 이동 */
    private void goMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        finish();
    }
}