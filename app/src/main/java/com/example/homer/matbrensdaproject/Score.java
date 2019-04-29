package com.example.homer.matbrensdaproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Score extends Activity {

    private DatabaseReference scoreDatabase;              //Initialise FirebaseDatabase Reference for reference to where to store data in the FireBase Database
    private ListView scoreList;
    private DatabaseReference length_testDatabase;

    private ArrayList <String> studentName= new ArrayList<>();

    String subject_title ;
    String subjectResults;
    int no_of_questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            subject_title = extras.getString("SUBJECT_TITLE_FOR_SCORE");           // Current ScoreCount SentBack from EndTest Activity
            // The following puts the subject_title string to UPPER CASE as the all subject Titles in the Firebase are upper case
            // and this makes makes user input NOT case sensitive, for convenience.
            subject_title = subject_title.toUpperCase();
            Toast.makeText(Score.this, "Subject Name is-" + subject_title, Toast.LENGTH_LONG).show();
        }



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


        subjectResults = (subject_title+"Results");


        scoreDatabase = FirebaseDatabase.getInstance().getReference().child(subjectResults);
        scoreList=(ListView)findViewById(R.id.score_list);

        final ArrayAdapter  <String>  arrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, studentName);

        scoreList.setAdapter(arrayAdapter);



        scoreDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {



                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    String question_element = dataSnapshot.child(key).getValue().toString();
                    int question_elementxx= Integer.valueOf(question_element);
                    double percentage = ((question_elementxx*100)/(no_of_questions));

                    studentName.add(key +"  scored - "+question_element+ " from "+no_of_questions + " Question(s)  = "  + percentage + "  % ");
                    arrayAdapter.notifyDataSetChanged();


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

    }

}
