package com.example.ogbeoziomajnr.githubprofileviewer;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


/**
 * Created by SQ-OGBE PC on 13/03/2017.
 * This is the utility class for performing general work on the application
 */

public class Utility {

    private  ProgressDialog pDialog;
    private Context context;
    private ImageLoaderConfiguration config;
    private DisplayImageOptions defaultOptions;

    public Utility(Context context) {
        this.context = context;

        // set the default option of the universal image loader library to cache the image
        defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        // setup the image loader global configuration
        config = new ImageLoaderConfiguration.Builder(context).defaultDisplayImageOptions(defaultOptions).build();
        ImageLoader.getInstance().init(config);
    }

    public  void showprogressDialog() {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);

        if (!pDialog.isShowing())
            pDialog.show();
    }

    public  void hideprogressDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void showLongToast (String message) {
        Toast.makeText(context,message, Toast.LENGTH_LONG );
    }

    public void showShortToast (String message) {
        Toast.makeText(context,message, Toast.LENGTH_SHORT);
    }

}
