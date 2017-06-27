package com.localtrip.jejulocaltrip.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.localtrip.jejulocaltrip.R;
import com.localtrip.jejulocaltrip.dto.CourseSimple;
import com.localtrip.jejulocaltrip.util.ImageAsyncTask;
import com.localtrip.jejulocaltrip.util.RecommendCourseInterface;

import java.util.ArrayList;

/**
 * Created by hong on 2017. 5. 29..
 */

public class RecommendCourseRecyclerAdapter extends RecyclerView.Adapter<RecommendCourseRecyclerAdapter.ViewHolder> implements View.OnClickListener{

    private Context context;
    private ArrayList<CourseSimple> courseSimpleArrayList;
    private RecommendCourseInterface recommendCourseInterface;

    public RecommendCourseRecyclerAdapter(Context context, ArrayList<CourseSimple> courseSimpleArrayList) {
        this.context = context;
        if(context instanceof RecommendCourseInterface)
            this.recommendCourseInterface = (RecommendCourseInterface)context;
        this.courseSimpleArrayList = courseSimpleArrayList;
    }

    @Override
    public RecommendCourseRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_recommend_course_item, null);
        view.setMinimumWidth(parent.getMinimumWidth());

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        CourseSimple courseSimple = courseSimpleArrayList.get(position);
        String courseImage = courseSimple.getCourseImage();
        new ImageAsyncTask(context, holder.imageViewCourseImage).execute(courseImage);
        holder.textViewCourseName.setText(courseSimple.getCourseName());
        holder.textViewCoursePrice.setText("$ " + courseSimple.getCoursePriceMin());
        holder.cardViewCourse.setTag(courseSimple.getCourseIdx());
        holder.cardViewCourse.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return courseSimpleArrayList.size();
    }

    @Override
    public void onClick(View v) {
       if(recommendCourseInterface != null) {
           recommendCourseInterface.onRecommendCourseInteraction((int)v.getTag());
       }
    }




    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageViewCourseImage;
        public TextView textViewCourseName;
        public TextView textViewCoursePrice;
        public CardView cardViewCourse;

        public ViewHolder(View itemView) {
            super(itemView);
            cardViewCourse = (CardView)itemView.findViewById(R.id.cardView_recommendCourse);
            imageViewCourseImage = (ImageView) itemView.findViewById(R.id.imageView_courseImage);
            textViewCourseName = (TextView)itemView.findViewById(R.id.textView_courseName);
            textViewCoursePrice = (TextView)itemView.findViewById(R.id.textView_coursePrice);

        }
    }


}
