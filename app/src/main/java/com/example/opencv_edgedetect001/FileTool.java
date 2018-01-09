package com.example.opencv_edgedetect001;



import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static android.support.v4.content.ContextCompat.getExternalFilesDirs;

/**
 * Created by blandfars on 2017-12-28.
 */

public class FileTool {


    //Takes a filename, returns the app-specific document filepaht (all files stored here are erased upon
    //de-installation of the app)
    public static String getAppSpecificDocumentPath(Context context, String fileName) {
        //create an absolute path using the filename and some gadgetry
        String filePath = "";
        File fs[] = getExternalFilesDirs(context, Environment.DIRECTORY_DOCUMENTS);

        if (fs.length > 0) {
            filePath = fs[0].getAbsolutePath();
            filePath = filePath + "/" + fileName;
        }
        return filePath;
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

    public static boolean configFileExists(Context context, String fileName){
        boolean retVal = false;
        String filePath = getAppSpecificDocumentPath(context, fileName);
        File f;
        f = new File(filePath);
        if (f.exists()) {
            retVal = true;
        }
        return retVal;
    }


    //Saves a config file that can later be used by a org.opencv.features2d.FeatureDetector.read()
    //Sadly, this is the ONLY way to get parameters to the function/object/thingy.
    public static boolean configFileSaviuor(Context context, String fileName) {
        //create an absolute path using the filename and some gadgetry
        //ToDo: default should be false?
        boolean writingWentWell = true;
        String filePath = getAppSpecificDocumentPath(context, fileName);
        if (filePath.length() > 3) {
            //if file exist -erase it and create a new one.
            //if it does not exist, just create it
            File f;
            //boolean fileIsReady = true;
            f = new File(filePath);
            if (f.exists()) {
                writingWentWell = f.delete();
            }
            if (writingWentWell) {
                try {
                    writingWentWell = f.createNewFile();
                } catch (IOException e) {
                    writingWentWell = false;
                    e.printStackTrace();
                }
            }
            //Write the file
            if (writingWentWell) {
                try {
                    FileOutputStream fout = new FileOutputStream(f);
                    try {
                        OutputStreamWriter someWriter = new OutputStreamWriter(fout, "UTF-8");
                        someWriter.write("<?xml version=\"" + 1.0 + "\"?>\n");
                        someWriter.write("<opencv_storage>\n");
                        someWriter.write("<format>3</format>\n");
                        //someWriter.write("<thresholdStep>10.</thresholdStep>\n");
                        someWriter.write("<thresholdStep>30.</thresholdStep>\n");
                        someWriter.write("<minThreshold>30.</minThreshold>\n");
                        someWriter.write("<maxThreshold>220.</maxThreshold>\n");
                        someWriter.write("<minRepeatability>2</minRepeatability>\n");
                        someWriter.write("<minDistBetweenBlobs>10.</minDistBetweenBlobs>\n");
                        someWriter.write("<filterByColor>1</filterByColor>\n");
                        someWriter.write("<blobColor>0</blobColor>\n");
                        someWriter.write("<filterByArea>1</filterByArea>\n");
                        //someWriter.write("<minArea>25.</minArea>\n");
                        //someWriter.write("<maxArea>5000.</maxArea>\n");
                        //someWriter.write("<minArea>1800.</minArea>\n");
                        //someWriter.write("<maxArea>7000.</maxArea>\n");
                        someWriter.write("<minArea>1800.</minArea>\n");
                        someWriter.write("<maxArea>7000.</maxArea>\n");
                        someWriter.write("<filterByCircularity>0</filterByCircularity>\n");
                        someWriter.write("<minCircularity>8.0000001192092896e-01</minCircularity>\n");
                        someWriter.write("<maxCircularity>3.4028234663852886e+38</maxCircularity>\n");
                        //someWriter.write("<filterByInertia>1</filterByInertia>\n");
                        someWriter.write("<filterByInertia>0</filterByInertia>\n");
                        someWriter.write("<minInertiaRatio>1.0000000149011612e-01</minInertiaRatio>\n");
                        someWriter.write("<maxInertiaRatio>3.4028234663852886e+38</maxInertiaRatio>\n");
                        //someWriter.write("<filterByConvexity>1</filterByConvexity>\n");
                        someWriter.write("<filterByConvexity>0</filterByConvexity>\n");
                        someWriter.write("<minConvexity>9.4999998807907104e-01</minConvexity>\n");
                        someWriter.write("<maxConvexity>3.4028234663852886e+38</maxConvexity>\n");
                        someWriter.write("</opencv_storage>\n");
                        someWriter.close();
                    } catch (IOException e) {
                        writingWentWell = false;
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    writingWentWell = false;
                    e.printStackTrace();
                }
            } else {
                writingWentWell = false;
                System.out.println("Somethign went wronk createing a file.");
            }
        }
        return writingWentWell;
    }

    /*
    someWriter.write("<thresholdStep>30.</thresholdStep>\n");
                        someWriter.write("<minThreshold>30.</minThreshold>\n");
                        someWriter.write("<maxThreshold>220.</maxThreshold>\n");
someWriter.write("<minArea>1800.</minArea>\n");
                        someWriter.write("<maxArea>7000.</maxArea>\n");

     */



    //Saves a config file that can later be used by a org.opencv.features2d.FeatureDetector.read()
    //Sadly, this is the ONLY way to get parameters to the function/object/thingy.
    public static boolean configFileSaviuor(Context context, String fileName,
                                            double thresholdStep,
                                            double minThreshold,
                                            double maxThreshold,
                                            double minDistBetweenBlobs,
                                            double minArea,
                                            double maxArea) {
        //If some dolt enters negative values: substitute for our own default values:
        if(thresholdStep<0)
            thresholdStep = 30;
        if(minThreshold<0)
            minThreshold=30;
        if(maxThreshold<0)
            maxThreshold=220;
        if(minDistBetweenBlobs<0)
            minDistBetweenBlobs=10;
        if(minArea<0)
            minArea=1800;
        if(maxArea<0)
            maxArea=7000;
        //create an absolute path using the filename and some gadgetry
        //ToDo: default should be false?
        boolean writingWentWell = true;
        String filePath = getAppSpecificDocumentPath(context, fileName);
        if (filePath.length() > 3) {
            //if file exist -erase it and create a new one.
            //if it does not exist, just create it
            File f;
            //boolean fileIsReady = true;
            f = new File(filePath);
            if (f.exists()) {
                writingWentWell = f.delete();
            }
            if (writingWentWell) {
                try {
                    writingWentWell = f.createNewFile();
                } catch (IOException e) {
                    writingWentWell = false;
                    e.printStackTrace();
                }
            }
            //Write the file
            if (writingWentWell) {
                try {
                    FileOutputStream fout = new FileOutputStream(f);
                    try {
                        OutputStreamWriter someWriter = new OutputStreamWriter(fout, "UTF-8");
                        someWriter.write("<?xml version=\"" + 1.0 + "\"?>\n");
                        someWriter.write("<opencv_storage>\n");
                        someWriter.write("<format>3</format>\n");
                        someWriter.write("<thresholdStep>"+ doubleToString(thresholdStep) +"</thresholdStep>\n");
                        someWriter.write("<minThreshold>"+ doubleToString(minThreshold) +"</minThreshold>\n");
                        someWriter.write("<maxThreshold>"+ doubleToString(maxThreshold) +"</maxThreshold>\n");
                        someWriter.write("<minRepeatability>2</minRepeatability>\n");
                        someWriter.write("<minDistBetweenBlobs>"+ doubleToString(minDistBetweenBlobs) +"</minDistBetweenBlobs>\n");
                        someWriter.write("<filterByColor>1</filterByColor>\n");
                        someWriter.write("<blobColor>0</blobColor>\n");
                        someWriter.write("<filterByArea>1</filterByArea>\n");
                        someWriter.write("<minArea>"+ doubleToString(minArea) +"</minArea>\n");
                        someWriter.write("<maxArea>"+ doubleToString(maxArea) +"</maxArea>\n");
                        someWriter.write("<filterByCircularity>0</filterByCircularity>\n");
                        someWriter.write("<minCircularity>8.0000001192092896e-01</minCircularity>\n");
                        someWriter.write("<maxCircularity>3.4028234663852886e+38</maxCircularity>\n");
                        someWriter.write("<filterByInertia>0</filterByInertia>\n");
                        someWriter.write("<minInertiaRatio>1.0000000149011612e-01</minInertiaRatio>\n");
                        someWriter.write("<maxInertiaRatio>3.4028234663852886e+38</maxInertiaRatio>\n");
                        someWriter.write("<filterByConvexity>0</filterByConvexity>\n");
                        someWriter.write("<minConvexity>9.4999998807907104e-01</minConvexity>\n");
                        someWriter.write("<maxConvexity>3.4028234663852886e+38</maxConvexity>\n");
                        someWriter.write("</opencv_storage>\n");
                        someWriter.close();
                    } catch (IOException e) {
                        writingWentWell = false;
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    writingWentWell = false;
                    e.printStackTrace();
                }
            } else {
                writingWentWell = false;
                System.out.println("Somethign went wronk createing a file.");
            }
        }
        return writingWentWell;
    }


    private static String doubleToString(double theDouble){
        int wholes = (int)Math.floor(theDouble);
        int deci = (int)Math.floor((theDouble-wholes)*10);
        int centi = (int)Math.floor((theDouble-wholes-deci)*100);
        int milli = (int)Math.floor((theDouble-wholes-deci-centi)*1000);
        String retVal = Integer.toString(wholes) + ".";
        if((deci!=0) || (centi!=0) || (milli!=0)){
            retVal = retVal + Integer.toString(deci)+Integer.toString(centi)+Integer.toString(milli);
        }
        return retVal;
    }

       /*
       From the original configuration file org.opencv.features2d.FeatureDetector.write():
        <?xml version="1.0"?>
        <opencv_storage>
        <format>3</format>
        <thresholdStep>10.</thresholdStep>
        <minThreshold>50.</minThreshold>
        <maxThreshold>220.</maxThreshold>
        <minRepeatability>2</minRepeatability>
        <minDistBetweenBlobs>10.</minDistBetweenBlobs>
        <filterByColor>1</filterByColor>
        <blobColor>0</blobColor>
        <filterByArea>1</filterByArea>
        <minArea>25.</minArea>
        <maxArea>5000.</maxArea>
        <filterByCircularity>0</filterByCircularity>
        <minCircularity>8.0000001192092896e-01</minCircularity>
        <maxCircularity>3.4028234663852886e+38</maxCircularity>
        <filterByInertia>1</filterByInertia>
        <minInertiaRatio>1.0000000149011612e-01</minInertiaRatio>
        <maxInertiaRatio>3.4028234663852886e+38</maxInertiaRatio>
        <filterByConvexity>1</filterByConvexity>
        <minConvexity>9.4999998807907104e-01</minConvexity>
        <maxConvexity>3.4028234663852886e+38</maxConvexity>
        </opencv_storage>
        */



    public static boolean doTheSoundBankFilesExist(Context context) {
        //create an absolute path using the filename and some gadgetry
        String[] existingSoundBankNames = new String[0];
        String directoryPath = getAppSpecificDocumentPath(context, "");
        if (directoryPath.length() > 4) {
            //remove trailing "/" sign.
            directoryPath = directoryPath.substring(0, directoryPath.length() - 1);
            File aDirectory = new File(directoryPath);
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File file, String s) {
                    if (
                            s.equals("soundbank_001.xml") ||
                                    s.equals("soundbank_002.xml") ||
                                    s.equals("soundbank_003.xml") ||
                                    s.equals("soundbank_004.xml") ||
                                    s.equals("soundbank_005.xml") ||
                                    s.equals("soundbank_006.xml") ||
                                    s.equals("soundbank_007.xml") ||
                                    s.equals("soundbank_008.xml") ||
                                    s.equals("soundbank_009.xml") ||
                                    s.equals("soundbank_010.xml")) {
                        return true;
                    }
                    return false;
                }
            };
            if (aDirectory.exists()) {
                existingSoundBankNames = aDirectory.list(filter);
                //existingSoundBankNames might be null or have length 0
                if (existingSoundBankNames == null) {
                    existingSoundBankNames = new String[0];
                }
            }
        }
        if (existingSoundBankNames.length == 10) {
            return true;
        }
        return false;
    }




    public static boolean writeDefaultSoundBankFiles(Context context){
        System.out.println("gör en skrivning...");
        IntStringVector resourceValues = getAllRawResources();
        int defaultResourceID = 0;
        String defaultSoundName = "default";
        if(resourceValues.length()>0){
            defaultResourceID = resourceValues.getResouceID(0);
            defaultSoundName = resourceValues.getResouceName(0);
        }
        boolean returnValue = true;
        IntStringVector intStringVector = new IntStringVector();
        for(int i=0;i<4;i++) {
            intStringVector.add(defaultResourceID, 0, defaultSoundName);
            intStringVector.setSoundPoolId(i, 0);
        }
        int bankNum=0;
        for(int i=0;i<9;i++){
            bankNum = i+1;
            if(!writeSoundBank(context, "soundbank_00"+bankNum+".xml", intStringVector)){
                returnValue=false;
            }
        }
        if(!writeSoundBank(context, "soundbank_010.xml", intStringVector)){
            returnValue=false;
        }
        return returnValue;
    }




    public static boolean writeSoundBank(Context context, String fileName, IntStringVector intStringVector) {
        boolean writingWentWell = false;
        if(intStringVector!=null) {
            System.out.println("length: "+intStringVector.length());
            if (intStringVector.length() == 4) {
                //create an absolute path using the filename and some gadgetry
                String filePath = getAppSpecificDocumentPath(context, fileName);
                System.out.println("Writing to "+filePath);
                if (filePath.length() > 3) {
                    //if file exist -erase it and create a new one.
                    //if it does not exist, just create it
                    File f;
                    //boolean fileIsReady = true;
                    f = new File(filePath);
                    if (f.exists()) {
                        writingWentWell = f.delete();
                    }else{
                        writingWentWell=true;
                    }
                    if (writingWentWell) {
                        try {
                            writingWentWell = f.createNewFile();
                        } catch (IOException e) {
                            writingWentWell = false;
                            e.printStackTrace();
                        }
                    }
                    //Write the file
                    if (writingWentWell) {
                        try {
                            FileOutputStream fout = new FileOutputStream(f);
                            try {
                                OutputStreamWriter someWriter = new OutputStreamWriter(fout, "UTF-8");
                                someWriter.write("<?xml version=\"" + 1.0 + "\"?>\n");
                                someWriter.write("<soundbank>\n");
                                for (int isvIndex = 0; isvIndex < intStringVector.length(); isvIndex++) {
                                    someWriter.write("<sound_00" + isvIndex + ">");
                                    someWriter.write("<resourceName>" + intStringVector.getResouceName(isvIndex) + "</resourceName>");
                                    someWriter.write("<shortSoundName>" + intStringVector.getShortSoundName(isvIndex) + "</shortSoundName>");
                                    someWriter.write("<octave>" + intStringVector.getOctave(isvIndex) + "</octave>");
                                    someWriter.write("<tone>" + intStringVector.getTone(isvIndex) + "</tone>");
                                    someWriter.write("<isSharp>" + intStringVector.isItSharp(isvIndex) + "</isSharp>");
                                    someWriter.write("<detectedColor>" + intStringVector.getDetectColor(isvIndex) + "</detectedColor>");
                                    someWriter.write("</sound_00" + isvIndex + ">");
                                }
                                someWriter.write("</soundbank>\n");
                                someWriter.close();
                            } catch (IOException e) {
                                writingWentWell = false;
                                e.printStackTrace();
                            }
                        } catch (FileNotFoundException e) {
                            writingWentWell = false;
                            e.printStackTrace();
                        }
                    } else {
                        writingWentWell = false;
                        System.out.println("Somethign went wronk createing a file.");
                    }
                }
            }
        }
        return writingWentWell;
    }


    private int getResourceIDFromResourceName(String likelyResourceName){
        IntStringVector resourceValues = getAllRawResources();
        for(int resourceIndex=0;resourceIndex<resourceValues.length();resourceIndex++){
            if(likelyResourceName.equals(resourceValues.getResouceName(resourceIndex))){
                return resourceValues.getResouceID(resourceIndex);
            }
        }
        return -1;
    }



    //ToDo: runs on hardcoded lengths, updade this after beta
    //ToDo: test for null and zero length fileName
    //ToDo: efter att denna körts måste soundPool uppdateras med "loadInUITHread(soundPool, intStringVector, 1, loadingDoneListener);"
    public static IntStringVector readSoundBank(Context context, String fileName) {
        String tempSoundName = "";
        int tempResourceID = 0;
        IntStringVector returnValue = new IntStringVector();
        String filePath = getAppSpecificDocumentPath(context, fileName);
        File f = new File(filePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            System.out.println("buider fel");
            return null;
        }
        Document document = null;
        try {
            document = builder.parse(f);
        } catch (SAXException e) {
            e.printStackTrace();
            System.out.println("sax fel");
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("sax fel");
            return null;
        }
        IntStringVector resourceValues = getAllRawResources();
        NodeList nodeList = document.getElementsByTagName("soundbank");
        if(nodeList.getLength()==1){
            NodeList nodes_shortSoundName = document.getElementsByTagName("shortSoundName");
            NodeList nodes_octave = document.getElementsByTagName("octave");
            NodeList nodes_tone = document.getElementsByTagName("tone");
            NodeList nodes_isSharp = document.getElementsByTagName("isSharp");
            NodeList nodes_detectedColor = document.getElementsByTagName("detectedColor");
            for(int i=0;i<4;i++) {
                /*
                System.out.println(
                        nodes_shortSoundName.item(i).getTextContent() + ", " +
                                nodes_octave.item(i).getTextContent() + ", " +
                                nodes_tone.item(i).getTextContent() + ", " +
                                nodes_isSharp.item(i).getTextContent() + ", " +
                                nodes_detectedColor.item(i).getTextContent());
                */
                tempSoundName =
                        nodes_shortSoundName.item(i).getTextContent()+"_"+
                        nodes_octave.item(i).getTextContent()+"_"+
                        nodes_tone.item(i).getTextContent();
                if(Boolean.valueOf(nodes_isSharp.item(i).getTextContent())){
                    tempSoundName = tempSoundName + "_s";
                }
                //tempSoundName = tempSoundName+".wav";
                tempResourceID=0;
                if(resourceValues.length()>0){
                    tempResourceID = resourceValues.getResouceID(0);
                }
                for(int resourceIndex=0;resourceIndex<resourceValues.length();resourceIndex++){
                    if(tempSoundName.equals(resourceValues.getResouceName(resourceIndex))){
                        tempResourceID = resourceValues.getResouceID(resourceIndex);
                        break;
                    }
                }
                returnValue.add(tempResourceID,0,tempSoundName);
                //ToDo: more errorchecking here please, things could really get messed up here...
                returnValue.setDetectColor(i,Integer.valueOf(nodes_detectedColor.item(i).getTextContent()));
            }
        }else{
            System.out.println("soundbank.length() != 1: ...");
            return null;
        }
        return returnValue;
    }


}