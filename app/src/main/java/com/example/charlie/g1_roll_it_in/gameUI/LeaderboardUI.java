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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Raiden on 2017/5/13 0015.
 */

public class LeaderboardUI extends Activity {
    public String path = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/players";

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

    /**
     * Reads the the file of player score and returns a list of their information
     * @return
     */
    private ArrayList<String> readfile() {
        StringBuffer sb = new StringBuffer();
        ArrayList<String> playerScores = new ArrayList<>();
        File file = new File(path + "/players.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            int number = 1;
            while ((line = br.readLine()) != null && number <= 10) { //read just the top 10 players or less
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

    /**
     * Creates a table from the player information
     * @param playerInfo an arraylist of player name and score as a string
     */
    public void createTable(ArrayList<String> playerInfo) {
        if (!playerInfo.isEmpty()) {
            TableLayout tableLayout = (TableLayout) findViewById(R.id.table_score);
            //Row 0 for row header as Rank | Name | Score
            TableRow tbRow0 = new TableRow(this);//create a table row

            TextView tv0 = new TextView(this);//create a table cell for rank
            tv0.setText("Rank");
            tv0.setTextColor(Color.parseColor("#FFFF4081"));
            tv0.setTypeface(null, Typeface.BOLD);
            tv0.setPadding(0, 20, 0, 20);
            tv0.setTextSize(16);
            tv0.setGravity(Gravity.CENTER);
            tbRow0.addView(tv0);//add the cell to the row

            TextView tv1 = new TextView(this);//create a table cell for name
            tv1.setText("Name");
            tv1.setTextSize(16);
            tv1.setTextColor(Color.parseColor("#FFFF4081"));
            tv1.setTypeface(null, Typeface.BOLD);
            tbRow0.addView(tv1);//add the cell to the row

            TextView tv2 = new TextView(this);//create a table cell for score
            tv2.setText("Score");
            tv2.setTextSize(16);
            tv2.setTextColor(Color.parseColor("#FFFF4081"));
            tv2.setTypeface(null, Typeface.BOLD);
            tv2.setGravity(Gravity.CENTER);
            tbRow0.addView(tv2);//add the cell to the row
            tbRow0.setPadding(0, 20, 0, 30);
            tableLayout.addView(tbRow0);//add the row to the table

            for (String playerScore : playerInfo) {
                String[] player = playerScore.split(" ");//split each line into two parts: first half for name, second half for score
                //adding each player score as a new row
                TableRow tbRow = new TableRow(this);//create a new row

                TextView tvPlayer0 = new TextView(this);//create a cell for the number of rank
                tvPlayer0.setText((playerInfo.indexOf(playerScore) + 1) + "");
                tvPlayer0.setGravity(Gravity.CENTER);
                tbRow.addView(tvPlayer0);//add the cell to the new row

                TextView tvPlayer1 = new TextView(this);//create a cell for the player name
                tvPlayer1.setText(player[0]);//set the name of the player
                tbRow.addView(tvPlayer1);//add the cell to the new row

                TextView tvPlayer2 = new TextView(this);//create a cell for the player score
                tvPlayer2.setText(player[1]);//set the score of the player
                tvPlayer2.setGravity(Gravity.CENTER);
                tbRow.addView(tvPlayer2);//add the cell to the new row
                tbRow.setPadding(0, 0, 0, 20);
                tableLayout.addView(tbRow);//add the row to the table
            }
        }
    }
}
