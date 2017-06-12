package com.example.charlie.g1_roll_it_in.gameModel;

import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by Thong on 4/04/2017.
 */

public abstract class GameObject {
    protected float x, y;
    protected float speedX, speedY;

    //constructor-----------------------------------------------------------------------------------
    public GameObject(float x, float y) {
        this.x = x;
        this.y = y;

        //obj is not moving when it's instantiated
        this.speedX = 0;
        this.speedY = 0;
    }
    //----------------------------------------------------------------------------------------------

    //helper methods--------------------------------------------------------------------------------

    /**
     * Not yet implemented
     *
     * @param gameObjects
     * @return
     */
    public boolean checkObjCollision(ArrayList<GameObject> gameObjects) {
        for (GameObject gameObj : gameObjects) {
            if (gameObj.equals(this)) {
                continue;
            }
            //check for the collision between objects
        }
        return true;
    }
    //----------------------------------------------------------------------------------------------

    //getters and setters---------------------------------------------------------------------------
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }
    //----------------------------------------------------------------------------------------------

    //abstract methods to be implemented by subclasses----------------------------------------------
    abstract public void update();

    abstract public void draw(Canvas canvas);
    //----------------------------------------------------------------------------------------------
}
