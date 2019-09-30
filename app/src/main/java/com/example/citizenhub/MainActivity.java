package com.example.citizenhub;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // https://stackoverflow.com/questions/8631095/how-to-prevent-going-back-to-the-previous-activity
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Put a test key and value...
        Map<String, String> testPut = new HashMap<>();
        testPut.put("Hello", "World!");
        db.collection("test").document("testDoc").set(testPut);
        // Now retrieve it!
        db.collection("test").document("testDoc").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            if(doc.exists()) {
                                Log.d("Jack's Tag: ", doc.getData().toString());
                            }
                        }
                    }
                });
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
        // goes home
        setContentView(R.layout.activity_main);
    }

    public void goSamp(View view) {
        // goes to sample layout fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Sample frag = new Sample();
        fragmentTransaction.replace(R.id.activity_main, frag);
        fragmentTransaction.commit();

    }
}