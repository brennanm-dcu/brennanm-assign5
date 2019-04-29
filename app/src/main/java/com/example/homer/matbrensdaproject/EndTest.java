package com.example.homer.matbrensdaproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EndTest extends Activity {

    int gg =2;
    TextView txt;
    int scoreCount;
    int questionCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_test);
        txt = (TextView) findViewById(R.id.textViewx);

        // The following takes the number of questions for this test, determined in the last Activity
        // and stored in SharedPreferences, from SharedPreferences and saves the value in variable
        // no_of_questions.
        SharedPreferences settings;
        SharedPreferences.Editor prefEditor;
        settings = getSharedPreferences(String.valueOf("Num Of Questions"), MODE_PRIVATE);
        int no_of_questions = settings.getInt("key_numQuest", Integer.parseInt("0"));



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            scoreCount = extras.getInt("scoreCount");
            questionCount = extras.getInt("questionCount");
        }


        if((questionCount)< (no_of_questions+1 )) {

            Intent intent = new Intent(EndTest.this, StudentTest.class);

            intent.putExtra("scoreCount", scoreCount);
            intent.putExtra("questionCount", questionCount);
            startActivity(intent);

        }



        else {
            txt.setText("ALL OVER NOW!!! Your Score is - " + scoreCount);
        }


    }
}

