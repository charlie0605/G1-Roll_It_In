package com.example.charlie.g1_roll_it_in.gameUI;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.example.charlie.g1_roll_it_in.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;



/**
 * Created by Raiden on 2017/5/13 0015.
 */

public class LeaderboardUI extends Activity{
    public String path = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/players";

   /* StringBuffer sb = new StringBuffer();
    File file = new File(path + "/players.txt");
    Buffer edReader br = new BufferedReader(new FileReader(file));
    String line = "";
    while((line = br.readLine())!=null){
        sb.append(line);
    }
    br.close();
    (TextView)findViewById(R.id.text1).setText(sb.toString());
*/
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.leaderboard);

        DisplayMetrics dl = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dl);

        int width = dl.widthPixels;
        int height = dl.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        readfile();

    }

    private void readfile(){
        StringBuffer sb = new StringBuffer();
        File file = new File(path + "/players.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            int number = 1;
            while((line = br.readLine())!=null && number <= 5){
                sb.append(number++ + "       " +line + "\n");
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        TextView lbtext = (TextView)findViewById(R.id.lbText);
        lbtext.setText(sb.toString());

    }
}
