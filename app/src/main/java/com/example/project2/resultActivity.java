package com.example.project2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class resultActivity extends AppCompatActivity {
    Button bt_replay;
    TextView tv_finalScore;
    TextView tv_rankId;
    TextView tv_rankScore;
    EditText et_id;
    Button bt_save;
    Button bt_exit;
    int score;
    float score2;
    int gameId;

    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences2;
    SharedPreferences sharedPreferences3;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editor2;
    SharedPreferences.Editor editor3;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        bt_replay = findViewById(R.id.btn_replay);
        tv_finalScore = findViewById(R.id.tv_finalScore);
        et_id = findViewById(R.id.id);
        bt_save = findViewById(R.id.save);
        bt_exit = findViewById(R.id.btn_exit);
        tv_rankId = findViewById(R.id.tv_rankid);
        tv_rankScore = findViewById(R.id.tv_rankscore);

        score = getIntent().getIntExtra("score", 0);
        score2= getIntent().getFloatExtra("score2", (float) 0.0);
        gameId = getIntent().getIntExtra("gameId", -1);

        tv_finalScore.setText(String.valueOf(score));

        // 게임에 sharedpreference연결시키기
        if (gameId == 1) {
            sharedPreferences = getSharedPreferences("game1", MODE_PRIVATE);
            editor = sharedPreferences.edit();
        } else if (gameId == 2) {
            sharedPreferences2 = getSharedPreferences("game2", MODE_PRIVATE);
            editor2 = sharedPreferences2.edit();
        } else {
            sharedPreferences3 = getSharedPreferences("game3", MODE_PRIVATE);
            editor3 = sharedPreferences3.edit();
        }


        bt_save.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                if (gameId == 1) {
                    editor.putString(et_id.getText().toString(), String.valueOf(score)) .commit();
                    editor.apply();

                    tv_rankId.setText(et_id.getText().toString());
                    tv_rankScore.setText(String.valueOf(score));

                } else if (gameId == 2) {
                    editor2.putString(et_id.getText().toString(), String.valueOf(score)).commit();
                    editor2.apply();

                    tv_rankId.setText(et_id.getText().toString());
                    tv_rankScore.setText(String.valueOf(score));
                } else {
                    editor3.putString(et_id.getText().toString(), String.valueOf(score2)).commit();
                    editor3.apply();

                    tv_rankId.setText(et_id.getText().toString());
                    tv_rankScore.setText(String.valueOf(score2));
                }
            }
        });

        bt_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(resultActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        bt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.finishAffinity(resultActivity.this);
                System.exit(0);
            }
        });
    }
}
