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
}
