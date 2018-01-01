package com.example.opencv_edgedetect001;

import android.content.Context;
import android.media.SoundPool;
import java.io.Serializable;

/**
 * Created by blandfars on 2017-12-29.
 */

//Contains all sorts of things needed to store sound names, soundPool, resourceIDs for sounds and etc.
public class SoundInformation{
    private Context context;
    private int priority;
    private SoundPool soundPool;
    private IntStringVector intStringVector;    //Used for storing (resouceID, soundPool-ID, resourceFile-name) for each sound file loaded.


    public SoundInformation(Context context, int priority, SoundPool soundPool){
        this.context = context;
        this.priority = priority;
        this.soundPool = soundPool;
        this.intStringVector = new IntStringVector();

    }

    public Context getContext(){return this.context;}
    public void setPriority(int prio){this.priority = prio;}
    public int getPriority(){return this.priority;}
    public SoundPool getSoundPool(){return this.soundPool;}
    public void setIntStringVector(IntStringVector intStringVector){
        this.intStringVector = new IntStringVector(intStringVector);
    }
    public IntStringVector getIntStringVector(){return this.intStringVector;}




}
