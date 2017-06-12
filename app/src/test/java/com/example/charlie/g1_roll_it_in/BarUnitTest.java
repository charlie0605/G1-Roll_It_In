package com.example.charlie.g1_roll_it_in;

import com.example.charlie.g1_roll_it_in.gameModel.Bar;
import com.example.charlie.g1_roll_it_in.gameUI.GameView;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Created by Thong on 12/06/2017.
 */

public class BarUnitTest {
    Bar bar;

    @Before
    public void setUp() throws Exception {
        bar = new Bar(0, 0, 100, 50);
        GameView.width = 1080;
    }

    @Test
    public void testBarScreenCollision(){
        int speed = -5;
        bar.setSpeedX(speed);
        bar.update();
        assertTrue("Moving x direction should change.", bar.getSpeedX() == -speed);

        bar.setSpeedY(speed);
        bar.update();
        assertTrue("Moving y direction should change.", bar.getSpeedY() == -speed);
    }
}
