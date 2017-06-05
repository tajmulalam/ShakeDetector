package com.educareappsltd.shakedetector;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.rarepebble.colorpicker.ColorPickerView;

/**
 * Created by Probook 440 on 2/20/2017.
 */

public class ViewDialog {

    public void showDialog(final Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.color_picker_dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final ColorPickerView colorPickerView = new ColorPickerView(activity);
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
            }
        });

        dialog.show();
    }
}
