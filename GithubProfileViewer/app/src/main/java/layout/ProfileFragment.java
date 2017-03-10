package layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.ogbeoziomajnr.githubprofileviewer.R;
import com.example.ogbeoziomajnr.githubprofileviewer.UserProfile;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class ProfileFragment extends DialogFragment {


    private static final String user_profile_key = "My_Key";
    private UserProfile profile;
    ImageLoader image_loader = ImageLoader.getInstance();
    ImageLoaderConfiguration config;
    DisplayImageOptions defaultOptions;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("User Profile");
        View view = inflater.inflate(R.layout.profile_fragment_layout, container, false);


        profile = (UserProfile) getArguments().getSerializable(user_profile_key);

        config = new ImageLoaderConfiguration.Builder(this.getContext()).defaultDisplayImageOptions(defaultOptions).build();
       ImageLoader.getInstance().init(config);
        return view;
    }

    @Override

    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        TextView user_name_view = (TextView) view.findViewById(R.id.username);
        user_name_view.setText(profile.getUser_name());

        TextView url_view = (TextView) view.findViewById(R.id.url);
        url_view.setText(profile.getUser_url());

        ImageView image_view = (ImageView)  view.findViewById(R.id.profile_image);
        image_view.setImageURI(null);
        image_view.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        image_loader.displayImage(profile.getImage_url(), image_view);
       // image_view.setImageDrawable(Utility.getBitmapFromURL(profile.getImage_url()));


        ImageButton share_profile_button = (ImageButton) view.findViewById(R.id.share_button);
        share_profile_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "check out this cool developer @"+ profile.getUser_name()+"," + profile.getUser_url());
                sharingIntent.setType("text/plain");
                startActivity(sharingIntent);
            }
        });

    }

    public static ProfileFragment newInstance(UserProfile profile) {
        ProfileFragment fragment = new ProfileFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(user_profile_key, profile);
        fragment.setArguments(bundle);

        return fragment;
    }


}
