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
    IntStringVector[] soundBankCollection;
    IntStringVector isv_AllAvaliSounds;

    FrameLayout achtungFrame;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent getenGoran = getIntent();
        isv_AllAvaliSounds = getenGoran.getParcelableExtra("theIntStringVector");
        soundBankCollection = new IntStringVector[10];
        soundBankCollection[0] = getenGoran.getParcelableExtra("soundbank_0");
        soundBankCollection[1] = getenGoran.getParcelableExtra("soundbank_1");
        soundBankCollection[2] = getenGoran.getParcelableExtra("soundbank_2");
        soundBankCollection[3] = getenGoran.getParcelableExtra("soundbank_3");
        soundBankCollection[4] = getenGoran.getParcelableExtra("soundbank_4");
        soundBankCollection[5] = getenGoran.getParcelableExtra("soundbank_5");
        soundBankCollection[6] = getenGoran.getParcelableExtra("soundbank_6");
        soundBankCollection[7] = getenGoran.getParcelableExtra("soundbank_7");
        soundBankCollection[8] = getenGoran.getParcelableExtra("soundbank_8");
        soundBankCollection[9] = getenGoran.getParcelableExtra("soundbank_9");

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

    }

    public void playMusic(View view) {
        Intent goToMain = new Intent(this, PlayActivity.class);
        goToMain.putExtra("theIntStringVector", isv_AllAvaliSounds);
        goToMain.putExtra("soundbank_0",soundBankCollection[0]);
        goToMain.putExtra("soundbank_1",soundBankCollection[1]);
        goToMain.putExtra("soundbank_2",soundBankCollection[2]);
        goToMain.putExtra("soundbank_3",soundBankCollection[3]);
        goToMain.putExtra("soundbank_4",soundBankCollection[4]);
        goToMain.putExtra("soundbank_5",soundBankCollection[5]);
        goToMain.putExtra("soundbank_6",soundBankCollection[6]);
        goToMain.putExtra("soundbank_7",soundBankCollection[7]);
        goToMain.putExtra("soundbank_8",soundBankCollection[8]);
        goToMain.putExtra("soundbank_9",soundBankCollection[9]);
        startActivity(goToMain);
    }

    public void loadInstruments(View view) {
        Intent goToMain = new Intent(this, InstrumentsActivity.class);
        goToMain.putExtra("theIntStringVector", isv_AllAvaliSounds);
        goToMain.putExtra("soundbank_0",soundBankCollection[0]);
        goToMain.putExtra("soundbank_1",soundBankCollection[1]);
        goToMain.putExtra("soundbank_2",soundBankCollection[2]);
        goToMain.putExtra("soundbank_3",soundBankCollection[3]);
        goToMain.putExtra("soundbank_4",soundBankCollection[4]);
        goToMain.putExtra("soundbank_5",soundBankCollection[5]);
        goToMain.putExtra("soundbank_6",soundBankCollection[6]);
        goToMain.putExtra("soundbank_7",soundBankCollection[7]);
        goToMain.putExtra("soundbank_8",soundBankCollection[8]);
        goToMain.putExtra("soundbank_9",soundBankCollection[9]);
        startActivity(goToMain);
    }

    public void loadTutorial(View view) {
        Intent goToMain = new Intent(this, TutorialActivity.class);
        goToMain.putExtra("theIntStringVector", isv_AllAvaliSounds);
        goToMain.putExtra("soundbank_0",soundBankCollection[0]);
        goToMain.putExtra("soundbank_1",soundBankCollection[1]);
        goToMain.putExtra("soundbank_2",soundBankCollection[2]);
        goToMain.putExtra("soundbank_3",soundBankCollection[3]);
        goToMain.putExtra("soundbank_4",soundBankCollection[4]);
        goToMain.putExtra("soundbank_5",soundBankCollection[5]);
        goToMain.putExtra("soundbank_6",soundBankCollection[6]);
        goToMain.putExtra("soundbank_7",soundBankCollection[7]);
        goToMain.putExtra("soundbank_8",soundBankCollection[8]);
        goToMain.putExtra("soundbank_9",soundBankCollection[9]);
        startActivity(goToMain);
    }
}
