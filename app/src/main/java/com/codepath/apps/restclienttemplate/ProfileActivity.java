package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.fragments.UserTimelineFragment;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // create the user fragment
        String screenName = getIntent().getStringExtra("screen_name");
        UserTimelineFragment userTimelineFragment = new UserTimelineFragment().newInstance(screenName);
        // display the user fragment inside the container (dynamically)

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        // make change
        ft.replace(R.id.flContainer, userTimelineFragment);
        // commit the transaction
        ft.commit();

        client = TwitterApp.getRestClient();
        client.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // deserialize the user object
                    User user = User.fromJSON(response);
                    // set the title of the action bar based on user information
                    getSupportActionBar().setTitle(user.screenName);
                    // populate the user headline
                    populateUserHeadline(user);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void populateUserHeadline(User user) {
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);

        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvName.setText(user.name);

        tvTagline.setText(user.tagLine);
        tvFollowers.setText(user.followersCount + " Followers");
        tvFollowing.setText(user.followingCount + " Following");

        // load profile image with Glide
        Glide.with(this).load(user.profileImageUrl).into(ivProfileImage);
    }
}
