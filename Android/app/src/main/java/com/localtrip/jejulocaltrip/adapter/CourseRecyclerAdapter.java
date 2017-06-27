package com.localtrip.jejulocaltrip.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.localtrip.jejulocaltrip.R;
import com.localtrip.jejulocaltrip.dto.CourseThumbNail;
import com.localtrip.jejulocaltrip.util.CircleImageAsyncTask;
import com.localtrip.jejulocaltrip.util.CircleImageView;
import com.localtrip.jejulocaltrip.util.CourseListInterface;
import com.localtrip.jejulocaltrip.util.ImageAsyncTask;

import java.util.ArrayList;

/**
 * Created by hong on 2017. 6. 2..
 */

public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.ViewHolder> implements View.OnClickListener{

    private Context context;
    private ArrayList<CourseThumbNail> courseThumbNailArrayList;
    private CourseListInterface courseListInterface;

    public CourseRecyclerAdapter(Context context, ArrayList<CourseThumbNail> courseThumbNailArrayList) {
        this.context = context;
        if(context instanceof CourseListInterface)
            this.courseListInterface = (CourseListInterface)context;
        this.courseThumbNailArrayList = courseThumbNailArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.recyclerview_course_item,null);
        view.setMinimumWidth( parent.getMinimumWidth() );

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CourseThumbNail courseThumbNail = courseThumbNailArrayList.get(position);

        new ImageAsyncTask(context, holder.imageViewCourseImage).execute(courseThumbNail.getCourseImage());
        new CircleImageAsyncTask(context, holder.imageViewUserImage).execute(courseThumbNail.getUserImage());
        holder.textViewCourseName.setText(courseThumbNail.getCourseName());
        holder.textViewUserName.setText(courseThumbNail.getUserName());
        holder.textViewCoursePrice.setText("$ " +courseThumbNail.getCoursePrice());
        holder.ratingBarCourseRate.setRating(courseThumbNail.getCourseRate());

        holder.cardViewCourse.setTag(courseThumbNail.getCourseIdx());
        holder.cardViewCourse.setOnClickListener(this);

    }

    @Override
    public int getItemCount() {
        return courseThumbNailArrayList.size();
    }

    @Override
    public void onClick(View v) {
        if(courseListInterface != null) {
            courseListInterface.onCourseListInteraction((int)v.getTag());
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageViewCourseImage;
        public CircleImageView imageViewUserImage;
        public TextView textViewCourseName, textViewUserName, textViewCoursePrice;
        public RatingBar ratingBarCourseRate;
        public CardView cardViewCourse;

        public ViewHolder(View itemView) {
            super(itemView);

            imageViewCourseImage = (ImageView)itemView.findViewById(R.id.imageView_courseImage);
            imageViewUserImage = (CircleImageView)itemView.findViewById(R.id.imageView_userImage);
            textViewCourseName = (TextView)itemView.findViewById(R.id.textView_courseName);
            textViewUserName = (TextView)itemView.findViewById(R.id.textView_userName);
            textViewCoursePrice = (TextView)itemView.findViewById(R.id.textView_coursePrice);
            ratingBarCourseRate = (RatingBar)itemView.findViewById(R.id.ratingBar_courseRate);
            cardViewCourse = (CardView)itemView.findViewById(R.id.cardView_course);
        }


    }
}
