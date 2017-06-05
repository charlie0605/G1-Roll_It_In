package com.example.charlie.g1_roll_it_in.gameUI;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.charlie.g1_roll_it_in.R;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;


/**
 * Created by Raiden on 2017/5/13 0015.
 */

public class LeaderboardUI extends Activity {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.leaderboard);

        DisplayMetrics dl = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dl);

        int width = dl.widthPixels;
        int height = dl.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        createTable(readfile());
    }

    private ArrayList<String> readfile() {
        StringBuffer sb = new StringBuffer();
        ArrayList<String> playerScores = new ArrayList<>();
        File file = new File(path + "/players.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            int number = 1;
            while ((line = br.readLine()) != null && number <= 10) {
                sb.append(line + "\n");
                playerScores.add(line);
                number++;
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return playerScores;
    }

    public void createTable(ArrayList<String> playerInfo) {
        if (!playerInfo.isEmpty()) {
            TableLayout tableLayout = (TableLayout) findViewById(R.id.table_score);
            //Row 0 for row header as Row | Name | Score
            TableRow tbRow0 = new TableRow(this);
            TextView tv0 = new TextView(this);
            tv0.setText("Rank");
            tv0.setTextColor(Color.parseColor("#FFFF4081"));
            tv0.setTypeface(null, Typeface.BOLD);
            tv0.setPadding(0, 20, 0, 20);
            tv0.setTextSize(16);
            tv0.setGravity(Gravity.CENTER);
            tbRow0.addView(tv0);
            TextView tv1 = new TextView(this);
            tv1.setText("Name");
            tv1.setTextSize(16);
            tv1.setTextColor(Color.parseColor("#FFFF4081"));
            tv1.setTypeface(null, Typeface.BOLD);
            tbRow0.addView(tv1);
            TextView tv2 = new TextView(this);
            tv2.setText("Score");
            tv2.setTextSize(16);
            tv2.setTextColor(Color.parseColor("#FFFF4081"));
            tv2.setTypeface(null, Typeface.BOLD);
            tv2.setGravity(Gravity.CENTER);
            tbRow0.addView(tv2);
            tbRow0.setPadding(0, 20, 0, 30);
            tableLayout.addView(tbRow0);

            for (String playerScore : playerInfo) {
                String[] player = playerScore.split(" ");
                //adding each player score as a new row
                TableRow tbRow = new TableRow(this);
                TextView tvPlayer0 = new TextView(this);
                tvPlayer0.setText((playerInfo.indexOf(playerScore) + 1) + "");
                tvPlayer0.setGravity(Gravity.CENTER);
                tbRow.addView(tvPlayer0);
                TextView tvPlayer1 = new TextView(this);
                tvPlayer1.setText(player[0]);
                tbRow.addView(tvPlayer1);
                TextView tvPlayer2 = new TextView(this);
                tvPlayer2.setText(player[1]);
                tvPlayer2.setGravity(Gravity.CENTER);
                tbRow.addView(tvPlayer2);
                tbRow.setPadding(0, 0, 0, 20);
                tableLayout.addView(tbRow);
            }
        }


    }
}
