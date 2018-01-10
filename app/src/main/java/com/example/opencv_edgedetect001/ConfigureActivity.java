package com.example.opencv_edgedetect001;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by blandfars on 2018-01-05.
 */

public class ConfigureActivity extends AppCompatActivity {
    IntStringVector intStringVector;
    IntStringVector[] soundBankCollection;
    IntStringVector isv_AllAvaliSounds;

    private int soundBankNumber;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_configure);
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
    }

    @Override
    public void onBackPressed() {
        //ToDo: what happends when back button is pressed?
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


    public void goToLoader(View view) {
        double thresholdStep = 0.0;
        double minThreshold = 0.0;
        double maxThreshold = 0.0;
        double minDistBetweenBlobs = 0.0;
        double minArea = 0.0;
        double maxArea = 0.0;
        boolean badValues = false;
        try {
            thresholdStep = Double.valueOf( ((TextView) findViewById(R.id.thresholdStep)).getText().toString());
            minThreshold = Double.valueOf( ((TextView) findViewById(R.id.minThreshold)).getText().toString());
            maxThreshold = Double.valueOf( ((TextView) findViewById(R.id.maxThreshold)).getText().toString());
            minDistBetweenBlobs = Double.valueOf( ((TextView) findViewById(R.id.minDistBetweenBlobs)).getText().toString());
            minArea = Double.valueOf( ((TextView) findViewById(R.id.minArea)).getText().toString());
            maxArea = Double.valueOf( ((TextView) findViewById(R.id.maxArea)).getText().toString());
        }catch (Exception e){
            badValues = true;
            //Toast.makeText(this, "Bad values, forget it", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        if(!badValues) {
            FileTool.configFileSaviuor(this, getString(R.string.blobConfigFileName), thresholdStep, minThreshold, maxThreshold, minDistBetweenBlobs, minArea, maxArea);
            Intent goToLoader = new Intent(this, LoadingActivity.class);
            startActivity(goToLoader);
        }else{
            //Bad values. Skip it, and go back to main.
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
    }
}
