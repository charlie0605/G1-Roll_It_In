package com.example.charlie.g1_roll_it_in;

import android.graphics.Color;

import com.example.charlie.g1_roll_it_in.gameModel.Ball;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class BallUnitTest {
    Ball ball;

    @Before
    public void setUp() throws Exception {
        ball = new Ball(0, 0, 50, Color.BLACK);
    }

    @Test
    public void testScaleSpeed(){
        assertEquals("Scale speed 10000", 40, ball.scaleSpeed(10000), 0.1f);
        assertEquals("Scale speed 20000", 80, ball.scaleSpeed(20000), 0.1f);
        assertEquals("Scale speed 5000", 20, ball.scaleSpeed(5000), 0.1f);

    }
}