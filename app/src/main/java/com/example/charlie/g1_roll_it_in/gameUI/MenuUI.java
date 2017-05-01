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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.menu);

        playPressed();
        sound();
        instruction();
    }


    public void playPressed(){
        Button playBtn = (Button) findViewById(R.id.playButton);
        final MediaPlayer sound = MediaPlayer.create(MenuUI.this,R.raw.button14);

        playBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),GameUI.class);
                startActivity(intent);
            }
        });
    }

    public void instruction(){
        Button playBtn = (Button) findViewById(R.id.instuctionButton);
        playBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),InstructionUI.class);
                startActivity(intent);
            }
        });
    }

    public void sound(){
        final MediaPlayer sound = MediaPlayer.create(MenuUI.this,R.raw.m1);
        final Switch soundBtn = (Switch) findViewById(R.id.musicSwitch);
        sound.start();
        sound.setLooping(true);
        soundBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (soundBtn.isChecked()){
                    sound.start();
                }else{
                    sound.pause();
                }
            }
        });
    }
}
