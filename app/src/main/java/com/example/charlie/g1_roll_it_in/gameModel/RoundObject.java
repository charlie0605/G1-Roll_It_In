package com.example.charlie.g1_roll_it_in.gameModel;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Thong on 12/04/2017.
 */

public abstract class RoundObject extends GameObject {
    protected  float radius;
    protected int color;

    //constructor-----------------------------------------------------------------------------------
    public RoundObject(float x, float y, float radius) {
        super(x, y);
        this.radius = radius;
    }
    //----------------------------------------------------------------------------------------------

    //getters and setters---------------------------------------------------------------------------
    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
    //----------------------------------------------------------------------------------------------

    //helper methods--------------------------------------------------------------------------------
    public void update(){
        //move the object with the speed in x and y direction
        x += speedX;
        y += speedY;
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
//        paint.setColor(Color.WHITE);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(10);
//        canvas.drawCircle(x, y, radius, paint);
        paint.setShadowLayer(20, 0, 0, Color.DKGRAY);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, radius, paint);//draw the circle onto the canvas using the paint tool
    }
    //----------------------------------------------------------------------------------------------
}
