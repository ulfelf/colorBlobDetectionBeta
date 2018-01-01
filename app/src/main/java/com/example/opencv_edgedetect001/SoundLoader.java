package com.example.opencv_edgedetect001;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by blandfars on 2017-12-29.
 */

//This thingy loads a lot of sound-files, in its own thread.
public class SoundLoader extends AsyncTask<SoundInformation,SoundInformation,SoundInformation> {
    private int soundFilesReadSoFar;
    private int totalNumberOfSoundFilesToRead;


    public SoundLoader(){super();}

    //When all is done, an IntStringVector that resides inside soundInformation (global-like object)
    //will contain {resourceID, soundPool sound ID, filename} for every soundfile in the resource folder "raw"
    @Override
    protected SoundInformation doInBackground(SoundInformation... soundInformations) {
        IntStringVector intStringVector;
        totalNumberOfSoundFilesToRead = 0;
        soundFilesReadSoFar = 0;
        intStringVector = null;
        if(soundInformations!=null) {
            if(soundInformations.length>0) {
                //Get resourceID and file name of all the files in the resourcefolder "raw"
                //put them in the IntStringVector
                intStringVector = getAllRawResources();
                totalNumberOfSoundFilesToRead = intStringVector.length();
                //Load the files intoto the soundpool
                //put the sound-pool-id for each loaded sound into the IntStringVector
                for (int i = 0; i < intStringVector.length(); i++) {
                    intStringVector.setSoundPoolId(
                            i,
                            soundInformations[0].getSoundPool().load(soundInformations[0].getContext(), intStringVector.getResouceID(i), soundInformations[0].getPriority())
                    );
                    soundFilesReadSoFar++;
                    //update the progressbar via onProgressUpdate
                    if(soundFilesReadSoFar%3==0) {
                        publishProgress(soundInformations);
                    }
                }
                //all files read, put the info in soundinformation object
                soundInformations[0].setIntStringVector(intStringVector);
            }
        }else{
            for(int i=0;i<500;i++){
                System.out.println("soundInformation var NULL");
            }
        }
        if(soundInformations!=null) {
            if (soundInformations.length > 0) {
                return soundInformations[0];
            }
        }
        return null;
    }


    //When all time consuming loading is done, this thing sends an Intent to everywhere in the app.
    //There is an IntentReciever in LoadingActivity
    @Override
    protected void onPostExecute(SoundInformation soundInformation) {
        super.onPostExecute(soundInformation);
        Intent intentColor = new Intent("trigger_aBroadcastRecieverForLoader");
        LocalBroadcastManager.getInstance(soundInformation.getContext()).sendBroadcast(intentColor);
    }


    //Sends an intent containing progress information
    //There is an IntentReciever in LoadingActivity, it updates a progressbar. Rather nifty if I might say so myself,
    //and I may.
    @Override
    protected void onProgressUpdate(SoundInformation[] soundInformations){
            Intent intent = new Intent("trigger_aBroadcastRecieverForLoaderProgress");
            intent.putExtra("soundFilesReadSoFar", soundFilesReadSoFar);
            intent.putExtra("totalNumberOfSoundFilesToRead", totalNumberOfSoundFilesToRead);
            LocalBroadcastManager.getInstance(soundInformations[0].getContext()).sendBroadcast(intent);
    }


    //Get resourceID and file name of all the files in the resourcefolder "raw"
    //Do not save information about broken files.
    private static IntStringVector getAllRawResources(){
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
