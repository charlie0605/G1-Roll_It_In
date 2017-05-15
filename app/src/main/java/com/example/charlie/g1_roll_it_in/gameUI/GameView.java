package com.example.charlie.g1_roll_it_in.gameUI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.example.charlie.g1_roll_it_in.R;
import com.example.charlie.g1_roll_it_in.gameModel.Ball;
import com.example.charlie.g1_roll_it_in.gameModel.Bar;
import com.example.charlie.g1_roll_it_in.gameModel.Effect;
import com.example.charlie.g1_roll_it_in.gameModel.Goal;
import com.example.charlie.g1_roll_it_in.gameModel.Player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.os.Handler;
import android.os.Message;

import static com.example.charlie.g1_roll_it_in.gameUI.NameUI.playerName;

/**
 * Created by Thong on 7/04/2017.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback, GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener{
    //variables-------------------------------------------------------------------------------------
    private MainThread thread;
    private int color;
    private Ball ball;
    private Goal goal;
    private Player player;
    private ArrayList<Bar> bars;
    private HashMap<String,Integer> playersMap;
    private boolean gameOver, effectPause, response, pause;
    private RectF outerRect;
    private Rect secondRect, firstRect, pauseRect;
    private TextPaint paint;
    private GestureDetector gestureDetector;
    public static int width, height;
    private float ballRadius, goalRadius;
    private int round;
    private Handler handler;
    public String path = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/players";
    //----------------------------------------------------------------------------------------------

    //constructor-----------------------------------------------------------------------------------
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
        color = getRandomColor();
        playersMap = new HashMap<>();
        ballRadius = width / 10;
        goalRadius = width / 7;
        round = 0;
        ball = createBallAtCenterX();//create a ball
        player = new Player(playerName);//create a player
        goal = createGoal();//create a goal
        bars = new ArrayList<>();
        bars.add(new Bar(0, 0, width / 20, height / 2));
        bars.add(new Bar(width - (width/20) - 2,0,width/20,height/2));

        bars.get(0).setSpeedY(15);
        bars.get(1).setSpeedY(15);

        gameOver = false;
        effectPause = false;
        response = false;
        pause = false;
        pauseRect = new Rect(width * 9 / 10, 0, width, width / 10);
        paint = new TextPaint();
        player.setScore(9);

        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdir();
        }

        readFile();//read files to create score map
        handler = new Handler(){//a handler to create toast for feedbacks
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Toast myToast = null;
                if(msg.what==0){
                    myToast = Toast.makeText(getContext(), getEmojiByUnicode(0x1F44D), Toast.LENGTH_SHORT);
                    if(goal.getX() > width / 2){
                        myToast.setGravity(Gravity.TOP, (int) (-width / 5), 0);
                    } else {
                        myToast.setGravity(Gravity.TOP, (int) (width / 5), 0);
                    }
                }
                if(msg.what==1){
                    myToast = Toast.makeText(getContext(), getEmojiByUnicode(0x1F613), Toast.LENGTH_SHORT);
                    if(goal.getX() > width / 2){
                        myToast.setGravity(Gravity.TOP, (int) (-width / 5), 0);
                    } else {
                        myToast.setGravity(Gravity.TOP, (int) (width / 5), 0);
                    }
                }
                if(msg.what==2) {
                    myToast = Toast.makeText(getContext(), "Effect wears off after 5 turns.", Toast.LENGTH_SHORT);
                }
                myToast.show();
                Handler handler = new Handler();
                final Toast finalMyToast = myToast;

                //shorten the duration of the toast to half a second
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finalMyToast.cancel();
                    }
                }, 500);
            }
        };

        //update the highscore of the player if their name exists in the file
        if(playersMap.containsKey(player.getName())){
            player.setHighScore(playersMap.get(player.getName()));
        }
    }
    //----------------------------------------------------------------------------------------------

    //helper methods--------------------------------------------------------------------------------
    /**
     * Returns a random drawable for the background
     * @return
     */
    public Drawable createRandomDrawable(){
        Random rand = new Random();

        //load the images
        int[] images = new int[]{
                R.drawable.bg1,
                R.drawable.bg2,
                R.drawable.bg3,
                R.drawable.bg4
        };

        //return a random image
        return ResourcesCompat.getDrawable(getResources(), images[rand.nextInt(images.length)], null);
    }

    public int getRandomColor(){
        Random rand = new Random();

        int[] colors = new int[]{
                Color.argb(255, 255, 240, 240), //lavenderblush
                Color.argb(255, 224, 255, 255), //lightcyan
                Color.argb(255, 209, 248, 227), //lightgreen
                Color.argb(255, 255, 240, 208) //papayawhip
        };

        return colors[rand.nextInt(colors.length)];
    }

    /**
     * Creates and returns a ball at the middle x position
     * @return
     */
    public Ball createBallAtCenterX(){
        return new Ball(width / 2 , height - (int)(ballRadius * 2), ballRadius);
    }

    /**
     * Creates and returns a ball at random x position
     * @return
     */
    public Ball createBallAtRandomX(){
        float minX = ballRadius;
        float maxX = width - ballRadius;
        return new Ball(getRandomFloatBetween(minX, maxX), height - (int)(ballRadius * 2), ballRadius);
    }

    /**
     * Creates and returns a new goal
     * @return
     */
    public Goal createGoal(){
        return new Goal(width / 2, (int)(goalRadius * 2), goalRadius);
    }


    public void barChecking(){
        if(ball.getBound().intersect(bars.get(0).getBound())){
//            ball.setSpeedY(ball.getSpeedY()*-1);
            if(ball.getX()< bars.get(0).getX())
            ball.setSpeedX(ball.getSpeedX() *-1);
        }
    }
    /**
     * Updates different game objects
     */
    public void update(){
        if(!gameOver && !effectPause) {//if it's not game over and not every 10th iteration
            if(!pause) {//if the game isn't paused
                player.update();

                goal.update(player);
                goal.update();




//                if(ball.getX() <= bars.get(0).getX() ){
//                    ball.setSpeedX((ball.getSpeedX()* -1));
////                    if(y < radius) {
////                        y = (int) radius;
////                    }
////                    if(y > GameView.height - radius){
////                        y = (int) (GameView.height - radius);
////                    }
//                }
                if (ball != null) {//if ball is not disappearing
                    for(Bar bar : bars){
                        bar.checkCollision(ball);
                        bar.update();
                    }

                    if (round <= 0) {//if there's no effect activating, set the radius of the ball and goal to default value
                        ballRadius = width / 10;
                        ball.setRadius(ballRadius);
                        goalRadius = width / 7;
                        goal.setRadius(goalRadius);
                    }
                    if (checkForGoal()) {//if there's a goal
                        Message msg = handler.obtainMessage();
                        msg.what = 0;
                        handler.sendMessage(msg);//call a feedback

                        player.scoreGoal();//update the score
                    } else {
                        ball.update();
                    }
                    if (ball.isOut()) {//if ball is out
                        Message msg = handler.obtainMessage();
                        msg.what = 1;
                        handler.sendMessage(msg);//call a feedback

                        gameOver = true;//confirm game over
                        if (!playersMap.containsKey(player.getName()) || playersMap.get(player.getName()) < player.getHighScore())
                            playersMap.put(player.getName(), player.getHighScore());
                        writingToFile();//update the text file with the new score
                    }
                } else {//if ball disappears or is goaled
                    ball = createBallAtRandomX();//create a new ball at random x position
                }
                //pause the game at every 10th iteration
                if (player.getScore() > 0 && player.getScore() % 10 == 0 && !response) {
                    effectPause = true;
                }
                //update the response
                if (player.getScore() % 10 != 0) {
                    response = false;
                }
            }
        }
    }

    /**
     * Draws the objects
     * @param canvas
     */
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawColor(color);//draw a random colored background
        goal.draw(canvas);
        for(Bar bar : bars){
            bar.draw(canvas);
        }

        if(!pause) {
            //draw a pause button on the right upper corner
            paint.setColor(Color.BLACK);
            canvas.drawRect(pauseRect, paint);
            paint.setColor(Color.WHITE);
            paint.clearShadowLayer();
            canvas.drawRect(width * 9 / 10, width / 50, width, width / 25, paint);
            canvas.drawRect(width * 9 / 10, width / 50 * 3, width, width / 25 * 2, paint);
        } else {//if game is paused
            drawPopUp(canvas, "PAUSE", "Sound", "Main");//draws a pause pop up menu
        }

        if(gameOver) {
            drawPopUp(canvas, "GAME OVER", "Restart", "Main");//draws a game over menu
            player.draw(canvas);//draws the player's score
        } else {//if game is not over
            //draw the current score at the center of the screen
            paint.setColor(Color.BLACK);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setShadowLayer(20, 0, 0, Color.WHITE);
            paint.setTextSize(width / 10);
            canvas.drawText(player.getScore() + "", width / 2, height / 2, paint);
        }

        if(ball != null) {//if ball is not goaled
            ball.draw(canvas);//draw ball after the goal so it will appear on top
        }

        if(effectPause && !response){//for every 10th score
            drawPopUp(canvas, "Chance", "Yes", "No");//draws a pop up for chance
            canvas.drawText("Use a chance?", width / 2, height / 2, paint);
        }
    }

    /**
     * This method creates a pop up with two rectangles as buttons.
     * @param canvas
     * @param heading
     * @param button1
     * @param button2
     */
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

    /**
     * Checks if the ball overlaps with the goal.
     * @return true if there is a goal and false if otherwise
     */
    public boolean checkForGoal(){
        float xDiff = Math.abs(goal.getX() - ball.getX());
        float yDiff = Math.abs(goal.getY() - ball.getY());
        float delta = ball.getRadius() / 2;
        float radiusDiff = goal.getRadius() - ball.getRadius() + delta;

        //checks the difference in radius of the ball and goal objects
        if(xDiff <= radiusDiff  && yDiff <= radiusDiff){//goal
            ball = null;
            if(round > 0){
                round--;
                if(round == 0){
                    handler.sendMessage(handler.obtainMessage(2));//calls for a feedback
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns a random float between the specified range
     * @param min
     * @param max
     * @return
     */
    public float getRandomFloatBetween(float min, float max){
        if(min < max) {
            Random r = new Random();
            return r.nextFloat() * (max - min) + min;
        } else {
            throw new IllegalArgumentException("Min value shouldn't be higher than max value");
        }
    }

    /**
     * Gets the string representation of an emoji by specifying its unicode value
     * @param unicode
     * @return
     */
    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

    /**
     * Writes the score into a text file
     */
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

    /**
     * Reads the score from the text file
     */
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

    /**
     * Used to move the ball in the direction of the swipe gesture
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(ball != null) {//check if the ball exists
            if(!ball.isTouched()) {
                //for debugging
                System.out.println("Before scaling:");
                System.out.println(velocityX);
                System.out.println(velocityY);
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

    /**
     * Checks if the buttons of the pop menu have been pressed
     * @param e
     * @return
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if(gameOver) {
            if (firstRect.contains((int) e.getX(), (int) e.getY())) {
                System.out.println("Restart pressed!");
                //restart the game
                gameOver = false;
                System.out.println(gameOver);
                player.setScore(0);
                ball = createBallAtCenterX();
                goalRadius = width / 7;
                goal = createGoal();
                round = 0;
            }
            if (secondRect.contains((int) e.getX(), (int) e.getY())) {
                //exit the game activity and go to main menu
                System.out.println("Main pressed!");
                ((GameUI)getContext()).finish();
            }
            return true;
        }

        if(effectPause) {
            paint.setTextSize(width / 20);
            if (firstRect.contains((int) e.getX(), (int) e.getY())) {
                //get a random effect
                System.out.println("Yes pressed!");
                Effect effect = Effect.getRandomEffect();
                switch (effect) {
                    //apply the effect
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
                Toast.makeText(this.getContext(), effect.getDescription(), Toast.LENGTH_SHORT).show();//inform the player on the effect applied
                round = 5;//make the effect lasts for 5 rounds
                effectPause = false;
                response = true;
            }
            if (secondRect.contains((int) e.getX(), (int) e.getY())) {
                //don't apply effect
                System.out.println("No pressed!");
                Toast.makeText(this.getContext(), "No change has been made.", Toast.LENGTH_SHORT).show();
                effectPause = false;
                response = true;
            }
            return true;
        }

        if(pauseRect.contains((int) e.getX(), (int) e.getY())){//checks if pause button is pressed
            pause = true;
            return true;
        }

        if(pause){//if pause button is pressed
            if(firstRect.contains((int) e.getX(), (int) e.getY())){
                //turn music on and off
                if(MenuUI.music.isPlaying()){
                    MenuUI.music.pause();
                    MenuUI.musicBtn.setChecked(false);
                } else {
                    MenuUI.music.start();
                    MenuUI.musicBtn.setChecked(true);
                }

            }
            if(secondRect.contains((int) e.getX(), (int) e.getY())){
                //go back to main
                System.out.println("Main pressed!");
                ((GameUI)getContext()).finish();
            }

            //if player pressed outside the pop up menu
            if(!outerRect.contains((int) e.getX(), (int) e.getY())){
                pause = false;//close the menu
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
