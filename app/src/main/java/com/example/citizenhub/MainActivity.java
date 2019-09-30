package com.example.citizenhub;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // https://stackoverflow.com/questions/8631095/how-to-prevent-going-back-to-the-previous-activity
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);


    }

    // https://stackoverflow.com/questions/8631095/how-to-prevent-going-back-to-the-previous-activity
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // https://stackoverflow.com/a/38607857
        DevicePolicyManager mDevicePolicyManager = (DevicePolicyManager) getSystemService(
                Context.DEVICE_POLICY_SERVICE);

        ComponentName mAdminComponentName =
                new ComponentName(this, MyDeviceAdminReceiver.class);

        mDevicePolicyManager.setLockTaskPackages(mAdminComponentName,
                new String[]{getPackageName()});

        startLockTask();
    }

    public void goHome(View view) {
        //goes home
        setContentView(R.layout.activity_main);
    }

    public void goSamp(View view) {
        setContentView(R.layout.fragment_sample);
    }

    public void goWeather(View view) {
        setContentView(R.layout.fragment_weather);
    }

    public void goMap(View view) {  setContentView(R.layout.activity_google_map);  }


}


