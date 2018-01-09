package com.example.opencv_edgedetect001;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Fredrik on 1/9/2018.
 */

public class InstrumentsActivity extends AppCompatActivity {

    boolean isTutorial = false;
    int tutorialstate = 0;
    TextView tutText = findViewById(R.id.tutorialText);
    Button okButton = findViewById(R.id.b_tut_ok);
    FrameLayout tutorialFrame = findViewById(R.id.invisibleFrame);
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ibPlay = findViewById(R.id.playbutton);
        ibAccept = findViewById(R.id.ib_accept);
        ibCancel = findViewById(R.id.ib_cancel);
        ibOpen = findViewById(R.id.ib_load);
        ibSave = findViewById(R.id.ib_save);

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

        ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO intent till själva "spelet"
            }
        });

        ibAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO koppla samman färgsymbol och ljud när denna knapp klickas på
            }
        });

        ibCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO ställ tillbaka färgsymbolens associerade ljud till "default"
            }
        });

        ibSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Spara uppsättningen instrument
            }
        });

        ibOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Ladda en uppsättning instrument
            }
        });

        //isTutorial måste ha samma värde som den parameter som skickas in isTutorial = inparameter;
    }

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

}
