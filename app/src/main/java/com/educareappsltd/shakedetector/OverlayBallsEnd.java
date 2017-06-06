package com.educareappsltd.shakedetector;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class OverlayBallsEnd extends OverlayEnd implements SensorDetector.ShakeListener {

    MediaPlayer mediaPlayer;

    private static final int NUM_BALLS = 16;
    //by default tap;
    Strategy strategyFrom_activity=Strategy.TAP;

    public static enum Strategy{
        BLOW, TAP, SHAKE;
    }

    //// animal int res ids array
    private static int[] ANIMALS = new int[]{

            R.drawable.facebook,
            R.drawable.facebook,
            R.drawable.facebook,
            R.drawable.facebook,
            R.drawable.facebook,
            R.drawable.facebook,
            R.drawable.facebook,
            R.drawable.facebook,
            R.drawable.facebook,
            R.drawable.facebook,
            R.drawable.facebook,
            R.drawable.facebook,
            R.drawable.facebook,
            R.drawable.facebook,
            R.drawable.facebook,
            R.drawable.facebook,
            R.drawable.facebook,
            R.drawable.facebook
    };


    //// clouds int res ids array
    private static int[] BALLS = new int[]{

    };


    private Random mRandom = new Random();
    private ArrayList<Ball> mBalls = new ArrayList<Ball>();
    AllBallsGone ballsGone_listener;
    public OverlayBallsEnd(Context context, int level, Strategy st, AllBallsGone ballsGone_listener) {
        super(context);
        strategyFrom_activity=st;
        this.ballsGone_listener=ballsGone_listener;
       // activity = (TransparentActivity) context;
        switch (level) {
            case 0:
                BALLS = ANIMALS;
                break;
            case 1:
                BALLS = ANIMALS;
                break;
            case 2:
                BALLS = ANIMALS;
                break;
            case 3:
                BALLS = ANIMALS;
                break;
            case 4:
                BALLS = ANIMALS;
                break;
            case 5:
                BALLS = ANIMALS;
                break;
        }
    }

    ////////////////////////////////// DRAWING STARTS *******************////////////

    @Override
    protected void onDraw(Canvas canvas) {
        // Update, create, delete
        update();

        // Draw
        for (Ball ball : mBalls) ball.draw(canvas);

        // Invalidate
        invalidate();
    }
    ///////////////////// THIS IS THE UPDATE ////////////////////////
    protected void update() {
        // Remove finished
        for (int i = 0; i < mBalls.size(); i++) {
            Ball ball = mBalls.get(i);
            if (ball.isFinished(getWidth())) {
               // ball.recycle();
                //mBalls.remove(i);
                ball.changeDirection();
            }
        }

        // Update and movement
        for (Ball ball : mBalls) ball.update();

        // Add new
        if (!isFinish() && mBalls.size() < NUM_BALLS) {
            Ball ball = new Ball();
            ball.init(mRandom);
            ball.bitmap = BitmapFactory.decodeResource(getResources(), BALLS[mRandom.nextInt(BALLS.length)]);
            ball.offsetX = getWidth() / 2;
            ball.offsetY = getHeight()/2;

            mBalls.add(ball);
        }
    }

    int timeInterval=2000;
    int timeIntervalListener=2500;
    int timeCount=0;
    Handler blastHandler=new Handler();
    Runnable blastRunnable=new Runnable() {
        @Override
        public void run() {
            timeCount++;
            Ball ball = mBalls.get(0);
            mBalls.remove(0);

            if(mBalls.size()==0)
                ballsGone_listener.finishedBalls();

            mediaPlayer = MediaPlayer.create(getContext(), R.raw.sd_faces_pop);
            mediaPlayer.start();

            if(timeCount<3)
            blastHandler.postDelayed(blastRunnable,700);

        }
    };

    boolean listeneing=false;
    Handler blastHandlerResart=new Handler();
    Runnable restartRunnable=new Runnable() {
        @Override
        public void run() {
            listeneing=false;
        }
    };

    ///////////////////////// THIS WILL BE CALLED FROM THE ACTIVITY //////////////////////
    public void blastThreeShots(){
        int count = mBalls.size();
        if(count>2 && !listeneing){
            listeneing=true;
            blastHandler.postDelayed(blastRunnable, timeInterval);
            blastHandlerResart.postDelayed(restartRunnable, timeIntervalListener);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        for (Ball ball : mBalls) ball.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(strategyFrom_activity==Strategy.TAP) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                float x = event.getRawX();
                float y = event.getRawY();

                int index = -1;
                int count = mBalls.size();
                for (int i = count - 1; i >= 0; i--) {
                    Ball ball = mBalls.get(i);
                    RectF rect = ball.getRectF();
                    if (rect.contains(x, y)) {
                        index = i;
                        break;
                    }
                }

                if (index != -1) {
                    mediaPlayer = MediaPlayer.create(getContext(), R.raw.sd_faces_pop);
                    mediaPlayer.start();
                    mBalls.remove(index);

                    if(mBalls.size()==0)
                        ballsGone_listener.finishedBalls();

                    return true;
                } else {
                    //finish it
                    // activity.finishActivity();
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void getShakePosition(float x, float y) {

    }

    /**
     * Gumball.
     */
    static class Ball {

        static final int MAX_K = 128;
        static final int MIN_K = 32;

        static final int MAX_SPEED = 8;
        static final int MIN_SPEED = 1;

        Bitmap bitmap;
        int speed;
        int direction;
        int x;
        int y;
        int k;
        int offsetX;
        int offsetY;

        boolean isFinished(int width) {
            return x + offsetX < -1 * bitmap.getWidth() || x + offsetX > width;
        }

        void changeDirection(){
            direction=direction==1?-1:1;
        }

        void init(Random random) {
            x = 0;
            y = 0;
            //range from 32-128
            k = random.nextInt(MAX_K - MIN_K) + MIN_K;
            direction = random.nextBoolean() ? 1 : -1;
            speed = random.nextInt(MAX_SPEED - MIN_SPEED) + MIN_SPEED;
        }

        void draw(Canvas canvas) {
            int x = this.offsetX + this.x;
            int y = this.offsetY - this.y;

            canvas.drawBitmap(bitmap, x, y, null);
        }
        
        void update() {
            x = x + direction * speed;
            y = (int) (k * Math.log((double) Math.abs(x) + 1));
        }

        void recycle() {
            if (bitmap != null) {
                bitmap.recycle();
                bitmap = null;
            }
        }

        RectF getRectF() {
            int x = this.offsetX + this.x;
            int y = this.offsetY - this.y;
            return new RectF(x, y, x + bitmap.getWidth(), y + bitmap.getHeight());
        }
    }


    //please which ever activity use this class must implement this interface and send strategy in contructor
    //when the balls are gone the finishedBalls() will be called automatically
    //***** call blastThreeShots() when u detect shake or blow;

    public interface AllBallsGone{
        public void finishedBalls();
    }


}
