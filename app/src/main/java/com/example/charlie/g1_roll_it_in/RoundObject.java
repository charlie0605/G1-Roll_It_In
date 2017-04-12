package com.example.charlie.g1_roll_it_in;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Thong on 12/04/2017.
 */

public abstract class RoundObject extends GameObject {
    protected  float radius;
    protected int color;

    //constructor-----------------------------------------------------------------------------------
    public RoundObject(int x, int y, float radius) {
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
        paint.setColor(color);
        //draw the circle onto the canvas using the paint tool
        canvas.drawCircle(x, y, radius, paint);
    }
    //----------------------------------------------------------------------------------------------
}
