package com.example.ogbeoziomajnr.githubprofileviewer.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ogbeoziomajnr.githubprofileviewer.AppController;
import com.example.ogbeoziomajnr.githubprofileviewer.HttpRequestHelper;
import com.example.ogbeoziomajnr.githubprofileviewer.ListViewAdapter;
import com.example.ogbeoziomajnr.githubprofileviewer.R;
import com.example.ogbeoziomajnr.githubprofileviewer.UserProfile;
import com.example.ogbeoziomajnr.githubprofileviewer.Utility;

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
    private HttpRequestHelper requestHelper;

    private static String TAG = MainLayoutActivity.class.getSimpleName();
    // private ProgressDialog pDialog;
    ArrayList<UserProfile> items;
    View footerView;

    //list parameters for pagination
    private int number_of_items = 0; // number of items in result
    private int number_items_remaining = 0; // the number of items remaining after paginating the result
    private int number_of_pages = 0;
    private boolean first_request = false;

    Utility utility;


    // fragment manager to be used in the fragment to display the profile dialog
    private FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the layout for this activity
        setContentView(R.layout.main_layout);


        utility = new Utility(this);
        requestHelper = new HttpRequestHelper(this);

        // get all the views related to this activity
        list_view = (ListView) findViewById(R.id.list);
        Button list_users_button = (Button) findViewById(R.id.btn_list_users);

        // the collection that would store the list of user profile
        items = new ArrayList<>();


        View footerView = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer, null, false);
        list_view.addFooterView(footerView);
        // get the adapter the we created and set it as the list view adapter
        adapter = new ListViewAdapter(this, items);
        list_view.setAdapter(adapter);

        TextView txt_load_more = (TextView) findViewById(R.id.textViewFooter);

        txt_load_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://api.github.com/search/users?q=location:lagos+language:java&page="+number_of_pages;
                first_request = false;
                if (number_items_remaining > 0) {
                   makeJsonArrayRequest(getApplicationContext(), url);
                }
                else {
                    utility.showShortToast("No more items to load");
                }
            }
        });


        //actions to be performed when a list view item is clicked
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // get the UserProfile that was clicked using the position
                UserProfile user_profile_clicked = (UserProfile) parent.getItemAtPosition(position);

                //get the Profile fragment using the factory initializer and show the dialog
                // the UserProfile object that is clicked is passed to the fragemnt
                ProfileFragment pf = ProfileFragment.newInstance(user_profile_clicked);
                pf.show(fm, "Dialog Fragment");
            }
        });

        list_users_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String url = "https://api.github.com/search/users?q=location:lagos+language:java";
                first_request = true;
                items = new ArrayList<UserProfile>();
                 makeJsonArrayRequest(getApplicationContext(), url);
                number_of_pages = 1;

            }
        });

    }

    private int makeJsonArrayRequest(final Context context, String url) {
        utility.showprogressDialog();
        final int[] count = {0};
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG, response.toString());

                        try {

                             count[0] = Integer.parseInt(response.getString("total_count"));
                            if (first_request) {
                                number_of_items = count[0];
                            }
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

                            number_items_remaining = number_of_items - 30*number_of_pages;
                            number_of_pages++;

                        } catch (JSONException e) {
                            e.printStackTrace();
                            utility.showLongToast("Error: " + e.getMessage());
                        } catch (Exception e) {

                        }
                        utility.hideprogressDialog();
                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println("The class of response is " + error.networkResponse);
                error.printStackTrace();
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                utility.showLongToast(error.getMessage());
                utility.hideprogressDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
        return count[0];

    }
}

