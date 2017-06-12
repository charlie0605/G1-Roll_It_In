package com.example.charlie.g1_roll_it_in;

import com.example.charlie.g1_roll_it_in.gameModel.Goal;
import com.example.charlie.g1_roll_it_in.gameModel.Player;
import com.example.charlie.g1_roll_it_in.gameUI.GameView;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GoalUnitTest {
    Goal goal;
    Player player;

    @Before
    public void setUp() throws Exception {
        goal = new Goal(0, 0, 250);
        player = new Player("James");
        GameView.width = 1080;
    }

    @Test
    public void testGoalSpeed(){
        player.setScore(20);
        goal.update(player);
        assertEquals("Speed x should be 30", 30, goal.getSpeedX(), 0);

        goal = new Goal(0, 0, 100);
        player.setScore(10);
        goal.update(player);
        assertEquals("Speed x should be 15", 15, goal.getSpeedX(), 0);
    }

    @Test
    public void testGoalScreenCollision(){
        //only check x direction because goal won't move in y direction
        int speed = -5;
        goal.setSpeedX(speed);
        goal.update();
        assertTrue("Moving x direction should change.", goal.getSpeedX() == -speed);
    }
}