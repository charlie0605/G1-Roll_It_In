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
//        paint.setColor(Color.BLUE);
//        canvas.drawRect(getBound(),paint);
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
    public void checkCollision(Ball ball){
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

        if(ball.getBound().intersect(getBound())){
            if(ball.getBound().left <= getBound().right || ball.getBound().right >= getBound().left){
                if(ball.getBound().left >= getBound().left && !ball.isBounceRight()){//ball is on the right of the bar
                    ball.setSpeedX(ball.getSpeedX() * -1);
                    ball.setX(getBound().right + ball.getRadius());
                    ball.setBounceRight(true);
                    ball.setBounceLeft(false);
                    ball.setBounceTop(true);
                    ball.setBounceBottom(true);
                }
                if(ball.getBound().right <= getBound().right && !ball.isBounceLeft()){//ball is on the left of the bar
                    ball.setSpeedX(ball.getSpeedX() * -1);
                    ball.setX(getBound().left - ball.getRadius());
                    ball.setBounceLeft(true);
                    ball.setBounceRight(false);
                    ball.setBounceTop(true);
                    ball.setBounceBottom(true);
                }
            }
            if(ball.getBound().top <= getBound().bottom || ball.getBound().bottom >= getBound().top){
                if(ball.getBound().top <= getBound().top && !ball.isBounceTop()){//ball on top of bar
                    ball.setSpeedY(ball.getSpeedY() * -1);
                    ball.setY(getBound().top - ball.getRadius());
                    ball.setBounceTop(true);
                    ball.setBounceBottom(false);
                    ball.setBounceLeft(false);
                    ball.setBounceRight(false);
                }
                if(ball.getBound().bottom >= getBound().bottom && !ball.isBounceBottom()){//ball at bottom of bar
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

//    public boolean intersect(Ball ball){
//        if (ball.getBound().left < this.getBound().right + Math.abs(ball.getSpeedX()) && this.getBound().left - Math.abs(ball.getSpeedX()) < ball.getBound().right && ball.getBound().top < this.getBound().bottom + Math.abs(ball.getSpeedY()) && this.getBound().top - Math.abs(ball.getSpeedY()) < ball.getBound().bottom) {
////            if (ball.getBound().left < this.getBound().left) ball.setX(getBound().left - ball.getRadius());
////            if (ball.getBound().top < this.getBound().top) ball.setY(getBound().top - ball.getRadius());
//            if (ball.getBound().right > this.getBound().right) {//ball on the right of bar
//                ball.setX(getBound().right + ball.getRadius());
//                ball.setSpeedX(Math.abs(ball.getSpeedX()));//speed x should be positive
//            }
////            if (ball.getBound().bottom > this.getBound().bottom) ball.setY(getBound().bottom + ball.getRadius());
//            return true;
//        }
//        return false;
//    }

    public Rect getBound() {
        return new Rect((int)x +2,(int)y,(int)(x+width +20),(int)(y+height));
    }


    //----------------------------------------------------------------------------------------------
}
