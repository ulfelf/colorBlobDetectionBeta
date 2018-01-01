package com.example.opencv_edgedetect001;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.features2d.FeatureDetector;
import org.w3c.dom.Text;

/**
 * Created by blandfars on 2017-12-30.
 */

public class LoadingActivity extends AppCompatActivity {



    public SoundPool soundPool; //Används för att spela upp flera ljud samtidigt
    private SoundInformation soundInformation;  //All sounds from "raw" reside in here.
    private SoundLoader soundLoaderr;           //this thingy loads sounds into a SoundPool that resides in a soundinformation
    private SoundKeeper soundKeeper;            //this ugly bugger uses a global static variable to carry information between activities. Parceable is a better approach, but difficult.
    private Context thisContext;                //A "mAppContext" might be abled to replace this thingy, read the manual.

    //Reciever for local implicit intents.
    //Gets triggered when loading all the sounds is completed
    private BroadcastReceiver aBroadcastRecieverForLoaderDone = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ((TextView)findViewById(R.id.textView)).setText(R.string.loader_text_done);
            ((ProgressBar)findViewById(R.id.progressBar)).setProgress(100);
            //toTheBatMobile();
            Intent goToMain = new Intent(thisContext, MainActivity.class);
            //All is loaded, move to next activity
            startActivity(goToMain);
        }
    };

    //Reciever for local implicit intents.
    //Recive intents for progressbar update.
    private BroadcastReceiver aBroadcastRecieverForLoaderProgress = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int soundFilesReadSoFar = intent.getIntExtra("soundFilesReadSoFar",0);
            int totalNumberOfSoundFilesToRead = intent.getIntExtra("totalNumberOfSoundFilesToRead", 0);
            ((TextView)findViewById(R.id.textView)).setText(R.string.loader_text);
            //workaround to get the progressbar animated. Common problem...
            ((ProgressBar)findViewById(R.id.progressBar)).setProgress(0);
            ((ProgressBar)findViewById(R.id.progressBar)).setMax(0);
            //Set the progressbar, use new method if build version allows for it
            ((ProgressBar)findViewById(R.id.progressBar)).setMax(totalNumberOfSoundFilesToRead);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                ((ProgressBar)findViewById(R.id.progressBar)).setProgress(soundFilesReadSoFar, true);
            }else{
                ((ProgressBar)findViewById(R.id.progressBar)).setProgress(soundFilesReadSoFar);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisContext = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_loader);
        //Register recievers for local implicit intents. They act a bit like EventHandlers, but they process intents instead of events.
        LocalBroadcastManager.getInstance(this).registerReceiver(aBroadcastRecieverForLoaderDone, new IntentFilter("trigger_aBroadcastRecieverForLoader"));
        LocalBroadcastManager.getInstance(this).registerReceiver(aBroadcastRecieverForLoaderProgress, new IntentFilter("trigger_aBroadcastRecieverForLoaderProgress"));
        //Save a config file for blob detection. Sadly, this is the only way to pass parametric information to blob detection
        FileTool.configFileSaviuor(this, getString(R.string.blobConfigFileName));
        //--- --- --- --- Grejor från Fredrik --- --- --- ---
        AudioAttributes aa = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(aa)
                .build();
        //the information needed to load a sound from resources is stored in a hand made "SoundInformation"
        soundInformation = new SoundInformation(this,1,this.soundPool);
        //ToDo: Make soundInformation implement parceable instead of this global static nonsens. It's ugly. Ugly!!! (Ulf is not insane, he only gets these headaches from time to time).
        //The soundKeeper is an ugly global static object that keeps the sound information between activities
        soundKeeper = new SoundKeeper();
        SoundKeeper.setSoundInformation(soundInformation);
        soundLoaderr = new SoundLoader();
        //"SoundInformation" is passed to a hand made AsyncTask, that loads all the sounds into a SoundPool that resides inside the Soundinformation.
        soundLoaderr.execute(new SoundInformation[]{ soundInformation});

        //--- --- --- --- Grejor från Fredrik --- --- --- ---
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


}
