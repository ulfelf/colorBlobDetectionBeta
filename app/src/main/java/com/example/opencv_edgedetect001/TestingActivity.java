package com.example.opencv_edgedetect001;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.File;

/**
 * Created by blandfars on 2018-01-09.
 */

public class TestingActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.testreadwrite);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void writeSomeDefaultThingies(View view) {
        boolean readingOk = false;
        readingOk = FileTool.writeDefaultSoundBankFiles(this);
        ((TextView)findViewById(R.id.textView2)).setText(String.valueOf( readingOk));
    }

    public void doATestRead(View view) {
        System.out.println("klickade på knappen");
        IntStringVector tja = FileTool.readSoundBank(this, "soundbank_001.xml");
        for(int i=0;i<4;i++) {
            System.out.println(
                    tja.getResouceName(i) + ", " +
                            tja.getResouceID(i) + ", " +
                            tja.getShortSoundName(i) + ", " +
                            tja.getOctave(i) + ", " +
                            tja.getTone(i) + ", " +
                            tja.isItSharp(i) + ", " +
                            tja.getDetectColor(i)
            );
        }
    }

}
