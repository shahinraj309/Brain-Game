package com.example.brangameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //+============================================================
    Button button0,button1,button2,button3;
    TextView sumtextView,scertextview,timerTextView,answer;
    int scrore=0;
    int nmberofQustions=0;
    ArrayList<Integer>answers=new ArrayList<Integer>();
    int lacatiocreccetanswers;
    LottieAnimationView animationView,rightanim,waronganim;
    MediaPlayer mediaPlayer,mediaPlayer2;
    EditText inputtime;
    Button playWithTime;
    Random random;
    int countTIme;
    String IntentCount;
    String answer1;

    ///===============================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        answer=findViewById(R.id.answer);
        mediaPlayer2=MediaPlayer.create(MainActivity.this,R.raw.correct);
        mediaPlayer= new MediaPlayer();
        mediaPlayer=MediaPlayer.create(MainActivity.this,R.raw.wrong);
        inputtime=findViewById(R.id.timeInput);
        playWithTime=findViewById(R.id.playwithtime);
        animationView=findViewById(R.id.animationView);
        random=new Random();
        timerTextView=findViewById(R.id.timer);
        scertextview=findViewById(R.id.score);
        sumtextView=findViewById(R.id.sumtextview);
        button0=findViewById(R.id.button0);
        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        waronganim=findViewById(R.id.wronganim);
        rightanim=findViewById(R.id.rightanim);
        waronganim.setVisibility(View.INVISIBLE);
        rightanim.setVisibility(View.INVISIBLE);
        newQutions();
        playWithTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time=inputtime.getText().toString();
                if (time.isEmpty()){
                    inputtime.setError("Enter Time In Minit");
                    inputtime.requestFocus();
                    return;
                }
                closekeybord();
                int intTime=Integer.parseInt(time);
                int max=intTime*60000;
               countTIme=max;
               playWithTime.setVisibility(View.GONE);
               inputtime.setVisibility(View.GONE);
               IntentCount=String.valueOf(max);
               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       playWithTime.setVisibility(View.VISIBLE);
                       inputtime.setVisibility(View.VISIBLE);
                       animationView.playAnimation();}
               },max);
                newQutions();
                timeup();

            }
        });


    }

    //========================================
    public void newQutions(){
         new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
                 waronganim.setVisibility(View.INVISIBLE);
                 rightanim.setVisibility(View.INVISIBLE); }
         },2200);
        int a=random.nextInt(91);
        int b=random.nextInt(91);
        lacatiocreccetanswers=random.nextInt(4);
        sumtextView.setText(Integer.toString(a)+"+"+Integer.toString(b));
        lacatiocreccetanswers=random.nextInt(4);

        for (int i=0;i<4;i++){
            if (i==lacatiocreccetanswers){
                answers.add(a+b);
            }else {
                int wrongaswer=random.nextInt(111);

                while (wrongaswer==a+b){
                    wrongaswer=random.nextInt(111);
                }
                answers.add(random.nextInt(111)); }
        }

        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }
    ///================================================================================//




///=============================================================================================
    public void choseAnswer(View view){
        if ( Integer.toString(lacatiocreccetanswers).equals(view.getTag().toString())){
            Log.i("Crecce","Got It");

            answer.setText("Correct ");
            answer.setVisibility(View.VISIBLE);
            scrore++;

            mediaPlayer2.start();
            answers.clear();
            rightanim.setVisibility(View.VISIBLE);
            newQutions();



        }
        else {
            answer.setText("Wrong");
            answer.setVisibility(View.VISIBLE);
            Log.i("Faild","Faild");
           mediaPlayer.start();
           waronganim.setVisibility(View.VISIBLE);
        }
        nmberofQustions++;
        scertextview.setText(Integer.toString(scrore)+"/"+Integer.toString(nmberofQustions));
        answer1=scertextview.getText().toString();
        answers.clear();
        newQutions();    }
        //======================================

    ////Auto Timer Class
    ////================================================
    public  void timeup(){
        animationView.playAnimation();
        new CountDownTimer(countTIme,1000){
            @Override
            public void onTick(long l) {
                timerTextView.setText(String.valueOf(l/1000)+"s");
            }

            @Override
            public void onFinish() {
                animationView.pauseAnimation();
                int minit=Integer.parseInt(IntentCount);
                float convartminit=Float.parseFloat(String.valueOf(minit));
                int cm= (int) (convartminit/60000);
                String name="আপনি "+cm+" মিনিটের ভিতরে \nউত্তর দিয়েছেন "+answer1+"টি প্রশ্নে ছিলো";
                Intent intent=new Intent(MainActivity.this,MainActivity2.class);
                intent.putExtra("t",name);
                startActivity(intent);
            }
        }.start();

    }

    ///=================================================================================
    private  void  closekeybord(){
        View view=this.getCurrentFocus();
        if (view!=null){
            InputMethodManager methodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            methodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}