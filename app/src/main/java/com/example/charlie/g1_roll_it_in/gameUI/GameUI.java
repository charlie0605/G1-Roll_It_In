package com.example.charlie.g1_roll_it_in.gameUI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.charlie.g1_roll_it_in.R;

import static com.example.charlie.g1_roll_it_in.gameUI.MenuUI.music;
import static com.example.charlie.g1_roll_it_in.gameUI.MenuUI.musicBtn;

public class GameUI extends AppCompatActivity{
    private GameView gameView;

    /**
     * Creates a game view and set it as the content view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.game);

        gameView = new GameView(this);

        setContentView(gameView);
    }

    /**
     * Pauses the music when the game is pause
     */
    @Override
    public void onPause() {
        super.onPause();
        if(music.isPlaying()) {
            music.pause();
        }
    }

    /**
     * Plays the music when the game continues
     */
    @Override
    protected void onResume() {
        super.onResume();
        if(!music.isPlaying() && musicBtn.isChecked()) {
            music.start();
        }
    }
}


