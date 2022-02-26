package com.example.project2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainGame extends AppCompatActivity {
    Intent i;
    SpeechRecognizer mRecognizer;
    TextView result;
    TextView sentence;
    int qNum=0;
    int limitNumber=10;
    Button btn_hearing;
    ImageView mark;
    ArrayList<Sentence> tongueTwisters= new ArrayList<>();
    private final int MY_PERMISSIONS_RECORD_AUDIO = 1;
    Timer t = new Timer();
//    long start_time = System.nanoTime();
    TextView realTime ;
    int value=0;


    TextView numberText;
    TextView scoreText;
    int scores=0;

    //게임종료 알림창
    View dialogView;
    TextView showScore ;
    TextView showTime ;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        result = (TextView) findViewById(R.id.textFromVoice);
        sentence = (TextView) findViewById(R.id.Sentence);
        btn_hearing = (Button) findViewById(R.id.btn_hearing);

        //json데이터를 읽어서 toungTwisters 어레이리스트에 넣어둔다.
        String setJson = getJsonString("sets.json");
        jsonParsing(setJson);
        i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getApplicationContext().getPackageName());
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
        mRecognizer = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
        mRecognizer.setRecognitionListener(listener);

        //문제 출제
        sentence.setText(tongueTwisters.get(qNum).getContent());
        realTime=(TextView)findViewById(R.id.time);
        mHandler.sendEmptyMessage(0);

        //문제 번호
        numberText = (TextView) findViewById(R.id.number);
        //맞은 개수
        scoreText = (TextView) findViewById(R.id.score);

        showScore = (TextView) findViewById(R.id.showScore);
        showTime = (TextView) findViewById(R.id.showTime);

        dialogView = (View)View.inflate(getApplicationContext(), R.layout.dialog,null);
    }
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        public void handleMessage(Message msg) {
            value++;
            realTime.setText(value+"초");
            // 메세지를 처리하고 또다시 핸들러에 메세지 전달 (1000ms 지연)
            mHandler.sendEmptyMessageDelayed(0,1000);
        }
    };
    public void leave(View v){
        finish();
    }

    //채점시 맞으면 점수 올려준다.
    public Boolean marking(String p, String[] input){
        String nsP = p.replace(" ","");//noSpaceInput,
        for(int i=0; i<input.length;i++){
            String nsInput = input[i].replace(" ","");
            if(nsP.equals(nsInput)){
                ++scores;
                scoreText.setText( scores+"점");
                return true;
            }
        }
        return false;
    }

    //json 파일 넣어서 String으로 환반
    private String getJsonString(String jsonName) {
        String json = "";

        try {
            InputStream is = getAssets().open(jsonName);
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return json;
    }

    //String타입 json을 받아서 Sentence Array완성
    private void jsonParsing(String json){
        try{
            RandomNumbersArray randomNumbersArray = new RandomNumbersArray(limitNumber);
            JSONObject jsonObject = new JSONObject(json);//json에서 json파일로 받아온다.
            JSONArray setArray = jsonObject.getJSONArray("Sets");//json파일의 set키값에 대응과는 value array형태로 받아온다.
            for(int i=0; i<limitNumber; i++) {
                JSONObject setObject = setArray.getJSONObject(randomNumbersArray.getNumbers()[i]);//array형태에서 i번째 json 객체를 가져온다.
                JSONArray ansArray = setObject.getJSONArray("answers"); //i번째 json 객체에서 answer에 해당하는 value를 array로 가져온다.
                int length = ansArray.length();
                String [] anss = new String [length];//AnswerSentences
                if (length > 0) {
                    for (int j = 0; j < length; j++) {
                        anss[j] = ansArray.getString(j);
                    }
                }

                Sentence set = new Sentence(
                        setObject.getString("id"),
                        setObject.getString("sentence"),
                        setObject.getString("difficulty"),
                        anss
                );
                tongueTwisters.add(set);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view) {
        System.out.println("-------------------------------------- 음성인식 시작!");
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainGame.this, new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_RECORD_AUDIO);
            //권한을 허용하지 않는 경우
        } else {
            //권한을 허용한 경우
            try {
                mRecognizer.startListening(i);
            } catch(SecurityException e) {
                e.printStackTrace();
            }
        }
    }


    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            System.out.println("onReadyForSpeech.........................");
            btn_hearing.setBackgroundColor(Color.YELLOW);

        }
        @Override
        public void onBeginningOfSpeech() {
            Toast.makeText(getApplicationContext(), "지금부터 말을 해주세요!", Toast.LENGTH_SHORT).show();
            btn_hearing.setBackgroundColor(Color.RED);
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            System.out.println("onRmsChanged.........................");
            btn_hearing.setBackgroundColor(Color.BLUE);
            btn_hearing.setText("듣는중");
            result.setText("듣는중...");
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            System.out.println("onBufferReceived.........................");
            btn_hearing.setBackgroundColor(Color.YELLOW);
        }

        @Override
        public void onEndOfSpeech() {
            System.out.println("onEndOfSpeech.........................");
            btn_hearing.setBackgroundColor(Color.BLACK);
        }

        @Override
        public void onError(int error) {
            btn_hearing.setText("speach to text");
            btn_hearing.setBackgroundColor(Color.BLACK);
            result.setText("천천히 다시 시도해주세요");

        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            System.out.println("onPartialResults.........................");
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            System.out.println("onEvent.........................");
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onResults(Bundle results) {
            String key= "";
            key = SpeechRecognizer.RESULTS_RECOGNITION;
            ArrayList<String> mResult = results.getStringArrayList(key);
            String[] rs = new String[mResult.size()];
            mResult.toArray(rs);
            result.setText(rs[0]);
            btn_hearing.setText("speach to text");
            btn_hearing.setBackgroundColor(R.color.purple_500);
            mark = (ImageView) findViewById(R.id.marking) ;

            //채점-정답
            if(marking(result.getText().toString(),tongueTwisters.get(qNum).getAnswers())){
                mark.setImageResource(R.drawable.o);
                mark.setVisibility(View.VISIBLE);
                t.schedule(new TimerTask(){
                    @Override
                    public void run(){
                        mark.setVisibility(View.INVISIBLE);
                    }
                },1000);

                result.setText("");

                //다음문제
                if(qNum+1<limitNumber){
                    qNum+=1;
                    sentence.setText(tongueTwisters.get(qNum).getContent());

                    numberText.setText("문제 "+(qNum+1)+"번");
                }else{
                    gameOver();
                }
            //채점-오답
            }else{
                    mark.setImageResource(R.drawable.x);
                    mark.setVisibility(View.VISIBLE);
                    t.schedule(new TimerTask(){
                        @Override
                        public void run(){
                            mark.setVisibility(View.INVISIBLE);
                        }
                    },1000);
            }
            //Toast.makeText(getApplicationContext(), rs[0], Toast.LENGTH_SHORT).show();
            //mRecognizer.startListening(i); //음성인식이 계속 되는 구문이니 필요에 맞게 쓰시길 바람
        }
    };

    public void pass(View view){
        if(qNum+1<limitNumber){
            qNum+=1;
            sentence.setText(tongueTwisters.get(qNum).getContent());
            numberText.setText("문제 "+(qNum+1)+"번");
            result.setText("");
        }else{
            gameOver();
        }

    }

    public void gameOver(){
        //질문
//        showScore.setText("맞은 개수 : "+scores+"개");
//        showTime.setText("걸린 시간 : "+value+"초");
        builder = new AlertDialog.Builder(this)
                .setMessage("맞은 개수 : "+scores+"\n걸린 시간 : "+value+"초")
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
                        Intent intent = new Intent(MainGame.this, resultActivity.class);
                        intent.putExtra("score", scores);
                        intent.putExtra("gameId",2);
                        startActivity(intent);
                        finish();
                    }
                })
        ;
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);//없어지지 않도록 설정

        mHandler.removeMessages(0);
        dialog.show();
    }


}