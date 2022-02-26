package com.example.project2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class tabActivity extends AppCompatActivity {
    Button bt_replay;
    TextView tv_finalScore;
    TextView tv_rankId1;
    TextView tv_rankScore1;
    EditText et_id;
    Button bt_exit;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences2;
    SharedPreferences sharedPreferences3;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editor2;
    SharedPreferences.Editor editor3;
    TabLayout tabLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        bt_exit = findViewById(R.id.btn_exit);
        bt_replay = findViewById(R.id.btn_replay);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                changeView(pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        bt_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tabActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        bt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.finishAffinity(tabActivity.this);
                System.exit(0);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void changeView(int pos) {

        tv_finalScore = findViewById(R.id.tv_finalScore);
        et_id = findViewById(R.id.id);

        int game1rank = 1;
        int game2rank = 1;
        int game3rank = 1;


        sharedPreferences = getSharedPreferences("game1", MODE_PRIVATE);
        editor = sharedPreferences.edit();
// ------------------------------------------------------------------------------
        sharedPreferences2 = getSharedPreferences("game2", MODE_PRIVATE);
        editor2 = sharedPreferences2.edit();
// ------------------------------------------------------------------------------
        sharedPreferences3 = getSharedPreferences("game3", MODE_PRIVATE);
        editor3 = sharedPreferences3.edit();

        switch (pos) {
            case 0:
                String idDataList = "";
                String scoreDataList = "";
                tv_rankId1 = findViewById(R.id.tv_rankid);
                tv_rankScore1 = findViewById(R.id.tv_rankscore);

                Map<String, ?> total = sharedPreferences.getAll();
                List<String> listKeySet = new ArrayList<>(total.keySet());
                listKeySet.sort((o1, o2) -> Integer.valueOf((String) total.get(o2)).compareTo(Integer.valueOf((String) total.get(o1))));
                for (String key : listKeySet) {
                    idDataList += game1rank + "등     " + key + "\r\n";
                    scoreDataList += total.get(key) + "점\r\n";
                    game1rank++;

                }
                tv_rankId1.setText(idDataList);
                tv_rankScore1.setText(scoreDataList);


                if (idDataList.equals("") && scoreDataList.equals("")) {
                    tv_rankId1.setText("not recorded");
                    tv_rankScore1.setText("-");
                    tv_rankId1.setTextColor(Color.GRAY);
                }
                break;
            case 1:
                String idDataList2 = "";
                String scoreDataList2 = "";
                tv_rankId1 = findViewById(R.id.tv_rankid);
                tv_rankScore1 = findViewById(R.id.tv_rankscore);

                Map<String, ?> total2 = sharedPreferences2.getAll();
                List<String> listKeySet2 = new ArrayList<>(total2.keySet());
                listKeySet2.sort((o1, o2) -> Integer.valueOf((String) total2.get(o2)).compareTo(Integer.valueOf((String) total2.get(o1))));
                for (String key : listKeySet2) {
                    idDataList2 += game2rank + "등     " + key + "\r\n";
                    scoreDataList2 += total2.get(key) + "점\r\n";
                    game2rank++;
                }

                tv_rankId1.setText(idDataList2);
                tv_rankScore1.setText(scoreDataList2);

                if (idDataList2.equals("") && scoreDataList2.equals("")) {
                    tv_rankId1.setText("not recorded");
                    tv_rankScore1.setText("-");
                    tv_rankId1.setTextColor(Color.GRAY);
                }
                break;

            case 2:
                String idDataList3 = "";
                String scoreDataList3 = "";
                tv_rankId1 = findViewById(R.id.tv_rankid);
                tv_rankScore1 = findViewById(R.id.tv_rankscore);

                Map<String, ?> total3 = sharedPreferences3.getAll();
                List<String> listKeySet3 = new ArrayList<>(total3.keySet());
                listKeySet3.sort((o1, o2) -> Float.valueOf((String) total3.get(o2)).compareTo(Float.valueOf((String) total3.get(o1))));
                for (String key : listKeySet3) {
                    idDataList3 += game3rank + "등     " + key + "\r\n";
                    scoreDataList3 += total3.get(key) + "초\r\n";
                    game3rank++;
                }

                tv_rankId1.setText(idDataList3);
                tv_rankScore1.setText(scoreDataList3);

                if (idDataList3.equals("") && scoreDataList3.equals("")) {
                    tv_rankId1.setText("not recorded");
                    tv_rankScore1.setText("-");
                    tv_rankId1.setTextColor(Color.GRAY);
                }
                break;

        }

    }

}