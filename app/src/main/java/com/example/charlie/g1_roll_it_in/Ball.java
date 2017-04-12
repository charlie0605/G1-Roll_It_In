package com.example.charlie.g1_roll_it_in;

import android.graphics.Color;
/**
 * Created by Thong on 5/04/2017.
 */

public class Ball extends RoundObject{

    public Ball(int x, int y, float radius){
        super(x, y, radius);
        this.radius = radius;
        this.color = getRandomColor();//get a random color for each ball
    }

    public int getRandomColor(){
        return Color.argb(255, 0, 0, 255);
        //will make this random
    }

    @Override
    public void update() {
        super.update();

        //will check for collision with the bars instead later on
        if(x <= radius || x >= screenWidth - radius){
            speedX *= -1;
            if(x < radius) {
                x = (int) radius;
            }
            if(x > screenWidth - radius){
                x = (int) (screenWidth - radius);
            }
        }
        if(y <= radius || y >= screenHeight - radius){
            speedY *= -1;
            if(y < radius) {
                y = (int) radius;
            }
            if(y > screenHeight - radius){
                y = (int) (screenHeight - radius);
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
