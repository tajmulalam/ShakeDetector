package com.educareappsltd.shakedetector;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

/**
 * Created by Rakib on 6/6/2017.
 */

public class BlowDetector {

    public BlowDetector() {
    }

    boolean recorder = true;
    private int blow_value;

    public boolean isBlowing() {

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

    public boolean isRecorder() {
        return recorder;
    }

    public void setRecorder(boolean recorder) {
        this.recorder = recorder;
    }
    public  interface BlowListener{
        public void  isBlowed(boolean blowed);
    }
}
