package com.educareappsltd.shakedetector;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by Probook 440 on 2/19/2017.
 */

public class SensorDetector implements SensorEventListener {
    float[] history = new float[2];
    String[] direction = {"NONE", "NONE"};
    private SensorManager manager;
    private Sensor accelerometer;
    private Context context;
    private float x, y;
    private ShakeListener shakeListener;

    public SensorDetector(Context context, ShakeListener shakeListener) {
        this.context = context;
        this.shakeListener = shakeListener;
        manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float xChange = history[1] - event.values[1];
        float yChange = history[0] - event.values[0];

        history[1] = event.values[1];
        history[0] = event.values[0];

        if (xChange > 1) {
            direction[0] = "LEFT";
            x = xChange;
        } else if (xChange < -1) {
            direction[0] = "RIGHT";
            x = xChange;
        }

        if (yChange > 1) {
            direction[1] = "DOWN";
            y = yChange;
        } else if (yChange < -1) {
            direction[1] = "UP";
            y = yChange;
        }

        shakeListener.getShakePosition(x, y);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public interface ShakeListener {
        public void getShakePosition(float x, float y);
    }
}
