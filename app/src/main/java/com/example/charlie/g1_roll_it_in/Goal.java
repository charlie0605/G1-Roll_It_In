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

    public Goal(int x, int y, float radius, int screenWidth, int screenHeight) {
        super(x, y, screenWidth, screenHeight);
        this.radius = radius;
        this.color = Color.BLACK;
    }

    @Override
    public void update() {

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
}
