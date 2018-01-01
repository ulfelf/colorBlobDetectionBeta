package com.example.opencv_edgedetect001;

import android.app.Application;

/**
 * Created by blandfars on 2017-12-31.
 */

//This rather ugly class acts like a global variable that keeps its information between activities
//Not a recomended practice
//Brrr....
//ToDo: eliminate the need of this horrible class. Implement parceable somewhere instead of this... thing.
public class SoundKeeper extends Application {
    public static SoundInformation soundInformation;
    public static void setSoundInformation(SoundInformation arg){soundInformation  = arg;}
    public static SoundInformation getSoundInformation(){return soundInformation;}
}
