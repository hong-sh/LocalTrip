package com.localtrip.jejulocaltrip.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.localtrip.jejulocaltrip.R;
import com.localtrip.jejulocaltrip.dto.CourseDetail;
import com.localtrip.jejulocaltrip.util.ImageAsyncTask;

import java.util.ArrayList;

/**
 * Created by hong on 2017. 5. 30..
 */

public class CourseDetailListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<CourseDetail> courseDetailArrayList;

    public CourseDetailListAdapter(Context context, ArrayList<CourseDetail> courseDetailArrayList) {
        this.context = context;
        this.courseDetailArrayList = courseDetailArrayList;
    }

    @Override
    public int getCount() {
        return courseDetailArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null)
            convertView = parent.inflate(context, R.layout.listview_course_detail_item, null);

        CourseDetail courseDetail = courseDetailArrayList.get(position);

        TextView textViewLocationName = (TextView) convertView.findViewById(R.id.textView_locationName);
        RatingBar ratingBarLocationRate = (RatingBar) convertView.findViewById(R.id.ratingBar_locationRate);
        TextView textViewCourseDetail = (TextView) convertView.findViewById(R.id.textView_courseDetail);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView_courseDetail);

        new ImageAsyncTask(context, imageView).execute(courseDetail.getDetailCourseImage());
        textViewLocationName.setText(courseDetail.getLocationName());
        ratingBarLocationRate.setRating(courseDetail.getLocationTotalRate());
        textViewCourseDetail.setText(courseDetail.getDetailCourseInfo());

        convertView.setTag(courseDetail.getLocationIdx());
        return convertView;
    }

}
