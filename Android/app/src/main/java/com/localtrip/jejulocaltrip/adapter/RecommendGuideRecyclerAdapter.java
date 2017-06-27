package com.localtrip.jejulocaltrip.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.localtrip.jejulocaltrip.R;
import com.localtrip.jejulocaltrip.dto.GuideSimple;
import com.localtrip.jejulocaltrip.util.CircleImageAsyncTask;
import com.localtrip.jejulocaltrip.util.CircleImageView;
import com.localtrip.jejulocaltrip.util.RecommendGuideInterface;

import java.util.ArrayList;

/**
 * Created by hong on 2017. 5. 30..
 */

public class RecommendGuideRecyclerAdapter extends RecyclerView.Adapter<RecommendGuideRecyclerAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private ArrayList<GuideSimple> guideSimpleArrayList;
    private RecommendGuideInterface recommendGuideInterface;

    public RecommendGuideRecyclerAdapter(Context context,ArrayList<GuideSimple> guideSimpleArrayList) {
        this.context = context;
        if(context instanceof RecommendGuideInterface)
            this.recommendGuideInterface = (RecommendGuideInterface) context;
        this.guideSimpleArrayList = guideSimpleArrayList;
    }

    @Override
    public RecommendGuideRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_recommend_guide_item, null);
        view.setMinimumWidth(parent.getMinimumWidth());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecommendGuideRecyclerAdapter.ViewHolder holder, int position) {

        GuideSimple guideSimple = guideSimpleArrayList.get(position);
        String userImage = guideSimple.getUserImage();
        new CircleImageAsyncTask(context, holder.imageViewGuideImage).execute(userImage);
        holder.textViewGuideName.setText(guideSimple.getUserName());
        holder.cardViewGuide.setTag(guideSimple.getUserIdx());
        holder.cardViewGuide.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return guideSimpleArrayList.size();
    }

    @Override
    public void onClick(View v) {
        if(recommendGuideInterface != null)
            recommendGuideInterface.onRecommendGuideInteraction((int)v.getTag());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardViewGuide;
        public CircleImageView imageViewGuideImage;
        public TextView textViewGuideName;

        public ViewHolder(View itemView) {
            super(itemView);
            cardViewGuide = (CardView)itemView.findViewById(R.id.cardView_recommendGuide);
            imageViewGuideImage = (CircleImageView)itemView.findViewById(R.id.imageView_guideImage);
            textViewGuideName = (TextView) itemView.findViewById(R.id.textView_guideName);
        }


    }
}
