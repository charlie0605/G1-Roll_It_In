package com.example.charlie.g1_roll_it_in.gameModel;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.charlie.g1_roll_it_in.gameUI.GameView;

/**
 * Created by Thong on 5/04/2017.
 */

public class Bar extends GameObject{
    //variables-------------------------------------------------------------------------------------
    private int width, height;
    //----------------------------------------------------------------------------------------------

    //constructor-----------------------------------------------------------------------------------
    public Bar(int x, int y, int width, int height){
        super(x, y);
        this.width = width;
        this.height = height;
    }
    //----------------------------------------------------------------------------------------------

    //helper methods--------------------------------------------------------------------------------
    @Override
    public void update() {
        x += speedX;
        y += speedY;
        checkScreenCollision();
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setShadowLayer(10, 0, 0, Color.DKGRAY);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(x+2, y, x + width, y + height, 100,100, paint);
    }

    public void checkScreenCollision(){
        checkScreenCollisionLeftRight();
        checkScreenCollisionTopBottom();
    }

    public void checkScreenCollisionLeftRight(){
        if(x <= 0 || x >= GameView.width - width){
            speedX *= -1;
            if(x < 0) {
                x = 0;
            }
            if(x > GameView.width - width){
                x = width;
            }
        }
    }

    public void checkScreenCollisionTopBottom(){
        if(y <= 0 || y >= GameView.height - height){
            speedY *= -1;
            if(y < 0) {
                y = 0;
            }
            if(y > GameView.height - height){
                y = height;
            }
        }
    }
    //----------------------------------------------------------------------------------------------
}
