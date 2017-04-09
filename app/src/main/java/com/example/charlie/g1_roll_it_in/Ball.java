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

    public int getColor(){
        return Color.argb(255, 255, 255, 255);
        //will make this random
    }

    public void moveX(){
        this.x += speedX;
        //might do checking here before adding speed to lessen the lagging effect
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
        }
        if(y <= radius || y >= screenHeight - radius){
            speedY *= -1;
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
        if(Math.abs(speedX) > 10000){
            this.speedX = speedX / 100;
        } else if(Math.abs(speedX) > 1000) {
            this.speedX = speedX / 10;
        } else {
            this.speedX = speedX;
        }
    }

    @Override
    public void setSpeedY(float speedY){
        if(Math.abs(speedY) > 10000){
            this.speedY = speedY / 1000;
        } else if(Math.abs(speedY) > 1000) {
            this.speedY = speedY / 100;
        } else {
            this.speedY = speedY;
        }
    }

    public float getRadius() {
        return radius;
    }
}
