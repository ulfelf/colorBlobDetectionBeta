package com.example.opencv_edgedetect001;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.features2d.FeatureDetector;


/*
These are needed in the manifest:
<uses-permission android:name="android.permission.CAMERA"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-feature android:name="android.hardware.camera" android:required="false"/>
<uses-feature android:name="android.hardware.camera.autofocus" android:required="false"/>
<uses-feature android:name="android.hardware.camera.front" android:required="false"/>
<uses-feature android:name="android.hardware.camera.front.autofocus" android:required="false"/>
*/




public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private CameraBridgeViewBase mOpenCvCameraView;
    private boolean alreadyWritten = false;
    private boolean alreadyRead = false;
    //private String blobConfigFileName = "blobConfig.xml";
    private org.opencv.features2d.FeatureDetector blobFeaturedetector;
    private int readyToPlayToneFrameCounter;
    private final int readyToPlayToneFrameCounter_max = 3;
    private int diagnosticToneCounter;
    private Rect sampleArea;
    double detectionAreatWidth = 50.0;
    double detectionAreatHeight = 50.0;
    int sampleAreatHeight = 15;
    int sampleAreatWidth = 15;

    SoundPool soundPool;
    IntStringVector intStringVector;
    SoundKeeper soundKeeper;


    //int mySound; //Ett ID som berättar för .play vilket ljud som ska spelas upp
    boolean canPlaySound; //Håller koll på vår handler
    Handler mHandler; //Håller koll på tidtagaruret
    Runnable mRunnable; //????
    int soundDelay; //Tidsfördröjning i milisekunder




    public enum detectedColor{DET_BLACK, DET_RED, DET_GREEN, DET_BLUE};
    public static final int DET_BLACK=0;
    public static final int DET_RED=1;
    public static final int DET_GREEN=2;
    public static final int DET_BLUE=3;


    //Reciever for local implicit intents.
    private BroadcastReceiver aBroadcastRecieverForRGB = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ((TextView) findViewById(R.id.textFelt)).setText(intent.getStringExtra("aBroadcastRecieverForRGB_hue"));
            double val_1 = intent.getIntExtra("aBroadcastRecieverForRGB_val1", 0);
            double val_2 = intent.getIntExtra("aBroadcastRecieverForRGB_val2", 0);
            double val_3 = intent.getIntExtra("aBroadcastRecieverForRGB_val3", 0);
            double val_4 = intent.getIntExtra("aBroadcastRecieverForRGB_val4", 0);
            int ScalarLength = intent.getIntExtra("aBroadcastRecieverForRGB_scalar_length", 0);
            ((TextView) findViewById(R.id.textFelt)).setText("color: ("+val_1+", "+val_2+", "+val_3+", "+val_4+")");
            playASound(val_1, val_2, val_3);
        }
    };




    @Override
    public void onCreate(Bundle savedInstanceState) {

        //Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);

        readyToPlayToneFrameCounter=0;
        diagnosticToneCounter=0;
        soundKeeper = new SoundKeeper();
        soundPool = soundKeeper.getSoundPool();
        intStringVector = soundKeeper.getIntStringVector();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.HelloOpenCvView);
        //mOpenCvCameraView.setMaxFrameSize(200, 200);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
        //Register the reciever for local implicit (non directed) Intents. Works like an EventHandler, but captures Intents in stead of Events.
        LocalBroadcastManager.getInstance(this).registerReceiver(aBroadcastRecieverForRGB, new IntentFilter("trigger_aBroadcastRecieverForRGB"));
        sampleArea = new Rect(10,10,2,2);
    }




    @Override
    public void onPause() {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Internal OpenCV library not found. Using OpenCV Manager for initialization
        if (!OpenCVLoader.initDebug()) {
            Log.d("OpenCV", "Library har inte laddats, ger managern en spark i röven...");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d("OpenCV", "OpenCV är igång och fungerar");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null) {
            mOpenCvCameraView.disableView();
        }
        LocalBroadcastManager.getInstance(this).unregisterReceiver (aBroadcastRecieverForRGB);
    }



    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    //Avoiding creating the same variable more times than neccesary
                    if (!alreadyWritten) {
                        alreadyWritten = true;
                        blobFeaturedetector = org.opencv.features2d.FeatureDetector.create(FeatureDetector.SIMPLEBLOB);
                        //ToDo: needed a context, mAppContext seems to do the trick. What the heck is a mAppContext? Read up on it, using unknowns is dangerous.
                        //ToDo: Read the f-ing manual
                        //String configFilePath = FileTool.getAppSpecificDocumentPath(mAppContext, blobConfigFileName);
                        String configFilePath = FileTool.getAppSpecificDocumentPath(mAppContext, getString(R.string.blobConfigFileName));
                        //Again: this is the only way to pass parametric informaion to the blob detection, by reading a file.
                        blobFeaturedetector.read(configFilePath);
                    }
                    mOpenCvCameraView.enableView();
                    CameraBridgeViewBase SetCaptureFormat;
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    public void onCameraViewStarted(int width, int height) {
    }

    public void onCameraViewStopped() {
    }

    //Everything happens here.
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        return detectAndMarkBlobs(inputFrame);
    }

    /*
    public void playTone(String aString){
        Toast.makeText(this, aString, Toast.LENGTH_SHORT).show();
    }
    */

    //This function plays different sounds depending on color values
    //It needs some work.
    //ToDo: talk to SwedFred
    public void playASound(double red, double green, double blue){
        double marginOfColor = 10.0;
        double[] kickRed = {55.0,3.0,16.0};
        double[] snareBlue = {17.0,36.0,73.0};
        double [] hihatGreen = {0.0,80.0,60.0};
        //0: kick
        //1:snare
        //2:orgel-grej
        //3:orgel
        //4:ack
        //5:org
        //6:long hihat
        //7;short hihat
        //8:bass
        int soundIndex = 0;
        if(detectAColor(red, green, blue, 5.0)==detectedColor.DET_RED){
            soundIndex = 1;
        }else if(detectAColor(red, green, blue, 5.0)==detectedColor.DET_GREEN){
            soundIndex = 6;
        }else if(detectAColor(red, green, blue, 5.0)==detectedColor.DET_BLUE){
            soundIndex = 7;
        }else{
            soundIndex = 0;
        }
        //soundIndex = 8;
        String debugString = "Renpenis: "+
                intStringVector.getShortSoundName(soundIndex) + ", " +
                intStringVector.getOctave(soundIndex) + ", " +
                intStringVector.getTone(soundIndex) + ", " +
                intStringVector.isItSharp(soundIndex);
        System.out.println(debugString);
        ((TextView)findViewById(R.id.textFelt)).setText(((TextView)findViewById(R.id.textFelt)).getText()+debugString);
        soundPool.play(intStringVector.getSoundPoolId(soundIndex),1,1,1,0,1);
    }

    //check if a color value from the camera is an expected value or not.
    //This function needs a lot of work.
    //ToDo: talk to SwedFred
    private detectedColor detectAColor (double cameraRed, double cameraGreen, double cameraBlue, double marginOfSomething){
        /*
        double[] kRed = new double[]{100,100,4,8,8,20};
        double[] kGreen = new double[]{1,6,100,100,60,75};
        double[] kBlue = new double[]{20,30,48,60,100,100};
        */
        double theRed = cameraRed;
        double theGreen = cameraGreen;
        double theBlue = cameraBlue;
        //Avoid division by zero
        if(theRed<=0){theRed = 1;}
        if(theGreen<=0){theGreen  =1;}
        if(theBlue<=0){theBlue = 1;}
        double theSum = theRed+theGreen+theBlue;
        int k_r = 0;
        int k_g = 0;
        int k_b = 0;
        //Get the proportions in relation to the largest value, R, G or B
        if((theRed >= theGreen) && (theRed >= theBlue)){
            k_r = 100;
            k_g = (int)((100/theRed)*theGreen);
            k_b = (int)((100/theRed)*theBlue);
        }else if((theGreen >= theRed)&& (theGreen>= theBlue)){
            k_r = (int)((100/theGreen)*theRed);
            k_g = 100;
            k_b = (int)((100/theGreen)*theBlue);
        }else if((theBlue >= theRed) && (theBlue >= theGreen)){
            k_r = (int)((100/theBlue)*theRed);
            k_g = (int)((100/theBlue)*theGreen);
            k_b = 100;
        }
        //is it black?
        if(theSum<=35){
            return detectedColor.DET_BLACK;
        //Om k_r == 100	Om 4<k_g<8		Om 8<k_b<20
        }//else if((k_r == 100)&&(4<k_g) && (k_g<8) && (8<k_b) && (k_b>20)){
         else if((k_r == 100)){
            return detectedColor.DET_RED;
        //Om k_g == 100	Om 1<k_r<6		Om 60<k_b<75
        }//else if((k_g == 100)&&(1<k_r) && (k_r<6) && (60<k_b) && (k_b>75)){
         else if((k_g == 100)){
            return detectedColor.DET_GREEN;
        //Om k_b == 100	Om 20<k_r<33		Om 48<k_g<60
        }//else if((k_b == 100)&&(20<k_r) && (k_r<33) && (48<k_g) && (k_g>60)){
          else if((k_b == 100)){
            return detectedColor.DET_BLUE;
        }
        return detectedColor.DET_BLACK;
    }


    Mat input_gray;
    Mat input_color_toBeCloned;
    Mat input_color;
    Mat output_color;
    MatOfKeyPoint matOfKeyPoint;
    boolean noHitsThisFrame;
    KeyPoint[] kps;


    //Detects blobs. If a blob enters the target rectangle (and if the target rectangle has
    //been empty for a while (readyToPlayToneFrameCounter_max number of frames), broadcast an Intent to play
    //a sound.
    public Mat detectAndMarkBlobs(CameraBridgeViewBase.CvCameraViewFrame inputFrame){
        /*
        Mat input_gray = inputFrame.gray();
        Mat input_color_toBeCloned = inputFrame.rgba();
        Mat input_color = input_color_toBeCloned.clone();
        Mat output_color = input_color.clone();
        MatOfKeyPoint matOfKeyPoint = new MatOfKeyPoint();
        */
        input_gray = inputFrame.gray();
        input_color_toBeCloned = inputFrame.rgba();
        input_color = input_color_toBeCloned.clone();
        output_color = input_color.clone();
        matOfKeyPoint = new MatOfKeyPoint();
        //detect blobs and store the center points of each blob in "matOfKeyPoint"
        blobFeaturedetector.detect(input_gray, matOfKeyPoint);
        //Let the detection area be a rectangle "detectionArea")
        org.opencv.core.Rect detectionArea = new org.opencv.core.Rect(
                (int)(input_gray.width()/2-detectionAreatWidth/2),
                (int)(input_gray.height()/2-detectionAreatHeight/2),
                (int)detectionAreatWidth,
                (int)detectionAreatHeight);
        //boolean noHitsThisFrame = true;
        noHitsThisFrame = true;
        //KeyPoint[] kps = matOfKeyPoint.toArray();
        kps = matOfKeyPoint.toArray();
        Scalar colorAtKeyPoint;
        //iterate through the color blobs KeyPoints (center of mass)
        for(int i=0;i<kps.length;i++) {
            //If theres a blob inside the detection area
            if (kps[i].pt.inside(detectionArea)) {
                noHitsThisFrame = false;
                //play a tone if
                // * theres a blob inside the target rectangle (there is),
                // * the target rectangle was previously empty for readyToPlayToneFrameCounter_max frames.
                if(readyToPlayToneFrameCounter>=readyToPlayToneFrameCounter_max) {
                    sampleArea = getRectangle(kps[i],sampleAreatWidth,sampleAreatHeight);
                    colorAtKeyPoint = getRgbScalarFromImage(output_color, kps[i], sampleAreatWidth, sampleAreatHeight);
                    Intent intentColor = new Intent("trigger_aBroadcastRecieverForRGB");
                    intentColor.putExtra("aBroadcastRecieverForRGB_val1", (int)colorAtKeyPoint.val[0]); //red?
                    intentColor.putExtra("aBroadcastRecieverForRGB_val2", (int)colorAtKeyPoint.val[1]); //
                    intentColor.putExtra("aBroadcastRecieverForRGB_val3", (int)colorAtKeyPoint.val[2]); //
                    intentColor.putExtra("aBroadcastRecieverForRGB_val4", (int)colorAtKeyPoint.val[3]); //
                    intentColor.putExtra("aBroadcastRecieverForRGB_scalar_length", colorAtKeyPoint.val.length);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intentColor);
                    //Draw a red circle if the blob is within the target area (detectionArea)
                    org.opencv.imgproc.Imgproc.circle(output_color, kps[i].pt, 8, new Scalar(255, 0, 0), 2);
                }
                readyToPlayToneFrameCounter=0;
            //If there is no blob inside the detection area:
            } else {
                readyToPlayToneFrameCounter++;
                if (readyToPlayToneFrameCounter >= readyToPlayToneFrameCounter_max) {
                    readyToPlayToneFrameCounter = readyToPlayToneFrameCounter_max + 1;
                }
                //Draw a green circle if the blob is outside the target rectangle
                org.opencv.imgproc.Imgproc.circle(output_color, kps[i].pt, 8, new Scalar(0, 255, 0), 2);
            }
        }
        //Draw a rectangle letting the user know where the target area is
        org.opencv.imgproc.Imgproc.rectangle(output_color, detectionArea.tl(), detectionArea.br(),new Scalar(255, 0, 0), 1,8,0);
        //Draw another rectangle letting the user know where the sampling area was at last color sample
        org.opencv.imgproc.Imgproc.rectangle(output_color, sampleArea.tl(), sampleArea.br(),new Scalar(0, 255, 0), 1,8,0);

        //lämna uttryckligen tillbaka resurser
        input_gray.release();
        input_color_toBeCloned.release();
        input_color.release();
        matOfKeyPoint.release();
        kps = null;



        return output_color;
    }




    //return the average color (Scalar(red, green, blue)) at KeyPoint kp.
    private Scalar getRgbScalarFromImage(Mat in, KeyPoint centreOfRectangle, int widthOfSamplingArea, int heightOfSamplingArea){
        Rect r = getRectangle(centreOfRectangle, widthOfSamplingArea, heightOfSamplingArea);
        Mat huga = new Mat(in, r);
        org.opencv.imgproc.Imgproc.GaussianBlur(huga, huga, new Size(5.0,5.0), 4.2);
        Scalar meanColor = org.opencv.core.Core.mean(huga).clone();
        org.opencv.imgproc.Imgproc.rectangle(in,r.tl(),r.br(),new Scalar(0, 0, 255));
        huga.release();
        return meanColor;
    }

    //ToDo: bara ett experiment, kanske ta bort innan leverans
    //return the average color (Scalar(red, green, blue)) at KeyPoint kp.
    private Scalar getSomethingElse(Mat in, KeyPoint centreOfRectangle, int widthOfSamplingArea, int heightOfSamplingArea){
        Rect r = getRectangle(centreOfRectangle, widthOfSamplingArea, heightOfSamplingArea);
        Mat huga = new Mat(in, r);
        org.opencv.imgproc.Imgproc.GaussianBlur(huga, huga, new Size(5.0,5.0), 4.2);
        //org.opencv.imgproc.Imgproc.blur(huga, huga, new Size(2.0,2.0));

        //org.opencv.imgproc.Imgproc.calcHist();


        Scalar meanColor = org.opencv.core.Core.mean(huga).clone();
        org.opencv.imgproc.Imgproc.rectangle(in,r.tl(),r.br(),new Scalar(0, 0, 255));

        return meanColor;
    }


    //ToDo: safeguard from null values and such
    private Rect getRectangle(KeyPoint centerOfRectangle, int widthOfRectangle, int heightOfRectamgle){
        return new Rect(
                (int)centerOfRectangle.pt.x-widthOfRectangle/2,
                (int)centerOfRectangle.pt.y-heightOfRectamgle/2,
                widthOfRectangle,heightOfRectamgle);
    }

}
