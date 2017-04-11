package com.example.charlie.g1_roll_it_in;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by Thong on 7/04/2017.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback, GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener{
    private MainThread thread;
    private Ball ball;
    private Goal goal;
    private Player player;
    private GestureDetector gestureDetector;
    private int width, height;

    public GameView(Context context){
        super(context);
        getHolder().addCallback(this);
        gestureDetector = new GestureDetector(context, this);
        gestureDetector.setOnDoubleTapListener(this);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
        thread = new MainThread(getHolder(), this);
        setFocusable(true);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;

        //create a ball
        ball = createBall();

        //create a player
        player = new Player("Justin");

        //create a goal
        float goalRadius = width / 7;
        goal = new Goal(width / 2, (int)(goalRadius * 2), goalRadius, width, height);
    }

    public Ball createBall(){
        //create a ball
        float ballRadius = width / 10;
        return new Ball(width / 2 , height - (int)(ballRadius * 2), ballRadius, width, height);
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
            ball = createBall();
        }
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawColor(Color.argb(255, 255, 255, 255));
        player.draw(canvas);
        goal.draw(canvas);
        if(ball != null) {
            ball.draw(canvas);//draw ball after the goal so it will appear on top
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

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
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(ball != null) {
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


}