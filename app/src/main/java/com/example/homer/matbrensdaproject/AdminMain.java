package com.example.homer.matbrensdaproject;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
// ******************** AdminMain Activity *******************************
/**
 The AdminMain Activity starts with an Intent from the MainActivity Login when an
 administrator Logs in.
 This activity presents to the administrator 3 facilities;
 1) To create a New Test
 2) To Retrieve the scores from a completed Test.
 3) To administrate Passwords.
 */
public class AdminMain extends Activity {

    private DatabaseReference passWordDatabase;             //Initialise DatabaseReference for reference the ADMIN area where passwords are stored.
    public Button getScoreBtn;                              //Button reference 'getScoreBtn' to the  button which returns the scores of a completed Test.
    public Button createTest;                               //Button reference 'creatTest' to the  button which takes the administrator to the activity which creates a new test.
    public Button changePasswordBtn;                        //Button reference 'changePasswordBtn' to the  button which executes the changing of a password.
    private RadioButton adminRadioButton;                   //RadioButton reference 'adminRadioButton' to a RadioButton to focus on Admin in relation to password facilities.
    private RadioButton studentRadioRutton;                 //RadioButton reference 'studentRadioRutton' to a RadioButton to focus on student in relation to password facilities.
    public EditText subject_title_input;                    //EditView reference to an EditView on the layout to take in the subject title in order to retrieve it scores.
    public EditText changePasswordInput;                    //EditView reference to an EditView on the layout to take in a new Password.
    String button_selected="0";                             //String to hold which bottom is selected
    private TextView currentPassword;                       //TextView which shows then current password.
    private  String userPassWord="000";                     //String to hold the user Password
    private String adminPassWord="000";                      //String to hold the admin Password
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        // The following sets the references to layout objects.
        createTest = (Button) findViewById(R.id.create_test_btn);
        getScoreBtn = (Button) findViewById(R.id.get_scoreBtn);
        adminRadioButton = (RadioButton) findViewById(R.id.admin_radioBtn);
        studentRadioRutton = (RadioButton) findViewById(R.id.student_radioBtn);
        currentPassword =(TextView)findViewById(R.id.current_password_textView);
        changePasswordInput = (EditText)findViewById(R.id.changePassword_editText);
        changePasswordBtn=(Button)findViewById(R.id.commit_newPassword_btn);
        subject_title_input =(EditText)findViewById(R.id.enter_subject_for_Scores_editView);

        // The first task here is to query the Firebase Databbase for login passwords.
        // The following sets a DatabaseReference 'passWordDatabase' to the FireBase DataBase
        // with child, 'ADMIN'  setting the level of the reference to the area where
        // the passwords are stored.
        // A childEventListener is then placed on the reference and a
        // dataSnapshot is taken and the password data stored with keys
        // 'userPassword' and 'adminPassword' got and held in variables 'userPassaord'
        // and 'adminPassword'

        //****************************** CREATE a New Test ****************************
        // The following sets an onClick Listener on the 'create_testBtn' Button.
        // When clicked the method 'createTest()' is called.
        createTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMain.this, NewQuestions.class);
                startActivity(intent);
            }
        });
        //********************************* Get Score of Test ************************************
        // The following sets an onClick Listener on the 'get_scoreBtn' Button.
        // When clicked the method 'score()' is called.
        getScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score();
            }
        });
        //********************************* Setting a Listener on the RadioGroup ****************
        // The following sets a listener on the RadioGroup and sets the variable 'button_selected'
        // to the number of the RadioButton Selected
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.password_radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup arg0, int id) {
                switch (id) {
                    case -1:
                        button_selected = "0";
                        break;
                    case R.id.admin_radioBtn:
                        button_selected = "1";
                        break;
                    case R.id.student_radioBtn:
                        button_selected = "2";
                        break;
                    default:
                        button_selected = "0";
                        break;
                }
                if (button_selected.equalsIgnoreCase("1")){
                    adminPassWord = getPassword("admin");
                    currentPassword.setText("Current Admin Password Is : " + adminPassWord );
                }

                //else if (button_selected .equals("answer")) {
                if (button_selected.equalsIgnoreCase("2")) {
                    adminPassWord = getPassword("user");
                    currentPassword.setText("Current Student Password Is : " + userPassWord );
                }
            }
        });
        //********************************* Setting a Listener on the change password Button ******
        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String passwordInput= changePasswordInput.getText().toString();
                if (passwordInput .equals("")){
                    Toast.makeText(AdminMain.this, "Please Enter a New Password!", Toast.LENGTH_LONG).show();
                }else{
                    if (button_selected.equalsIgnoreCase("1")){
                        passwordInput = passwordInput.toUpperCase();
                        setPassword("AdminPassword", passwordInput);
                        Toast.makeText(AdminMain.this, "ADMIN password is set to ' " +passwordInput+ " and Capitalised!", Toast.LENGTH_LONG).show();
                    }
                    if (button_selected.equalsIgnoreCase("2")){
                        setPassword("UserPassword", passwordInput);
                        Toast.makeText(AdminMain.this, "User Password is set to ' " +passwordInput, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    } //End of onCreate
    //*********************************SCORE METHOD **************************************
    /**
     *   When called the score() method first checks the 'edit_subject_for_score' Edit box for input,
     *   and if present an Intent is activated to activate the Score activity and
     *   pass the input Subject NAME in 'subject_title' in a putExtra. If no input is present then
     *   a Toast id displayed to indicate to the user to input a Subject.
     */
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
    // *********************************Get Password Method*********************************
    /**
     * The following extracts the current passwords for Admin and User
     * @param passwordType String variable holding who the password refers to, Admin or a Student.
    */
    public String getPassword(String passwordType){
        String returnValue = null;
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
        if(passwordType .equals("admin")){
            returnValue = adminPassWord;
        }
        if(passwordType .equals("user")){
            returnValue =userPassWord;
        }
        return returnValue;
    }
    // **************************** Set New Password ********************************************
    /**
     * The following sets the new password in the Firebase database for either then'AdminPassword' or the 'UsertPassword'
     * @param passwordType String variable holding who the password refers to, Admin or a Student.
     * @param newPassword String holding the new replacement password.
     */
    public void setPassword(String passwordType, String newPassword){
        passWordDatabase = FirebaseDatabase.getInstance().getReference().child("ADMIN").child("Passwords");
        passWordDatabase.child(passwordType).setValue(newPassword);
    }
}
