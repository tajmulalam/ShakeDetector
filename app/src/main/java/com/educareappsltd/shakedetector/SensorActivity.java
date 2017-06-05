package com.educareappsltd.shakedetector;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class SensorActivity extends AppCompatActivity {
    float[] history = new float[2];
    String[] direction = {"NONE", "NONE"};
    private SensorManager manager;
    private Sensor accelerometer;
    private TextView tvInfo;
    private StringBuilder builder = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OverlayBallsEnd view = new OverlayBallsEnd(this);
        setContentView(view);

    }


    public void doneFlyBall() {
        Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(SensorActivity.this, MainActivity.class));
    }
}
