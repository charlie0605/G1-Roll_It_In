package com.example.charlie.g1_roll_it_in.gameUI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Looper;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.example.charlie.g1_roll_it_in.R;
import com.example.charlie.g1_roll_it_in.gameModel.Ball;
import com.example.charlie.g1_roll_it_in.gameModel.Effect;
import com.example.charlie.g1_roll_it_in.gameModel.Goal;
import com.example.charlie.g1_roll_it_in.gameModel.Player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.os.Handler;
import android.os.Message;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import static com.example.charlie.g1_roll_it_in.gameUI.NameUI.playerName;

/**
 * Created by Thong on 7/04/2017.
 */


public class GameView extends SurfaceView implements SurfaceHolder.Callback, GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener{
    private MainThread thread;
    private Drawable drawable;
    private Ball ball;
    private Goal goal;
    private Player player;
    private HashMap<String,Integer> playersMap;
    private boolean gameOver, pause, response;
    private RectF outerRect;
    private Rect secondRect, firstRect;
    private TextPaint paint;
    private GestureDetector gestureDetector;
    public static int width, height;
    private float ballRadius, goalRadius;
    private int round;
    private Handler handler;

    public String path = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/players";

    /**
     * Construct a game view
     *
     * @param context
     */
    public GameView(Context context){
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        playersMap = new HashMap<>();
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

        ballRadius = width / 10;
        goalRadius = width / 7;
        round = 0;
        ball = createBallAtCenterX();//create a ball
        player = new Player(playerName);//create a player
        goal = createGoal();//create a goal
        gameOver = false;
        pause = false;
        response = false;
        drawable = createRandomDrawable();
        paint = new TextPaint();
        player.setScore(9);

        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdir();
        }

        readFile();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==1){
                    Toast.makeText(getContext(), "Good job", Toast.LENGTH_SHORT).show();
                }
                if(msg.what==2){
                    Toast.makeText(getContext(), "Better luck next time", Toast.LENGTH_SHORT).show();
                }
            }
        };
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
        return new Ball(width / 2 , height - (int)(ballRadius * 2), ballRadius);
    }

    public Ball createBallAtRandomX(){
        float minX = ballRadius;
        float maxX = width - ballRadius;
        return new Ball(getRandomFloatBetween(minX, maxX), height - (int)(ballRadius * 2), ballRadius);
    }

    public Goal createGoal(){
        return new Goal(width / 2, (int)(goalRadius * 2), goalRadius);
    }

    public void update(){

        if(!gameOver && !pause) {
            player.update();
            goal.update(player);
            goal.update();
            if (ball != null) {
                if(round <= 0){
                    ballRadius = width / 10;
                    ball.setRadius(ballRadius);
                    goalRadius = width / 7;
                    goal.setRadius(goalRadius);
                }

                if (checkForGoal()) {
                    Message msg = handler.obtainMessage();
                    msg.what=1;
                    handler.sendMessage(msg);
                    player.scoreGoal();
                } else {
                    ball.update();
                }
                if (ball.isOut()){
                    Message msg = handler.obtainMessage();
                    msg.what = 2;
                    handler.sendMessage(msg);
                    gameOver = true;
                    if(!playersMap.containsKey(player.getName()) || playersMap.get(player.getName())< player.getHighScore())
                        playersMap.put(player.getName(),player.getHighScore());
                    writingToFile();
                }
            } else {
                ball = createBallAtRandomX();
            }
            if (player.getScore() > 0 && player.getScore() % 10 == 0 && !response){
                pause = true;
            }
        }
    }

    public boolean isGameOver(){
        return gameOver;
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
//        drawable.setBounds(canvas.getClipBounds());
//        drawable.draw(canvas);
        canvas.drawColor(Color.WHITE);
        goal.draw(canvas);
        if(gameOver) {
            drawPopUp(canvas, "GAME OVER", "Restart", "Main");
            player.draw(canvas);
        } else {
            paint.setColor(Color.BLACK);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setShadowLayer(20, 0, 0, Color.WHITE);
            paint.setTextSize(width / 10);
            canvas.drawText(player.getScore() + "", width / 2, height / 2, paint);
        }

        if(ball != null) {
            ball.draw(canvas);//draw ball after the goal so it will appear on top
        }
        if(pause && !response){//for every 10th score
            drawPopUp(canvas, "Chance", "Yes", "No");
            canvas.drawText("Use a chance?", width / 2, height / 2, paint);
        }
    }

    public void drawPopUp(Canvas canvas, String heading, String button1, String button2){
        int spacing = width / 10;
        paint.setColor(Color.argb(150, 195, 195, 195));
        //left, top, right, bottom
        outerRect = new RectF(width / 6, height / 4, width / 6 * 5, height/ 4 * 3);
        firstRect = new Rect(width / 8 * 3, height / 5 * 3, width / 8 * 5, height / 20 * 13);
        secondRect = new Rect(firstRect.left, firstRect.top + spacing, firstRect.right, firstRect.bottom + spacing);
        canvas.drawRoundRect(outerRect, 20, 20, paint);
        paint.setColor(Color.RED);
        canvas.drawText(heading, width / 2, outerRect.top + spacing , paint);
        paint.setColor(Color.GRAY);
        canvas.drawRect(firstRect, paint);
        canvas.drawRect(secondRect, paint);
        paint.setTextSize(width / 15);
        paint.setColor(Color.BLACK);
        canvas.drawText(button1, firstRect.exactCenterX(), firstRect.bottom, paint);
        canvas.drawText(button2, secondRect.exactCenterX(), secondRect.bottom, paint);
    }

    //helper methods--------------------------------------------------------------------------------
    public boolean checkForGoal(){
        float xDiff = Math.abs(goal.getX() - ball.getX());
        float yDiff = Math.abs(goal.getY() - ball.getY());
        float delta = ball.getRadius() / 2;
        float radiusDiff = goal.getRadius() - ball.getRadius() + delta;

        if(xDiff <= radiusDiff  && yDiff <= radiusDiff){//goal
            ball = null;
            if(round > 0){
                round--;
            }
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

    public void writingToFile(){
        String playerInfo = "";
        File file = new File(path + "/players.txt");

        try {
            FileOutputStream fo = new FileOutputStream(file);

            for(Map.Entry<String, Integer> entry: playersMap.entrySet()) {
                playerInfo = entry.getKey() + " " + entry.getValue() + "\n";
                fo.write(playerInfo.toString().getBytes());
            }

            fo.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void readFile(){
        BufferedReader in = null;

        try {
            in = new BufferedReader(new FileReader(path + "/players.txt"));
            String line;
            String[] splitLine;
            while ((line = in.readLine()) != null) {
                splitLine = line.split(" ");
                playersMap.put(splitLine[0], Integer.valueOf(splitLine[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                    in = null;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
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
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {

        if(gameOver) {
            if (firstRect.contains((int) e.getX(), (int) e.getY())) {
                System.out.println("Restart pressed!");
                gameOver = false;
                System.out.println(gameOver);
                player.setScore(0);
                ball = createBallAtCenterX();
                goal = createGoal();
                round = 0;
            }
            if (secondRect.contains((int) e.getX(), (int) e.getY())) {
                System.out.println("Main pressed!");
                ((GameUI)getContext()).finish();
            }
            return true;
        }

        if(pause) {
            paint.setTextSize(width / 20);
            if (firstRect.contains((int) e.getX(), (int) e.getY())) {
                System.out.println("Yes pressed!");
                Effect effect = Effect.getRandomEffect();
                switch (effect) {
                    case BALL_BIG:
                        ballRadius = goalRadius;
                        ball.setRadius(ballRadius);
                        break;
                    case BALL_SMALL:
                        ballRadius *= 0.7;
                        ball.setRadius(ballRadius);
                        break;
                    case GOAL_BIG:
                        goalRadius *= 1.5;
                        goal.setRadius(goalRadius);
                        break;
                    case GOAL_SMALL:
                        goalRadius = ballRadius;
                        goal.setRadius(goalRadius);
                        break;
                    default:
                        break;
                }
                Toast.makeText(this.getContext(), effect.getDescription(), Toast.LENGTH_SHORT).show();
                round = 5;
                pause = false;
                response = true;
            }
            if (secondRect.contains((int) e.getX(), (int) e.getY())) {
                System.out.println("No pressed!");
//                MainThread.canvas.drawText("No change has been made.", width / 2, height / 2, paint);
                Toast.makeText(this.getContext(), "No change has been made.", Toast.LENGTH_SHORT).show();
                pause = false;
                response = true;
            }
            return true;
        }

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
