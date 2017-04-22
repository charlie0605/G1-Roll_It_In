package com.example.charlie.g1_roll_it_in.gameUI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.charlie.g1_roll_it_in.R;
import com.example.charlie.g1_roll_it_in.gameModel.Ball;
import com.example.charlie.g1_roll_it_in.gameModel.Goal;
import com.example.charlie.g1_roll_it_in.gameModel.Player;

import java.util.Random;

/**
 * Created by Thong on 7/04/2017.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback, GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener{
    private MainThread thread;
    private Drawable drawable;
    private Ball ball;
    private Goal goal;
    private Player player;
    private boolean gameOver;
    private RectF outerRect;
    private Rect mainRect, restartRect;
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
        gameOver = false;
        drawable = createRandomDrawable();
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
        return ResourcesCompat.getDrawable(getResources(), images[rand.nextInt(images.length)], null);
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
        if(!gameOver) {
            player.update();
            goal.update(player);
            goal.update();
            if (ball != null) {
                if (checkForGoal()) {
                    player.scoreGoal();
                } else {
                    ball.update();
                }
                if (ball.isOut()){
                    gameOver = true;
                }
            } else {
                ball = createBallAtRandomX();
            }
        }
    }

    public void draw(Canvas canvas){
        TextPaint paint = new TextPaint();
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setShadowLayer(20, 0, 0, Color.WHITE);
        paint.setTextSize(width / 10);
        super.draw(canvas);
        drawable.setBounds(canvas.getClipBounds());
        drawable.draw(canvas);
        goal.draw(canvas);

        if(gameOver) {
            int spacing = width / 10;
            paint.setColor(Color.argb(150, 195, 195, 195));
            //left, top, right, bottom
            outerRect = new RectF(width / 6, height / 4, width / 6 * 5, height/ 4 * 3);
            restartRect = new Rect(width / 8 * 3, height / 5 * 3, width / 8 * 5, height / 20 * 13);
            mainRect = new Rect(restartRect.left, restartRect.top + spacing, restartRect.right, restartRect.bottom + spacing);

            canvas.drawRoundRect(outerRect, 20, 20, paint);

            paint.setColor(Color.RED);
            canvas.drawText("GAME OVER", width / 2, outerRect.top + spacing , paint);
            paint.setColor(Color.GRAY);
            canvas.drawRect(restartRect, paint);
            canvas.drawRect(mainRect, paint);
            paint.setTextSize(width / 15);
            paint.setColor(Color.BLACK);
            canvas.drawText("Restart", restartRect.exactCenterX(), restartRect.bottom, paint);
            canvas.drawText("Main", mainRect.exactCenterX(), mainRect.bottom, paint);
            player.draw(canvas);
        } else {
            canvas.drawText(player.getScore() + "", width / 2, height / 2, paint);
        }

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

        if(xDiff <= radiusDiff  && yDiff <= radiusDiff){//goal
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
            if(!ball.isTouched()) {
                System.out.println("Before scaling:");
                System.out.println(velocityX);
                System.out.println(velocityY);
//            ball.setSpeedX(velocityX);
//            ball.setSpeedY(velocityY);
                ball.setSpeed(velocityX, velocityY);
                System.out.println("After scaling:");
                System.out.println("Speed X: " + ball.getSpeedX());
                System.out.println("Speed Y: " + ball.getSpeedY());
                ball.setTouched(true);
            }
        }
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        if(gameOver) {
            if (restartRect.contains((int) e.getX(), (int) e.getY())) {
                System.out.println("Restart pressed!");
                gameOver = false;
                System.out.println(gameOver);
                player.setScore(0);
                ball = createBallAtCenterX();
            }
            if (mainRect.contains((int) e.getX(), (int) e.getY())) {
                System.out.println("Main pressed!");
                ((GameUI)getContext()).setContentView(R.layout.menu);
                ((GameUI)getContext()).init();
                gameOver = false;
                player.setScore(0);
                ball = createBallAtCenterX();
            }
            return true;
        } else {
            return false;
        }
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
