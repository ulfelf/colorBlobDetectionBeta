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

//All sounds are loaded into a SoundPool in this activity
//file name, resource ID and SoundPool ID are saved in an IntStringVector.
//The SoundPool and the IntStringVector are stored in a SoundKeeper, that acts like an ugly global variable.
public class LoadingActivity extends AppCompatActivity {
    public SoundPool soundPool; //Används för att spela upp flera ljud samtidigt
    private SoundKeeper soundKeeper;            //this ugly bugger uses a global static variable to carry information between activities. Parceable is a better approach, but difficult.
    private Context thisContext;                //A "mAppContext" might be abled to replace this thingy, read the manual.
    IntStringVector intStringVector;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisContext = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_loader);
        //Save a config file for blob detection. Sadly, this is the only way to pass parametric information to blob detection
        FileTool.configFileSaviuor(this, getString(R.string.blobConfigFileName));
        AudioAttributes aa = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(aa)
                .build();

        //This listener will be used later, but now is not the time.
        SoundPool.OnLoadCompleteListener loadingDoneListener = new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                //Change the loading text message
                ((TextView)findViewById(R.id.textView)).setText(R.string.loader_text_done);
                //Update the progress bar
                progressBarUpdate(10,10);
                //put information into the SoundKeeper, it will carry the info to the next activity
                soundKeeper = new SoundKeeper(soundPool, intStringVector);
                //All is loaded, move to next activity
                Intent goToMain = new Intent(thisContext, MainActivity.class);
                startActivity(goToMain);
            }
        };
        //ToDo: säkerställ att getAllRawResources() körts klart innan loadInUITHread(...)
        //Load file name and reource ID into the IntStringVector
        this.intStringVector = getAllRawResources();
        loadInUITHread(soundPool, intStringVector, 1, loadingDoneListener);
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

    
    //Load all sounds to the soundpool -do it in the UI thread (SoundPool.load(...) is thread safe)
    protected boolean loadInUITHread(SoundPool soundPool, IntStringVector intStringVector, int priority, SoundPool.OnLoadCompleteListener loadingDoneListener) {
        boolean returnValue = false;
        int totalNumberOfSoundFilesToRead = 0;
        int soundFilesReadSoFar = 0;
        if((soundPool!=null) && (intStringVector!=null)) {
            totalNumberOfSoundFilesToRead = intStringVector.length();
            //make sure there is no listerer for loadingcomplete. We will activate a listener while we read the last of the sound files
            soundPool.setOnLoadCompleteListener(null);
            //Load the files into the soundpool
            //put the sound-pool-id for each loaded sound into the IntStringVector
            for (int i = 0; i < intStringVector.length(); i++) {
                intStringVector.setSoundPoolId(i, soundPool.load(this, intStringVector.getResouceID(i), priority));
                soundFilesReadSoFar++;
                //update the progressbar
                if (soundFilesReadSoFar % 3 == 0) {
                    progressBarUpdate(totalNumberOfSoundFilesToRead, soundFilesReadSoFar);
                }
                if(i==intStringVector.length()-1){
                    //This is the last sound-file to load, enable callback for loading complete:
                    soundPool.setOnLoadCompleteListener(loadingDoneListener);
                }
            }
            returnValue = true;
        }else{
            for(int i=0;i<500;i++){
                System.out.println("soundInformation var NULL");
            }
        }
        return returnValue;
    }

    private void progressBarUpdate(int totalNumberOfSoundFilesToRead, int soundFilesReadSoFar){
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

    //Get resourceID and file name of all the files in the resourcefolder "raw"
    //Do not save information about broken files.
    public static IntStringVector getAllRawResources(){
        java.lang.reflect.Field[] rawResources = R.raw.class.getFields();
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
        //how many filename/resourceid combinations were broken?
        int failedResourceReads = 0;
        for(int i=0;i<likelyResourceIds.length;i++){
            if(likelyResourceIds[i]==-1){
                failedResourceReads++;
            }
        }
        //filter out broken filename/resourceid combinations
        int[] resourceIds = new int[rawResources.length-failedResourceReads];
        String[] resourceNames = new String[rawResources.length-failedResourceReads];
        int resourceCounter = 0;
        for(int i=0;i<likelyResourceIds.length;i++){
            if(likelyResourceIds[i]!=-1){
                resourceIds[resourceCounter] = likelyResourceIds[i];
                resourceNames[resourceCounter]  = likelyResourceNames[i];
                resourceCounter++;
            }
        }
        //save the non-broken pairs in a new IntStringVector
        IntStringVector retVal = new IntStringVector();
        for(int i=0;i<resourceIds.length;i++){
            retVal.add(resourceIds[i], -1, resourceNames[i]);
            //ToDo: ta bort felsökningsraden
            Log.i("Ljudfil: "+resourceNames[i], String.valueOf(resourceIds[i]));
        }
        return retVal;
    }


}
