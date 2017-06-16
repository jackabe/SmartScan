package com.smartscan.app.smartscanapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Jack_Allcock on 15/06/2017.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timer.start();
    }

    //Destroy Welcome_screen.java after it goes to next activity
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();

    }
}