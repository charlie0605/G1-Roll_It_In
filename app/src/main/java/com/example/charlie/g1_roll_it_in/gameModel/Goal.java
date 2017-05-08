package com.example.charlie.g1_roll_it_in.gameModel;

import android.graphics.Color;
import com.example.charlie.g1_roll_it_in.gameUI.GameView;

/**
 * Created by Thong on 9/04/2017.
 */

public class Goal extends RoundObject{
    private boolean updatedSpeed = false;

    public Goal(int x, int y, float radius) {
        super(x, y, radius);
        this.color = Color.BLACK;
    }

    @Override
    public void update() {
        //goal will only move horizontally, so no need to update y
        x += speedX;

        //check if the goal reaches the edge horizontally
        if(x <= radius || x >= GameView.width - radius){
            speedX *= -1;
            if(x < radius) {
                x = (int) radius;
            }
            if(x > GameView.width - radius){
                x = (int) (GameView.width - radius);
            }
        }
    }

    //update the speed of goal according to the player's score
    public void update(Player player) {
        if (player.getScore() == 20) {
            if(!updatedSpeed) {
                this.setSpeedX(30);
                updatedSpeed = true;
            }
        } else if (player.getScore() == 10) {
            if(!updatedSpeed) {
                this.setSpeedX(15);
                updatedSpeed = true;
            }
        } else {
            updatedSpeed = false;
        }
    }
}
