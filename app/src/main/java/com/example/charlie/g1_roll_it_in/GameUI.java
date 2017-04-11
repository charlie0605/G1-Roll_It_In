package com.example.charlie.g1_roll_it_in;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameUI extends AppCompatActivity {
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.menu);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        gameView = new GameView(this);
//        setContentView(gameView);

        Button playButton = (Button)findViewById(R.id.playButton);
        //add a listener for the click on the play button
        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setContentView(gameView);//if the play button is clicked, set the content view to the game view
            }
        });

    }

}
