package com.example.charlie.g1_roll_it_in;

import android.graphics.Canvas;
import android.graphics.Color;
/**
 * Created by Thong on 5/04/2017.
 */

public class Ball extends RoundObject{

    public Ball(int x, int y, float radius){
        super(x, y, radius);
        this.color = Color.RED;//get a random color for each ball
    }

    public int getRandomColor(){
        return Color.argb(255, 255, 0, 0);
        //will make this random
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
        
        if(y <= radius || x >= GameView.height - radius){
            speedX *= -1;
            if(y < radius) {
                y = (int) radius;
            }
            if(x > GameView.height - radius){
                x = (int) (GameView.height - radius);
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
