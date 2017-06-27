package com.localtrip.jejulocaltrip.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.localtrip.jejulocaltrip.R;
import com.localtrip.jejulocaltrip.adapter.WriteReviewListAdapter;
import com.localtrip.jejulocaltrip.dto.WriteReview;
import com.localtrip.jejulocaltrip.util.HttpConnector;
import com.localtrip.jejulocaltrip.util.URLDefine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class WriteReviewFragment extends DialogFragment {


    private ProgressBar progressBar;
    private ListView listViewWriteReview;
    private Button buttonWriteReview;

    private ArrayList<WriteReview> writeReviewArrayList;
    private WriteReviewListAdapter writeReviewListAdapter;

    private int courseIdx;
    private int userIdx;

    public WriteReviewFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static WriteReviewFragment newInstance(int courseIdx, int userIdx) {
        WriteReviewFragment fragment = new WriteReviewFragment();
        Bundle args = new Bundle();
        args.putInt("courseIdx", courseIdx);
        args.putInt("userIdx", userIdx);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            courseIdx = getArguments().getInt("courseIdx");
            userIdx = getArguments().getInt("userIdx");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_write_review, container, false);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        listViewWriteReview = (ListView)rootView.findViewById(R.id.listView_writeReview);
        buttonWriteReview = (Button)rootView.findViewById(R.id.button_writeReview);
        writeReviewArrayList = new ArrayList<>();
        writeReviewListAdapter = new WriteReviewListAdapter(getActivity(), writeReviewArrayList, buttonWriteReview, WriteReviewFragment.this);
        listViewWriteReview.setAdapter(writeReviewListAdapter);
        new WriteReviewListTask().execute();

        return rootView;

    }

    public void writeReviewTask(ArrayList<WriteReview> writeReviewArrayList) {
        this.writeReviewArrayList = writeReviewArrayList;

        for(int i=0; i<writeReviewArrayList.size(); i++) {

            WriteReview writeReview = writeReviewArrayList.get(i);

            if(writeReview.getType() == 0)
                new WriteCourseReviewTask().execute(writeReview);
            else if(writeReview.getType() == 1)
                new WriteGuideReviewTask().execute(writeReview);
            else if(writeReview.getType() ==2)
                new WriteLocationReviewTask().execute(writeReview);
        }

        Toast.makeText(getActivity().getApplicationContext(), "Success insert Review", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
        dismiss();

    }

    public class WriteCourseReviewTask extends AsyncTask<WriteReview, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(WriteReview... params) {

            String result = "false";
            String reviewContent = params[0].getContent();
            try {
                reviewContent = URLEncoder.encode(reviewContent, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            float reviewRate = params[0].getRate();

            HashMap<String, String> parameter = new HashMap<>();
            parameter.put("user_idx", String.valueOf(userIdx));
            parameter.put("review_content", reviewContent);
            parameter.put("review_rate", String.valueOf(reviewRate));
            parameter.put("course_idx", String.valueOf(courseIdx));

            try {
                JSONObject jsonObject = HttpConnector.callPOSTMethod(URLDefine.WRITE_COURSEREVIEW_URL, parameter);

                if(jsonObject != null && jsonObject.getString("result").equals("true")) {
                    result = "true";
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("true")) {
            } else {
            }
            super.onPostExecute(result);
        }
    }

    public class WriteGuideReviewTask extends AsyncTask<WriteReview, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(WriteReview... params) {

            String result = "false";
            String reviewContent = params[0].getContent();
            try {
                reviewContent = URLEncoder.encode(reviewContent, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            float reviewRate = params[0].getRate();

            HashMap<String, String> parameter = new HashMap<>();
            parameter.put("user_idx", String.valueOf(userIdx));
            parameter.put("review_content", reviewContent);
            parameter.put("review_rate", String.valueOf(reviewRate));
            parameter.put("guide_idx", String.valueOf(params[0].getIdx()));

            try {
                JSONObject jsonObject = HttpConnector.callPOSTMethod(URLDefine.WRITE_GUIDEREVIEW_URL, parameter);

                if(jsonObject != null && jsonObject.getString("result").equals("true")) {
                    result = "true";
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("true")) {
            } else {
            }
            super.onPostExecute(result);
        }
    }

    public class WriteLocationReviewTask extends AsyncTask<WriteReview, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(WriteReview... params) {

            String result = "false";
            String reviewContent = params[0].getContent();
            try {
                reviewContent = URLEncoder.encode(reviewContent, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            float reviewRate = params[0].getRate();
            int locationIdx = params[0].getIdx();

            HashMap<String, String> parameter = new HashMap<>();
            parameter.put("user_idx", String.valueOf(userIdx));
            parameter.put("review_content", reviewContent);
            parameter.put("review_rate", String.valueOf(reviewRate));
            parameter.put("location_idx", String.valueOf(locationIdx));

            try {
                JSONObject jsonObject = HttpConnector.callPOSTMethod(URLDefine.WRITE_LOCATIONREVIEW_URL, parameter);

                if(jsonObject != null && jsonObject.getString("result").equals("true")) {
                    result = "true";
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("true")) {

            } else {

            }
            super.onPostExecute(result);
        }
    }


    public class WriteReviewListTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";

            try {
                JSONObject jsonObject = HttpConnector.callGETMethod(URLDefine.WRITE_REVIEW_LIST_URL + "?course_idx=" + courseIdx);

                if (jsonObject != null && jsonObject.getString("result").equals("true")) {

                    writeReviewArrayList.clear();
                    JSONArray reviewList = jsonObject.getJSONArray("write_review_list");
                    JSONObject tmpObject = reviewList.getJSONObject(0);

                    WriteReview writeReviewCourse = new WriteReview();
                    writeReviewCourse.setType(0);
                    writeReviewCourse.setIdx(Integer.parseInt(tmpObject.getString("course_idx")));

                    WriteReview writeReviewUser = new WriteReview();
                    writeReviewUser.setType(1);
                    writeReviewUser.setIdx(Integer.parseInt(tmpObject.getString("user_idx")));

                    writeReviewArrayList.add(writeReviewCourse);
                    writeReviewArrayList.add(writeReviewUser);

                    for (int i = 0; i < reviewList.length(); i++) {
                        JSONObject writeReviewObject = reviewList.getJSONObject(i);
                        WriteReview writeReview = new WriteReview();
                        writeReview.setType(2);
                        writeReview.setTitle(writeReviewObject.getString("location_name"));
                        writeReview.setIdx(Integer.parseInt(writeReviewObject.getString("location_idx")));
                        writeReviewArrayList.add(writeReview);
                    }


                    result = "true";
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }


        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            if (result.equals("true")) {
                Log.d("MyTag", "list size = " +writeReviewArrayList.size());
                writeReviewListAdapter.notifyDataSetChanged();
                listViewWriteReview.invalidate();
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Fail to call write review list", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

}
