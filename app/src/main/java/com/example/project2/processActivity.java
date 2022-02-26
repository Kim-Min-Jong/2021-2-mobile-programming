package com.example.project2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import java.util.Random;

public class processActivity extends AppCompatActivity {
    Thread thread = null;
    TextView time;
    TextView score;
    ImageView[] imgArr = new ImageView[16];
    int[] imageId = {R.id.field1, R.id.field2, R.id.field3, R.id.field4, R.id.field5, R.id.field6, R.id.field7, R.id.field8, R.id.field9, R.id.field10, R.id.field11, R.id.field12, R.id.field13, R.id.field14, R.id.field15, R.id.field16};
    final int object[] = {R.drawable.moleup, R.drawable.bombmole, R.drawable.bombup, R.drawable.star,R.drawable.hole};
    int score2 = 0;
    final String TAG_Mole1 = "moleup";
    final String TAG_Mole2 = "bombmole";
    final String TAG_Star = "star";
    final String TAG_bomb = "bomb";
    final String TAG_empty = "empty";
    static final int TIME = 20;
    Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameview);

        time = findViewById(R.id.time);
        score = findViewById(R.id.score);
        for (int i = 0; i < imgArr.length; i++) {
            imgArr[i] = (ImageView) findViewById(imageId[i]);
            imgArr[i].setImageResource(R.drawable.hole);

            imgArr[i].setOnClickListener(new View.OnClickListener() { //두더지이미지에 온클릭리스너
                @Override
                public void onClick(View v) {
                    try {
                        if (((ImageView) v).getTag().toString().equals(TAG_Mole1)) {
                            score.setText("점수 : " + String.valueOf(score2 += 50));
                            ((ImageView) v).setImageResource(R.drawable.hole);
                        } else if (((ImageView) v).getTag().toString().equals(TAG_Mole2)) {
                            score.setText("점수 : " + String.valueOf(score2 -= 30));
                            ((ImageView) v).setImageResource(R.drawable.hole);
                        } else if (((ImageView) v).getTag().toString().equals(TAG_Star)) {
                            score.setText("점수 : " + String.valueOf(score2 += 70));
                            ((ImageView) v).setImageResource(R.drawable.hole);
                        } else {
                            score.setText("점수 : " + String.valueOf(score2 -= 50));
                            ((ImageView) v).setImageResource(R.drawable.hole);
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "두더지를 터치하세요!", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }

        time.setText("남은시간 : 20");
        time.setTextColor(Color.BLACK);
        score.setText("점수 : 0");
        score.setTextColor(Color.BLACK);

        new Thread(new timer()).start();
        for (int i = 0; i < imgArr.length; i++) {
            t = new Thread(new moleThread(i));
            t.start();
        }

    }

    // 시간 출력
    Handler timehandle = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            time.setText("남은 시간: " + msg.arg1);
            time.setTextColor(Color.BLACK);
        }
    };

    // 종료 트리거
    Handler endhandle = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            endTrigger();
        }
    };

    // 두더지 x
    Handler downhandle = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            imgArr[msg.arg1].setImageResource(R.drawable.hole);
        }
    };

    // 두더지 o
    Handler uphandle = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Random r = new Random();
            for (int x = 0; x < 3; x++) {
                int i = r.nextInt(5);
                imgArr[msg.arg1].setImageResource(object[i]);
                if (i == 0) {
                    imgArr[msg.arg1].setTag(TAG_Mole1);
                } else if (i == 1) {
                    imgArr[msg.arg1].setTag(TAG_Mole2);
                } else if (i == 3) {
                    imgArr[msg.arg1].setTag(TAG_Star);
                }  else {
                    imgArr[msg.arg1].setTag(TAG_bomb);
                }
            }
        }
    };

    // 시간 제한 - 시간이 끝나면 종료트리거 실행
    public class timer implements Runnable {

        Message M = new Message();

        @Override
        public void run() {
            for (int i = TIME; i >= 0; i--) {
                Message m = new Message();
                m.arg1 = i;
                timehandle.sendMessage(m);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            t.interrupt();
            endhandle.sendMessage(M);
        }

    }

    // 두더지 등장 시간 조절
    public class moleThread implements Runnable {
        int i;

        public moleThread(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Message m1 = new Message();
                    Message m2 = new Message();
                    Random r = new Random();
                    // 내려가있는 시간기본 2초에 랜덤 0~3초 추가시간
                    int downtime = r.nextInt(8000) + 2000;
                    Thread.sleep(downtime);
                    m1.arg1 = i;
                    uphandle.sendMessage(m1);

                    // 올라와있는 시간 기본 1초에  0~2초 랜덤 추가 시간
                    int uptime = r.nextInt(1000) + 500;
                    Thread.sleep(uptime);
                    m2.arg1 = i;
                    downhandle.sendMessage(m2);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    // 앱종료
    public void exit() {
        ActivityCompat.finishAffinity(this);
        System.exit(0);
    }


    // 종료 시 나오는 다이얼로그
    public void endTrigger() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("게임종료").setMessage("점수: " + score2 + "점   (종료 or 점수입력)")
                .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exit();
                    }
                }).setNegativeButton("점수입력", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(processActivity.this, tabActivity.class);
                Intent intent = new Intent(processActivity.this, resultActivity.class);
                intent.putExtra("score", score2);
                intent.putExtra("gameId", 1);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        try {
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
