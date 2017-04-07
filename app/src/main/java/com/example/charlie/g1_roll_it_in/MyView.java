package com.example.charlie.g1_roll_it_in;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Thong on 7/04/2017.
 */

public class MyView extends View {
    Paint paint;

    public MyView(Context context){
        super(context);
        paint = new Paint();
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        int x = getWidth();
        int y = getHeight();
        int radius = 150;
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawCircle(x/2, y/2, radius, paint);
    }
}
