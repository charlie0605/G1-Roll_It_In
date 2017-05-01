package com.example.charlie.g1_roll_it_in.gameModel;

import android.graphics.Color;

import com.example.charlie.g1_roll_it_in.gameUI.GameView;

import java.util.Random;

/**
 * Created by Thong on 5/04/2017.
 */

public class Ball extends RoundObject{
    private boolean out;
    private boolean touched;

    public Ball(float x, float y, float radius){
        super(x, y, radius);
        this.color = getRandomColor();//get a random color for each ball
        out = false;
        touched = false;
    }

    public Ball(float x, float y, float radius, int color){
        super(x, y, radius);
        this.color = color;
        out = false;
        touched = false;
    }

    public boolean isOut() {
        return out;
    }

    public void setOut(boolean out) {
        this.out = out;
    }

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public int getRandomColor(){
        Random rand = new Random();
        int red = rand.nextInt(256);
        int green = rand.nextInt(256);
        int blue = rand.nextInt(256);
        return Color.argb(255, red, green, blue);
    }

    @Override
    public void update() {
        super.update();

        //will check for collision with the bars instead later on
//        checkCollisionLeftRight();
//        checkBallOutTopBottom();

        checkBallOutTopBottom();
        checkBallOutLeftRight();
    }

    public void checkBallOutTopBottom(){
        //if ball is out of top and bottom
        if(y <= -radius || y >= GameView.height + radius){
            out = true;
        }
    }

    public void checkBallOutLeftRight(){
        //if ball is out of left and right
        if(x <= -radius || x >= GameView.width + radius){
            out = true;
        }
    }

    public void checkCollisionLeftRight(){
        if(x <= radius || x >= GameView.width - radius){
            speedX *= -1;
            if(x < radius) {
                x = (int) radius;
            }
            if(x > GameView.width - radius){
                x = (int) (GameView.width - radius);
            }
        }
    }

    public void checkCollisionTopBottom(){
        if(y <= radius || y >= GameView.height - radius){
            speedY *= -1;
            if(y < radius) {
                y = (int) radius;
            }
            if(y > GameView.height - radius){
                y = (int) (GameView.height - radius);
            }
        }
    }

    public void setSpeed(float speedX, float speedY){
        this.speedX = speedX * getScale(speedX, speedY);
        this.speedY = speedY * getScale(speedX, speedY);
    }

    @Override
    public void setSpeedX(float speedX){
        this.speedX = scaleSpeed(speedX);
    }

    @Override
    public void setSpeedY(float speedY){
        this.speedY = scaleSpeed(speedY);
    }

    public float scaleSpeed(float speed){
        //scale the speed of fling so it will range between -80 to 80
        float percentage = speed / 20000;
        float maxSpeed = 80;

        return maxSpeed * percentage;
    }

    public float getScale(float speedX, float speedY) {
        float vTarget = 100;
        double v = Math.sqrt((speedX * speedX) + (speedY * speedY));
        return vTarget / (float) v;
    }
}
