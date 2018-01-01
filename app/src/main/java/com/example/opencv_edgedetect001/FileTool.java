package com.example.opencv_edgedetect001;



import android.content.Context;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


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




    //Saves a config file that can later be used by a org.opencv.features2d.FeatureDetector.read()
    //Sadly, this is the ONLY way to get parameters to the function/object/thingy.
    public static boolean configFileSaviuor(Context context, String fileName) {
        //create an absolute path using the filename and some gadgetry
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
                        someWriter.write("<minThreshold>50.</minThreshold>\n");
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

}