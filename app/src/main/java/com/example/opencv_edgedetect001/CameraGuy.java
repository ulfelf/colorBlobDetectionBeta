package com.example.opencv_edgedetect001;

/**
 * Created by blandfars on 2018-01-08.
 */

        import android.media.Image;
        import android.provider.ContactsContract;
        import android.support.v4.content.res.ResourcesCompat;
        import android.support.v4.media.session.IMediaControllerCallback;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.SurfaceView;
        import android.view.WindowManager;
        import android.widget.ImageView;

        import org.opencv.android.BaseLoaderCallback;
        import org.opencv.android.CameraBridgeViewBase;
        import org.opencv.android.LoaderCallbackInterface;
        import org.opencv.android.OpenCVLoader;
        import org.opencv.android.Utils;
        import org.opencv.core.Core;
        import org.opencv.core.CvType;
        import org.opencv.core.Mat;
        import org.opencv.core.Scalar;
        import org.opencv.imgcodecs.Imgcodecs;
        import org.opencv.imgproc.Imgproc;

        import java.io.IOException;
        import java.util.List;

        import static org.opencv.core.CvType.CV_8UC;
        import static org.opencv.core.CvType.CV_8UC4;

public class CameraGuy extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    //Log.i(TAG, "OpenCV ld sucsly");
                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
        //lade till en kommentar
    };

    @Override
    public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallback);
    }

    private CameraBridgeViewBase mOpenCvCameraView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_camera_guy);
        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.HelloOpenCvView);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }


    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        //Imgproc proc = new Imgproc();

        Mat rgb1 = inputFrame.rgba();
        Mat rgb2 = rgb1.clone();

        Mat gray1 = inputFrame.gray();
        Mat gray2 = gray1.clone();

        //Testning: Vad kan jag köra och vad kan jag inte köra från denna miljö?
        //Funkar:
        //Imgproc.threshold(gray1, gray2, 100, 255, Imgproc.THRESH_BINARY);

        //Core.transpose(kek, bur);

        //Core.flip(rgb2, rgb1, -1);
        //Funkar inte:
        //Core.completeSymm(rgb2, false);

        return gray1;
    }

}
