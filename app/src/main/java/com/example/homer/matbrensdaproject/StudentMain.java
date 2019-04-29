package com.example.homer.matbrensdaproject;

/*
******************** StudentMain Activity *******************************

The StudentMain Activity starts with an Intent from the MainActivity Login with
the subject "NAME" for the test, input by the student, in the Intent extras.
This activity then takes in a student name from the user and then stores it and the
subject 'NAME" in shared preferences and then launches the StudentTest Activity with an intent.
*/
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StudentMain extends Activity {
    private Button studentBtn;          // Button reference 'studentBtn' for the Button on the layout.
    private EditText studentID;         // EditText Reference 'studentID' for the EditText box on the
    // layout which takes in the student 'name' or 'ID'.
    private String selectedSubject;    // Variable reference to the subject selected by student to be tested on.
    Button videoBtn;                   // Button reference 'videoBtn' for the Button on the layout to access a DemoVideo.

    @Override   // onCreate method
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        // The following examines the Bundle extras and retrieves the
        // subject 'NAME" stored there from the MainActivity, and holds
        // it in the variable selectedSubject.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedSubject = extras.getString("keySubject");  // Subject selected by user Sent from MainActivity
        }
        // The following sets the references to layout objects.
        studentBtn = (Button) findViewById(R.id.student_button);
        studentID = (EditText) findViewById(R.id.student_editText);
        videoBtn = (Button) findViewById(R.id.videoBnt);

        // Listener on the Video Button which linke to the Video activity to display a Demo Video
        videoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentMain.this, Video.class);
                startActivity(intent);
            }
        });

        //The following set a listener on the Button 'studentBtn'
        studentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            // The Button should be pressed after the student entered a student Name or ID
            public void onClick(View v) {
                // After the Button was clicked the studentID EditText reference is read for input
                // stored in variable 'studentInputID'.
                String studentInputID =(studentID.getText().toString());

                // Checks if there is NO input and the Button has been Pressed
                // Then a TOAST is displayed reminding the user to provide input.
                if ((studentInputID .equals(""))) {
                    Toast.makeText(StudentMain.this, "Please Enter Your Name or Student ID!", Toast.LENGTH_LONG).show();
                // If input data is present then the Activity StudentTest is activated with an Intent.
                // Also the student name in variable 'studentInputID' and the test subject name in 'selectedSubject'
                // are stored in sharedPreferences.
                }else {
                    Intent intent = new Intent(StudentMain.this, StudentTest.class); // Intent
                    SharedPreferences settings;
                    SharedPreferences.Editor prefEditor;
                    settings = getSharedPreferences("Student Data", MODE_PRIVATE);
                    prefEditor = settings.edit();
                    prefEditor.putString("keyStudentName", studentInputID);
                    prefEditor.putString("keySelectedSubject", selectedSubject);
                    prefEditor.commit();
                    startActivity(intent);
                }
            }
        });
    }
}
