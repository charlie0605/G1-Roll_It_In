package com.example.charlie.g1_roll_it_in;

import android.graphics.Color;

import com.example.charlie.g1_roll_it_in.gameModel.Ball;
import com.example.charlie.g1_roll_it_in.gameUI.GameView;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BallUnitTest {
    Ball ball;

    @Before
    public void setUp() throws Exception {
        ball = new Ball(0, 0, 50, Color.BLACK);
        GameView.width = 1080;
    }

    @Test
    public void testBallDirection(){
        float speedX = 100, speedY = 200;
        float expectedDirection = speedX / speedY;
        ball.setSpeed(speedX, speedY);
        assertEquals("the expectedDirection should be the same", expectedDirection, ball.getSpeedX() / ball.getSpeedY(), 0.1f);

        speedX = 400;
        speedY = 150;
        expectedDirection = speedX / speedY;
        ball.setSpeed(speedX, speedY);
        assertEquals("the expectedDirection should be the same", expectedDirection, ball.getSpeedX() / ball.getSpeedY(), 0.1f);
    }

    @Test
    public void testBallSpeed(){
        ball.setSpeed(100, 300);
        assertEquals("Speed should be around width/14", GameView.width  /14, Math.sqrt(ball.getSpeedX() * ball.getSpeedX() + ball.getSpeedY() * ball.getSpeedY()), 0.1f);

        ball.setSpeed(500, 125);
        assertEquals("Speed should be around width/14", GameView.width  /14, Math.sqrt(ball.getSpeedX() * ball.getSpeedX() + ball.getSpeedY() * ball.getSpeedY()), 0.1f);

    }
}