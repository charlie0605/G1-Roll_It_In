package com.example.charlie.g1_roll_it_in;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class GameUI extends AppCompatActivity {
    public static int screenWidth, screenHeight;
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;
        int screenWidth = metrics.widthPixels;

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.menu);
        gameView = new GameView(this);

        Button playBtn = (Button)findViewById(R.id.playButton);

        playBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setContentView(gameView);
            }
        });

        Button instructionBtn = (Button)findViewById(R.id.instuctionButton);
        instructionBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            }
        });

    }

}
