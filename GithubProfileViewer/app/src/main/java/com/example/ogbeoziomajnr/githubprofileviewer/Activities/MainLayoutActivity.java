package com.example.ogbeoziomajnr.githubprofileviewer.Activities;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ogbeoziomajnr.githubprofileviewer.AppController;
import com.example.ogbeoziomajnr.githubprofileviewer.ListViewAdapter;
import com.example.ogbeoziomajnr.githubprofileviewer.R;
import com.example.ogbeoziomajnr.githubprofileviewer.UserProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import layout.ProfileFragment;

/**
 * The activity for the mani view of the app that contains the list view of user profiles
 */
public class MainLayoutActivity extends AppCompatActivity {
    //Declare global Variable
    private ListView list_view;
    private ListViewAdapter adapter;
    // private HttpRequestHelper requestHelper = new HttpRequestHelper();
    private String url = "https://api.github.com/search/users?q=location:lagos+language:java";
    private static String TAG = MainLayoutActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    ArrayList<UserProfile> items;


    // temporary string to show the parsed response
    private String jsonResponse;


    // fragment manager to be used in the fragment to display the profile dialog
    private FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the layout for this activity
        setContentView(R.layout.main_layout);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        // get all the views related to this activity
        list_view = (ListView) findViewById(R.id.list);
        Button list_users_button = (Button) findViewById(R.id.btn_list_users);

        // the collection that would store the list of user profile
        items = new ArrayList<>();


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

        list_users_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeJsonArrayRequest(getApplicationContext());
            }
        });

    }

    private void makeJsonArrayRequest(final Context context) {
        showpDialog();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG, response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object

                            jsonResponse = "";
                            items = new ArrayList<>();


                            JSONArray response_array = response.getJSONArray("items");

                            for (int i = 0; i < response_array.length(); i++) {

                                JSONObject person = (JSONObject) response_array
                                        .get(i);
                                UserProfile profile = new UserProfile();
                                profile.setUser_name(person.getString("login"));
                                profile.setUser_url(person.getString("html_url"));
                                profile.setImage_url(person.getString("avatar_url"));

                                items.add(profile);

                            }
                            adapter = new ListViewAdapter(context, items);
                            list_view.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        } catch (Exception e) {

                        }
                        hidepDialog();
                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println("The class of response is " + error.networkResponse);
                error.printStackTrace();
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}

