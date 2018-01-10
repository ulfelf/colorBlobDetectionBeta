package com.example.opencv_edgedetect001;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gigamole.infinitecycleviewpager.VerticalInfiniteCycleViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fredrik on 1/9/2018.
 */

public class InstrumentsActivity extends AppCompatActivity {

    //TODO lägg till cycle views

    boolean isTutorial = false;
    int tutorialstate = 1;
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
    private int soundBankNumber=0;

    VerticalInfiniteCycleViewPager firstPager;
    CycleAdapter firstCycleAdapter;
    VerticalInfiniteCycleViewPager secondPager;
    CycleAdapter secondCycleAdapter;

    List<Integer> shapeList = new ArrayList();
    List<Integer> instrumentList = new ArrayList();

    IntStringVector[]  soundBankCollection;
    IntStringVector isv_AllAvaliSounds;

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

        doThis();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        doThis();
    }

    private void doThis() {
        Intent intent = getIntent();
        isTutorial = intent.getBooleanExtra("booltosendtoinstrumentstotellitwhetherornotitstutorialtime", false);
        isv_AllAvaliSounds = intent.getParcelableExtra("theIntStringVector");
        soundBankNumber = intent.getIntExtra("soundBankNumber",0);
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

        setContentView(R.layout.activity_instruments);

        ibPlay = findViewById(R.id.playbutton);
        ibAccept = findViewById(R.id.ib_accept);
        ibCancel = findViewById(R.id.ib_cancel);
        ibOpen = findViewById(R.id.ib_load);
        ibSave = findViewById(R.id.ib_save);

        TextView tutText = (TextView) findViewById(R.id.tutorialText);
        Button okButton = findViewById(R.id.b_tut_ok);
        FrameLayout tutorialFrame = findViewById(R.id.invisibleFrame);

        scrollfinger = findViewById(R.id.img_tut_scrollFinger);
        clicktotrysound = findViewById(R.id.img_tut_clickonsound);
        clicktosave = findViewById(R.id.img_tut_acceptinstrument);
        clicktosaveinstruments = findViewById(R.id.img_tut_clicktosave);
        clicktoplay = findViewById(R.id.img_tut_clicktoplay);

        if (isTutorial == true)
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

        firstPager = (VerticalInfiniteCycleViewPager) findViewById(R.id.first_cycler);
        firstCycleAdapter = new CycleAdapter(shapeList, getBaseContext());
        firstPager.setAdapter(firstCycleAdapter);

        secondPager = (VerticalInfiniteCycleViewPager) findViewById(R.id.second_cycler);
        secondCycleAdapter = new CycleAdapter(instrumentList, getBaseContext());
        secondPager.setAdapter(secondCycleAdapter);

    }

    private void populateInstruments() {

        java.lang.reflect.Field[] rawResources = R.drawable.class.getFields();
        int[] likelyResourceIds = new int[rawResources.length];
        String[] likelyResourceNames = new String[rawResources.length];
        //try getting all filename/resourceid pairs from the resourcefolder "raw"
        //some of them might be broken.
        for(int i=0;i<rawResources.length;i++) {
            likelyResourceNames[i] = rawResources[i].getName();
            try {
                likelyResourceIds[i] = rawResources[i].getInt(rawResources[i]);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                likelyResourceIds[i] = -1;
            }
        }
        int count = 0;
        for(int soundCound=0;soundCound<isv_AllAvaliSounds.length();soundCound++){
            for(int resourceCount=0;resourceCount<likelyResourceIds.length;resourceCount++) {
                if (isv_AllAvaliSounds.getResouceName(soundCound).equals(likelyResourceNames[resourceCount])) {
                    instrumentList.add(likelyResourceIds[resourceCount]);
                    break;
                }
            }
        }
        //instrumentList.add(R.drawable.trumpet);
        //instrumentList.add(R.drawable.drum);
        //instrumentList.add(R.drawable.piano);
        //instrumentList.add(R.drawable.cat);
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
            ((FrameLayout)findViewById(R.id.invisibleFrame)).setVisibility(View.INVISIBLE);
            //tutorialFrame.setVisibility(View.INVISIBLE);
            ((Button) findViewById(R.id.b_tut_ok)).setVisibility(View.INVISIBLE);
            //okButton.setVisibility(View.INVISIBLE);
            ((ImageView)findViewById(R.id.img_tut_clicktoplay)).setVisibility(View.INVISIBLE);
        }
    }


    void playTutorialstate()
    {
        switch (tutorialstate)
        {
            case 1:
                //tutText.setText(R.string.tutorial_instrument_1);
                ((TextView)findViewById(R.id.tutorialText)).setText(R.string.tutorial_instrument_1);
                ((ImageView) findViewById(R.id.img_tut_scrollFinger)).setVisibility(View.VISIBLE);
                //scrollfinger.setVisibility(View.VISIBLE);
                break;
            case 2:
                //tutText.setText(R.string.tutorial_instrument_2);
                ((TextView)findViewById(R.id.tutorialText)).setText(R.string.tutorial_instrument_2);
                //scrollfinger.setVisibility(View.INVISIBLE);
                ((ImageView) findViewById(R.id.img_tut_scrollFinger)).setVisibility(View.INVISIBLE);
                clicktotrysound.setVisibility(View.VISIBLE);
                break;
            case 3:
                //tutText.setText(R.string.tutorial_instrument_3);
                ((TextView)findViewById(R.id.tutorialText)).setText(R.string.tutorial_instrument_3);
                clicktotrysound.setVisibility(View.INVISIBLE);
                clicktosave.setVisibility(View.VISIBLE);
                break;
            case 4:
                //tutText.setText(R.string.tutorial_instrument_4);
                ((TextView)findViewById(R.id.tutorialText)).setText(R.string.tutorial_instrument_4);
                clicktosave.setVisibility(View.INVISIBLE);
                clicktosaveinstruments.setVisibility(View.VISIBLE);
                break;
            case 5:
                //tutText.setText(R.string.tutorial_instrument_5);
                ((TextView)findViewById(R.id.tutorialText)).setText(R.string.tutorial_instrument_5);
                clicktosaveinstruments.setVisibility(View.INVISIBLE);
                clicktoplay.setVisibility(View.VISIBLE);
                break;
            default:
                ((ImageView)findViewById(R.id.img_tut_clicktoplay)).setVisibility(View.INVISIBLE);
                //clicktoplay.setVisibility(View.INVISIBLE);
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

    public void saveInstrumentSet(View view) {
        Context context = getApplicationContext();
        CharSequence text = "Coming in a future update!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void loadInstrumentSet(View view) {
        Context context = getApplicationContext();
        CharSequence text = "Coming in a future update!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void acceptCombination(View view) {
        Context context = getApplicationContext();
        CharSequence text = "Coming in a future update!";
        int duration = Toast.LENGTH_SHORT;

        /*
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        */

        int colorID = shapeList.get(firstPager.getRealItem()); //ResourceID för shape/color
        int instrumentImageResID = instrumentList.get(secondPager.getRealItem()); //ResourceID för Instrument


        java.lang.reflect.Field[] rawResources = R.drawable.class.getFields();
        int[] likelyResourceIds = new int[rawResources.length];
        String[] likelyResourceNames = new String[rawResources.length];
        //try getting all filename/resourceid pairs from the resourcefolder "raw"
        //some of them might be broken.
        for(int i=0;i<rawResources.length;i++) {
            likelyResourceNames[i] = rawResources[i].getName();
            try {
                likelyResourceIds[i] = rawResources[i].getInt(rawResources[i]);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                likelyResourceIds[i] = -1;
            }
        }
        String imageName = "default";

        for(int resourceCount=0;resourceCount<likelyResourceIds.length;resourceCount++) {
            if(instrumentImageResID == likelyResourceIds[resourceCount]) {
                //hu har vi fanimej namnet
                imageName = likelyResourceNames[resourceCount];
                break;
            }
        }
        int sourceIndex = isv_AllAvaliSounds.getIndexForResourceName(imageName);
        soundBankCollection[0].replaceElement(firstPager.getRealItem(), isv_AllAvaliSounds, sourceIndex);
        soundBankCollection[0].setDetectColor(firstPager.getRealItem(), firstPager.getRealItem());
    }

    public void resetCombination(View view) {
        Context context = getApplicationContext();
        CharSequence text = "Coming in a future update!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();




    }
}
