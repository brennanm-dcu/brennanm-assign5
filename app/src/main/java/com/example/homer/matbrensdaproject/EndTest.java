package com.example.homer.matbrensdaproject;

/*
******************** EndTest Activity *******************************

The EndTest Activity provides the layout for the end of the Test. It also plays a
key role with the StudentTest activity in providing an intermeditary step
for the restarting that activity for the displaying of each new question as
described in the activity.

*/

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
    int scoreCount;             // This hold the current score or number of question answered right
    int questionCount;          // This hold the current question count.
    int no_of_questions;         // This holds the number of questions in teh test.

    @Override   // onCreat method
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_test);

        // The following sets the references to layout objects.
        txt = (TextView) findViewById(R.id.textViewx);

        // The following takes the number of questions for this test, determined in the last Activity
        // and stored in SharedPreferences, from SharedPreferences and saves the value in variable
        // no_of_questions.
        SharedPreferences settings;
        SharedPreferences.Editor prefEditor;
        settings = getSharedPreferences(String.valueOf("Num Of Questions"), MODE_PRIVATE);
        int no_of_questions = settings.getInt("key_numQuest", Integer.parseInt("0"));


        // This activity is activated by an Intent from the EndTest activity which also sends
        // the variables scoreCound and questionCount in extras. This information is recovered here.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            scoreCount = extras.getInt("scoreCount");
            questionCount = extras.getInt("questionCount");
        }

        // This activity works with the StudentTest activity in that it activates
        // an Intent to activate the StudentTest activity at the end of each question in
        // StusentTest so as to restart this activity for the next question. The variables
        // scoreCount and questionCount are sent back to StudentTest in extras.
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

