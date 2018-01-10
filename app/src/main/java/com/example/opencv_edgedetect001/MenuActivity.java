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
    private int soundBankNumber;
    IntStringVector isv_AllAvaliSounds;

    FrameLayout achtungFrame;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        isv_AllAvaliSounds = intent.getParcelableExtra("theIntStringVector");
        soundBankCollection = new IntStringVector[10];
        soundBankNumber = intent.getIntExtra("soundBankNumber",0);
        soundBankCollection[0] = intent.getParcelableExtra("soundbank_0");
        soundBankCollection[1] = intent.getParcelableExtra("soundbank_1");
        soundBankCollection[2] = intent.getParcelableExtra("soundbank_2");
        soundBankCollection[3] = intent.getParcelableExtra("soundbank_3");
        soundBankCollection[4] = intent.getParcelableExtra("soundbank_4");
        soundBankCollection[5] = intent.getParcelableExtra("soundbank_5");
        soundBankCollection[6] = intent.getParcelableExtra("soundbank_6");
        soundBankCollection[7] = intent.getParcelableExtra("soundbank_7");
        soundBankCollection[8] = intent.getParcelableExtra("soundbank_8");
        soundBankCollection[9] = intent.getParcelableExtra("soundbank_9");


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
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("theIntStringVector", isv_AllAvaliSounds);
        intent.putExtra("soundBankNumber",soundBankNumber);
        intent.putExtra("soundbank_0",soundBankCollection[0]);
        intent.putExtra("soundbank_1",soundBankCollection[1]);
        intent.putExtra("soundbank_2",soundBankCollection[2]);
        intent.putExtra("soundbank_3",soundBankCollection[3]);
        intent.putExtra("soundbank_4",soundBankCollection[4]);
        intent.putExtra("soundbank_5",soundBankCollection[5]);
        intent.putExtra("soundbank_6",soundBankCollection[6]);
        intent.putExtra("soundbank_7",soundBankCollection[7]);
        intent.putExtra("soundbank_8",soundBankCollection[8]);
        intent.putExtra("soundbank_9",soundBankCollection[9]);
        startActivity(intent);
    }

    public void loadInstruments(View view) {
        Intent intent = new Intent(this, InstrumentsActivity.class);
        intent.putExtra("theIntStringVector", isv_AllAvaliSounds);
        intent.putExtra("soundBankNumber",soundBankNumber);
        intent.putExtra("soundbank_0",soundBankCollection[0]);
        intent.putExtra("soundbank_1",soundBankCollection[1]);
        intent.putExtra("soundbank_2",soundBankCollection[2]);
        intent.putExtra("soundbank_3",soundBankCollection[3]);
        intent.putExtra("soundbank_4",soundBankCollection[4]);
        intent.putExtra("soundbank_5",soundBankCollection[5]);
        intent.putExtra("soundbank_6",soundBankCollection[6]);
        intent.putExtra("soundbank_7",soundBankCollection[7]);
        intent.putExtra("soundbank_8",soundBankCollection[8]);
        intent.putExtra("soundbank_9",soundBankCollection[9]);
        startActivity(intent);
    }

    public void loadTutorial(View view) {
        Intent intent = new Intent(this, TutorialActivity.class);
        intent.putExtra("theIntStringVector", isv_AllAvaliSounds);
        intent.putExtra("soundBankNumber",soundBankNumber);
        intent.putExtra("soundbank_0",soundBankCollection[0]);
        intent.putExtra("soundbank_1",soundBankCollection[1]);
        intent.putExtra("soundbank_2",soundBankCollection[2]);
        intent.putExtra("soundbank_3",soundBankCollection[3]);
        intent.putExtra("soundbank_4",soundBankCollection[4]);
        intent.putExtra("soundbank_5",soundBankCollection[5]);
        intent.putExtra("soundbank_6",soundBankCollection[6]);
        intent.putExtra("soundbank_7",soundBankCollection[7]);
        intent.putExtra("soundbank_8",soundBankCollection[8]);
        intent.putExtra("soundbank_9",soundBankCollection[9]);
        startActivity(intent);
    }
}
