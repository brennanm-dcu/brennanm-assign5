package com.example.homer.matbrensdaproject;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class StudentMain extends Activity {

    private Button studentBtn;
    private EditText studentID;               // Reference to a Button on the layout for logging in.
    private String selectedSubject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedSubject = extras.getString("keySubject");  // Subject selected by user Sent from MainActivity
        }

        studentBtn=(Button)findViewById(R.id.student_button);
        studentID =(EditText)findViewById(R.id.student_editText);

        studentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentInputID =(studentID.getText().toString());

                // Checks if there is NO input and the Button has been Pressed
                // If so a TOAST is displayed.
                if ((studentInputID .equals(""))) {
                    Toast.makeText(StudentMain.this, "Please Enter Your Name or Student ID!", Toast.LENGTH_LONG).show();
                }      else {
                    Intent intent = new Intent(StudentMain.this, StudentTest.class);
                   // intent .putExtra("keySubject",selectedSubject);
                   // intent .putExtra("keyStudentID",studentInputID);
                    Toast.makeText(StudentMain.this, "Subject - " +selectedSubject + " and StudentId - " + studentInputID , Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }

            }
        });










    }

}
