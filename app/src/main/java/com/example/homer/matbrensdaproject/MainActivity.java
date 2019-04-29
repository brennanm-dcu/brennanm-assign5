package com.example.homer.matbrensdaproject;


/*
****************************** Matthew Brennan - SDA PROJECT - Student No -17112702 *****************************
*
                                             PROJECT PROPOSAL

I work in a training environment, lecturing in Communications, IT and Electronic Engineering to Degree Level with technical students.
We have following reoccurring requirements:
•	The need to initially evaluate students’ needs and suitability.
•	To do quick assessments to monitor students progress.
•	To conduct simple continuous assessment tests.
Most of these are of a brief nature and may simply consist of a short set of questions, multiple choice questions or to identify features from images.
These are not full blown 3 hour exams but quick assessments to determine the state of a student’s knowledge at a particular time.
These tests have been paper based and consumed time to administer and run. A computer based approach is a solution but availability of these recourse
 can be an issue as computer rooms are scarce resource.

The Aim

The aim of my SDA project is to design a smart phone app that can deliver to a group of students a simple set of questions that when answered a score is returned.
This takes advantage of the widespread use of android smart devices, also for students with iphones or other non android platforms, the institution could have a number
of android smart phones, or tablets, in stock for loan to students to do the assessments. It is much more convenient to have a small box of smart devices on a shelf in
the stores, than providing a whole computer room.

The Application:

The Application would allow the instructor to quickly produce a set of simple multiple choice questions with short answers.
Then convey the tests to the students via a download from a Firebase Database.
Then students will then open the questions on the app and when finished the results will automatically calculated and sent to the Database.

The application will use of the following Android API classes:
1.	Multimedia graphics
2.   Database – to access stored questions.

 */
// ***************************************************************************************************************


//                  The Main Activity performs the 'Front Door' to the application and requires a Login String and a Password for access.
//  Normal User;
//   A normal User will have a Login which is the Name or Number of the Test they are to undertake, and a provided password. On successful access
//   they will be directed to the downloaded questions from the Firebase Database to begin. When finished then results will automatically be sent back
//  to the Firebase Database.
// Admin Access:
// The Administrator or the lecturer will have an Admin Logon which will take them to a facility which will allow the production of multiple choice
//questions which when completed are sent to the Firebase Database for access by students. There is also a facility for the administrator to download all
// scores returned from then students after taking the tests.

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText userInput;               // EditText reference for the EditText box on the layout which receives the users login input string
    private EditText Password;                // EditText reference for the EditText box on the layout which receives the users login password string
    private TextView Remaining_Attempts;      // TextView reference to a TextView which displays the number of remaining attempts to login.
    private Button LoginBtn;                  // Reference to a Button on the layout for logging in.
    private int attempts =5;                  // This int sets the number of attempts for logging in allowed(Currently set to 5).
    private String uInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // The following sets the references to layout objects.
        userInput = (EditText)findViewById(R.id.login_name);
        Password = (EditText)findViewById(R.id.login_password);
        Remaining_Attempts = (TextView) findViewById(R.id.login_attempts_remaining);
        LoginBtn = (Button) findViewById(R.id.login_button);

        //The following sets text in the Remaining Attempts Text View.
        Remaining_Attempts.setText("   Remaining is  5");

        //The following sets an onClick Listener on the Login Button.
        //When clicked a validate method is called and passed the input 'name' and 'password'
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(userInput.getText().toString(), Password.getText().toString());
            }
        });
    }// End of OnCreate

    // The validate method below compares the input data with the correct data and allows access if they match.
    // Admin and User whill be directed to their respective Activities on correct login.
    private void validate (String uInput, String uPassword){
        // The following converts the string to uppercase, allowing flexibility for the user input.
        // Also IMPORTANT as all subjects will be titles in Uppercase in the Database
        uInput = uInput.toUpperCase(); //Admin Login
        if ((uInput .equals("ADMIN")) && (uPassword .equals("1234"))) {
             //  Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            //  startActivity(intent);
        }
        //The following converts the string to uppercase,
        //User Login
        else if (uPassword .equals("111")){
             Intent intent = new Intent(MainActivity.this, StudentMain.class);
             intent .putExtra("keySubject",uInput);
             startActivity(intent);
        }
        //Exceeded allowed number of logins
        else {
            attempts --;
            Remaining_Attempts.setText("   Remaining is " + attempts);

            if(attempts==0){
                LoginBtn.setEnabled(false);
                Toast.makeText(MainActivity.this, "LOGIN Disabled! Load App Again", Toast.LENGTH_LONG).show();
            }
        }
    }
}