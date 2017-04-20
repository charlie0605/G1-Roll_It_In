package com.example.charlie.g1_roll_it_in;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.Random;

/**
 * Created by Thong on 7/04/2017.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback, GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener{
    private MainThread thread;
    private Bitmap bitmap;
    private Drawable drawable;
    private Ball ball;
    private Goal goal;
    private Player player;
    private GestureDetector gestureDetector;
    public static int width, height;

    /**
     * Construct a game view
     *
     * @param context
     */
    public GameView(Context context){
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);

        //get the phone display pixels
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;

        //detecting the gesture for flinging the ball
        gestureDetector = new GestureDetector(context, this);
        gestureDetector.setOnDoubleTapListener(this);

        //get touchscreen input, so gesture detector can be used
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });


        ball = createBallAtCenterX();//create a ball
        player = new Player("Justin");//create a player
        goal = createGoal();//create a goal
        drawable = createRandomDrawable();
//        bitmap = createRandomBitmap();//create a random bitmap
    }

    public Bitmap createRandomBitmap(){
        Random rand = new Random();

        //load the images
        int[] images = new int[]{
                R.drawable.bg1,
                R.drawable.bg2,
                R.drawable.bg3,
                R.drawable.bg4,
                R.drawable.bg5,
                R.drawable.bg6,
                R.drawable.bg7,
                R.drawable.bg8,
                R.drawable.bg9,
                R.drawable.bg10
        };

        //return a random image
        return BitmapFactory.decodeResource(getResources(), images[rand.nextInt(images.length) + 1]);
    }

    public Drawable createRandomDrawable(){
        Random rand = new Random();

        //load the images
        int[] images = new int[]{
                R.drawable.bg1,
                R.drawable.bg2,
                R.drawable.bg3,
                R.drawable.bg4,
                R.drawable.bg5,
                R.drawable.bg6,
                R.drawable.bg7,
                R.drawable.bg8,
                R.drawable.bg9,
                R.drawable.bg10
        };

        //return a random image
        return getResources().getDrawable(images[rand.nextInt(images.length) + 1]);
    }

    public Ball createBallAtCenterX(){
        float ballRadius = width / 10;
        return new Ball(width / 2 , height - (int)(ballRadius * 2), ballRadius);
    }

    public Ball createBallAtRandomX(){
        float ballRadius = width / 10;
        float minX = ballRadius;
        float maxX = width - ballRadius;
        return new Ball(getRandomFloatBetween(minX, maxX), height - (int)(ballRadius * 2), ballRadius);
    }

    public Goal createGoal(){
        float goalRadius = width / 7;
        return new Goal(width / 2, (int)(goalRadius * 2), goalRadius);
    }

    public void update(){
        player.update();
        goal.update(player);
        goal.update();
        if(ball != null) {
            ball.update();
            if(checkForGoal()){
                player.addScore();
            }
        } else {
            ball = createBallAtRandomX();
        }
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
//        canvas.drawColor(Color.BLACK);
//        canvas.drawBitmap(bitmap, 0, 0, null);
        drawable.setBounds(canvas.getClipBounds());
        drawable.draw(canvas);
        player.draw(canvas);
        goal.draw(canvas);
        if(ball != null) {
            ball.draw(canvas);//draw ball after the goal so it will appear on top
        }
    }

    //helper methods--------------------------------------------------------------------------------
    public boolean checkForGoal(){
        float xDiff = Math.abs(goal.getX() - ball.getX());
        float yDiff = Math.abs(goal.getY() - ball.getY());
        float delta = ball.getRadius() / 2;
        float radiusDiff = goal.getRadius() - ball.getRadius() + delta;

        if(xDiff <= radiusDiff  && yDiff <= radiusDiff){
            ball = null;
            return true;
        } else {
            return false;
        }
    }

    public float getRandomFloatBetween(float min, float max){
        if(min < max) {
            Random r = new Random();
            return r.nextFloat() * (max - min) + min;
        } else {
            throw new IllegalArgumentException("Min value shouldn't be higher than max value");
        }
    }
    //----------------------------------------------------------------------------------------------

    //surface holder methods------------------------------------------------------------------------
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            retry = false;
        }
    }
    //----------------------------------------------------------------------------------------------

    //gesture detection-----------------------------------------------------------------------------
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(ball != null) {//check if the ball exists
            System.out.println("Before scaling:");
            System.out.println(velocityX);
            System.out.println(velocityY);
            ball.setSpeedX(velocityX);
            ball.setSpeedY(velocityY);
            System.out.println("After scaling:");
            System.out.println("Speed X: " + ball.getSpeedX());
            System.out.println("Speed Y: " + ball.getSpeedY());
        }
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }
    //----------------------------------------------------------------------------------------------

}
