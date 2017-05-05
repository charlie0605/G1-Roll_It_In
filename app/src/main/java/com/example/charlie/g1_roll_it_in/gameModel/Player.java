package com.example.charlie.g1_roll_it_in.gameModel;

/**
 * Created by Charlie on 4/04/2017.
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;


public class Player {

    private String name;
    private int score;
    private int highScore;

    public Player(String name){
        this.setName(name);
        this.score = 0;
        this.highScore = 0;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score=score;
    }

    public int getHighScore(){
       return this.highScore;
    }

    public void scoreGoal(){
        this.score++;
    }
    //display score
    private void displayScore(){
        if(score>=0){
            System.out.println("Current Score: "+score);
        }
        else{
            System.out.println("Invalid Current Score, check method");
        }
    }

    //high score
    /*private SharedPreferences gamePrefs;
    public static final String GAME_PREFS="Score";*/
    public void setHighScore(int score){
        //set high score
        //display high score
        if(score>=0){
            if(highScore<score){
                System.out.println("New High score!");
                highScore=score;
                System.out.print("High score: "+highScore);
            }
            else{
                System.out.println("Better luck next time!");
                System.out.println("High score: "+highScore);
            }
        }
        else{
            System.out.println("invaild score input,check input");
        }
    }
    //String scores=gamePrefs.getString("highScores","");

    public void update(){
        if(this.score > highScore){
            highScore = this.score;
        }
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
