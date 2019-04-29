package com.example.homer.matbrensdaproject;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
//******************** Score Activity *******************************
/**
 *  The Score Activity starts with an Intent from the AdminMain Activity containing
 * in Extras the name of the Test for which the score is required. Using this then scores for
 * the Test are retrieved from the Database and show in a ListView on the Layout.
*/
public class Score extends Activity {

    private DatabaseReference scoreDatabase;                         //Initialise FirebaseDatabase Reference for reference to where to store data in the FireBase Database
    private DatabaseReference length_testDatabase;                   //Initialise FirebaseDatabase Reference used to get the number of entries in this database.
    private ListView scoreList;                                      // ListView reference to ListView in Layout
    private ArrayList <String> studentName= new ArrayList<>();       // ArrayList used to hold scores from data base.
    private TextView scoreTextView;                                  //TextView reference which shows information on on the layout about the Test the scores are been retrieved for.
    int no_of_questions;                                             // int to hold then number of questions in the test.
    String subject_title ;                                           // String which hold the name or title of the test which scores are been retrieved for.
    String subjectResults;                                           // String which holds the Database Name where the scores are stored for the required test.
                                                                     // The Database Name of where the scores are stored for a Test, is the name of the Test with '-Results' appended at the end.
                                                                     // So subjectResults is derived from  (subject_title+"-Results");
    @Override  // onCreate method
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        // The following recovers then Test Name sent from putExtras and stores it in
        // String subject_title. This is then converted to Uppercase as all database
        // test titles are in Uppercase but usr input is not case sensitive for convenience.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            subject_title = extras.getString("SUBJECT_TITLE_FOR_SCORE");           // Current ScoreCount SentBack from EndTest Activity
            // The following puts the subject_title string to UPPER CASE as the all subject Titles in the Firebase are upper case
            // and this makes makes user input NOT case sensitive, for convenience.
            subject_title = subject_title.toUpperCase();
            Toast.makeText(Score.this, "Subject Name is-" + subject_title, Toast.LENGTH_LONG).show();
        }
        // The following sets the references to layout objects.
        scoreTextView = (TextView)findViewById(R.id.score_textView);
        // The following shows title information for the scores activity at ths top of the page with the 'Test Name'.
        scoreTextView.setText("Scores for Subject "+ subject_title);
        // The following retrieves the length of the database for the test of the number of questions in the test
        // and stores the value in variable no_of_questions.
        length_testDatabase = FirebaseDatabase.getInstance().getReference().child(subject_title);
        length_testDatabase.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Variable 'no_of_questions' below holds the obtained number of questions available for this Test.
                no_of_questions = (int) dataSnapshot.getChildrenCount();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        // The results of the Test are held in database 'Test-Results'. Thi string s created below from then test title
        // and stored in string subjectResults. This is then used as a child with Database Reference to retrieve the score.
        subjectResults = (subject_title+"-Results");
        scoreDatabase = FirebaseDatabase.getInstance().getReference().child(subjectResults);
        // The following sets the references to layout object score_list.
        scoreList=(ListView)findViewById(R.id.score_list);
        // The following sets up an array adapter and then sets the scoreList to the arrayAdapter.
        final ArrayAdapter  <String>  arrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, studentName);
        scoreList.setAdapter(arrayAdapter);
        // The following then retrieves each score from the database and sets it with its  key, which is the student name of the score.
        // This is then added to the ArrayList 'studentName' with score in % form also.
        scoreDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    String question_element = dataSnapshot.child(key).getValue().toString();
                    int question_elementxx= Integer.valueOf(question_element);
                    double percentage = ((question_elementxx*100)/(no_of_questions));

                    studentName.add(key +"  - "+question_element+ " From "+no_of_questions + " Qns Right  = "  + percentage + "  % ");
                    arrayAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}
