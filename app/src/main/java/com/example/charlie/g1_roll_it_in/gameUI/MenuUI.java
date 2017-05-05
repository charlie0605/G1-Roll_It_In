package com.example.charlie.g1_roll_it_in.gameUI;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.charlie.g1_roll_it_in.R;

/**
 * Created by Charlie on 26/04/2017.
 */

public class MenuUI extends Activity {
    private GameView gameView;
    private MediaPlayer music;
    private MediaPlayer sound;
    private Switch soundBtn;
    private Switch musicBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.menu);

        sound = MediaPlayer.create(MenuUI.this,R.raw.button14);
        soundBtn = (Switch) findViewById(R.id.soundSwitch);
        musicBtn = (Switch) findViewById(R.id.musicSwitch);

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
        music.release();
        music = null;
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
}
