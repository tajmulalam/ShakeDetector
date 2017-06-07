package com.educareappsltd.shakedetector;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.rarepebble.colorpicker.ColorPickerView;


public class MainActivity extends AppCompatActivity implements ShakeListener.OnShakeListener {
    private ShakeListener mShaker;
    RelativeLayout activity_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShaker = new ShakeListener(this, this);
        activity_main = (RelativeLayout) findViewById(R.id.activity_main);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mShaker.resume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mShaker.pause();
        //   super.onPause();

    }

    private int count = 0;

    @Override
    public void onShake() {
        count++;
        Toast.makeText(this, "Shake count: " + count, Toast.LENGTH_SHORT).show();
        goPlay();
//        showDialog();
    }

    private void goPlay() {
        Intent intent = new Intent(MainActivity.this, SensorActivity.class);
        intent.putExtra("blowChoice", 1);
        startActivity(intent);
    }

    public void doneFlyBall() {
        Toast.makeText(this, "Fly Done", Toast.LENGTH_SHORT).show();
    }

    private int selectedColor = -1;

    public void showColorPicker() {
        ColorPickerView picker = new ColorPickerView(this);
        picker.setColor((0xff12345));
        activity_main.addView(picker);
        selectedColor = picker.getColor();
        if (selectedColor != -1) {
            Toast.makeText(MainActivity.this, "#" + String.valueOf(Integer.toHexString(selectedColor)).toUpperCase(), Toast.LENGTH_SHORT).show();
            activity_main.setBackgroundColor(selectedColor);
        }

    }

    public void showDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.color_picker_dialog_layout);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final ColorPickerView colorPickerView = new ColorPickerView(MainActivity.this);
        colorPickerView.setColor(0xff12345);

        RelativeLayout rlColorPicker = (RelativeLayout) dialog.findViewById(R.id.rlColorPicker);
        rlColorPicker.addView(colorPickerView);


        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor = colorPickerView.getColor();
                Toast.makeText(MainActivity.this, "#" + String.valueOf(Integer.toHexString(selectedColor)).toUpperCase(), Toast.LENGTH_SHORT).show();
                activity_main.setBackgroundColor(selectedColor);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void play(View view) {
        goPlay();
    }
}
