package com.example.charlie.g1_roll_it_in.gameUI;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.example.charlie.g1_roll_it_in.R;

/**
 * To be implemeneted
 * Created by Charlie on 2/05/2017.
 */

public class GameOverMenu extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gameovermenu);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.7),(int)(height*.5));

        menuBtnListener();
    }

    public void menuBtnListener(){
        final Button menuBtn = (Button) findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
