package com.example.homer.matbrensdaproject;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//  ***********************************************- NewQuestionsCreate-*********************************************************************
/**
 *   Welcome to NewQuestionsCreate Activity this is where Questions are created for the new Test, with help from the QuestionsEnd Activity. The
 *   questions when created, are then sent to a Database in FireBase. The name of the new Test is taken from the intent sent from the AdminMain Activity.
 *   This is then used with a set question format, to produce the questions. Basically each iteration through the code produces a
 *   question and each question is referred to as 'qx' where x is the question number. Each question will have the same notation in
 *   its layout. There will be six Strings of data taken from six EditText boxed on the layout. There will be a question String,
 *   referred to as "q", there will be four alternative answers each referred to as "a1" to "a4" and finally there will be a variable
 *   that holds then number of the right answer from the four alternatives.
 *   Each cycle provides six empty EditText boxes to the user and only when they are all filled in, and the 'ADD QUESTION' button is
 *   pressed, will that question be sent to the Database and the the cycle begins again with six empty EditText boxes until the
 *   finish Button is pressed. In restarting a cycle the AdminExtra Activity must be restarted and this is done
 *   by sending an Intent to the QuestionsEnd Activity with Question Number and Test Name data. This activity then immediately sends
 *   an Intent back to the AdminExtra Activity to begin the cycle again with the data.
 */
public class NewQuestionsCreate extends Activity {

    private DatabaseReference myNewTestDatabase;               //Initialise Firebase Database Reference for reference to where to store data in the FireBase Database
    private TextView QuestNo;                                  // TextView reference to a textView to display the question number on the layout
    private TextView testTitle;
    private EditText editNewQuestion;                          // EditView reference to an EditView on the layout to take in the question text.
    private EditText editNewA1;                                // EditViews editNewA1 to editNewA4 reference EditViews on the layout to take answers to the question.
    private EditText editNewA2;
    private EditText editNewA3;
    private EditText editNewA4;
    private EditText editNewAns;                               // EditView reference to an EditView on the layout to take in the number of the right answer.
    String newTestName;                                        // String to hold the new Test Name.
    String newQuestionName="q1";                               // String to hold the question name and initialised to 'q1' the first question name.
    int newQuestionNumber = 1;                                 // Int to hold the new question number and initialised to '1' for the first question.
    int returnedQuestionNumber=0;                              // Int to hold the returned question number from Activity AdminEnd.
    private Button btnfinish;                                  // Button reference to the Finish button.
    private Button addQuestion;                                // Button reference to the adQuestion button.
    private  final String TAG = this.getClass().getSimpleName();    // Log Tag

    @Override  //on create Method
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_questions_create);

        // The following sets references to the layout objects.
        QuestNo=(TextView)findViewById(R.id.q_number_textView);
        testTitle=(TextView)findViewById(R.id.test_title_textView);
        editNewQuestion=(EditText) findViewById(R.id.editTextQ);
        editNewA1=(EditText) findViewById(R.id.editTextA1);
        editNewA2=(EditText) findViewById(R.id.editTextA2);
        editNewA3=(EditText) findViewById(R.id.editTextA3);
        editNewA4=(EditText) findViewById(R.id.editTextA4);
        editNewAns=(EditText) findViewById(R.id.editTextAns);
        btnfinish= (Button)findViewById(R.id.finish_btn);
        addQuestion= (Button)findViewById(R.id.add_question_btn);

        // The following Bundle takes in messages from Intents
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            newTestName = extras.getString("keyNewTestName");
            //This will be zero on the first cycle as there is no Intent from AdminEnd Activity
            // on the first cycle and will be used with the 'if 'else' statement below.
            returnedQuestionNumber = extras.getInt("QUESTION_COUNT");

            //If it is on the second or above cycle then there will be Intent data returned from the AdminEnd Activity so the
            // returnedQuestionNumberthe will be greater than '0' and the 'if' part of below executes. This sets
            // newQuestionNumber = returnedQuestionNumber, where returnedQuestionNumber has been incremented from the last cycle.
            // newQuestionName = ("q" + newQuestionNumber) to give a new question name 'qx' where x is the cycle number.
            // This is given to the FirebaseDatabase reference to create the next question name 'qx'.
            // Finally   QuestNo becomes ("Q" + newQuestionNumber) to display the current question number in the layout.
            if (returnedQuestionNumber != 0) {
                newQuestionNumber = returnedQuestionNumber;
                newQuestionName = ("q" + newQuestionNumber);
                myNewTestDatabase = FirebaseDatabase.getInstance().getReference().child(newTestName).child(newQuestionName);
                Toast.makeText(NewQuestionsCreate.this, " QNumber =" + newQuestionName, Toast.LENGTH_SHORT).show();
                QuestNo.setText("Q" + newQuestionNumber);
                //If it is the first cycle then there will be no Intent return from the AdminEnd Activity so the 'else'
                // part of this is active and the question number displayed is "Q1". Also the FirebaseDatabase reference
                // is given the name of the Test, which came from the Admin Activity Intent, as the child name for the
                // next level (or child name) first from the database root. Also the  question name will be 'q1' as initialised
                // at setup, and this will be the next level (or child name)in the database.
            }else{
                myNewTestDatabase = FirebaseDatabase.getInstance().getReference().child(newTestName).child(newQuestionName);
                QuestNo.setText("Q1");
            }
        }
        // The following sets the Test Title in the 'testTitle' ViewText
        testTitle.setText("Test Title - " + newTestName);
        // The following sets an onClick Listener  on the Finish Button and when pressed
        // an Intent is activated to activate the QuestionsEnd activity.
        btnfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: ---------------NewQuestionsCreate - FINISHED CLICKED!!!!-----------");
                Intent intent = new Intent(NewQuestionsCreate.this, QuestionsEnd.class);
                startActivity(intent);
            }
        });
        // The following sets an ocClick listener on the 'AddQuestion' button and when pressed
        // the data entered into the question fields on the layout is captured in the relevant strings.
        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: ---------------ADD QUESTION CLICKED!!!!!-----------");
                String newQuestion = editNewQuestion.getText().toString().trim();
                String newA1 = editNewA1.getText().toString().trim();
                String newA2 = editNewA2.getText().toString().trim();
                String newA3 = editNewA3.getText().toString().trim();
                String newA4 = editNewA4.getText().toString().trim();
                String newAns = editNewAns.getText().toString().trim();

                //The following checks that all EditText boxes have been filled in and if not a Toast is displayed to remind the user to do so.
                if ((newQuestion.equals("")) || (newA1.equals("")) || (newA2.equals("")) || (newA3.equals("")) || (newA4.equals("")) || (newAns.equals("")) ) {
                    Toast.makeText(NewQuestionsCreate.this, "Please Fill In All Options Above!", Toast.LENGTH_SHORT).show();
                // If all EditText boxes have been filled in then the question data is set in the Firebase database to form a question in the test
                // This is followed by incrementing the Question number and then sending an Intent to activate the 'QuestionsEnd' activity and pass the current
                // question number and test name strings in putExtras.
                }else {
                    myNewTestDatabase.child("q").setValue(newQuestion);
                    myNewTestDatabase.child("a1").setValue(newA1);
                    myNewTestDatabase.child("a2").setValue(newA2);
                    myNewTestDatabase.child("a3").setValue(newA3);
                    myNewTestDatabase.child("a4").setValue(newA4);
                    myNewTestDatabase.child("ans").setValue(newAns);
                    newQuestionNumber++;
                    Intent intent = new Intent(NewQuestionsCreate.this, QuestionsEnd.class);
                    intent.putExtra("QUESTION_NUM", newQuestionNumber);
                    intent.putExtra("TEST_NAME", newTestName);
                    startActivity(intent);
                }
            }
        });
    }
}








