package com.example.ogbeoziomajnr.githubprofileviewer;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by SQ-OGBE PC on 10/03/2017.
 * <p>
 * This class is the adapter for the list  view that displays the user profiles
 */

public class ListViewAdapter extends ArrayAdapter<UserProfile> {

    private ArrayList<UserProfile> userProfiles;
    private ImageLoader image_loader = ImageLoader.getInstance();

    /**
     * Constructor for the list view adapter, accepts a list of user profile that would be used
     * for populating the list view, this constructor also initialises the  the default option for
     * the image loading library for loading the images in the list view asynchronously
     * @param context the context of the list view
     * @param user_profile a list of user profiles
     */
    public ListViewAdapter(Context context, ArrayList<UserProfile> user_profile) {
        super(context, 0, user_profile);
        userProfiles = user_profile;


    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        UserProfile profile = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.mylist, parent, false);
        }


        // get all the view for the layout that would be used to display user profile data
        TextView username = (TextView) convertView.findViewById(R.id.list_text);
        ImageView user_image = (ImageView) convertView.findViewById(R.id.list_image);

        //load the image and display it in the ImageView from the fragment
        //after the image is loaded once, it is cached so that further load
        // of the same image would not have to go online
        image_loader.displayImage(profile.getImage_url(), user_image);
        username.setText(profile.getUser_name());

        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public UserProfile getItem(int position) {
        return userProfiles.get(position);
    }
}
