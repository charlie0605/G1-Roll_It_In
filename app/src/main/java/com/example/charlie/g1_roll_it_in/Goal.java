package com.example.charlie.g1_roll_it_in;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Thong on 9/04/2017.
 */

public class Goal extends GameObject{
    private float radius;
    private int color;
    private boolean updateSpeed = false;

    public Goal(int x, int y, float radius, int screenWidth, int screenHeight) {
        super(x, y, screenWidth, screenHeight);
        this.radius = radius;
        this.color = Color.BLACK;
    }

    @Override
    public void update() {
        this.x += speedX;
        this.y += speedY;
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

    public float getRadius() {
        return radius;
    }

    public void update(Player player) {
        if (player.getScore() == 20) {
            if(!updateSpeed) {
                this.setSpeedX(30);
                updateSpeed = true;
            }
        } else if (player.getScore() == 10) {
            if(!updateSpeed) {
                this.setSpeedX(15);
                updateSpeed = true;
            }
        } else {
            updateSpeed = false;
        }
    }
}
