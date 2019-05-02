package com.example.homer.matbrensdaproject;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
// * ***************************** EndTest Activity *******************************
/**
 * The EndTest Activity provides the layout for the end of the Test. It also plays a
 * key role with the StudentTest activity in providing an intermeditary step
 * for the restarting that activity for the displaying of each new question as
 * described in the activity.
 */
public class EndTest extends Activity {

    TextView textViewEnd;       //TextView reference 'textViewEnd' to a TextView which displays the final score to
                                // a student after completing the test.
    TextView textViewEndScore;  // TextView reference 'textViewEndScore' to a TextView which displays the final score to
                                // a student after completing the test in % form.
    int scoreCount;             // This hold the current score or number of question answered right
    int questionCount;          // This hold the current question count.

    @Override   // onCreat method
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_test);

        // The following sets the references to layout objects.
        textViewEnd = (TextView) findViewById(R.id.textViewEnd);
        textViewEndScore = (TextView) findViewById(R.id.textViewEndScore);

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
        // This activity works with the StudentTest activity to display the next question. This activity
        // is always started with an Intent from the StudentTest activity at the end of each question,
        // with variables scoreCount and questionCount sent in Extras.
        // This activity determines if it is at the last question using variables questionCount and
        // no_of_questions. If it is not the last question then an Intent is
        // activated to activate the StudentTest activity again so as to restart this activity for the
        // next question. The variables scoreCount and questionCount are sent back to StudentTest
        // in Extras, to set the number of the next question and retrieve it from Firebase Database, and
        // also later increment the score if necessary.
        // If it is the last Question then NO Intent is activated for StudentTest and the the layout for
        // this Activity is shown indicating the final score for 5 seconds then an Intent takes
        // the user back to the Home page.
        if((questionCount)< (no_of_questions+1 )) {
            Intent intent = new Intent(EndTest.this, StudentTest.class);
            intent.putExtra("scoreCount", scoreCount);
            intent.putExtra("questionCount", questionCount);
            startActivity(intent);
        }
        else {
             textViewEnd.setText("ASSIGNMENT COMPLETE!!! Your Score is - " + scoreCount + " Out of "+ no_of_questions + " Questions");
             double percentage = ((scoreCount*100)/(no_of_questions));
             textViewEndScore.setText(percentage + " %");
             // After 7 seconds then following executes returning to the Home Page.
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    // this code will be executed after 5 seconds
                    Intent intent = new Intent(EndTest.this, MainActivity.class);
                    startActivity(intent);
                }
            }, 5000);
        }
    }
}

