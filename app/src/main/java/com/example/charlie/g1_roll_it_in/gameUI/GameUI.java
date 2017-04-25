package com.example.charlie.g1_roll_it_in.gameUI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.charlie.g1_roll_it_in.R;

public class GameUI extends AppCompatActivity {
    private GameView gameView;
    private Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.menu);
        gameView = new GameView(this);
        init();
    }

    public void init(){
        playButton = (Button)findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setContentView(gameView);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(findViewById(android.R.id.content) == gameView) {
            setContentView(R.layout.menu);
            init();
        }
        else {
            finish();//close the app, but still runs in background
        }
    }
}
