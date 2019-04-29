package com.example.homer.matbrensdaproject;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class QuestionsEnd extends Activity {


    TextView endTextView;
    int newQuestionNumber = 1;
    String newTestName =" BAAA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_end);

        Bundle extras = getIntent().getExtras();
        if(extras !=null){
            newQuestionNumber =extras.getInt("QUESTION_NUM");
            newTestName =extras.getString("TEST_NAME");


            Intent intent = new Intent(QuestionsEnd.this, NewQuestionsCreate.class);
            intent .putExtra("QUESTION_COUNT",newQuestionNumber);
            intent .putExtra("keyNewTestName",newTestName);
            startActivity(intent);

        }




      //  endTextView = (TextView)findViewById(R.id.textViewEnd);
      //  endTextView.setText( "THE END FOLKS!!!- ");




    }

}
