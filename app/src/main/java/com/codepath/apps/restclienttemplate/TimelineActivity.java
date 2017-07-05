package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.restclienttemplate.fragments.TweetsPagerAdapter;

public class TimelineActivity extends AppCompatActivity {


    final int REQUEST_CODE = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // get the view pager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        // set the adapter for the pager
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager(), this));
        // set up the tab layout to use the view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    public void onProfileView(MenuItem item) {
        // launch the profile view
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu); // POSSIBLE ERROR HERE
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.miCompose:
                Intent i = new Intent(this, ComposeActivity.class);
                startActivityForResult(i, REQUEST_CODE);
                return true;
            default:
                Toast.makeText(this, "default Case", Toast.LENGTH_LONG).show();
                return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check request code and result code first
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras

            // Use data parameter
            Tweet tweet = data.getParcelableExtra("tweet");
           /* String someName = data.getStringExtra("someName");
            Log.d("Correct", "Tweet: " + tweet.body);
            tweets.add(0, tweet);
            tweetdAdapter.notifyItemInserted(0);
            rvTweets.scrollToPosition(0);*/
            // Toast the name to display temporarily on screen
       /* }

    }*/
}
