package com.example.ogbeoziomajnr.githubprofileviewer;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by SQ-OGBE PC on 10/03/2017.
 * This class helps us make http request using unirest library
 */

public class HttpRequestHelper extends Application {

    private  Context context;
    private Utility utility;
    private ArrayList<UserProfile> items;
    private static final String TAG = AppController.class.getSimpleName();
    private String url = "https://api.github.com/search/users?q=location:lagos+language:java";

    // this is the total number of items expected from the result
    // we need it to implement pagination
    private int total_items = 0;

    public HttpRequestHelper(Context context) {
        this.context = context;
        utility = new Utility(context);
        items = new ArrayList<>();
    }

    public int getTotal_items() {
        return total_items;
    }


    public List<UserProfile> makeRequest() {

        JsonObjectRequest req = new JsonObjectRequest(com.android.volley.Request.Method.GET, url, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

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

                        } catch (JSONException e) {
                            e.printStackTrace();
                            utility.showLongToast("Error: " + e.getMessage());
                        } catch (Exception e) {

                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {


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
        return  items;
    }
}
