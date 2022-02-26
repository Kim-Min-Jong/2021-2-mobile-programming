package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button score = (Button)findViewById(R.id.start);
        Button end = (Button)findViewById(R.id.end);
        Button game1 = (Button)findViewById(R.id.game1);
        Button game2 = (Button)findViewById(R.id.game2);
        Button game3 = (Button)findViewById(R.id.game3);
        score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, tabActivity.class);
                startActivity(intent);
//                finish();
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.finishAffinity(MainActivity.this);
                System.exit(0);
            }
        });
        game1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, startActivity.class);
                startActivity(intent);
            }
        });
        game2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, startActivity2.class);
                startActivity(intent);
            }
        });
        game3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, startActivity3.class);
                startActivity(intent);
            }
        });
    }
}