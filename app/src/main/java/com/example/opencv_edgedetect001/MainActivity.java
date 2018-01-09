package com.example.opencv_edgedetect001;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /*List<Integer> shapeList = new ArrayList();
    List<Integer> instrumentList = new ArrayList();*/
    static ImageView phone;
    static int counter = 0;
    static int viewLocation[] = {0,0};
    static ArrayList<ImageView> dots = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_howtoplay);


        phone = findViewById(R.id.img_phone);
        dots.add((ImageView)findViewById(R.id.img_blackdot));
        dots.add((ImageView)findViewById(R.id.img_reddot));
        dots.add((ImageView)findViewById(R.id.img_blackdot2));
        dots.add((ImageView)findViewById(R.id.img_bluedot));
        dots.add((ImageView)findViewById(R.id.img_greendot2));
        dots.add((ImageView)findViewById(R.id.img_greendot));

        handler.post(r);

        /*populateShapes();
        populateInstruments();

        VerticalInfiniteCycleViewPager firstPager = (VerticalInfiniteCycleViewPager) findViewById(R.id.first_cycler);
        CycleAdapter firstCycleAdapter = new CycleAdapter(shapeList, getBaseContext());
        firstPager.setAdapter(firstCycleAdapter);

        VerticalInfiniteCycleViewPager secondPager = (VerticalInfiniteCycleViewPager) findViewById(R.id.second_cycler);
        CycleAdapter secondCycleAdapter = new CycleAdapter(instrumentList, getBaseContext());
        secondPager.setAdapter(secondCycleAdapter);*/

    }
    /*private void populateInstruments() {
        instrumentList.add(R.drawable.trumpet);
        instrumentList.add(R.drawable.drum);
        instrumentList.add(R.drawable.piano);
        instrumentList.add(R.drawable.cat);
    }

    private void populateShapes() {
        shapeList.add(R.drawable.circle_blue);
        shapeList.add(R.drawable.circle_green);
        shapeList.add(R.drawable.circle_red);
        shapeList.add(R.drawable.circle_black);
    }*/



    final Handler handler = new Handler();
    final Runnable r = new Runnable()
    {
        public void run()
        {
         dots.get(counter % 6).getLocationInWindow(viewLocation);
         phone.setX(viewLocation[0] - 60);
         phone.setY(viewLocation[1] - 160);
         counter++;
         //Spela upp ett ljud här, följ en specifik ordning: svart, röd, svart, blå, grön, grön

         handler.postDelayed(r, 1000);
            //int nextLocation [] = { phoneLocation[0]+1, (phoneLocation[1]-72)+1 }; //Av någon anledning måste man ta -72 för att den ska flyttas korrekt. Detta gjorde att jag gjorde designvalet att bara flytta telefonjäveln manuellt.
            /*phone.setX(viewLocation[0] - 60); Bra centrerade värden om man utgår från blackdot2s position i landskapsläge
            phone.setY(viewLocation[1] - 160);*/

        }
    };




    //TODO copy-paste från stackoverflow för att spela video, har bekräftat att det fungerar
    /*getWindow().setFormat(PixelFormat.TRANSLUCENT);
    VideoView videoView = new VideoView(this);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() +"/" + R.raw.anim));  //Don't put extension
        videoView.requestFocus();
        setContentView(videoView);
        videoView.start();*/

}
