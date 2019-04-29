package com.example.homer.matbrensdaproject;
import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;
//***************************** **** Video Activity ********************************************
/**
 * The Video Activity provides a basic Multimedia element to the project in an instructional video
 * on how to answer multiple choice questions. This is very basic but due to the size of video file
 * allowed by android in the 'raw' directory, this was all I could afford. I had intended to use a full
 * instructional video for the whole app.
 */
public class Video extends Activity {

    @Override  // onCreate Method
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        // The following sets the VideoView references 'videoView to to layout VideoView object  video_view.
        // Then a String videoPath sets reference to where the video clip is in the 'raw' directory.
        // ure is set to the URI  video path
        // Also MediaController is added
        VideoView videoView = findViewById(R.id.video_view);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.demo_quiz;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
    }
}
