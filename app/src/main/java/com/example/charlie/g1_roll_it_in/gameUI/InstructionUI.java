package com.example.charlie.g1_roll_it_in.gameUI;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.example.charlie.g1_roll_it_in.R;

/**
 * Created by Charlie on 26/04/2017.
 */

public class InstructionUI extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.instruction);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));

    }
}
