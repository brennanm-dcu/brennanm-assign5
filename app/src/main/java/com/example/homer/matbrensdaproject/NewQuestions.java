package com.example.homer.matbrensdaproject;

/*
   - Welcome to NewQuestions this is where Questions are created and stored in a Database in FireBase. -
            This first page queries the user for a Name for the Test and then sends
                     it with an Intent to the NewQuestionsCreate Activity.
 */
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewQuestions extends Activity {

    private Button creatNewTestBtn;
    private EditText new_test_name_input;
    private String newTestName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_questions);
        //The following creates references to layout elements
        new_test_name_input=(EditText) findViewById(R.id.new_test_name_input_EitText);
        creatNewTestBtn = (Button)findViewById(R.id.new_testBtn);

        // The following puts a listener on the creatNewTestBtn and when it is clicked
        // it checks if text has been typed into the 'new_test_name_input' EditText box and if not
        // a Toast is displayed reminding the user to provide a name for the new test.
        // If a name is provided then an Intent is activated to the NewQuestionsCreate activity
        // with the name of the new Test sent with the Intent.
        creatNewTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTestName = new_test_name_input.getText().toString();
                // The following ensures the New Test Name is in capitals as this is the convention for the Database
                newTestName = newTestName.toUpperCase();
                if (newTestName .equals("")) {
                    Toast.makeText(NewQuestions.this, "Please Provide a New Test Name!!" + newTestName, Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(NewQuestions.this, NewQuestionsCreate.class);
                    intent.putExtra("keyNewTestName", newTestName);
                    startActivity(intent);
                }
            }
        });
    }
}
