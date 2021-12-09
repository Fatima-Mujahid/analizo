package com.example.analizo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.Timer;
import java.util.TimerTask;

public class AppIntro extends AppCompatActivity {

    private ViewPager mpager1;
    private LinearLayout mlinear1;
    private SliderAdapter sliderAdapter;
    private TextView[] mdots;
    private NoInternet ni;

    private int currentPage = 0;
    final int NUM_PAGES=3;
    private Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3500; // time in milliseconds between successive task executions.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_intro);

        ni=new NoInternet();
        mpager1=(ViewPager)findViewById(R.id.pager1);
        mlinear1=(LinearLayout)findViewById(R.id.linear1);

        sliderAdapter=new SliderAdapter(this);
        mpager1.setAdapter(sliderAdapter);
        addDots(0);
        mpager1.addOnPageChangeListener(pageListener);

        //Handler to move the slides according to set time
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mpager1.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

    }

    //Add dots at the bottom of screen showing number of slides and current slide
    public void addDots(int position){
        //3 dots
        mdots=new TextView[3];
        mlinear1.removeAllViews();
        for (int x=0;x<mdots.length;x++){
            mdots[x]=new TextView(this);
            mdots[x].setText(Html.fromHtml("&#8226"));
            mdots[x].setTextSize(30);
            mdots[x].setTextColor(getResources().getColor(R.color.colorGray));
            mlinear1.addView(mdots[x]);

        }
        if (mdots.length>0){
            //Set the corresponding dot color dark to indicate current slide
            mdots[position].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    //When the slide changes, correspondingly changes are made to dots (current slide etc.)
    ViewPager.OnPageChangeListener pageListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    };

    //Get started
    public void onClick(View view) {
        //If connected to internet, then movie to ProductList
        if(ni.isConnectedToInternet(AppIntro.this))
        {
            Intent intent = new Intent(AppIntro.this, ProductList.class);
            startActivity(intent);
        }
        //Otherwise display NoInternet activity
        else
        {
            Intent intent = new Intent(AppIntro.this, NoInternet.class);
            intent.putExtra("class",ProductList.class);
            startActivity(intent);
        }

    }
}
