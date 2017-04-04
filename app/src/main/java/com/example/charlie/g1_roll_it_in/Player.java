package com.example.charlie.g1_roll_it_in;

/**
 * Created by Charlie on 4/04/2017.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import android.content.SharedPreferences;


public class Player {

    private String name;
    private int score;
    private int HighScore=50;

    public Player(String name){
        this.name = name;
        this.score = 0;
    }

    public int getScore(){
        //if roll in balls
        return score;
    }
    public void setScore(int score){
        this.score=score;
    }
    //display score
    private void displayScore(){
        if(score>=0){
            System.out.println("Current Score:"+score);
        }
        else{
            System.out.println("Invalid Current Score, check method");
        }
    }

    //high score
    /*private SharedPreferences gamePrefs;
    public static final String GAME_PREFS="Score";*/
    private void setHighScore(int score){
        //set high score
        //display high score
        if(score>=0){
            if(HighScore<score){
                System.out.println("New High score!");
                HighScore=score;
                System.out.print("High score: "+HighScore);
            }
            else{
                System.out.println("Better luck next time!");
                System.out.println("High score: "+HighScore);
            }
        }
        else{
            System.out.println("invaild score input,check inout");
        }
    }
    //String scores=gamePrefs.getString("highScores","");

}
