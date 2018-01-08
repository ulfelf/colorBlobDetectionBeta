package com.example.opencv_edgedetect001;

/**
 * Created by blandfars on 2018-01-08.
 */

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.gigamole.infinitecycleviewpager.VerticalInfiniteCycleViewPager;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Integer> shapeList = new ArrayList();
    List<Integer> instrumentList = new ArrayList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_with_carousels);

        populateShapes();
        populateInstruments();

        VerticalInfiniteCycleViewPager firstPager = (VerticalInfiniteCycleViewPager) findViewById(R.id.first_cycler);
        CycleAdapter firstCycleAdapter = new CycleAdapter(shapeList, getBaseContext());
        firstPager.setAdapter(firstCycleAdapter);

        VerticalInfiniteCycleViewPager secondPager = (VerticalInfiniteCycleViewPager) findViewById(R.id.second_cycler);
        CycleAdapter secondCycleAdapter = new CycleAdapter(instrumentList, getBaseContext());
        secondPager.setAdapter(secondCycleAdapter);

/*
        Intent i = new Intent(this, CameraGuy.class);
        startActivity(i);*/
    }

    private void populateInstruments() {
        instrumentList.add(R.drawable.trumpet);
        instrumentList.add(R.drawable.drum);
        instrumentList.add(R.drawable.piano);
    }

    private void populateShapes() {
        shapeList.add(R.drawable.circle_blue);
        shapeList.add(R.drawable.circle_green);
        shapeList.add(R.drawable.circle_red);
    }



}
