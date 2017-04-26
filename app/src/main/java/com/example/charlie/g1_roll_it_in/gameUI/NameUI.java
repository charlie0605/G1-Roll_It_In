package com.example.charlie.g1_roll_it_in.gameUI;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.charlie.g1_roll_it_in.R;
import com.example.charlie.g1_roll_it_in.gameModel.Player;

/**
 * Created by Charlie on 26/04/2017.
 */

public class NameUI extends Activity {
    private String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.name);

        continuePressed();
    }

    public void continuePressed(){
        Button playBtn = (Button) findViewById(R.id.continueBtn);
        final MediaPlayer sound = MediaPlayer.create(NameUI.this,R.raw.button31);

        playBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                sound.start();
                EditText edText = (EditText) findViewById(R.id.name);
                edText.setInputType(InputType.TYPE_CLASS_TEXT);
                playerName = edText.getText().toString();
                Intent intent = new Intent(getApplicationContext(),MenuUI.class);

                startActivity(intent);

                System.out.println(playerName);
            }
        });
    }
}
