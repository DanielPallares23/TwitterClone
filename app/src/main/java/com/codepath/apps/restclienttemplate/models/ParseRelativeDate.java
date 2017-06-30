package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by danielpb on 6/26/17.
 */

public class ParseRelativeDate {


    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // make format '13h' not '13 hours ago' for all cases
        int indexOfHour = relativeDate.indexOf('h');
        int indexOfMinute = relativeDate.indexOf('m');
        int indexOfSecond = relativeDate.indexOf('s');
        if(indexOfHour != -1) {
            return relativeDate.substring(0,indexOfHour + 1).replaceAll("\\s","");
        }
        if(indexOfMinute != -1) {
            return relativeDate.substring(0, indexOfMinute + 1).replaceAll("\\s","");
        }
        return relativeDate.substring(0, indexOfSecond + 1).replaceAll("\\s","");
    }

}
