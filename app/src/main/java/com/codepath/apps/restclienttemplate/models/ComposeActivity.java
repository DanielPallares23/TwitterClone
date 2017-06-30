package com.codepath.apps.restclienttemplate.models;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.codepath.apps.restclienttemplate.R.layout.activity_compose;

public class ComposeActivity extends AppCompatActivity {

    public static String TWITTER_KEY; // Change this, base API
    public String screenName;
    public String name;
    public String profileImageUrl;
    public boolean hasEdited;
    public int charactersLeft;

    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_compose);
        // set hasEdited to false
        hasEdited = false;
        // set characters remaining to 140
        charactersLeft = 140;
        // Place User on Screen
        setUserProfile();


    }

    public void onCancel(View view){
        finish();
    }

    public void onSubmit(View v) {
        if(hasEdited) {
            EditText etName = (EditText) findViewById(R.id.et_composeTweet);
            final String message = etName.getText().toString();
            TwitterClient twitterClient = new TwitterClient(this);
            twitterClient.sendTweet(message, new JsonHttpResponseHandler() {

                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        Intent data = new Intent();

                        Tweet tweet = Tweet.fromJSON(response);
                        data.putExtra("tweet", tweet);
                        data.putExtra("someName", "someBody");
                        //data.putExtra(USER_KEY, tweet.user);
                        // data.putExtra(USER_KEY, tweet.user);
                        setResult(RESULT_OK, data); // set result code and bundle data for response

                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // closes the activity, pass data to parent

                }

            });
        }
    }

    public void onEditTweet(View view) {
        hasEdited = true;
        EditText etName = (EditText) findViewById(R.id.et_composeTweet);
        if (etName.getText().toString().equals("What's happening?")) {
            etName.setText("", null);
            etName.setTextColor(Color.BLACK);
        }
    }

    public void setUserProfile(){
        final TwitterClient client = TwitterApp.getRestClient();
        client.getScreenName(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    screenName = response.get("screen_name").toString();
                    Log.d("Twitter Client", screenName);
                } catch (JSONException e) {
                    Log.d("Twitter Client",response.toString());

                    e.printStackTrace();
                }

                final TextView tvScreenName = (TextView) findViewById(R.id.tvScreenName);
                Log.d("Setting",screenName);
                tvScreenName.setText("@" + screenName, null);

                client.getProfileDetails(screenName, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            name = response.get("name").toString();
                            Log.d("Twitter Client", name);

                            profileImageUrl = response.get("profile_image_url").toString();
                            Log.d("Twitter Client", profileImageUrl);
                        } catch (JSONException e) {
                            Log.d("Twitter Client","Failed");

                            e.printStackTrace();
                        }

                        TextView tvUserName = (TextView) findViewById(R.id.tvUserName);
                        tvUserName.setText(name, null);
                        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

                        context = ivProfileImage.getContext();
                        Glide.with(context).load(profileImageUrl).into(ivProfileImage);
                    }
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        Log.d("Twitter Client", response.toString());

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.d("Twitter Client", responseString);
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.d("Twitter Client", errorResponse.toString());
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        Log.d("Twitter Client", errorResponse.toString());
                        throwable.printStackTrace();
                    }


                });







            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("Twitter Client", response.toString());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Twitter Client", responseString);
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Twitter Client", errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("Twitter Client", errorResponse.toString());
                throwable.printStackTrace();
            }
        });
    }


}
