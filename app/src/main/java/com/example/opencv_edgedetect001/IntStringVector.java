package com.example.opencv_edgedetect001;

/**
 * Created by blandfars on 2017-12-29.
 */

//A kind of vector where each index corresponds to two Integers and one String
//Used for storing (resouceID, soundPool-ID, resourceFile-name) for each sound file loaded.
public class IntStringVector {
    private int[] resourceID;
    private int[] soundPoolID;
    private String[] resourceName;

    public IntStringVector() {
        resourceID = new int[0];
        soundPoolID = new int[0];
        resourceName = new String[0];
    }
    public IntStringVector(IntStringVector intStringVector) {
        resourceID = new int[intStringVector.length()];
        soundPoolID = new int[intStringVector.length()];
        resourceName = new String[intStringVector.length()];
        for(int i=0;i<intStringVector.length();i++){
            resourceID[i] = intStringVector.getResouceID(i);
            soundPoolID[i] = intStringVector.getSoundPoolId(i);
            resourceName[i] = new String(intStringVector.getResouceName(i));
        }
    }

    public void add(int resId, int soundId, String soundName){
        if(soundName==null){
            soundName = "";
        }
        int[] tempResId = resourceID.clone();
        int[] tempSoundId = soundPoolID.clone();
        String[] tempStr = resourceName.clone();
        resourceID = new int[tempResId.length+1];
        soundPoolID = new int[tempResId.length+1];
        resourceName = new String[tempResId.length+1];
        for(int i=0;i<tempResId.length;i++){
            resourceID[i] = tempResId[i];
            soundPoolID[i] = tempSoundId[i];
            resourceName[i] = tempStr[i];
        }
        resourceID[tempResId.length] = resId;
        soundPoolID[tempResId.length] = soundId;
        resourceName[tempResId.length] = soundName;
    }

    public int length(){
        return resourceID.length;
    }

    public int getResouceID(int index){return this.resourceID[index];}
    public int getSoundPoolId(int index){return this.soundPoolID[index];}
    public String getResouceName(int index){return this.resourceName[index];}
    public boolean setSoundPoolId(int index, int soundID){
        if(this.soundPoolID.length>index){
            this.soundPoolID[index] = soundID;
            return  true;
        }else{
            return false;
        }
    }
}
