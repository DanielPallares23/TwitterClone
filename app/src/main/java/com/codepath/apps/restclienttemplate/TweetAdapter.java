package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.ParseRelativeDate;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by danielpb on 6/26/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{

    private List<Tweet> mTweets;
    private TweetAdapterListener mListener;
    Context context;

    // define an interface required by the viewholder
    public interface TweetAdapterListener {
        public void onItemSelected (View view, int position);
    }
    TwitterClient client;

     // pass in the Tweets array in the constructor
public TweetAdapter(List<Tweet>tweets, TweetAdapterListener listener) {
    mTweets = tweets;
    mListener = listener;
}
    // for each row, inflate the layout and cache references into viewHolder

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        client = new TwitterClient(context);
        return viewHolder;

    }

    // bind the values based on the position of the element

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get the area according to position
        Tweet tweet = mTweets.get(position);

        // populate the views according to this data
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        holder.tvScreenName.setText("@" + tweet.user.screenName);
        holder.tvTimeStamp.setText(ParseRelativeDate.getRelativeTimeAgo(tweet.createdAt));
        holder.tvRetweets.setText("" + tweet.retweets);
        holder.tvFavorites.setText("" + tweet.favorites);
        holder.onClick(holder.btFavorite);


        Glide.with(context).load(tweet.user.profileImageUrl).into(holder.ivProfileImage);


    }



    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    // create the ViewHolder class

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvScreenName;
        public TextView tvTimeStamp;
        public TextView tvRetweets;
        public TextView tvFavorites;
        public Button btFavorite;


        public ViewHolder (final View itemView) {
            super(itemView);

            // perform findViewById lookups

            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvScreenName = (TextView) itemView.findViewById(R.id.tvScreenName);
            tvTimeStamp = (TextView) itemView.findViewById(R.id.tvTimeStamp);
            tvRetweets = (TextView) itemView.findViewById(R.id.tv_Retweets);
            tvFavorites = (TextView) itemView.findViewById(R.id.tv_Favorites);
            btFavorite = (Button) itemView.findViewById(R.id.bt_Favorite);

            // handle row click event
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null) {
                        // get the position of this row element
                        int position = getAdapterPosition();
                        // fire the listener callback
                        mListener.onItemSelected(itemView, position);// POSSIBLE ERR HERE
                    }
                }
            });

        }


        public void onClick(View v) {
            Log.d("Working","onClick");

            btFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        //Toast.makeText(context,String.valueOf(mTweets.get(position).uid), Toast.LENGTH_LONG).show();
                        final Tweet tweet = mTweets.get(position);
                        //tweet.favorites += 1;
                        client.favoriteTweet(tweet.uid, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                //Toast.makeText(context, "onSuccess", Toast.LENGTH_LONG).show();
                                Tweet newTweet = null;
                                try {
                                    newTweet = Tweet.fromJSON(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if (newTweet != null) {
                                    tvFavorites.setText(String.valueOf(newTweet.favorites));
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                Toast.makeText(context, "onFailure", Toast.LENGTH_LONG).show();

                                super.onFailure(statusCode, headers, responseString, throwable);
                            }
                        });


                    }
                }
            });
        }
    }
}
