package com.example.charlie.g1_roll_it_in;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameUI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_ui);
        //hey, this is me!

        Button playBtn = (Button) findViewById(R.id.playBtn);
        playBtn.setOnClickListener(
                //do something when play button is clicked
                new Button.OnClickListener(){
                    public void onClick(View v){
                        TextView text = (TextView) findViewById(R.id.welcomeTxt);
                        text.setText("Loading...");
                    }
                }
        );

        //hello, this is a test from Thong.
        //another test string
        //Charlie, Testing
        //Thong, from charlie branch
    }
}
