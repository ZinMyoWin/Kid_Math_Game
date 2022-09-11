package com.example.kid_game;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class Level0 extends AppCompatActivity implements View.OnClickListener{

    TextView Level,TotalQ,Question,Score;
    Button AnsA, AnsB,AnsC,submitBtn;

    int score=0;
    int totQuestion=QuestionAnswer.question.length;
    int currentQuestionIndex=0;
    String selectedAnswer="";
    int answer=0, num1=0, num2=0, operatorNumber = 0;
    String operator="";
    ArrayList<Integer> optionList;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level0);

        Level=findViewById(R.id.idLevel);
        TotalQ=findViewById(R.id.idTotalQ);
        Question=findViewById(R.id.idQuestion);
        Score=findViewById(R.id.idscore);



        AnsA=findViewById(R.id.idansA);
        AnsB=findViewById(R.id.idansB);
        AnsC=findViewById(R.id.idansC);
        submitBtn=findViewById(R.id.idSubmit);

        AnsA.setOnClickListener(this);
        AnsB.setOnClickListener(this);
        AnsC.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        TotalQ.setText("Total question: "+totQuestion);
        loadNewQuestion();
        Score.setText("Score:"+score);



    }

    @Override
    public void onClick(View view) {


        AnsA.setBackgroundColor(Color.WHITE);
        AnsB.setBackgroundColor(Color.WHITE);
        AnsC.setBackgroundColor(Color.WHITE);
        Score.setText("Score:"+score);
        Button clickedButton=(Button) view;

        if (clickedButton.getId()==R.id.idSubmit){


            if(selectedAnswer.equals(String.valueOf(answer)))
            {
                score++;
                Score.setText("Score:"+score);

                MediaPlayer ring= MediaPlayer.create(Level0.this,R.raw.win);
                ring.start();
            }
            else{
                MediaPlayer ring= MediaPlayer.create(Level0.this,R.raw.fail);
                ring.start();
            }

            currentQuestionIndex++;
            loadNewQuestion();
        }
        else{
            selectedAnswer=clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.GREEN);
        }



    }

    public void loadNewQuestion(){

        if(currentQuestionIndex==totQuestion){
            finishQuiz();
            return;
        }
        /*
        Question.setText(QuestionAnswer.question[currentQuestionIndex]);
        AnsA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        AnsB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        AnsC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
         */
        num1 = randomNumber(12,1);
        num2 = randomNumber(12,1);
        operatorNumber = randomNumber(4, 1);
        if(operatorNumber==1){
            operator = "+";
            answer = num1 + num2;
        }
        else if(operatorNumber==2){
            operator = "-";
            while(num1<num2){
                num1 = randomNumber(12, 1);
                num2 = randomNumber(12, 1);
            }
            answer = num1 - num2;
        }
        else if(operatorNumber == 3){
            operator = "*";
            answer = num1 * num2;
        }
        else if(operatorNumber == 4){
            operator = "/";
            while (num1<num2){
                num1 = randomNumber(12,1);
                num2 = randomNumber(12, 1);
            }
            answer = num1 / num2;
        }
        Question.setText(num1 + " "+operator+" "+num2);
        setUpOption();
    }

    public void finishQuiz(){
        String passStatus="";


        if(score>totQuestion){
            passStatus="Passed";
        }

        else{
            passStatus="Failed";
        }
        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Congratulations!!\n"+"Your score is "+score+"out of"+totQuestion)
                .setPositiveButton("Restart",(dialogInterface, i) -> restartQuiz() )
                .setCancelable(false)
                .show();
    }

    public void restartQuiz(){
        score=0;
        Score.setText("Score:"+score);
        currentQuestionIndex=0;
        loadNewQuestion();
    }

    public int randomNumber(int max, int min){
        int range = max - min + 1;
        int rand = (int)(Math.random() * range) + min;
        return rand;
    }


    public void setUpOption(){
        optionList = new ArrayList<Integer>();
        optionList.add(answer);
        for(int i = 0; i<2; i++){
            int randomNum = randomNumber(12,1);
            while(optionList.contains(randomNum)){
                randomNum = randomNumber(12,1);
            }
            optionList.add(randomNum);
        }
        Collections.shuffle(optionList);
        AnsA.setText(optionList.get(0)+"");
        AnsB.setText(optionList.get(1)+"");
        AnsC.setText(optionList.get(2)+"");
    }




}