package com.example.homer.matbrensdaproject;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;
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
        // If the Intent results from the the 'ADD QUESTION' button been pressed, then data is sent
        // in the Extras and this is extracted and then sent back to the 'NewQuestionCreate' activity
        // to restart that activity and create a new question with current question number and question
        // name sent in Extras.
        // If the Intent results from the the 'FINISH' button been pressed, then there is no
        // Data in Extras and the so the question creating process ends and No Intent is sent back to
        // the 'NewQuestionCreate' activity and  this activity displays 'NEW TEST COMPLETED' from the layout.
        Bundle extras = getIntent().getExtras();
         if(extras !=null){
            newQuestionNumber =extras.getInt("QUESTION_NUM");
            newTestName =extras.getString("TEST_NAME");
            Intent intent = new Intent(QuestionsEnd.this, NewQuestionsCreate.class);
            intent .putExtra("QUESTION_COUNT",newQuestionNumber);
            intent .putExtra("keyNewTestName",newTestName);
            startActivity(intent);
        }
    }
}
