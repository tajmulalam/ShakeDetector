package com.educareappsltd.shakedetector;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

/**
 * Created by Rakib on 6/6/2017.
 */

public class BlowDetector {
    BlowListener blowListener;

    public BlowDetector(BlowListener blowListener) {
        this.blowListener = blowListener;
    }

    private int blow_value;
    boolean controlRecord = true;

    public void startBlowing(final boolean startRecord) {
        new Thread(new Runnable() {
            public void run() {
                int fooCount = 0;
                int minSize = AudioRecord.getMinBufferSize(8000, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT);
                AudioRecord ar = new AudioRecord(MediaRecorder.AudioSource.MIC, 8000, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT, minSize);
                short[] buffer = new short[minSize];
                ar.startRecording();
                if (startRecord) {
                    while (controlRecord) {
                        ar.read(buffer, 0, minSize);
                        for (short s : buffer) {
                            if (s > 20000) {
                                System.out.println("minSize /  signal / if (signal > 20000) = " + s);
                            }
                            if (Math.abs(s) > 20000 && Math.abs(s) < 22000)   //DETECT VOLUME (IF I BLOW IN THE MIC)
                            {
                                fooCount++;
                                blow_value = Math.abs(s);
                                System.out.println("Blow Value=" + blow_value);
                                if (fooCount == 10) {
                                    blowListener.isBlowed(true);
                                    fooCount = 0;

                                }

                            }

                        }
                    }
                } else {
                    ar.stop();
                    controlRecord = false;
                }
            }

        }).start();


    }

    public interface BlowListener {
        public void isBlowed(boolean blowed);
    }
}
