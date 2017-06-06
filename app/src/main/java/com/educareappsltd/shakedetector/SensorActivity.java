package com.educareappsltd.shakedetector;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class SensorActivity extends Activity implements OverlayBallsEnd.AllBallsGone, SensorDetector.ShakeListener {
    float[] history = new float[2];
    String[] direction = {"NONE", "NONE"};
    private SensorManager manager;
    private Sensor accelerometer;
    private TextView tvInfo;
    private StringBuilder builder = new StringBuilder();
    SensorDetector sensorDetector;
    int choice = -1;
    boolean isShakingChoice = false;
    OverlayBallsEnd view;

    //////choice=1-BLOW///choice=2-TAP///choice=3-SHAKE,
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        choice = getIntent().getIntExtra("blowChoice", -1);
        sensorDetector = new SensorDetector(this, this);
        if (choice != -1)
            turnOverlayBallOn(choice);
    }

    private void turnOverlayBallOn(int choice) {

        switch (choice) {
            case 1:
                view = new OverlayBallsEnd(this, 0, OverlayBallsEnd.Strategy.BLOW, this);
                setContentView(view);
                break;
            case 2:
                view = new OverlayBallsEnd(this, 0, OverlayBallsEnd.Strategy.TAP, this);
                setContentView(view);
                break;
            case 3:
                view = new OverlayBallsEnd(this, 0, OverlayBallsEnd.Strategy.SHAKE, this);
                setContentView(view);
                isShakingChoice = true;
                break;

        }
    }

    @Override
    public void finishedBalls() {

    }

    @Override
    public void getShakePosition(float x, float y) {
        if (isShakingChoice) {
            view.blastThreeShots();
        }
    }

    @Override
    public void getShakeDirection(String[] direction) {

    }


}
