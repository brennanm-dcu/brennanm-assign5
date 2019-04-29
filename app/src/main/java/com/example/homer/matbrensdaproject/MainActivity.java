package com.example.homer.matbrensdaproject;


/*
****************************** Matthew Brennan - SDA PROJECT - Student No -17112702 *****************************
*
                                                   PROJECT PROPOSAL

I work in a training environment, lecturing in Communications, IT and Electronic Engineering to Degree Level, with college students and with students
on other courses.
We have the following reoccurring requirements:
•	The need to initially evaluate students’ needs and suitability for a course.
•	To do quick assessments to monitor students progress.
•	To conduct simple continuous assessment tests.
Most of these are of a brief nature and may simply consist of a short set of questions, multiple choice questions or to identify features from images.
These are not full blown 3 hour exams but quick assessments to determine the state of a student’s knowledge.
These tests have been paper based and consumed time to administer and run. A computer based approach is a solution but availability of these recourse
can be an issue as computer rooms are constantly in use.

The Aim

The aim of my SDA project is to design a smart phone app that can deliver, to a group of students, a simple set of multiplechoice questions that when answered, a score
is returned for each student, and a list of the class scores can be accessed from the app.
This takes advantage of the widespread use of android smart devices, also for students with iphones or other non android platforms, the institution could have a number
of android smart phones, or tablets, in stock for loan to students to do the assessments. It is much more convenient to have a small box of smart devices on a shelf in
the stores, than providing a whole computer room.

The Application:

The Application would allow the instructor to quickly produce a set of simple multiple choice questions with short answers.
Then convey tests to the students via a download from a Firebase Database.
Then students will then open the questions on the app and when all questions are answered, the results will automatically calculated and sent to the Database,
where they can be accessed by the app.

The application will use of the following Android API classes:
1.	Multimedia graphics
2. Firebase Database – to store questions.


 ***************************************************************************************************************

                                                       APPLICATION OPERATION
Normal User(student) access:
A normal User will be given a Login which is the Name or Number of the Test they are to undertake, and a password. On successful access
they will be directed to the Student section of the application where they will be required to input their Name or Student ID. When this is completed, and
the continue Button pressed, the app will download the questions for the respective test from the Firebase Database and begin to
display a series of views each showing one question. Each question contains a question and four possible answers.
The student selects the Radio Button beside the option they think is the right answer and then press the 'Next Question' Button. Thet do this until they
reach the last question. When finished the results will automatically be sent to the Firebase Database.
PARTICULARS: 1. The app monitors inputs such as names and passwords and that each question have been answered, if not! Toasts appear indicating that something needs
                to be completed.
             2. After each question the result will be displayed in a Toast and then precede to the next question,
             3. The initial login using subject Name or ID is not case sensitive but the app converts all inputs for subject name to capitals as all subject names on the
                Firebase Database are in capitals and Firebase is case sensitive. This avoids problems with case sensitivity.
             4. The login passowrd IS case sensitive for extra security.
             5. Also there is a maxium number of login attempts allowed after which the login button will disable.
Admin Access:
The Administrator, or the lecturer, will have an Admin Logon, which is 'Admin', and an Admin password, this will take them to the admin section of the app. Here they can access
 a number of facilities. One is for the production of multiple choice tests, another allows for the viewing of a set of results from a particular test, and also there is a facility
 to change user and admin passwords.

Results - Each right question gets one mark. After the question the score count is incremented for each right answer and at the end of the test the final score, along with the
          student name is sent to the Firebase Database.
*/

//                          The Main Activity performs the 'Front Door' to the application and requires a Login String and a Password for access.

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference passWordDatabase;  //Initialise DatabaseReference for reference the ADMIN area where passwords are stored.
    private EditText loginInput;                  // EditText reference for the EditText box on the layout which receives the users login input string
    private EditText passwordInput;                   // EditText reference for the EditText box on the layout which receives the users login password string
    private TextView Remaining_Attempts;         // TextView reference to a TextView which displays the number of remaining attempts to login.
    private Button LoginBtn;                     // Reference to a Button on the layout for logging in.
    private int attempts =5;                     // This int sets the number of attempts for logging in allowed(Currently set to 5).
    private  String userPassWord="000";
    private String adminPassWord="000";


    @Override       // onCreat method
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // The first task here is to query the Firebase Databbase for login passwords.
        // The following sets a DatabaseReference 'passWordDatabase' to the FireBase DataBase
        // with child, 'ADMIN'  setting the level of the reference to the area where
        // the passwords are stored.
        // A childEventListener is then placed on the reference and a
        // dataSnapshot is taken and the password data stored with keys
        // 'userPassword' and 'adminPassword' got and held in variables 'userPassaord'
        // and 'adminPassword'
        passWordDatabase = FirebaseDatabase.getInstance().getReference().child("ADMIN");
        passWordDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                   userPassWord = dataSnapshot.child("UserPassword").getValue().toString();
                   adminPassWord = dataSnapshot.child("AdminPassword").getValue().toString();
                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        // The following sets the references to layout objects.
        loginInput = (EditText)findViewById(R.id.login_name);
        passwordInput = (EditText)findViewById(R.id.login_password);
        Remaining_Attempts = (TextView) findViewById(R.id.login_attempts_remaining);
        LoginBtn = (Button) findViewById(R.id.login_button);

        //The following sets text in the Remaining Attempts TextView.
        Remaining_Attempts.setText("   Remaining is  5");

        // The following sets an onClick Listener on the Login Button.
        // When clicked a validate method is called and passed the user input 'loginInput' and 'passwordInput'
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(loginInput.getText().toString(), passwordInput.getText().toString());
            }
        });
    }// End of OnCreate

    // The validate method below compares the user input data with the correct data and allows access if they match.
    // The users Admin and User whill be directed to their respective Activities on correct login.
    private void validate (String uInput, String uPassword){
        // The following converts the string 'uInput', repersenting a subject to be tested on, or the Admin user,
        // to uppercase, making the input here not case sensitive and allowing flexibility for the user input.
        // Also IMPORTANT as all subjects titles will be repersented in Uppercase in the Database
        uInput = uInput.toUpperCase();
        // Admin Login: if the uInput = ADMIN and with the correct admin password
        if ((uInput .equals("ADMIN")) && (uPassword .equals(adminPassWord))) {
            // with correct login the ADMIN is directed to the admin section of the app.
            Intent intent = new Intent(MainActivity.this, AdminMain.class);
            startActivity(intent);
        }
        // User Login : If uInput is not 'ADMIN' then it repersents a subject 'NAME' to be tested on,
        // in capitals, and only the uPassword is validated to allow sanctioned students assess the test area.
        // If successful the student is directed to the student section of the app and also the
        // subject NAME is sent with the Intent.
        else if (uPassword .equals(userPassWord)){
             Intent intent = new Intent(MainActivity.this, StudentMain.class);
             intent .putExtra("keySubject",uInput);
             startActivity(intent);
        }
        // If the input data not validated or there is no data input then on each click of the Button
        // the variable 'attempts' holding the allowable number of attempts is decremented.
        // if the maximum number of attempts is exceeded then the Login Button is disabled.
        else {
            attempts --;
            Remaining_Attempts.setText("   Remaining is " + attempts);
            Toast.makeText(MainActivity.this, " New Password = " +userPassWord , Toast.LENGTH_LONG).show();
            if(attempts==0){
                LoginBtn.setEnabled(false);
                Toast.makeText(MainActivity.this, "LOGIN Disabled! Load App Again", Toast.LENGTH_LONG).show();
            }
        }
    }
}