package com.localtrip.jejulocaltrip.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.localtrip.jejulocaltrip.R;
import com.localtrip.jejulocaltrip.dto.Review;
import com.localtrip.jejulocaltrip.util.CircleImageAsyncTask;
import com.localtrip.jejulocaltrip.util.CircleImageView;

import java.util.ArrayList;

/**
 * Created by hong on 2017. 5. 30..
 */

public class ReviewListAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Review> reviewArrayList;

    public ReviewListAdapter(Context context, ArrayList<Review> reviewArrayList) {
        this.context = context;
        this.reviewArrayList = reviewArrayList;
    }

    @Override
    public int getCount() {
        return reviewArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
            convertView = parent.inflate(context, R.layout.listview_review_item, null);

        Log.d("MyTag", "enter this and position = " +position);

        Review review = reviewArrayList.get(position);
        CircleImageView imageViewUserImage = (CircleImageView)convertView.findViewById(R.id.imageView_userImage);
        TextView textViewUserName = (TextView)convertView.findViewById(R.id.textView_userName);
        TextView textViewReviewContent = (TextView)convertView.findViewById(R.id.textView_reviewContent);
        RatingBar ratingBarReviewRate = (RatingBar)convertView.findViewById(R.id.ratingBar_reviewRate);

        new CircleImageAsyncTask(context, imageViewUserImage).execute(review.getUserImage());
        textViewUserName.setText(review.getUserName());
        textViewReviewContent.setText(review.getReviewContent());
        ratingBarReviewRate.setRating(review.getReviewRate());

        return convertView;
    }
}
