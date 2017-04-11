package com.example.charlie.g1_roll_it_in;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.shapes.OvalShape;

/**
 * Created by Thong on 5/04/2017.
 */

public class Ball extends GameObject{
    private int color;
    private float radius;

    public Ball(int x, int y, float radius, int screenWidth, int screenHeight){
        super(x, y, screenWidth, screenHeight);
        this.radius = radius;
        this.color = Color.RED;
    }

    public int getRandomColor(){
        return Color.argb(255, 255, 255, 255);
        //will make this random
    }

    public void moveX(){
        this.x += speedX;
    }

    public void moveY(){
        this.y += speedY;
    }

    @Override
    public void update() {
        this.moveX();
        this.moveY();
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
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(this.color);
        canvas.drawCircle(this.x, this.y, this.radius, paint);
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public void setSpeedX(float speedX){
//        if(Math.abs(speedX) > 10000){
//            this.speedX = speedX / 5000;
//        } else if(Math.abs(speedX) > 1000) {
//            this.speedX = speedX / 500;
//        } else {
//            this.speedX = speedX;
//        }
//        this.speedX = speedX;
        this.speedX = scaleSpeed(speedX);
    }

    @Override
    public void setSpeedY(float speedY){
//        if(Math.abs(speedY) > 10000){
//            this.speedY = speedY / 5000;
//        } else if(Math.abs(speedY) > 1000) {
//            this.speedY = speedY / 500;
//        } else {
//            this.speedY = speedY;
//        }
//        this.speedY = speedY;
        this.speedY = scaleSpeed(speedY);
    }


    public float scaleSpeed(float speed){
        float percentage = speed / 20000;
        float maxSpeed = 80;

        return maxSpeed * percentage;
    }

    public float getRadius() {
        return radius;
    }
}
