package com.example.ogbeoziomajnr.githubprofileviewer.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ogbeoziomajnr.githubprofileviewer.R;

/**
 * The activity for the start up screen
 */
public class WelcomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);


    }
    public void openMainView(View view) {
        Intent intent = new Intent(this, MainLayoutActivity.class);
        startActivity(intent);

    }
}
