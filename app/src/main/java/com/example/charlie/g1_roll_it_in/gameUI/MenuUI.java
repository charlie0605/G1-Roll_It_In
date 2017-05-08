package com.example.charlie.g1_roll_it_in.gameUI;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.charlie.g1_roll_it_in.R;

/**
 * Created by Charlie on 26/04/2017.
 */

public class MenuUI extends Activity {
    private TextView welcomeMessg;
    public static MediaPlayer music;
    public static MediaPlayer sound;
    public static Switch soundBtn;
    public static Switch musicBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.menu);

        sound = MediaPlayer.create(MenuUI.this,R.raw.button14);
        soundBtn = (Switch) findViewById(R.id.soundSwitch);
        musicBtn = (Switch) findViewById(R.id.musicSwitch);
        music = MediaPlayer.create(MenuUI.this,R.raw.m1);
        welcomeMessg = (TextView) findViewById(R.id.welcomMessg);

        welcomeMessg.setText("WELCOME\n" + NameUI.playerName);
        playPressed();
        musicActionListener();
        instructionBtnListener();

    }


    public void playPressed(){
        Button playBtn = (Button) findViewById(R.id.playButton);

        playBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(soundBtn.isChecked()){
                    sound.start();
                }
                Intent intent = new Intent(getApplicationContext(),GameUI.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void instructionBtnListener(){
        Button playBtn = (Button) findViewById(R.id.instuctionButton);
        playBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(soundBtn.isChecked()){
                    sound.start();
                }
                Intent intent = new Intent(getApplicationContext(),InstructionUI.class);
                startActivity(intent);
            }
        });
    }

    public void musicActionListener(){
        music = MediaPlayer.create(MenuUI.this,R.raw.m1);
        music.start();
        music.setLooping(true);
        musicBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (musicBtn.isChecked()){
                    music.start();
                }else{
                    music.pause();
                }
            }
        });
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
