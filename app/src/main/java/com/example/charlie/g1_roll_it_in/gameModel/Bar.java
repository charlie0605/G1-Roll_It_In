package com.example.charlie.g1_roll_it_in.gameModel;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.charlie.g1_roll_it_in.gameUI.GameView;

/**
 * Created by Thong on 5/04/2017.
 */

public class Bar extends GameObject {
    //variables-------------------------------------------------------------------------------------
    private int width;
    private int height;
    //----------------------------------------------------------------------------------------------

    //constructor-----------------------------------------------------------------------------------
    public Bar(int x, int y, int width, int height) {
        super(x, y);
        this.setWidth(width);
        this.setHeight(height);
    }
    //----------------------------------------------------------------------------------------------

    //helper methods--------------------------------------------------------------------------------
    @Override
    public void update() {
        x += speedX;
        y += speedY;
        checkScreenCollision();
        System.out.println(this.getSpeedX());
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setShadowLayer(10, 0, 0, Color.DKGRAY);
        paint.setColor(Color.argb(255, 255, 64, 129));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(x, y, x + getWidth(), y + getHeight(), 100, 100, paint);
//        paint.setColor(Color.BLUE);
//        canvas.drawRect(getBound(),paint);
    }

    public void checkScreenCollision() {
        checkScreenCollisionLeftRight();
        checkScreenCollisionTopBottom();
    }

    public void checkScreenCollisionLeftRight() {
        if (x <= 0 || x >= GameView.width - getWidth()) {
            speedX *= -1;
            if (x < 0) {
                x = 0;
            }
            if (x > GameView.width - getWidth()) {
                x = GameView.width - getWidth();
            }
        }
    }

    public void checkScreenCollisionTopBottom() {
        if (y <= 0 || y >= GameView.height - getHeight()) {
            speedY *= -1;
            if (y < 0) {
                y = 0;
            }
            if (y > GameView.height - getHeight()) {
                y = getHeight();
            }
        }
    }

    public void checkCollision(Ball ball) {
//        if(ball.getBound().intersect(getBound())){
////            ball.setSpeedY(ball.getSpeedY()*-1);
//            if(ball.getX() > getX()) {
//                //check if the ball is on the right of the bar
//                if (ball.getBound().left <= getBound().right) {  //check if the ball is over the line
//                    ball.setX(getBound().right + ball.getRadius());
//                    ball.setSpeedX(ball.getSpeedX() * -1);
//                }
//            }else if(ball.getX()< getX()){
//                if(ball.getBound().right >= getBound().left){
//                    ball.setX(getBound().left - ball.getRadius());
//                    ball.setSpeedX(ball.getSpeedX() * -1);
//                }
//            }
//        }
        if (this.intersect(ball)) {
//        if (ball.getBound().intersect(getBound())) {
            System.out.println("bar y: " + getY());
            if (ball.getY() <= getY() + getHeight() && (ball.getBound().left <= getBound().right || ball.getBound().right >= getBound().left)) {
                if (ball.getBound().left >= getBound().left && !ball.isBounceRight()) {//ball is on the right of the bar
                    ball.setSpeedX(ball.getSpeedX() * -1);
                    ball.setX(getBound().right + ball.getRadius());// set the position of the ball to the right of the bar
                    ball.setBounceRight(true); //
                    ball.setBounceLeft(false);
                    ball.setBounceTop(true);
                    ball.setBounceBottom(true);
                }
                if (ball.getBound().right <= getBound().right && !ball.isBounceLeft()) {//ball is on the left of the bar
                    ball.setSpeedX(ball.getSpeedX() * -1);
                    ball.setX(getBound().left - ball.getRadius());
                    ball.setBounceLeft(true);
                    ball.setBounceRight(false);
                    ball.setBounceTop(true);
                    ball.setBounceBottom(true);
                }
            }
            if (ball.getBound().top <= getBound().bottom || ball.getBound().bottom >= getBound().top) {
                if (ball.getBound().top <= getBound().top && !ball.isBounceTop()) {//ball on top of bar
                    ball.setSpeedY(ball.getSpeedY() * -1);
                    ball.setY(getBound().top - ball.getRadius());
                    ball.setBounceTop(true);
                    ball.setBounceBottom(false);
                    ball.setBounceLeft(false);
                    ball.setBounceRight(false);
                }
                if (ball.getBound().bottom >= getBound().bottom && !ball.isBounceBottom()) {//ball at bottom of bar
                    ball.setSpeedY(ball.getSpeedY() * -1);
                    ball.setY(getBound().bottom + ball.getRadius());
                    ball.setBounceBottom(true);
                    ball.setBounceTop(false);
                    ball.setBounceLeft(false);
                    ball.setBounceRight(false);
                }
            }
        }
    }

    public boolean intersect(Ball ball) {
        if (ball.getBound().left < this.getBound().right + Math.abs(ball.getSpeedX() / 2) && this.getBound().left - Math.abs(ball.getSpeedX() / 2) < ball.getBound().right && ball.getBound().top < this.getBound().bottom + Math.abs(ball.getSpeedY() / 2) && this.getBound().top - Math.abs(ball.getSpeedY() / 2) < ball.getBound().bottom) {
            return true;
        }
        return false;
    }

    public Rect getBound() {
        return new Rect((int) x + 2, (int) y, (int) (x + getWidth() + 20), (int) (y + getHeight()));
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    //----------------------------------------------------------------------------------------------
}
