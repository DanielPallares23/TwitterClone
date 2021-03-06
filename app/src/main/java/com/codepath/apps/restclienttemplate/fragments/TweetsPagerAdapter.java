package com.codepath.apps.restclienttemplate.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by danielpb on 7/3/17.
 */

public class TweetsPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[] {"Home", "Mentions"};
    private Context context;

    static HomeTimelineFragment htFragment = new HomeTimelineFragment();
    static MentionsTimelineFragment mtFragment = new MentionsTimelineFragment();


    public TweetsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0)
            return htFragment;
        else {
            if(position == 1)
                return mtFragment;
        }
        return null;
    }

    public CharSequence getPageTitle(int position) {
        // generate title based on item position
        return tabTitles[position];
    }
    @Override
    public int getCount() {
        return 2;
    }
}
