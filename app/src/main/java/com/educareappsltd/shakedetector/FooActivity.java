package com.educareappsltd.shakedetector;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class FooActivity extends AppCompatActivity {

    private int blow_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foo);

    }


    public boolean isBlowing() {
        boolean recorder = true;
        int fooCount = 0;
        int minSize = AudioRecord.getMinBufferSize(8000, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT);
        AudioRecord ar = new AudioRecord(MediaRecorder.AudioSource.MIC, 8000, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT, minSize);
        short[] buffer = new short[minSize];

        ar.startRecording();
        while (recorder) {

            ar.read(buffer, 0, minSize);
            for (short s : buffer) {
                if (s > 20000) {
                    System.out.println("minSize /  signal / if (signal > 20000) = " + s);
                }
                if (Math.abs(s) > 20000)   //DETECT VOLUME (IF I BLOW IN THE MIC)
                {
                    fooCount++;
                    blow_value = Math.abs(s);
                    System.out.println("Blow Value=" + blow_value);
                    ar.stop();
                    if (fooCount == 10) {
                        recorder = false;
                        return true;
                    }

                }

            }
        }
        return false;

    }

    public void blowStart(View view) {
        boolean isBlowed = isBlowing();
        if (isBlowed) {
            Toast.makeText(this, "Foo Done", Toast.LENGTH_SHORT).show();
        }
    }
}
