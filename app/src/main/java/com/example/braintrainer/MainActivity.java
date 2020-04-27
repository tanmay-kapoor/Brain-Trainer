package com.example.braintrainer;

import androidx.annotation.IntegerRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startButton, button0, button1, button2, button3, playAgainButton;
    TextView sumTextView, resultTextView, scoreTextView, timerTextView;
    CountDownTimer countDownTimer;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int ansLocation;
    int score;
    int questionsSolved ;
    boolean gameOver;

    public void start(View view) {

        view.setVisibility(View.INVISIBLE);
        timerTextView.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);
        sumTextView.setVisibility(View.VISIBLE);
        button0.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);

        resultTextView.setText("");
        resultTextView.setTextSize(50f);
        resultTextView.setTextColor(0xFF7F7F7F);

        scoreTextView.setText("0/0");

        score = 0;
        questionsSolved = 0;
        gameOver = false;

        countDownTimer = new CountDownTimer(30100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished/1000) + "s");
            }

            @Override
            public void onFinish() {
                resultTextView.setTextSize(25f);
                resultTextView.setTextColor(0xFFFF0000);
                resultTextView.setText("\t  Game Over!\nYour score: " + score + "/" + questionsSolved);
                gameOver = true;
                playAgainButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public void generateQuestion() {

        char[] operators = {'+', '-', '*'};
        int result = 0, wrongAns;

        Random rand = new Random();
        int num1 = rand.nextInt(21);
        int num2 = rand.nextInt(21);
        int operator = rand.nextInt(3);
        int bound = 21;

        sumTextView.setText(Integer.toString(num1) + " " + operators[operator] + " " + Integer.toString(num2));
        switch(operator) {

            case 0:
                result = num1 + num2;
                break;

            case 1:
                result = num1-num2;
                break;

            case 2:
                result = num1*num2;
                bound = 401;
                break;
        }

        ansLocation = rand.nextInt(4);

        for(int i=0; i<4; i++) {
            if(i == ansLocation)
                answers.add(result);
            else{

                wrongAns = rand.nextInt(bound);

                if (result<0)
                    wrongAns *= -1;

                while(wrongAns == result) {

                    wrongAns = rand.nextInt(bound);

                    if (result<0)
                        wrongAns *= -1;
                }
                answers.add(wrongAns);
            }
        }
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));

        answers.clear();
    }

    public void chooseAnswer(View view) {

        if(!gameOver) {

            if(view.getTag().toString().equals(Integer.toString(ansLocation))){
                resultTextView.setText("Correct!");
                score++;
            } else {
                resultTextView.setText("Incorrect!");
            }

            questionsSolved++;
            scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(questionsSolved));

            generateQuestion();

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.resultTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        sumTextView = findViewById(R.id.sumTextView);
        timerTextView = findViewById(R.id.timerTextView);
        playAgainButton = findViewById(R.id.playAgainButton);


        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        generateQuestion();
    }
}
