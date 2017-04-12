package com.example.charlie.g1_roll_it_in;

import android.graphics.Canvas;
import android.graphics.Color;

import java.util.Random;

/**
 * Created by Thong on 5/04/2017.
 */

public class Ball extends RoundObject{

    public Ball(int x, int y, float radius){
        super(x, y, radius);
        this.color = getRandomColor();//get a random color for each ball
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
        if(x <= radius || x >= GameView.width - radius){
            speedX *= -1;
            if(x < radius) {
                x = (int) radius;
            }
            if(x > GameView.width - radius){
                x = (int) (GameView.width - radius);
            }
        }

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
}
