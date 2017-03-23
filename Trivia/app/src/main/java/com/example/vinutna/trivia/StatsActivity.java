package com.example.vinutna.trivia;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        LinearLayout ll=(LinearLayout) findViewById(R.id.statsLayout);
        //ll.removeAllViews();

        TextView resultView=(TextView)findViewById(R.id.performanceView);
        ProgressBar resultBar=(ProgressBar) findViewById(R.id.performanceBar);
        resultBar.setMax(100);

        ArrayList<Questions> answersList=new ArrayList<Questions>();

        answersList.add(new Questions("1?","",null,"a","0","a"));
        answersList.add(new Questions("2?","",null,"b","0",""));
        answersList.add(new Questions("3?","",null,"c","0","c"));
        answersList.add(new Questions("4?","",null,"t","0",""));
        answersList.add(new Questions("5?","",null,"c","0","c"));
        answersList.add(new Questions("6?","",null,"h","0","h"));
        answersList.add(new Questions("7?","",null,"r","0","r"));
        answersList.add(new Questions("8?","",null,"f","0","f"));
        answersList.add(new Questions("9?","",null,"g","0","k"));

        Collections.sort(answersList,Questions.sortByAnswers);

        int rightAnswers=0;
        int wrongAnswers=0;
        int resultScore;
        int totalQuestions=answersList.size();
        for(int i=0;i<answersList.size();i++){
            if(answersList.get(i).getGivenAnswer().equals(answersList.get(i).getAnswer())) {
                rightAnswers++;

                TextView textView = new TextView(this);
                textView.setText(answersList.get(i).getQuestion() + "?");
                ll.addView(textView);

                textView = new TextView(this);
                textView.setText(answersList.get(i).getGivenAnswer() + "?");
                textView.setBackgroundColor(Color.GREEN);
                ll.addView(textView);

                textView = new TextView(this);
                textView.setText(answersList.get(i).getAnswer() + "?");
                ll.addView(textView);
            }
            else if(!answersList.get(i).getGivenAnswer().equals(answersList.get(i).getAnswer())){
                wrongAnswers++;

                TextView textView = new TextView(this);
                textView.setText(answersList.get(i).getQuestion() + "?");
                ll.addView(textView);

                textView = new TextView(this);
                textView.setText(answersList.get(i).getGivenAnswer() + "?");
                textView.setBackgroundColor(Color.RED);
                ll.addView(textView);

                textView = new TextView(this);
                textView.setText(answersList.get(i).getAnswer() + "?");
                ll.addView(textView);
            }
        }
        resultScore=(int)(rightAnswers/(float)totalQuestions*100);
        resultView.setText(String.valueOf(resultScore)+"%");
        resultBar.setProgress(resultScore);
        final TextView t=(TextView) findViewById(R.id.timer);

        new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                t.setText(millisUntilFinished / 1000+"");
            }

            public void onFinish() {
                t.setText("done!");
            }
        }.start();

        findViewById(R.id.buttonFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
