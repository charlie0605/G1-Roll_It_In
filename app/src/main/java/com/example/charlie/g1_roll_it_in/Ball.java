package com.example.charlie.g1_roll_it_in;

/**
 * Created by Thong on 5/04/2017.
 */

public class Ball extends GameObject{

    public Ball(int x, int y){
        super(x, y);
    }

    public void moveX(){
        this.x += speedX;
    }

    public void moveY(){
        this.y += speedY;
    }

    @Override
    public void update() {
        this.moveX();
        this.moveY();
    }

    @Override
    public void render() {

    }
}
