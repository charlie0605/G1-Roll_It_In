package com.example.charlie.g1_roll_it_in;

/**
 * Created by Thong on 4/04/2017.
 */

public abstract class GameObject {
    private int x, y;
    private float speedX, speedY;

    public GameObject(int x, int y){

    }

    abstract public void update();
    abstract public void render();
}
