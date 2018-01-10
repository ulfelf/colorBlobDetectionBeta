package com.example.opencv_edgedetect001;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gigamole.infinitecycleviewpager.VerticalInfiniteCycleViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fredrik on 1/9/2018.
 */

public class InstrumentsActivity extends AppCompatActivity {

    //TODO lägg till cycle views

    boolean isTutorial = false;
    int tutorialstate = 0;
    TextView tutText;
    Button okButton;
    FrameLayout tutorialFrame;
    ImageButton ibPlay;
    ImageButton ibAccept;
    ImageButton ibCancel;
    ImageButton ibOpen;
    ImageButton ibSave;
    ImageView scrollfinger;
    ImageView clicktotrysound;
    ImageView clicktosave;
    ImageView clicktosaveinstruments;
    ImageView clicktoplay;

    List<Integer> shapeList = new ArrayList();
    List<Integer> instrumentList = new ArrayList();

    IntStringVector[]  soundBankCollection;
    IntStringVector isv_AllAvaliSounds;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        setContentView(R.layout.activity_instruments);

        ibPlay = findViewById(R.id.playbutton);
        ibAccept = findViewById(R.id.ib_accept);
        ibCancel = findViewById(R.id.ib_cancel);
        ibOpen = findViewById(R.id.ib_load);
        ibSave = findViewById(R.id.ib_save);

        TextView tutText = findViewById(R.id.tutorialText);
        Button okButton = findViewById(R.id.b_tut_ok);
        FrameLayout tutorialFrame = findViewById(R.id.invisibleFrame);

        scrollfinger = findViewById(R.id.img_tut_scrollFinger);
        clicktotrysound = findViewById(R.id.img_tut_clickonsound);
        clicktosave = findViewById(R.id.img_tut_acceptinstrument);
        clicktosaveinstruments = findViewById(R.id.img_tut_clicktosave);
        clicktoplay = findViewById(R.id.img_tut_clicktoplay);

        if (tutorialstate == 1)
        {
            okButton.setVisibility(View.VISIBLE);
            tutorialFrame.setVisibility(View.VISIBLE);

            okButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    tutorialstate++;
                    checkTutorialstate();
                }
            });
        }

        populateShapes();
        populateInstruments();

        VerticalInfiniteCycleViewPager firstPager = (VerticalInfiniteCycleViewPager) findViewById(R.id.first_cycler);
        CycleAdapter firstCycleAdapter = new CycleAdapter(shapeList, getBaseContext());
        firstPager.setAdapter(firstCycleAdapter);

        VerticalInfiniteCycleViewPager secondPager = (VerticalInfiniteCycleViewPager) findViewById(R.id.second_cycler);
        CycleAdapter secondCycleAdapter = new CycleAdapter(instrumentList, getBaseContext());
        secondPager.setAdapter(secondCycleAdapter);

    }
    private void populateInstruments() {
        instrumentList.add(R.drawable.trumpet);
        instrumentList.add(R.drawable.drum);
        instrumentList.add(R.drawable.piano);
        instrumentList.add(R.drawable.cat);
    }

    private void populateShapes() {
        shapeList.add(R.drawable.circle_blue);
        shapeList.add(R.drawable.circle_green);
        shapeList.add(R.drawable.circle_red);
        shapeList.add(R.drawable.circle_black);
    }
        //isTutorial måste ha samma värde som den parameter som skickas in isTutorial = inparameter;


    void checkTutorialstate()
    {
        if (tutorialstate <= 5)
        {
            playTutorialstate();
        } else
        {
            tutorialFrame.setVisibility(View.INVISIBLE);
            okButton.setVisibility(View.INVISIBLE);
        }
    }


    void playTutorialstate()
    {
        switch (tutorialstate)
        {
            case 1:
                tutText.setText(R.string.tutorial_instrument_1);
                scrollfinger.setVisibility(View.VISIBLE);
                break;
            case 2:
                tutText.setText(R.string.tutorial_instrument_2);
                scrollfinger.setVisibility(View.INVISIBLE);
                clicktotrysound.setVisibility(View.VISIBLE);
                break;
            case 3:
                tutText.setText(R.string.tutorial_instrument_3);
                clicktotrysound.setVisibility(View.INVISIBLE);
                clicktosave.setVisibility(View.VISIBLE);
                break;
            case 4:
                tutText.setText(R.string.tutorial_instrument_4);
                clicktosave.setVisibility(View.INVISIBLE);
                clicktosaveinstruments.setVisibility(View.VISIBLE);
                break;
            case 5:
                tutText.setText(R.string.tutorial_instrument_5);
                clicktosaveinstruments.setVisibility(View.INVISIBLE);
                clicktoplay.setVisibility(View.VISIBLE);
                break;
            default:
                clicktoplay.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void playMusicFromInstruments(View view) {
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
}
