package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        Intent intent = getIntent();
        float score = intent.getFloatExtra("score", 0);
        TextView scoreView = (TextView) findViewById(R.id.scoreEnd);
        scoreView.setText(score + "s");

        TextView re = findViewById(R.id.re);

        Intent mainIntent = new Intent(this, MainActivity.class);
        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(mainIntent);
            }
        });


        Intent intentResult = new Intent(this, resultActivity.class);

         new AlertDialog.Builder(this)
                .setMessage("스코어 : "+ score +"초")
//                .setView(dialogView)
                .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("점수입력", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        intentResult.putExtra("score2", score);
                        intentResult.putExtra("gameId",3);
                        startActivity(intentResult);
                        finish();
                    }
                }).create().show();

//        dialog = builder.create();
//        dialog.setCanceledOnTouchOutside(false);//없어지지 않도록 설정 dialog.show();

    }
}