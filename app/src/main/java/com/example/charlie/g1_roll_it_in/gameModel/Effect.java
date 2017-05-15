package com.example.charlie.g1_roll_it_in.gameModel;

import java.util.Random;

/**
 * Created by Thong on 22/04/2017.
 */

/**
 * A list of effects that can manipulate the ball and goal.
 */
public enum Effect {
    BALL_BIG(1, "Oh no, the ball gets bigger."),
    BALL_SMALL(2, "Great, the ball gets smaller."),
    GOAL_BIG(3, "Great, the goal gets bigger."),
    GOAL_SMALL(4, "Oh no, the goal gets smaller."),
    BALL_MULTIPLE(5, "Oh no, must score all balls to go to next level."),
    GOAL_MULTIPLE(6, "Great, ball can go in any goal.");

    //variables-------------------------------------------------------------------------------------
    private int id;
    private String description;
    private static final Effect[] VALUES = values();
    private static final int SIZE = VALUES.length;
    private static final Random RANDOM = new Random();
    //----------------------------------------------------------------------------------------------

    //constructor-----------------------------------------------------------------------------------
    Effect(int id, String description){
        this.id = id;
        this.description = description;
    }
    //----------------------------------------------------------------------------------------------

    //getter and setter-----------------------------------------------------------------------------
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    //----------------------------------------------------------------------------------------------

    //helper methods--------------------------------------------------------------------------------

    /**
     * Returns a random effect out of the list
     * @return
     */
    public static Effect getRandomEffect() {
        return VALUES[RANDOM.nextInt(SIZE)];
    }
    //----------------------------------------------------------------------------------------------
}
