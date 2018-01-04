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
    private String[] shortSoundName;
    private int[] octave;
    String[] tone;
    private boolean[] isSharp;

    public IntStringVector() {
        resourceID = new int[0];
        soundPoolID = new int[0];
        resourceName = new String[0];
        shortSoundName = new String[0];
        octave = new int[0];
        tone = new String[0];
        isSharp = new boolean[0];
    }
    public IntStringVector(IntStringVector intStringVector) {
        resourceID = new int[intStringVector.length()];
        soundPoolID = new int[intStringVector.length()];
        resourceName = new String[intStringVector.length()];
        shortSoundName = new String[intStringVector.length()];
        octave = new int[intStringVector.length()];
        tone = new String[intStringVector.length()];
        isSharp = new boolean[intStringVector.length()];
        for(int i=0;i<intStringVector.length();i++){
            resourceID[i] = intStringVector.getResouceID(i);
            soundPoolID[i] = intStringVector.getSoundPoolId(i);
            resourceName[i] = new String(intStringVector.getResouceName(i));
            shortSoundName[i] = new String(intStringVector.getShortSoundName(i));
            octave[i] = intStringVector.getOctave(i);
            tone[i] = intStringVector.getTone(i);
            isSharp[i] = intStringVector.isItSharp(i);
        }
    }

    public void add(int resId, int soundId, String soundName){
        if(soundName==null){
            soundName = "";
        }
        int[] tempResId = resourceID.clone();
        int[] tempSoundId = soundPoolID.clone();
        String[] tempStr = resourceName.clone();
        String[] tempShortSoundName = shortSoundName.clone();
        int[] tempOctave = octave.clone();
        String[] tempTone = tone.clone();
        boolean[] TempIsSharp = isSharp.clone();

        resourceID = new int[tempResId.length+1];
        soundPoolID = new int[tempResId.length+1];
        resourceName = new String[tempResId.length+1];
        shortSoundName = new String[tempResId.length+1];
        octave = new int[tempResId.length+1];
        tone = new String[tempResId.length+1];
        isSharp = new boolean[tempResId.length+1];

        for(int i=0;i<tempResId.length;i++){
            resourceID[i] = tempResId[i];
            soundPoolID[i] = tempSoundId[i];
            resourceName[i] = tempStr[i];
            shortSoundName[i] = tempShortSoundName[i];
            octave[i] = tempOctave[i];
            tone[i] = tempTone[i];
            isSharp[i] = TempIsSharp[i];
        }
        resourceID[tempResId.length] = resId;
        soundPoolID[tempResId.length] = soundId;
        resourceName[tempResId.length] = soundName;
        shortSoundName[tempResId.length] = "";
        octave[tempResId.length] = 0;
        tone[tempResId.length] = "E";
        isSharp[tempResId.length] = false;
        getShortSoundNameOctaveIsSharp(tempResId.length);
    }

    public int length(){
        return resourceID.length;
    }

    public int getResouceID(int index){return this.resourceID[index];}
    public boolean setSoundPoolId(int index, int soundID){
        if(this.soundPoolID.length>index){
            this.soundPoolID[index] = soundID;
            return  true;
        }else{
            return false;
        }
    }
    public int getSoundPoolId(int index){return this.soundPoolID[index];}
    public String getResouceName(int index){return this.resourceName[index];}
    public String getShortSoundName(int index){return this.shortSoundName[index];}
    public int getOctave(int index){return this.octave[index];}
    public String getTone(int index){return this.tone[index];}
    public boolean isItSharp(int index){return this.isSharp[index];}


    //interpret the resource file name, for example "shortbass_1_a_s", and
    //break it down into "shortbass", 1, "a" and true/false.
    private void getShortSoundNameOctaveIsSharp(int index) {
        if(resourceName!=null) {
            if (resourceName[index] != null) {
                //put the filename (for example "shortbass_1_a_s") in remainder
                String remainder = resourceName[index];
                //Create and initiate a String[]. It will look like {"shortbass", "1", "a", "s"},
                //once it's complete. Worst case: {"","","",""}
                String[] shortnameOctaveToneSharp = new String[4];
                for (int i = 0; i < shortnameOctaveToneSharp.length; i++) {
                    shortnameOctaveToneSharp[i] = "";
                }
                //Interpret the resourcename, put values in the String[]
                for (int i = 0; i < shortnameOctaveToneSharp.length; i++) {
                    shortnameOctaveToneSharp[i] = subStringUntilChar(remainder, '_');
                    if ((shortnameOctaveToneSharp[i].length() > 0) && ((remainder.length() > shortnameOctaveToneSharp[i].length() + 2))) {
                        //When a substring has been removed, the remainder remains
                        remainder = remainder.substring(shortnameOctaveToneSharp[i].length() + 1);
                    } else {
                        break;
                    }
                }
                //put the interpreted values into their proper place
                //the short name (for example "shortbass"):
                if(shortnameOctaveToneSharp[0].length()<1){
                    shortSoundName[index] = "unreadable";
                }else{
                    shortSoundName[index] = shortnameOctaveToneSharp[0];
                }
                //The octave, for example "1":
                if(shortnameOctaveToneSharp[1].length()<1){
                    octave[index] = 0;
                }else{
                    try {
                        octave[index] = Integer.valueOf(shortnameOctaveToneSharp[1]);
                    }catch (NumberFormatException e){
                        octave[index] = 0;
                    }
                }
                //The tone, for example "c":
                if(shortnameOctaveToneSharp[2].length()!=1){
                    tone[index] = "E";  //E stands for error. Nobody needs to know but us.
                }else{
                    tone[index] = shortnameOctaveToneSharp[2];
                }
                //Then if it's sharp or not. Assume it's not:
                isSharp[index] = false;
                if(shortnameOctaveToneSharp[3].length()==1) {
                    if (shortnameOctaveToneSharp[3].equals("s")) {
                        isSharp[index] = true;
                    }
                }
                //All done.
            }
        }
    }

    private String subStringUntilChar(String soundName, char until){
        if(soundName!=null){
            if(soundName.length()>0){
                int cutAt = soundName.indexOf(until);
                if(cutAt>0) {
                    return soundName.substring(0, cutAt);
                }
            }
        }
        return "";
    }
}
