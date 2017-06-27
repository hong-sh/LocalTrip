package com.localtrip.jejulocaltrip.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.localtrip.jejulocaltrip.R;
import com.localtrip.jejulocaltrip.dto.Reservation;
import com.localtrip.jejulocaltrip.util.ImageAsyncTask;
import com.localtrip.jejulocaltrip.view.ReservationDetailFragment;
import com.localtrip.jejulocaltrip.view.WriteReviewFragment;

import java.util.ArrayList;

/**
 * Created by hong on 2017. 6. 3..
 */

public class ReservationRecyclerAdapter extends RecyclerView.Adapter<ReservationRecyclerAdapter.ViewHolder> implements View.OnClickListener{

    private Context context;
    private ArrayList<Reservation> reservationArrayList;


    public ReservationRecyclerAdapter(Context context, ArrayList<Reservation> reservationArrayList) {
        this.context = context;
        this.reservationArrayList = reservationArrayList;
    }

    @Override
    public ReservationRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_reservation_item, null);
        view.setMinimumWidth(parent.getMinimumWidth());

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReservationRecyclerAdapter.ViewHolder holder, int position) {

        Reservation reservation = reservationArrayList.get(position);
        new ImageAsyncTask(context, holder.imageViewCourseImage).execute(reservation.getCourseImage());
        holder.textViewCourseName.setText(reservation.getCourseName());
        holder.textViewDetail.setTag(position);
        holder.textViewWriteReview.setTag(position);
        holder.textViewDetail.setOnClickListener(this);
        holder.textViewWriteReview.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return reservationArrayList.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView_reservationDetail:
                AppCompatActivity appCompatActivity = (AppCompatActivity)context;
                FragmentManager fm = appCompatActivity.getSupportFragmentManager();
                ReservationDetailFragment reservationDetailFragment = ReservationDetailFragment.newInstance(reservationArrayList.get((int)v.getTag()));
                reservationDetailFragment.show(fm, "reservation detail");
                break;
            case R.id.textView_writeReview:
                AppCompatActivity appCompatActivity1 = (AppCompatActivity)context;
                FragmentManager fm1 = appCompatActivity1.getSupportFragmentManager();
                WriteReviewFragment writeReviewFragment = WriteReviewFragment.newInstance(reservationArrayList.get((int)v.getTag()).getCourseIdx(), reservationArrayList.get((int)v.getTag()).getUserIdx());
                writeReviewFragment.show(fm1, "reservation detail");
                break;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageViewCourseImage;
        public TextView textViewCourseName;
        public TextView textViewDetail, textViewWriteReview;

        public ViewHolder(View itemView) {
            super(itemView);

            imageViewCourseImage = (ImageView)itemView.findViewById(R.id.imageView_courseImage);
            textViewCourseName = (TextView)itemView.findViewById(R.id.textView_courseName);
            textViewDetail = (TextView)itemView.findViewById(R.id.textView_reservationDetail);
            textViewWriteReview = (TextView)itemView.findViewById(R.id.textView_writeReview);
        }
    }
}
