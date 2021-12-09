package com.example.analizo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //Time for spash screen (in milliseconds)
    private static int SPLASH_TIME=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Handler is being used as splash screen is time controlled activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(MainActivity.this, AppIntro.class);
                startActivity(homeIntent);
                finish();
            }

        }, SPLASH_TIME);
    }
}
