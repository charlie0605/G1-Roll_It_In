package com.example.charlie.g1_roll_it_in;

import com.example.charlie.g1_roll_it_in.gameModel.Player;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class PlayerUnitTest {
    Player player;

    @Before
    public void setUp() throws Exception {
        player = new Player("John");
    }

    @Test
    public void testScoring(){
        assertEquals("Score should initially be zero", 0, player.getScore());
        //player scores 2 goals
        player.scoreGoal();
        player.scoreGoal();
        assertEquals("Score should be 2", 2, player.getScore());
    }
}