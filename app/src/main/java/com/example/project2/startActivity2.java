package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;

public class startActivity2 extends AppCompatActivity {
    SoundPool soundPool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start2);

    }
    public void totheGame(View v){
        Intent intent = new Intent(getApplicationContext(), MainGame.class);
        startActivity(intent);
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        int soundId = soundPool.load(this, R.raw.wrong,0);
        soundPool.play(soundId,1f,1f,0,0,1f);	//작성

    }
    public void quit(View v){
        System.exit(0);
    }
}