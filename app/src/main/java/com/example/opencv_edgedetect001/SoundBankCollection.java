package com.example.opencv_edgedetect001;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by blandfars on 2018-01-09.
 */

public class SoundBankCollection implements Parcelable {
    private static final int NO_OF_SOUNDBANKS=10;
    public static final int SOUNDS_PER_SOUNDBANK=4;
    IntStringVector[] soundbanks;
    public SoundBankCollection(){
        soundbanks = new IntStringVector[NO_OF_SOUNDBANKS];
        for(int soundBankIndex=0;soundBankIndex<NO_OF_SOUNDBANKS;soundBankIndex++){
            soundbanks[soundBankIndex] = new IntStringVector();
            for(int soundIndex=0;soundIndex<SOUNDS_PER_SOUNDBANK;soundIndex++) {
                soundbanks[soundBankIndex].add(0, 0, "default");
                soundbanks[soundBankIndex].setSoundPoolId(soundIndex, 0);
            }
        }
    }


    //Inherited overridden thingy, neccesary for parceability
    public static final Parcelable.Creator<SoundBankCollection> CREATOR
            = new Parcelable.Creator<SoundBankCollection>() {
        public SoundBankCollection createFromParcel(Parcel in) {
            return new SoundBankCollection(in);
        }
        public SoundBankCollection[] newArray(int size) {
            return new SoundBankCollection[size];
        }
    };

    public int getLength(){return NO_OF_SOUNDBANKS;}
    public IntStringVector getSoundBank(int index){return soundbanks[index];}


    @Override
    public int describeContents() {
        return 0;
    }

    //ToDo: can you get a classloader this way or not? Testing will answer this question...
    public SoundBankCollection(Parcel parcel){
        soundbanks = (IntStringVector[]) parcel.readParcelableArray(IntStringVector.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelableArray(this.soundbanks, 0);
    }
}


