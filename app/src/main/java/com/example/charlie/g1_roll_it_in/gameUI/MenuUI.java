package com.example.charlie.g1_roll_it_in.gameUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.charlie.g1_roll_it_in.R;

/**
 * Created by Charlie on 26/04/2017.
 */

public class MenuUI extends Activity {
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.menu);

        playPressed();
    }


    public void playPressed(){
        Button playBtn = (Button) findViewById(R.id.playButton);
        playBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),GameUI.class);

                startActivity(intent);
            }
        });
    }
}
