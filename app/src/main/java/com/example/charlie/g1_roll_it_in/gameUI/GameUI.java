package com.example.charlie.g1_roll_it_in.gameUI;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import android.os.Handler;
import android.os.Message;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.charlie.g1_roll_it_in.R;

import static com.example.charlie.g1_roll_it_in.gameUI.MenuUI.music;
import static com.example.charlie.g1_roll_it_in.gameUI.MenuUI.musicBtn;

public class GameUI extends AppCompatActivity{
    private GameView gameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.game);

        gameView = new GameView(this);

        setContentView(gameView);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(music.isPlaying()) {
            music.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!music.isPlaying() && musicBtn.isChecked()) {
            music.start();
        }
    }
}


