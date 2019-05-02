package com.example.homer.matbrensdaproject;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
// ***************************** QuestionsEnd Activity *******************************
/**  The  QuestionsEnd Activity provides the layout for the end of a test by a student. It
 * also plays a key role with the NewQuestionCreate activity in providing an intermeditary
 * step for the restarting of that activity for the displaying of each new question as
 * described in the activity.
*/
public class QuestionsEnd extends Activity {

    int newQuestionNumber = 1;      // int to represent the question number initialised to 1.
    String newTestName ="";        // String to represent then new Test Name.
    @Override  // onCreate method
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_end);

        // This activity is always started by an Intent from the 'NewQuestionCreate' activity.
        // If the Intent results from the 'ADD QUESTION' button been pressed, then data is sent
        // in Extras and this is extracted here and then sent back to the 'NewQuestionCreate' activity
        // to restart that activity and create a new question with the current question number, and
        //  question name, sent in Extras.
        // If the Intent results from the the 'FINISH' button been pressed in the 'NewQuestionCreate' activity,
        // then there is no Data in Extras and so the question creating process ends and NO Intent is activated to start
        // the 'NewQuestionCreate' activity. Then this activity displays 'NEW TEST COMPLETED' from the layout.
        // This is displayed for 5 seconds and then an Intent is activated and takes the user back to the Home page.
        Bundle extras = getIntent().getExtras();
         if(extras !=null){
            newQuestionNumber =extras.getInt("QUESTION_NUM");
            newTestName =extras.getString("TEST_NAME");
            Intent intent = new Intent(QuestionsEnd.this, NewQuestionsCreate.class);
            intent .putExtra("QUESTION_COUNT",newQuestionNumber);
            intent .putExtra("keyNewTestName",newTestName);
            startActivity(intent);
        }else {
             // After 5 seconds then following executes returning to the Home Page.
             new Timer().schedule(new TimerTask() {
                 @Override
                 public void run() {
                     // this code will be executed after 5 seconds
                     Intent intent = new Intent(QuestionsEnd.this, MainActivity.class);
                     startActivity(intent);
                 }
             }, 5000);
         }
    }
}
