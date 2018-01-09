package com.example.opencv_edgedetect001;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Fredrik on 1/9/2018.
 */

public class TutorialActivity extends AppCompatActivity {

    ImageButton play;
    ImageButton instrumentTutorial;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        play.findViewById(R.id.ib_play);
        instrumentTutorial.findViewById(R.id.ib_instrument_tutorial);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Skicka en intent till själva "spelet"
            }
        });

        instrumentTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Skicka en intent till "instrument tutorial"
            }
        });
    }
}
