package com.example.opencv_edgedetect001;

import android.app.Application;
import android.media.SoundPool;

/**
 * Created by blandfars on 2017-12-31.
 */

//This rather ugly class acts like a global variable that keeps its information between activities
//Not a recomended practice
//Brrr....
//ToDo: eliminate the need of this horrible class. Implement parceable somewhere instead of this... thing.
public class SoundKeeper extends Application {
    private static SoundPool soundPool;

    public SoundKeeper(SoundPool soundPool){
        this.soundPool = soundPool;
    }
    public SoundKeeper(){}
    public SoundPool getSoundPool(){return soundPool;}


    /*
    private static SoundPool soundPool;
    private static IntStringVector intStringVector;
    public SoundKeeper(SoundPool soundPool, IntStringVector intStringVector){
        this.soundPool = soundPool;
        this.intStringVector = intStringVector;
    }
    public SoundKeeper(){}
    public SoundPool getSoundPool(){return soundPool;}
    public IntStringVector getIntStringVector(){return intStringVector;}
    */
}
