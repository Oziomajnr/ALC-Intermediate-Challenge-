package com.example.ogbeoziomajnr.githubprofileviewer.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ogbeoziomajnr.githubprofileviewer.ListViewAdapter;
import com.example.ogbeoziomajnr.githubprofileviewer.R;
import com.example.ogbeoziomajnr.githubprofileviewer.UserProfile;

import java.util.ArrayList;

import layout.ProfileFragment;

/**
 * The activity for the mani view of the app that contains the list view of user profiles
 */
public class MainLayoutActivity extends AppCompatActivity {
    //Declare global Variable
    private ListView list_view;
    private ListViewAdapter adapter;
    // fragment manager to be used in the fragment to display the profile dialog
    private FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the layout for this activity
        setContentView(R.layout.main_layout);

        // get all the views related to this activity
        ListView list_view = (ListView) findViewById(R.id.list);

        // the collection that would store the list of user profile
        ArrayList<UserProfile> items = new ArrayList<>();

        //////////////////////////////////////////////////////////////////////////////////////
        // this is our dummy data for testing the functionality of the listview
        UserProfile userProfile = new UserProfile();
        userProfile.setUser_name("oziomajnr");
        userProfile.setUser_url("https://avatars1.githubusercontent.com/u/8110201?v=3");
        userProfile.setImage_url("https://avatars1.githubusercontent.com/u/8110201?v=3");
        //////////////////////////////////////////////////////////////////////////////////////


        items.add(userProfile);

        // get the adapter the we created and set it as the list view adapter
        adapter = new ListViewAdapter(this, items);
        list_view.setAdapter(adapter);

        //actions to be performed when a list view item is clicked
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // get the UserProfile that was clicked using the position
                UserProfile user_profile_clicked = (UserProfile) parent.getItemAtPosition(position);

                //get the Profile fragment using the factory initializer and show the dialog
                // the UserProfile object that is clicked is passsed to the fragemnt
                ProfileFragment pf = ProfileFragment.newInstance(user_profile_clicked);
                pf.show(fm, "Dialog Fragment");
            }
        });

    }

}
