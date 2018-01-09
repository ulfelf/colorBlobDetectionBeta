package com.example.opencv_edgedetect001;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

/**
 * Created by Fredrik on 1/9/2018.
 */

public class MenuActivity extends AppCompatActivity {

    ImageButton achtung;
    ImageButton play;
    ImageButton instrument;
    ImageButton tutorial;

    FrameLayout achtungFrame;
    IntStringVector intStringVector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent getenGoran = getIntent();
        intStringVector = getenGoran.getParcelableExtra("theIntStringVector");
        achtung = findViewById(R.id.ib_attribution);
        play = findViewById(R.id.ib_play);
        instrument = findViewById(R.id.ib_instruments);
        tutorial = findViewById(R.id.ib_tutorial);
        achtungFrame = findViewById(R.id.achtungframe);

        achtung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO visa vartifrån våra bilder och ljud kommer
                if(achtungFrame.getVisibility() == View.INVISIBLE)
                {
                    achtungFrame.setVisibility(View.VISIBLE);
                } else
                {
                    achtungFrame.setVisibility(View.INVISIBLE);
                }


            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Öppna själva "spelet" aktiviteten
            }
        });

        instrument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Öppna "Instrument" aktiviteten
            }
        });

        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Öppna "Tutorial" aktiviteten
            }
        });

    }
}
