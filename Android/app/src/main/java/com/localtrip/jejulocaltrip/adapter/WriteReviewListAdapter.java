package com.localtrip.jejulocaltrip.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.localtrip.jejulocaltrip.R;
import com.localtrip.jejulocaltrip.dto.WriteReview;
import com.localtrip.jejulocaltrip.view.WriteReviewFragment;

import java.util.ArrayList;

/**
 * Created by hong on 2017. 6. 3..
 */

public class WriteReviewListAdapter extends BaseAdapter implements RatingBar.OnRatingBarChangeListener, View.OnClickListener{

    private Context context;
    private WriteReviewFragment fragment;
    private ArrayList<WriteReview> writeReviewArrayList;

    private Button buttonWrtieReview;

    public WriteReviewListAdapter(Context context, ArrayList<WriteReview> writeReviewArrayList, Button buttonWriteReview, WriteReviewFragment fragment) {
        this.context = context;
        this.writeReviewArrayList = writeReviewArrayList;
        this.buttonWrtieReview = buttonWriteReview;
        this.buttonWrtieReview.setOnClickListener(this);
        this.fragment = fragment;
    }

    @Override
    public int getCount() {
        return writeReviewArrayList.size();
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

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = parent.inflate(context, R.layout.listview_write_review_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        WriteReview writeReview = writeReviewArrayList.get(position);

        viewHolder.ratingBarWriteReview.setTag(position);
        viewHolder.ratingBarWriteReview.setOnRatingBarChangeListener(this);
        viewHolder.ratingBarWriteReview.setRating(writeReviewArrayList.get(position).getRate());
        viewHolder.editTextWriteReview.setTag(position);
        viewHolder.editTextWriteReview.addTextChangedListener(new WriteReviewTextWatcher(position));
        viewHolder.editTextWriteReview.setText(writeReviewArrayList.get(position).getContent());

        switch (writeReview.getType()) {
            case 0:
                viewHolder.textViewWriteReviewTitle.setText("Course Review");
                viewHolder.textViewWriteReviewSubTitle.setText("Write Review about Course");
                break;
            case 1:
                viewHolder.textViewWriteReviewTitle.setText("Guide Review");
                viewHolder.textViewWriteReviewSubTitle.setText("Write Review about Guide");
                break;
            case 2:
                viewHolder.textViewWriteReviewTitle.setText("Location Review");
                viewHolder.textViewWriteReviewSubTitle.setText("Write Review about " + writeReview.getTitle());
                break;
        }


        return convertView;
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        writeReviewArrayList.get((int)ratingBar.getTag()).setRate(rating);
    }

    @Override
    public void onClick(View v) {
        for(int i=0; i<writeReviewArrayList.size(); i++) {
            if(writeReviewArrayList.get(i).getRate() == 0.0f || writeReviewArrayList.get(i).getContent().length() <= 0) {
                Toast.makeText(fragment.getActivity().getApplicationContext(), "Insert all Review Rate and Content", Toast.LENGTH_SHORT).show();
            }
        }
        fragment.writeReviewTask(writeReviewArrayList);
    }


    public class ViewHolder {
        public TextView textViewWriteReviewTitle, textViewWriteReviewSubTitle;
        public RatingBar ratingBarWriteReview;
        public EditText editTextWriteReview;

        public ViewHolder(View view) {

            textViewWriteReviewTitle = (TextView) view.findViewById(R.id.textView_writeReviewTitle);
            textViewWriteReviewSubTitle = (TextView) view.findViewById(R.id.textView_writeReviewSubTitle);
            ratingBarWriteReview = (RatingBar) view.findViewById(R.id.ratingBar_writeReview);
            editTextWriteReview = (EditText) view.findViewById(R.id.editText_writeReview);
        }
    }

    public class WriteReviewTextWatcher implements TextWatcher {

        private int position;

        public WriteReviewTextWatcher(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            writeReviewArrayList.get(position).setContent(s.toString());
        }
    }


}
