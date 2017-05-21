package com.example.charlie.g1_roll_it_in.gameModel;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;

import com.example.charlie.g1_roll_it_in.gameUI.GameView;

/**
 * Created by Thong on 5/04/2017.
 */

public class Bar extends GameObject {
    //variables-------------------------------------------------------------------------------------
    private int width, height;
    //----------------------------------------------------------------------------------------------

    //constructor-----------------------------------------------------------------------------------
    public Bar(int x, int y, int width, int height) {
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
        canvas.drawRoundRect(x + 2, y, x + width, y + height, 100, 100, paint);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawRect(getBound(), paint);
    }

    public void checkScreenCollision() {
        checkScreenCollisionLeftRight();
        checkScreenCollisionTopBottom();
    }

    public void checkScreenCollisionLeftRight() {
        if (x <= 0 || x >= GameView.width - width) {
            speedX *= -1;
            if (x < 0) {
                x = 0;
            }
            if (x > GameView.width - width) {
                x = width;
            }
        }
    }

    public void checkScreenCollisionTopBottom() {
        if (y <= 0 || y >= GameView.height - height) {
            speedY *= -1;
            if (y < 0) {
                y = 0;
            }
            if (y > GameView.height - height) {
                y = height;
            }
        }
    }

    public void checkCollision(Ball ball) {
//        if (ball.getBound().intersect(getBound())) {
//            if (ball.getBound().left - getBound().right <= 0 && ball.getY() <= getBound().bottom) {
//
//                ball.setX(width + ball.getRadius());
//                ball.setSpeedX(ball.getSpeedX() * -1);
//            }
//        }

//        if(ball.getX() > getX()) {//check if the ball is on the right of the bar
//            if (ball.getBound().left <= getBound().right) {//check for collision at ball left and bar right
//                if (ball.getBound().bottom >= getBound().top && ball.getBound().top <= getBound().bottom) {
//                    ball.setX(getBound().right + ball.getRadius());
//                    ball.setSpeedX(ball.getSpeedX() * -1);
//                }
//            }
//
//            if (ball.getBound().top >= getBound().bottom) {//check for collision at ball top and bar bottom
//
//            }
//
//        if (ball.getBound().intersect(getBound())) {
//
//            if (ball.getX() < GameView.width / 2) {
//                if (ball.getX() - ball.getRadius() <= getBound().right && ball.getBound().top - ball.getRadius() <= getBound().bottom) {
//
////                if(ball.getBound().bottom >= getBound().top && ball.getBound().top <= getBound().bottom){
////                    ball.setX(getBound().right + ball.getRadius());
//                    ball.setSpeedX(ball.getSpeedX() * -1);
//                }
//                if (ball.getY() - ball.getRadius() >= getBound().bottom) {
//                    ball.setSpeedY(ball.getSpeedY() * -1);
//                }
//            } else if (ball.getX() > GameView.width / 2) {
//                if (ball.getX() + ball.getRadius() >= getBound().left) {
////                    ball.setX(getBound().left - ball.getRadius());
//                    ball.setSpeedX(ball.getSpeedX() * -1);
//                }
//            }
//            if (ball.getBound().top <= getBound().bottom) {
//                ball.setSpeedY(ball.getSpeedY() * -1);
//            }
//            if(ball.getBound().top >= getBound().bottom){
//                ball.setY(getBound().bottom + ball.getRadius());
//                ball.setSpeedY(ball.getSpeedY() * -1);
//            }

//
//            if(ball.getBound().bottom <= getBound().top){
//                ball.setY(getBound().top - ball.getRadius());
//                ball.setSpeedY(ball.getSpeedY() * -1);
//            }
//                if(ball.getBound().left <= getBound().right && ball.getBound().right >=getBound().left){
//                    ball.setSpeedY(ball.getSpeedY() * -1);
//                }
//            }
//        }


//        if(ball.getBound().intersect(getBound())){
////            ball.setSpeedY(ball.getSpeedY()*-1);
//            if(ball.getX() > getX()) { //check if the ball is on the right of the bar
//                if (ball.getBound().left <= getBound().right) {  //check if the ball is over the line
////                    ball.setX(getBound().right + ball.getRadius());
//                    ball.setSpeedX(ball.getSpeedX() * -1);
//                }
//            }else if(ball.getX()< getX()){
//                if(ball.getBound().right >= getBound().left){
////                    ball.setX(getBound().left - ball.getRadius());
//                    ball.setSpeedX(ball.getSpeedX() * -1);
//                }
//            }
//        }
    }

    public Rect getBound() {
        return new Rect((int) x, (int) y, (int) (x + width), (int) (y + height));
    }


    //----------------------------------------------------------------------------------------------
}
