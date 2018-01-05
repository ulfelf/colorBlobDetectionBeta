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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_configure);
        Intent gottenIntent = getIntent();
        intStringVector = gottenIntent.getParcelableExtra("theIntStringVector");
    }

    @Override
    public void onBackPressed() {
        //ToDo: what happends when back button is pressed?
        Intent goToMain = new Intent(this, MainActivity.class);
        goToMain.putExtra("theIntStringVector", intStringVector);
        startActivity(goToMain);
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
            Intent goToMain = new Intent(this, MainActivity.class);
            goToMain.putExtra("theIntStringVector", intStringVector);
            startActivity(goToMain);
        }
    }
}
