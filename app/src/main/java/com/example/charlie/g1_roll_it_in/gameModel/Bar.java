package com.example.charlie.g1_roll_it_in.gameModel;

import android.graphics.Canvas;

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
        //to be implemented
    }

    @Override
    public void draw(Canvas canvas) {
        //to be implemented
    }
    //----------------------------------------------------------------------------------------------
}
