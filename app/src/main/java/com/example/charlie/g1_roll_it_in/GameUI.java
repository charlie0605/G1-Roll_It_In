package com.example.charlie.g1_roll_it_in;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class GameUI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.menu);
        //hey, this is me!
        //hello, this is a test from Thong.
        //another test string
        //Charlie, Testing
        //Thong, from charlie branch
    }
}
