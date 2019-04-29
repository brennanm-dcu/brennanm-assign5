package com.example.homer.matbrensdaproject;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.List;

public class StudentTest extends Activity {

    private DatabaseReference multiple_choice_testDatabase;         //Initialise DatabaseReference for reference to question level
    private DatabaseReference length_testDatabase;                  //Initialise DatabaseReference for reference to subject level
    private DatabaseReference myTestResultsDatabase;                //Initialise DatabaseReference for companion FirebaseDatabase to the Test Database to hold
    // the Results for this test.

    private TextView questionView;                                  // TextView reference to a textView to display a question
    private TextView questionNo;                                    // TextView reference to a textView to display  the question Number
    private Button nextButton;                                      // Button reference to a Button to get the NEXT QUESTION
    private RadioButton radioButton1;                               // RadioButton reference to a RadioButton to select a question as an answer.
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    int scoreCount =0;                                              // Int Variable to hold the score
    int questionCount =1;                                           // Int Variable to hold the current question number
    int no_of_questions =0;                                         // Int Variable to hold the total number of question for this subject
    String button_selected="0";                                     // String Variable to hold the name of the Button selected.
    String test_title ;                                 // String Variable to hold the name of subject.
    String question = "q1";                                         // String Variable to hold the name of the current question, initialised here to "q1".
    String answer;                                                  // String Variable to hold the number of the right answer to the question from the list of options.
    String Student;

    ArrayList <String> question_data_list= new ArrayList<String>();     // ArrayList to hold strings of question data such as question Text, answers, and correct answer

    // onCreate Method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_test);

        // Setting up the view elements to references
        questionView = (TextView)    findViewById(R.id.textView_question);
        questionNo   = (TextView)    findViewById(R.id.textViewQ_No);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
        nextButton   = (Button)      findViewById(R.id.next_btn);

        // The following 'if' statement initialises aspects of the activity such as
        // determining the number of questions available for this test, and using it here
        // to indicate this number to the user.
        if(question=="q1") {


            // Below- Retrieving the number of questions for this test from Shared Preferences.
            // and displaying it in TextView reference 'questNo' which displays the
            // current question number and 'of how many'.
            SharedPreferences settings;
            SharedPreferences.Editor prefEditor;
            settings = getSharedPreferences(String.valueOf("Student Data"), MODE_PRIVATE);
            test_title = settings.getString("keySelectedSubject","No Subject");

            // The following method call sends the test title, stored in 'subject', to
            // the method 'numOfQuestions' where the number of questions for this test
            // is determined and stored in sharedPreferences, where it will be used
            // in Activity NextQuestion.java later, as well as here.
            numOfQuestions(test_title);

            settings = getSharedPreferences(String.valueOf("Num Of Questions"), MODE_PRIVATE);
            no_of_questions = settings.getInt("key_numQuest", Integer.parseInt("0"));
            questionNo.setText("Q" + 1 + " of " + no_of_questions);
        }


        // The following bundle thakes in information sent with Intentions
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            scoreCount = extras.getInt("scoreCount");                          // Current ScoreCount SentBack from NextQuestion Activity
            questionCount = extras.getInt("questionCount");                    // Current questionCount SentBack from NextQuestion Activity
            question = ("q"+questionCount );                                        // Here the current questionCount is combined with "q" to produce
            // the current question reference e.g. "qx" to select the next question
            questionNo.setText("Q"+questionCount + " of " + no_of_questions);       // Displays then current question Number in textView against total number
            // questions.
        }

        // The following sets a DatabaseReference 'multiple_choice_testDatabase ' to the FireBase DataBase
        // with variables, or children, 'subject' and 'question' setting the level of the reference.
        multiple_choice_testDatabase = FirebaseDatabase.getInstance().getReference().child(test_title).child(question);

        // The first task below is to create a title for the sister database of this test which will hold
        //the results of the test for all students that will attempt it. The database name will be the 'test_title'
        // appended with "_Results", and referenced by variable 'nameOfTestResultsRecord'.
        // The myTestResultsDatabase DatabaseReference is the reference to the FireBase DataBase
        // with variable, or child, 'nameOfTestResultsRecord'.
        // This is used after the final question to upload to the Database the student name and their score.
        String nameOfTestResultsRecord = (test_title + "Results");
        myTestResultsDatabase = FirebaseDatabase.getInstance().getReference().child(nameOfTestResultsRecord).child("Student Name & Score");


        // The Following calls the FirebaseCallback method 'readQuestionData', which extracts the question data for
        // the current question from the FireBase Database and then displays it in the relevant parts of the layout.
        // The question element of the question data is displayed in the question textView and the four options,
        // from which to select an answer, are displayed beside relevant RadioButtons in the RadioGroup. Also the number
        // of the right answer in the list is held in a variable 'answer'  which will be used to determine
        // if the student selected the right answer to the question.
        readQuestionData(new FirebaseCallback() {
            @Override
            // The readQuestionData will return a List of Strings, where each string will hold question data,
            // The first four elements of the returned List will be the four answer options, the fifth element
            // will hold the list number of the correct answer.
            // The sixth element of the returned list will hold the question text.
            public void onCallback(final List<String> question_list) {

                // The following extracts the question part of the question data from the list question_data_list
                final String question_text = question_data_list.get(5);

                // The following extracts the answer parts of the question data from the list question_data_list
                // and saves the questions to the relevant 'btn' variables
                String btn1 = question_data_list.get(0);
                String btn2 = question_data_list.get(1);
                String btn3 = question_data_list.get(2);
                String btn4 = question_data_list.get(3);

                // The following extracts the number the correct answer is at in the list question_data_list
                final String answer = question_data_list.get(4);

                // The following sets the question part of the question data to the questionView TextView
                questionView.setText(question_text);

                // The following sets the answer parts of the question data into the relevant RadioButtons.
                radioButton1.setText(btn1);
                radioButton2.setText(btn2);
                radioButton3.setText(btn3);
                radioButton4.setText(btn4);

                // The following sets a listener on the RadioGroup and sets the variable 'button_selected'
                // to the number of the RadioButton Selected
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);


                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup arg0, int id) {
                        switch (id) {
                            case -1:
                                button_selected = "0";
                                break;
                            case R.id.radioButton1:
                                button_selected = "1";
                                break;
                            case R.id.radioButton2:
                                button_selected = "2";
                                break;
                            case R.id.radioButton3:
                                button_selected = "3";
                                break;
                            case R.id.radioButton4:
                                button_selected = "4";
                                break;
                            default:
                                button_selected = "0";
                                break;
                        }
                    }
                });

                // The following set a Listener on the NEXT button
                // If the NEXT button is pressed and variable 'button_selected' is set to "0" indicating no RadioButtons have been
                //                      selected, then a Toast is displayed asking the user to select a RadioButton.
                // If the NEXT button is pressed and variable 'button_selected' is equal to the String 'answer' then the
                //                      variable 'score' is incremented and a Toast displayed then we move to the next question.
                // If the NEXT button is pressed and variable 'button_selected' is  NOT equal to the String 'answer' then the
                //                      variable 'score' is NOT incremented and a Toast is displayed and then we move to the next question.

                nextButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {


                        if (button_selected == "0")

                            Toast.makeText(StudentTest.this, "Please Select an Option", Toast.LENGTH_SHORT).show();
                            //else if (button_selected .equals("answer")) {
                        else if (button_selected.equalsIgnoreCase(answer)) {
                            scoreCount++;
                            questionCount++;
                            // The following 'if' statement determines if it is the last question, and if it is the student name and their score
                            // is sent to the Database.
                            if((questionCount-1)==no_of_questions){
                                SharedPreferences settings;
                                SharedPreferences.Editor prefEditor;
                                settings = getSharedPreferences(String.valueOf("Student Data"), MODE_PRIVATE);
                                Student = settings.getString("keyStudentName","No Subject");
                                myTestResultsDatabase.child(Student).setValue(scoreCount);
                            }
                            Intent intent = new Intent(StudentTest.this, EndTest.class);
                            intent .putExtra("scoreCount",scoreCount);
                            intent .putExtra("questionCount",questionCount);
                            startActivity(intent);
                            double percentage = ((scoreCount*100)/(no_of_questions));
                            Toast.makeText(StudentTest.this, " RIGHT Answer Current Score - " + percentage+"%", Toast.LENGTH_SHORT).show();

                        } else {
                            questionCount++;
                            // The following 'if' statement determines if it is the last question, and if it is the student name and their score
                            // is sent to the Database.
                            if((questionCount-1)==no_of_questions){
                                SharedPreferences settings;
                                SharedPreferences.Editor prefEditor;
                                settings = getSharedPreferences(String.valueOf("Student Data"), MODE_PRIVATE);
                                Student = settings.getString("keyStudentName","No Subject");
                                myTestResultsDatabase.child(Student).setValue(scoreCount);
                            }
                            Intent intent = new Intent(StudentTest.this, EndTest.class);
                            intent .putExtra("scoreCount",scoreCount);
                            intent .putExtra("questionCount",questionCount);
                            startActivity(intent);
                            double percentage = ((scoreCount*100)/(no_of_questions));
                            Toast.makeText(StudentTest.this, " WRONG! Answer Current Score - " + percentage+"%", Toast.LENGTH_SHORT).show();
                        }

                    }
                }); //End of  nextButton.setOnClickListener

            } // End of CallBack
        });

    }  // End of OnCreate

    //  **************  The FirebaseCallback method 'readQuestionData' ***************
    // The FirebaseCallback method 'readQuestionData', below, extracts the question data for a question
    // from the FireBase Database. Basically in the FireBase Database this Android Application has
    // it own Database which contains Multiple Choice Tests for different Subjects. Each Subject Test
    // will have a number of  multiple choice questions and each question will have its own record
    // containing all the data pertaining to that multiple choice question.
    // So each question has 'question data' made up  of six String elements. These elements are:
    // 1) Question text,
    // 2) to 5) Answer Options for the question (Four of)
    // 6) The number of the location of Right Answer in the sequence of answers.
    // All this information is used to process one question and forms the question data which may be
    // held in a list of six String Items or in the FresBase Database as the elements of a question
    // chlid of a Test Subject Child off then main Applications main Database.

    private void readQuestionData(final FirebaseCallback firebaseCallback){

        // The following adds a ValueEventListener to the DatabaseReference multiple_choice_testDatabase
        multiple_choice_testDatabase.addValueEventListener(new ValueEventListener() {
            // The following creates a DataSnapshot of the  DatabaseReference multiple_choice_testDatabase
            public void onDataChange(DataSnapshot dataSnapshot) {
                // The following cycles through the elements of the DataSnapshot and
                // for each element retrieves its key and then uses this key to extract the data
                // for the key and then adds it to List question_data_list
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    String question_element = dataSnapshot.child(key).getValue().toString();
                    question_data_list.add(question_element);
                }
                firebaseCallback.onCallback(question_data_list);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    // The FirebaseCallback method is used to accomodate asychronous nature
    // of dealing with retrieving Data from an undependable source such as over
    // internet links, where there may be time delays. On a Callback method the programme will progress by
    // a method dealing with a slow connection and when the method is ready it will update what it had to
    // do such as filling in a TextView.
    private interface FirebaseCallback {
        void onCallback(List <String> question_list);
    }

    // The following method takes in the subject title for the test and determines the number of
    // questions available, which it then stored in SharedPreferences where it will be used
    // in Activity NextQuestion.java later.
    private void numOfQuestions(String subject) {
        //The following DatabaseReference 'length_testDatabase' will reference as far as the Test title, then
        // it will add a listener and take a DataSnapshot, then use the method 'getChildrenCount' to determine the number
        // of children (questions) available and save this number in variable 'no_of_questions'.
        length_testDatabase = FirebaseDatabase.getInstance().getReference().child(subject);
        length_testDatabase.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Variable 'no_of_questions' below holds the obtained number of questions available for this Test.
                int no_of_questions = (int) dataSnapshot.getChildrenCount();

                // The following stores the variable 'num_of_questions' in SharedPreferences
                SharedPreferences settings;
                SharedPreferences.Editor prefEditor;
                settings = getSharedPreferences("Num Of Questions", MODE_PRIVATE);
                prefEditor = settings.edit();
                prefEditor.putInt("key_numQuest", no_of_questions);
                prefEditor.commit();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(StudentTest.this, "  BIG PROBLEM " , Toast.LENGTH_SHORT).show();
            }
        });

    }

}








