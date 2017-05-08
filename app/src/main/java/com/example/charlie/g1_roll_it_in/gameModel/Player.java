package com.example.charlie.g1_roll_it_in.gameModel;

/**
 * Created by Charlie on 4/04/2017.
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;

public class Player {
    //variables-------------------------------------------------------------------------------------
    private String name;
    private int score;
    private int highScore;
    //----------------------------------------------------------------------------------------------

    //constructor-----------------------------------------------------------------------------------
    public Player(String name){
        this.setName(name);
        this.score = 0;
        this.highScore = 0;
    }
    //----------------------------------------------------------------------------------------------

    //getter and setter-----------------------------------------------------------------------------
    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score=score;
    }

    public int getHighScore(){
       return this.highScore;
    }

    public void setHighScore(int score){
        highScore = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //----------------------------------------------------------------------------------------------

    //helper methods--------------------------------------------------------------------------------

    /**
     * Increase the score of the player by 1
     */
    public void scoreGoal(){
        this.score++;
    }

    /**
     * Update the highscore when the score is higher than the current highscore
     */
    public void update(){
        if(this.score > highScore){
            highScore = this.score;
        }
    }

    /**
     * Draw the score of the player onto the specified canvas
     * @param canvas an object used to draw on
     */
    public void draw(Canvas canvas) {
        String highScoreStr = "Best: " + this.getHighScore();
        String scoreStr = "Score: " + this.getScore();
        TextPaint textPaint = new TextPaint();
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(100);
        canvas.drawText(highScoreStr, canvas.getWidth()/2, canvas.getHeight()/2, textPaint);
        canvas.drawText(scoreStr, canvas.getWidth()/2, canvas.getHeight()/2 + 100, textPaint);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setStrokeWidth(5);
        textPaint.setColor(Color.WHITE);
        canvas.drawText(highScoreStr, canvas.getWidth()/2, canvas.getHeight()/2, textPaint);
        canvas.drawText(scoreStr, canvas.getWidth()/2, canvas.getHeight()/2 + 100, textPaint);
    }
    //----------------------------------------------------------------------------------------------
}
