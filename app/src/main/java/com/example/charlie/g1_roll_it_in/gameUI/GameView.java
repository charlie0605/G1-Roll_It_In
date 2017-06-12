package com.example.charlie.g1_roll_it_in.gameUI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;
import android.os.Handler;
import android.os.Message;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static com.example.charlie.g1_roll_it_in.gameUI.NameUI.playerName;

/**
 * Created by Thong on 7/04/2017.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback, GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener {
    //variables-------------------------------------------------------------------------------------
    private MainThread thread;
    private int color;
    private ArrayList<Ball> balls;
    private ArrayList<Goal> goals;
    private Player player;
    private ArrayList<Bar> bars;
    private HashMap<String, Player> playersMap;
    private boolean gameOver; //when game is over or not
    private boolean effectPause; //when there's a pause at every 10th iteration so that player can choose to use an effect or not
    private boolean response; //when the player has interacted with the pop up created
    private boolean pause; //when the pause button on the game screen is pressed
    private RectF outerRect; //the outer window for the pop up
    private Rect secondRect, firstRect, pauseRect; //the 1st and 2nd rect represent the two buttons in the pop up
    private TextPaint paint;
    private GestureDetector gestureDetector;
    public static int width, height;
    private float ballRadius, goalRadius;
    private int round; //representing the number of rounds left for the effect
    private Handler handler;
    private Effect effect;
    public String path = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/players"; //a path to a file storing player information
    //----------------------------------------------------------------------------------------------

    //constructor-----------------------------------------------------------------------------------
    /**
     * Construct a game view
     *
     * @param context
     */
    public GameView(Context context) {
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
        color = getRandomColor(); //get a random color for the background
        ballRadius = width / 10;
        goalRadius = width / 7;
        round = 0;

        balls = new ArrayList<>();
        balls.add(createBallAtCenterX());//create a ball at the center of the screen and add it to the list

        playersMap = new HashMap<>();
        player = new Player(playerName);//create a player

        goals = new ArrayList<>();
        goals.add(createGoal());//create a goal

        bars = new ArrayList<>();//create an array list of bars to hold the bars creating later

        gameOver = false;
        effectPause = false;
        response = false;
        pause = false;
        pauseRect = new Rect(width * 9 / 10, 0, width, width / 10);
        paint = new TextPaint();

        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();//create the file if it doesn't exist
        }

        readFile();//read files to create score map
        handler = new Handler() {//a handler to create toast for different feedbacks
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Toast myToast = null;
                if (msg.what == 0) { //when the ball is goaled
                    myToast = Toast.makeText(getContext(), getEmojiByUnicode(0x1F44D), Toast.LENGTH_SHORT); //emoji for a sad face
                    if (!goals.isEmpty()) {
                        if (goals.get(0).getX() > width / 2) { //if the goal is on the half right of the screen
                            myToast.setGravity(Gravity.TOP, (int) (-width / 5), 0); //set the position of the toast at the left
                        } else {
                            myToast.setGravity(Gravity.TOP, (int) (width / 5), 0); //set the position of the toast at the right
                        }
                    }
                }
                if (msg.what == 1) { //when the ball is out
                    myToast = Toast.makeText(getContext(), getEmojiByUnicode(0x1F613), Toast.LENGTH_SHORT); //emoji code for a thumb up
                    if (!goals.isEmpty()) {
                        if (goals.get(0).getX() > width / 2) {
                            myToast.setGravity(Gravity.TOP, (int) (-width / 5), 0);
                        } else {
                            myToast.setGravity(Gravity.TOP, (int) (width / 5), 0);
                        }
                    }
                }
                if (msg.what == 2) {//when the effect is gone
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
        if (playersMap.containsKey(player.getName())) {
            player.setHighScore(playersMap.get(player.getName()).getHighScore());
        }
    }
    //----------------------------------------------------------------------------------------------

    //helper methods--------------------------------------------------------------------------------
    /**
     * Returns a color from the pre-selected colors for the background
     * @return an interger representing a color
     */
    public int getRandomColor() {
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
     *
     * @return
     */
    public Ball createBallAtCenterX() {
        return new Ball(width / 2, height - (int) (width / 5), ballRadius);
    }

    /**
     * Creates and returns a ball at random x position
     *
     * @return
     */
    public Ball createBallAtRandomX() {
        float minX = ballRadius * 2;
        float maxX = width - ballRadius * 2;
        return new Ball(getRandomFloatBetween(minX, maxX), height - (int) (width / 5), ballRadius);
    }

    /**
     * Creates and returns a new goal
     *
     * @return
     */
    public Goal createGoal() {
        return new Goal(width / 2, (int) (goalRadius * 2), goalRadius);
    }

    /**
     * Updates different game objects
     */
    public void update() {
        if (!gameOver && !effectPause) {//if it's not game over and not every 10th iteration
            if (!pause) {//if the game isn't paused
                player.update();//update the score of the player

                if (effect == Effect.GOAL_MULTIPLE) { //if multiple goal effect is activating
                    createThreeGoals(); //create three goals instead of just one
                } else {
                    if (goals.size() == 3) { //check if there are three goals on the screen
                        goals.clear();//remove all the goals
                        goals.add(createGoal());//create a single goal and display it
                    }
                }
                for (Goal goal : goals) {
                    if (goals.size() == 1) {//only move the goal when there's just one goal
                        goal.update(player);//this sets the speed of the goal according to the player score
                    }
                    goal.update();//update the position of the goals
                }

                //for the first 10 scores, create two bars
                if (bars.isEmpty() && player.getScore() < 10) {
                    bars.add(new Bar(0, 0, width / 30, height));
                    bars.add(new Bar(width - (width / 30), 0, width / 30, height));
                }
                //for the score between 10 and 20, create no bar
                if (!bars.isEmpty() && player.getScore() >= 10 && player.getScore() < 20) {
                    bars.clear();
                }
                //between score of 20 and 25, create a vertical bar of slow moving speed
                if (bars.isEmpty() && player.getScore() >= 20 && player.getScore() < 25) {
                    bars.add(new Bar(0, height / 3, width / 6, width / 30));
                    bars.get(0).setSpeedX(width / 100);
                }
                //after the score of 25, make the vertical bar moves faster
                if (!bars.isEmpty() && player.getScore() == 25 && player.getScore() >= 20) {
                    if (bars.get(0).getWidth() != width / 4) {
                        bars.get(0).setWidth(width / 4);
                    }
                }

                for (Bar bar : bars) {
                    bar.update(); //update the position of the bars on the screen
                }

                for (Ball ball : balls) {
                    if (ball != null) {//if there is a ball
                        for (Bar bar : bars) {
                            bar.checkCollision(ball);//check collision of each ball to the bars
                        }

                        if (round <= 0) {//if there's no effect activating, set the radius of the ball and goal to default value
                            ballRadius = width / 10;
                            ball.setRadius(ballRadius);
                            goalRadius = width / 7;
                            goals.get(0).setRadius(goalRadius);
                        }

                        if (checkForGoal(ball)) {//if there's a goal
                            Message msg = handler.obtainMessage();
                            msg.what = 0;
                            handler.sendMessage(msg);//call the feedback for a goal

                            if (balls.isEmpty()) {
                                player.scoreGoal();//update the player score only when all balls are goaled
                            }
                        } else {
                            ball.update();//update the position of the ball and check if it's out of the screen
                        }

                        if (ball.isOut()) {//if ball is out
                            Message msg = handler.obtainMessage();
                            msg.what = 1;
                            handler.sendMessage(msg);//call a feedback for a lost
                            gameOver = true;//confirm game over
                            if (!playersMap.containsKey(player.getName()) || playersMap.get(player.getName()).getHighScore() < player.getHighScore())
                                playersMap.put(player.getName(), player);
                            writingToFile();//update the text file with the new score
                        }
                    }
                }
                if (balls.isEmpty()) {//if ball disappears or is goaled, create new balls
                    if (effect == Effect.BALL_MULTIPLE) {
                        createThreeBalls();//create three balls instead of one if there's an effect to have multiple balls
                    } else {
                        balls.add(createBallAtRandomX());//create a ball at random x position of the screen
                    }
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
     * Create three balls at different positions
     */
    private void createThreeBalls() {
        balls.clear(); //remove all the balls
        balls.add(new Ball(ballRadius * 2, height - width / 5, ballRadius)); //add a ball on the right of the screen
        balls.add(new Ball(width - ballRadius * 2, height - width / 5, ballRadius)); //add a ball on the left of the screen
        balls.add(createBallAtCenterX()); //add a ball at the center of the screen
    }

    /**
     * Create three goals at different positions
     */
    private void createThreeGoals() {
        goals.clear(); //remove all the goals
        goals.add(new Goal(width / 7, (int) (goalRadius * 2), goalRadius)); //add a goal to right of the screen
        goals.add(createGoal()); //add a goal at the center of the screen
        goals.add(new Goal(width - width / 7, (int) (goalRadius * 2), goalRadius)); //add a goal to the left of the screen
    }

    /**
     * Draws the objects onto the screen
     *
     * @param canvas an object to draw on
     */
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(color);//draw a random colored background

        for (Goal goal : goals) {
            goal.draw(canvas); //draw the goals onto the screen
        }
        for (Bar bar : bars) {
            bar.draw(canvas); //draw the bars onto the screen
        }

        if (!pause) {
            //draw a pause button on the right upper corner
            paint.setColor(Color.BLACK);
            canvas.drawRect(pauseRect, paint);//draw the black background of the pause button
            paint.setColor(Color.WHITE);
            paint.clearShadowLayer();
            canvas.drawRect(width * 9 / 10, width / 50, width, width / 25, paint);//draw the white spacing
            canvas.drawRect(width * 9 / 10, width / 50 * 3, width, width / 25 * 2, paint);//draw the 2nd white spacing
        } else {//if game is paused
            drawPopUp(canvas, "PAUSE", "Sound", "Main");//draws a pause pop up menu
        }

        if (gameOver) { //if the game is over
            drawPopUp(canvas, "GAME OVER", "Restart", "Main");//draws a game over menu
            player.draw(canvas);//draws the player's score
        } else {//if game is not over
            paint.setColor(Color.BLACK);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setShadowLayer(20, 0, 0, Color.WHITE);
            paint.setTextSize(width / 10);
            canvas.drawText(player.getScore() + "", width / 2, height / 2, paint);//draw the current score at the center of the screen
        }

        for (Ball ball : balls) {
            if (ball != null) {//if ball is not goaled
                ball.draw(canvas);//draw ball after the goal so it will appear on top of goal
            }
        }

        if (effectPause && !response) {//for every 10th score
            drawPopUp(canvas, "Chance", "Yes", "No");//draws a pop up for chance
            canvas.drawText("Use a chance?", width / 2, height / 2, paint);
        }
    }

    /**
     * This method creates a pop up with two rectangles as buttons.
     *
     * @param canvas the object to be drawn on
     * @param heading the title for the heading
     * @param button1 the name for the first button
     * @param button2 the name for the second button
     */
    public void drawPopUp(Canvas canvas, String heading, String button1, String button2) {
        int spacing = width / 10;
        //create three rectangles at different positions of left, top, right, bottom
        outerRect = new RectF(width / 6, height / 4, width / 6 * 5, height / 4 * 3);
        firstRect = new Rect(width / 8 * 3, height / 5 * 3, width / 8 * 5, height / 20 * 13);
        secondRect = new Rect(firstRect.left, firstRect.top + spacing, firstRect.right, firstRect.bottom + spacing);
        paint.setColor(Color.argb(150, 195, 195, 195));
        canvas.drawRoundRect(outerRect, 20, 20, paint);//draws a rounded rectangle of gray translucent color
        paint.setColor(Color.RED);
        paint.setTextSize(width / 10);
        canvas.drawText(heading, width / 2, outerRect.top + spacing, paint);//draws the heading of a red color
        paint.setColor(Color.GRAY);
        canvas.drawRect(firstRect, paint);//draws the first button of gray colour
        canvas.drawRect(secondRect, paint);//draws the second button of gray colour
        int textSize = width / 15;
        paint.setTextSize(textSize);
        paint.setColor(Color.BLACK);
        canvas.drawText(button1, firstRect.exactCenterX(), firstRect.bottom - textSize / 4, paint);//draw the name of first button inside the rectangle
        canvas.drawText(button2, secondRect.exactCenterX(), secondRect.bottom - textSize / 4, paint);//draw the name of second button inside the rectangle
    }

    /**
     * Checks if the ball overlaps with the goal by calculating the differences in the ball and goal center points.
     *
     * @return true if there is a goal and false if otherwise
     */
    public boolean checkForGoal(Ball ball) {
        for (Goal goal : goals) {
            float xDiff = Math.abs(goal.getX() - ball.getX());//the difference between x positions of the goal and ball
            float yDiff = Math.abs(goal.getY() - ball.getY());//difference between y positions of goal and ball
            float delta = ball.getRadius() / 2; //the tolerance value for the difference
            float radiusDiff = goal.getRadius() - ball.getRadius() + delta;

            //checks the difference in radius of the ball and goal objects
            if (xDiff <= radiusDiff && yDiff <= radiusDiff) {//goal
                balls.remove(ball);
                if (balls.isEmpty()) {//when all balls are gone
                    if (round > 0) {//if number of effect lasting round is not 0
                        round--; //reduce the number of effect lasting round
                        if (round == 0) {
                            handler.sendMessage(handler.obtainMessage(2));//calls for a feedback for when effects are wearing off
                            effect = null;
                        }
                    }
                }
                return true; //there's a scoring goal
            }
        }
        return false; //no scoring
    }

    /**
     * Returns a random float between the specified range
     *
     * @param min minimum float
     * @param max maximum float
     * @return a float value between the inputs
     */
    public float getRandomFloatBetween(float min, float max) {
        if (min < max) {
            Random r = new Random();
            return r.nextFloat() * (max - min) + min;
        } else {
            throw new IllegalArgumentException("Min value shouldn't be higher than max value");
        }
    }

    /**
     * Gets the string representation of an emoji by specifying its unicode value
     *
     * @param unicode the unicode value for the emoji
     * @return a String representing the emoji
     */
    public String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }

    /**
     * Writes the score into a text file
     */
    public void writingToFile() {
        String playerInfo = "";
        File file = new File(path + "/players.txt");

        try {
            FileOutputStream fo = new FileOutputStream(file);

            List<Player> playersByScore = new ArrayList<>(playersMap.values());
            //sort the player collection by their high scores
            Collections.sort(playersByScore, new Comparator<Player>() {
                @Override
                public int compare(Player o1, Player o2) {
                    return o2.getHighScore() - o1.getHighScore();
                }
            });

            for (Player player : playersByScore) {
                System.out.println(player.getName() + "\t" + player.getHighScore());//for testing
                playerInfo = player.getName() + " " + player.getHighScore() + "\n";
                fo.write(playerInfo.getBytes());
            }

            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the score from the text file
     */
    public void readFile() {
        BufferedReader in = null;

        try {
            in = new BufferedReader(new FileReader(path + "/players.txt"));
            String line;
            String[] splitLine;
            while ((line = in.readLine()) != null) {
                splitLine = line.split(" ");
                Player player = new Player(splitLine[0]);
                player.setHighScore(Integer.valueOf(splitLine[1]));
                playersMap.put(player.getName(), player);
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
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }
    //----------------------------------------------------------------------------------------------

    //gesture detection-----------------------------------------------------------------------------

    /**
     * Used to move the ball in the direction of the swipe gesture
     *
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        for (Ball ball : balls) {
            if (ball.getBound().contains((int) e1.getX(), (int) e1.getY())) {//check for the touch input on the position of the ball
                if (!ball.isTouched()) {//only set the speed when ball hasn't been touched
                    //for debugging
                    System.out.println("Before scaling:");
                    System.out.println(velocityX);
                    System.out.println(velocityY);
                    ball.setSpeed(velocityX, velocityY);
                    System.out.println("After scaling:");
                    System.out.println("Speed X: " + ball.getSpeedX());
                    System.out.println("Speed Y: " + ball.getSpeedY());
                    ball.setTouched(true); //ball has been touched, so don't update the speed again
                }
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
     *
     * @param e
     * @return
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if (gameOver) { //if it's game over
            if (firstRect.contains((int) e.getX(), (int) e.getY())) { //if first button is pressed
                System.out.println("Restart pressed!");//for debugging
                //restart the game
                gameOver = false;
                player.setScore(0);//reset player score
                balls.clear();
                balls.add(createBallAtCenterX());//create a new ball at the center
                goalRadius = width / 7;
                goals.clear();
                goals.add(createGoal());//create a new goal at the center
                round = 0;//reset the number of effect lasting rounds
                effect = null;//reset the effect
                bars.clear();//remove the bars
            }
            if (secondRect.contains((int) e.getX(), (int) e.getY())) {
                System.out.println("Main pressed!");//for debugging
                ((GameUI) getContext()).finish();//exit the game activity and go to main menu
            }
            return true;
        }

        if (effectPause) { //if it's a pause by the effect every 10th iteration
            if (firstRect.contains((int) e.getX(), (int) e.getY())) {
                //get a random effect
                System.out.println("Yes pressed!");//for debugging
                effect = Effect.getRandomEffect();//get a random effect
//                effect = Effect.GOAL_MULTIPLE; //for debugging, please don't remove
                switch (effect) {
                    //apply the effect
                    case BALL_BIG:
                        ballRadius = goalRadius;//make the size of ball equal to the goal
                        for (Ball ball : balls) {
                            ball.setRadius(ballRadius);
                        }
                        break;
                    case BALL_SMALL:
                        ballRadius *= 0.7;//make the size the ball 30% smaller
                        for (Ball ball : balls) {
                            ball.setRadius(ballRadius);
                        }
                        break;
                    case GOAL_BIG:
                        goalRadius *= 1.5;//make the goal expand 50%
                        for (Goal goal : goals) {
                            goal.setRadius(goalRadius);
                        }
                        break;
                    case GOAL_SMALL:
                        goalRadius = ballRadius;//make the goal size as small as the ball
                        for (Goal goal : goals) {
                            goal.setRadius(goalRadius);
                        }
                        break;
                    case BALL_MULTIPLE:
                        balls.clear();//clear the balls
                    default:
                        break;
                }
                Toast.makeText(this.getContext(), effect.getDescription(), Toast.LENGTH_SHORT).show();//inform the player on the effect applied
                round = 5;//make the effect lasts for 5 rounds
                effectPause = false;
                response = true;
            }
            if (secondRect.contains((int) e.getX(), (int) e.getY())) {//if the second button is pressed
                //don't apply effect
                System.out.println("No pressed!");//for debugging
                Toast.makeText(this.getContext(), "No change has been made.", Toast.LENGTH_SHORT).show();
                effectPause = false;
                response = true;
            }
            return true;
        }

        if (pauseRect.contains((int) e.getX(), (int) e.getY())) {//checks if pause button is pressed
            pause = true;
            return true;
        }

        if (pause) {//if pause button is pressed
            if (firstRect.contains((int) e.getX(), (int) e.getY())) {//if first button is pressed
                //turn music on and off
                if (MenuUI.music.isPlaying()) {
                    MenuUI.music.pause();
                    MenuUI.musicBtn.setChecked(false);
                } else {
                    MenuUI.music.start();
                    MenuUI.musicBtn.setChecked(true);
                }

            }
            if (secondRect.contains((int) e.getX(), (int) e.getY())) {//if main button is prssed
                System.out.println("Main pressed!");//for debugging
                ((GameUI) getContext()).finish();//go back to main
            }

            //if player pressed outside the pop up menu
            if (!outerRect.contains((int) e.getX(), (int) e.getY())) {
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