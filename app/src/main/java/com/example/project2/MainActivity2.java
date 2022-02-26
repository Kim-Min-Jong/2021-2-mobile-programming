package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity2 extends AppCompatActivity {


    private static float velocity = 0;
    private static float magnetoVelocity = -48.375f;

    private static final float gravity = 9.8f;
    private static final float gravityWeight = 0.02f;
    ImageView idle;
    Timer timerKeyDown;
    boolean keyLeftDown = false;
    boolean keyRightDown = false;
    float totalX = 0;
    float currentX = 0;

    SensorManager manager;
    SensorEventListener listener;

    ArrayList<ImageView> backgroundObject = new ArrayList<>();
    ArrayList<PineApple> backgroundPineapples = new ArrayList<>();


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (keyLeftDown) return true;
                if (keyRightDown) {
                    keyRightDown = false;
                    timerKeyDown.cancel();
                }
                timerKeyDown = new Timer();
                timerKeyDown.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        idle.setX(idle.getX() - 4f);
                    }
                }, 0, 10);
                keyLeftDown = true;
                return true;

            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (keyRightDown) return true;
                if (keyLeftDown) {
                    keyLeftDown = false;
                    timerKeyDown.cancel();
                }
                timerKeyDown = new Timer();
                timerKeyDown.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        idle.setX(idle.getX() + 4f);
                    }
                }, 0, 10);
                keyRightDown = true;
                return true;
            case KeyEvent.KEYCODE_DPAD_UP:
                velocity = 100f;
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                keyLeftDown = false;
                timerKeyDown.cancel();
                return true;

            case KeyEvent.KEYCODE_DPAD_RIGHT:
                keyRightDown = false;
                timerKeyDown.cancel();
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = this;
        setContentView(R.layout.activity_game);
        idle = (ImageView) findViewById(R.id.idle);
        TextView score = (TextView) findViewById(R.id.score);
        final float[] time = {0};

        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.mainConstraint);
        ConstraintSet set = new ConstraintSet();
        velocity = 100f;
        magnetoVelocity = 0f;

        for (int i = 1; i <= 9; i++) {
            try {
                backgroundObject.add(findViewById(R.id.class.getField("bottom_" + i).getInt(null)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        Sensor AccelSensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor magnetoSensor = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);


        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float value = sensorEvent.values[0];
                magnetoVelocity = value;
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
                return;
            }
        };

        manager.registerListener(listener, magnetoSensor, SensorManager.SENSOR_DELAY_GAME);


        // 파인애플 생성관리 쓰레드
        Timer timer3 = new Timer();
        timer3.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time[0] += 0.1f;
                        score.setText((Math.round(time[0] * 10) / 10f) + "s");

                        if (totalX > currentX + 500) {
                            currentX += 500;
                            PineApple pineApple = new PineApple(context);
                            pineApple.setId(View.generateViewId());
                            layout.addView(pineApple, 0);
                            pineApple.setSizeDp(48, 33);

                            set.clone(layout);
                            set.connect(pineApple.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP, -200);
                            set.applyTo(layout);
                            backgroundPineapples.add(pineApple);

                            pineApple.setX((float) (Math.random() * 1000));
                        }
                    }
                });
            }
        }, 1000, 100);


        //중력 이동 쓰레드
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {


                idle.setX(idle.getX() + (magnetoVelocity / 2f));

                if (idle.getY() < 400 && velocity > 0) {

                    for (ImageView em : backgroundObject) {
                        em.setY(em.getY() + velocity);
                    }
                    for (PineApple em : backgroundPineapples) {
                        em.setY(em.getY() + velocity);
                    }
                } else if (idle.getY() < 2000) {
                    idle.setY(idle.getY() - velocity);
                } else {
                    Intent intent = new Intent(context, EndActivity.class);
                    intent.putExtra("score", Math.round(time[0] * 10) / 10f);
                    startActivity(intent);
                    finish();
                    timer.cancel();
                }

                if(idle.getX() > 1100){
                    idle.setX(-90);
                }else if(idle.getX() < -100){
                    idle.setX(1090);
                }

                velocity = velocity - 0.03f * gravity;
                totalX += velocity;

            }
        }, 2000, 10);

        //오브젝트 충돌 관리 쓰레드
        Timer timer4 = new Timer();
        timer4.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Iterator<PineApple> list = backgroundPineapples.listIterator();
                        while (list.hasNext()) {
                            PineApple pa = list.next();
                            if (pa.collision(idle)) {
                                if (velocity <= 30) velocity = 30f;

                                layout.removeView(pa);
                                list.remove();

                            } else if (pa.getY() > 2000) {
                                layout.removeView(pa);
                                list.remove();
                            }
                        }
                    }
                });
            }
        }, 1000, 10);

    }
}