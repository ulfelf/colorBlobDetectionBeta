package com.example.opencv_edgedetect001;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Fredrik on 1/9/2018.
 */

public class TutorialActivity extends AppCompatActivity {

    ImageButton play;
    ImageButton instrumentTutorial;
    IntStringVector[] soundBankCollection;
    IntStringVector isv_AllAvaliSounds;

    private int soundBankNumber;

    public ImageView phone;
    static int counter = 0;
    static int viewLocation[] = {0,0};
    public ArrayList<ImageView> dots = new ArrayList<>();
    final Handler handler = new Handler();

    @Override
    public void onBackPressed() {
        //ToDo: what happends when back button is pressed?
        Intent intent = new Intent(this, MenuActivity.class);
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_howtoplay);
        Intent intent = getIntent();
        isv_AllAvaliSounds = intent.getParcelableExtra("theIntStringVector");
        soundBankNumber = intent.getParcelableExtra("soundBankNumber");
        soundBankCollection = new IntStringVector[10];
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
        phone = findViewById(R.id.img_phone);
        dots.add((ImageView)findViewById(R.id.img_blackdot));
        dots.add((ImageView)findViewById(R.id.img_reddot));
        dots.add((ImageView)findViewById(R.id.img_blackdot2));
        dots.add((ImageView)findViewById(R.id.img_bluedot));
        dots.add((ImageView)findViewById(R.id.img_greendot2));
        dots.add((ImageView)findViewById(R.id.img_greendot));

        handler.post(r);

        play = findViewById(R.id.ib_play);
        instrumentTutorial = findViewById(R.id.ib_instrument_tutorial);

    }


    final Runnable r = new Runnable()
    {
        public void run()
        {
            dots.get(counter % 6).getLocationInWindow(viewLocation);
            phone.setX(viewLocation[0] - 60);
            phone.setY(viewLocation[1] - 160);
            counter++;
            //Spela upp ett ljud här, följ en specifik ordning: svart, röd, svart, blå, grön, grön

            handler.postDelayed(r, 1000);
            //int nextLocation [] = { phoneLocation[0]+1, (phoneLocation[1]-72)+1 }; //Av någon anledning måste man ta -72 för att den ska flyttas korrekt. Detta gjorde att jag gjorde designvalet att bara flytta telefonjäveln manuellt.
            /*phone.setX(viewLocation[0] - 60); Bra centrerade värden om man utgår från blackdot2s position i landskapsläge
            phone.setY(viewLocation[1] - 160);*/

        }
    };

    public void playMusicFromTutorial(View view) {
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

    public void gotoTutorial(View view) {
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
}
