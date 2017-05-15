package com.example.charlie.g1_roll_it_in;

import com.example.charlie.g1_roll_it_in.gameModel.Goal;
import com.example.charlie.g1_roll_it_in.gameModel.Player;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GoalUnitTest {
    Goal goal;
    Player player;

    @Before
    public void setUp() throws Exception {
        goal = new Goal(0, 0, 250);
        player = new Player("James");
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
}