package com.example.homer.matbrensdaproject;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AdminMain extends Activity {

    public Button creatTest;
    public Button getScoreBtn;
    public EditText subject_title_input;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);


        // The following sets the references to layout objects.
        creatTest = (Button) findViewById(R.id.create_test_btn);
        getScoreBtn = (Button) findViewById(R.id.get_scoreBtn);
        subject_title_input =(EditText)findViewById(R.id.enter_subject_for_Scores_editView);




        // The following sets an onClick Listener on the 'create_testBtn' Button.
        // When clicked the method 'createTest()' is called.
        creatTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMain.this, NewQuestions.class);
                startActivity(intent);
            }
        });

        // The following sets an onClick Listener on the 'get_scoreBtn' Button.
        // When clicked the method 'score()' is called.
        getScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score();
            }
        });



    } //End of onCreate


    // When called the score() method first checks the 'edit_subject_for_score' Edit box for input,
    // and if present an Intent is activated to activate the Score activity and
    // pass the input Subject NAME in 'subject_title' in a putExtra. If no input is present then
    // a Toast id displayed to indicate to the user to input a Subject.
     public void score(){
        //The following retrieves any input in the EditText
        String subject_title =subject_title_input.getText().toString();
        // If there is NO input then a Toast in generated.
        if (subject_title .equals("")) {
            Toast.makeText(AdminMain.this, "Please Enter a Subject Title-", Toast.LENGTH_LONG).show();
        // If input is present then it is sent to the Score activity with an Intent
        }else{
            Intent intent = new Intent(AdminMain.this, Score.class);
            intent .putExtra("SUBJECT_TITLE_FOR_SCORE",subject_title);
            startActivity(intent);
        }
    }
}
