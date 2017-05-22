package com.example.charlie.g1_roll_it_in.gameModel;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.constraint.solver.widgets.Rectangle;

import com.example.charlie.g1_roll_it_in.gameUI.GameView;

import java.util.Random;

/**
 * Created by Thong on 5/04/2017.
 */

public class Ball extends RoundObject {
    //variables-------------------------------------------------------------------------------------
    private boolean out;
    private boolean touched;
    private boolean bounceRight, bounceLeft, bounceTop, bounceBottom;
    //----------------------------------------------------------------------------------------------

    //constructors----------------------------------------------------------------------------------
    public Ball(float x, float y, float radius) {
        super(x, y, radius);
        this.color = getRandomColor();//get a random color for each ball
        out = false;
        touched = false;
    }

    public Ball(float x, float y, float radius, int color) {
        super(x, y, radius);
        this.color = color;
        out = false;
        touched = false;
    }
    //----------------------------------------------------------------------------------------------

    //accessors and mutators------------------------------------------------------------------------

    /**
     * Return true if ball is out of the screen and false if otherwise
     *
     * @return
     */
    public boolean isOut() {
        return out;
    }

    /**
     * Set the status of whether the ball is out of the screen or not
     *
     * @param out boolean value true if ball is out and false if otherwise
     */
    public void setOut(boolean out) {
        this.out = out;
    }

    /**
     * Returns true if player has interacted with the ball and false if otherwise
     *
     * @return
     */
    public boolean isTouched() {
        return touched;
    }

    /**
     * Set the value whether ball has been touched by the player or not
     *
     * @param touched a boolean value true if player has touched the ball and false if otherwise
     */
    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public boolean isBounceRight() {
        return bounceRight;
    }

    public void setBounceRight(boolean bounceRight) {
        this.bounceRight = bounceRight;
    }

    public boolean isBounceLeft() {
        return bounceLeft;
    }

    public void setBounceLeft(boolean bounceLeft) {
        this.bounceLeft = bounceLeft;
    }

    public boolean isBounceTop() {
        return bounceTop;
    }

    public void setBounceTop(boolean bounceTop) {
        this.bounceTop = bounceTop;
    }

    public boolean isBounceBottom() {
        return bounceBottom;
    }

    public void setBounceBottom(boolean bounceBottom) {
        this.bounceBottom = bounceBottom;
    }

    //----------------------------------------------------------------------------------------------

    //helper methods--------------------------------------------------------------------------------

    /**
     * Returns a random color using different rgb values
     *
     * @return an integer representing the value of the color
     */
    public int getRandomColor() {
        Random rand = new Random();

        int[] colors = new int[]{
                Color.argb(255, 255, 216, 0), //gold
                Color.argb(255, 0, 255, 255), //aqua
                Color.argb(255, 128, 255, 208), //aquamarine
                Color.argb(255, 176, 255, 48), //greenyellow
                Color.argb(255, 255, 104, 176), //hotpink
                Color.argb(255, 240, 128, 128), //lightcoral
                Color.argb(255, 255, 96, 64), //tomato
                Color.argb(255, 255, 255, 0) //yellow
        };
        int red = rand.nextInt(256);
        int green = rand.nextInt(256);
        int blue = rand.nextInt(256);
//        return Color.argb(255, red, green, blue);
        return colors[rand.nextInt(colors.length)];
    }


    /**
     * This method is repeatedly called and check whether the ball leaves the game screen
     */
    @Override
    public void update() {
        super.update();

        //will check for collision with the bars instead later on
//        checkScreenCollisionLeftRight();
        checkScreenCollisionTopBottom();

        //check if ball is out
        checkBallOutTopBottom();
        checkBallOutLeftRight();
    }

    /**
     * Checks if ball is out from the top or the bottom screen
     *
     * @return true if ball is out and false if otherwise
     */
    public boolean checkBallOutTopBottom() {
        //if ball is out of top and bottom
        if (y <= -radius || y >= GameView.height + radius) {
            out = true;
            return true;
        }
        return false;
    }

    /**
     * Checks if ball is out from the left and right screen
     *
     * @return true if ball is out and false if otherwise
     */
    public boolean checkBallOutLeftRight() {
        //if ball is out of left and right
        if (x <= -radius || x >= GameView.width + radius) {
            out = true;
            return true;
        }
        return false;
    }

    /**
     * Checks if ball collides with the left and right edge of the screen, set it to the other direction if it is.
     * Made for testing.
     */
    public void checkScreenCollisionLeftRight() {
        if (x <= radius || x >= GameView.width - radius) {
            speedX *= -1;
            if (x < radius) {
                x = (int) radius;
            }
            if (x > GameView.width - radius) {
                x = (int) (GameView.width - radius);
            }
        }
    }

    /**
     * Checks if ball collides with the top and bottom edge of the screen, set it to the other direction if it is.
     * Made for testing.
     */
    public void checkScreenCollisionTopBottom() {
        if (y <= radius || y >= GameView.height - radius) {
            speedY *= -1;
            if (y < radius) {
                y = (int) radius;
            }
            if (y > GameView.height - radius) {
                y = (int) (GameView.height - radius);
            }
        }
    }

    /**
     * This method sets the speed to a constant rate while keeping the direction of the ball
     *
     * @param speedX specified speed in x direction for the ball
     * @param speedY specified spped in y direnction for the ball
     */
    public void setSpeed(float speedX, float speedY) {
        this.speedX = speedX * getScale(speedX, speedY);
        this.speedY = speedY * getScale(speedX, speedY);
    }

    /**
     * This method get a scale value so that the speed can be constant
     *
     * @param speedX the specified speed in x direction
     * @param speedY the specified speed in y direction
     * @return
     */
    public float getScale(float speedX, float speedY) {
        float vTarget = GameView.width / 14;
        double v = Math.sqrt((speedX * speedX) + (speedY * speedY));//actual speed of the swipe gesture
        return vTarget / (float) v;
    }

    public Rect getBound() {
        //left, top, right, bottom
        return new Rect((int) (x - radius), (int) (y - radius), (int) (x + radius), (int) (y + radius));
    }
    //----------------------------------------------------------------------------------------------
}
