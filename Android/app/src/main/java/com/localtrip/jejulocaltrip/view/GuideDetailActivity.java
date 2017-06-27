package com.localtrip.jejulocaltrip.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.localtrip.jejulocaltrip.R;
import com.localtrip.jejulocaltrip.adapter.RecommendCourseRecyclerAdapter;
import com.localtrip.jejulocaltrip.adapter.ReviewListAdapter;
import com.localtrip.jejulocaltrip.dto.CourseSimple;
import com.localtrip.jejulocaltrip.dto.GuideInfo;
import com.localtrip.jejulocaltrip.dto.Review;
import com.localtrip.jejulocaltrip.dto.Setting;
import com.localtrip.jejulocaltrip.util.ActivityManager;
import com.localtrip.jejulocaltrip.util.CircleImageAsyncTask;
import com.localtrip.jejulocaltrip.util.CircleImageView;
import com.localtrip.jejulocaltrip.util.HttpConnector;
import com.localtrip.jejulocaltrip.util.ImageAsyncTask;
import com.localtrip.jejulocaltrip.util.KoreaLocalTripApplication;
import com.localtrip.jejulocaltrip.util.RecommendCourseInterface;
import com.localtrip.jejulocaltrip.util.URLDefine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GuideDetailActivity extends AppCompatActivity implements View.OnClickListener, RecommendCourseInterface{

    private KoreaLocalTripApplication app;
    private ActivityManager activityManager;
    private Setting setting;

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private ListView listViewGuideReview;


    private ImageView imageViewGuideImage;
    private CircleImageView imageViewUserImage;
    private RatingBar ratingBarGuideRate;
    private TextView textViewGuideName, textViewGuideIntroduce, textViewGuideCapability, textViewGuideFirstLanguage, textViewGuideAvailableLanguage;


    private RelativeLayout relativeLayoutButtonMore;
    private Button buttonReviewMore;
    private RecyclerView recyclerViewGuideCourse;

    private int userIdx;
    private GuideInfo guideInfo;
    private ReviewListAdapter reviewListAdapter;
    private RecommendCourseRecyclerAdapter courseRecyclerAdapter;
    private ArrayList<Review> reviewArrayList;
    private ArrayList<CourseSimple> courseSimpleArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_detail);

        app = (KoreaLocalTripApplication)getApplication();
        activityManager = app.instanceOfActivityManager();
        activityManager.addActivity(this);
        setting = app.instanceOfSetting();

        userIdx = app.getUserIdx();

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initLayout();
        new GuideFullTask().execute();
        new GuideReviewListLimitTask().execute();
        new GuideCourseListTask().execute();

    }

    private void initLayout() {
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        listViewGuideReview = (ListView)findViewById(R.id.listView_guideReview);
        reviewArrayList = new ArrayList<>();
        reviewListAdapter = new ReviewListAdapter(GuideDetailActivity.this, reviewArrayList);
        listViewGuideReview.setAdapter(reviewListAdapter);

        View headerView = getLayoutInflater().inflate(R.layout.listview_guide_pofile_header, null, false);
        View footerView = getLayoutInflater().inflate(R.layout.listview_guide_profile_footer, null, false);

        listViewGuideReview.addHeaderView(headerView);
        listViewGuideReview.addFooterView(footerView);

        imageViewGuideImage = (ImageView)headerView.findViewById(R.id.imageView_guideImage);
        imageViewUserImage = (CircleImageView)headerView.findViewById(R.id.imageView_userImage);
        ratingBarGuideRate = (RatingBar)headerView.findViewById(R.id.ratingBar_guideRate);
        textViewGuideName = (TextView)headerView.findViewById(R.id.textView_guideName);
        textViewGuideIntroduce = (TextView)headerView.findViewById(R.id.textView_guideIntroduce);
        textViewGuideCapability = (TextView)headerView.findViewById(R.id.textView_guideCapability);
        textViewGuideFirstLanguage = (TextView)headerView.findViewById(R.id.textView_guideFirstLanguage);
        textViewGuideAvailableLanguage = (TextView)headerView.findViewById(R.id.textView_guideAvailableLanguage);

        relativeLayoutButtonMore = (RelativeLayout)footerView.findViewById(R.id.layout_button_more);
        buttonReviewMore = (Button)footerView.findViewById(R.id.button_more);
        buttonReviewMore.setOnClickListener(this);
        recyclerViewGuideCourse = (RecyclerView)footerView.findViewById(R.id.recyclerView_guideCourse);
        courseSimpleArrayList = new ArrayList<>();
        courseRecyclerAdapter = new RecommendCourseRecyclerAdapter(GuideDetailActivity.this, courseSimpleArrayList);
        recyclerViewGuideCourse.setHasFixedSize(true);
        recyclerViewGuideCourse.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewGuideCourse.setAdapter(courseRecyclerAdapter);
    }


    public class GuideFullTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";

            try {
                JSONObject jsonObject = HttpConnector.callGETMethod(URLDefine.GUIDEFULL_URL + "?user_idx=" +userIdx);

                if(jsonObject != null && jsonObject.getString("result").equals("true")) {

                    JSONObject guideProfile = jsonObject.getJSONObject("guide_profile");
                    guideInfo = new GuideInfo();
                    guideInfo.setUserIdx(Integer.parseInt(guideProfile.getString("user_idx")));
                    guideInfo.setUserName(guideProfile.getString("user_name"));
                    guideInfo.setUserImage(guideProfile.getString("user_image"));
                    guideInfo.setGuideImage(guideProfile.getString("guide_image"));
                    guideInfo.setGuideIntroduce(guideProfile.getString("guide_introduce"));
                    guideInfo.setGuideFirstLanguage(Integer.parseInt(guideProfile.getString("guide_first_language")));
                    guideInfo.setGuideAvailableLanguage(guideProfile.getString("guide_available_language"));
                    guideInfo.setGuideCapability(guideProfile.getString("guide_capability"));
                    guideInfo.setGuideTotalRate(Float.parseFloat(guideProfile.getString("guide_total_rate")));
                    guideInfo.setGuideTotalReview(Integer.parseInt(guideProfile.getString("guide_total_review")));
                    guideInfo.setGuideTotalCourse(Integer.parseInt(guideProfile.getString("guide_total_course")));

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
                new ImageAsyncTask(GuideDetailActivity.this, imageViewGuideImage).execute(guideInfo.getGuideImage());
                new CircleImageAsyncTask(GuideDetailActivity.this, imageViewUserImage).execute(guideInfo.getUserImage());
                textViewGuideName.setText(guideInfo.getUserName());
                textViewGuideIntroduce.setText(guideInfo.getGuideIntroduce());
                ratingBarGuideRate.setRating(guideInfo.getGuideTotalRate());
                textViewGuideCapability.setText(guideInfo.getGuideCapability());
                int guideType = guideInfo.getGuideFirstLanguage();
                switch (guideType) {
                    case 0:
                        textViewGuideFirstLanguage.setText("English");
                        break;
                    case 1:
                        textViewGuideFirstLanguage.setText("Korea");
                        break;
                    case 2:
                        textViewGuideFirstLanguage.setText("Chinese (Simplified)");
                        break;
                    case 3:
                        textViewGuideFirstLanguage.setText("Chinese (Traditional)");
                        break;
                    case 4:
                        textViewGuideFirstLanguage.setText("Japanese");
                        break;
                }
                textViewGuideAvailableLanguage.setText(guideInfo.getGuideAvailableLanguage());
            } else {
                Toast.makeText(getApplicationContext(), "Fail to call Guide Info", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    public class GuideReviewListLimitTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";
            int start = 0;

            try {
                JSONObject jsonObject = HttpConnector.callGETMethod(URLDefine.GUIDE_REVIEWLIST_LIMIT_URL +"?guide_idx=" +userIdx +"&start=" +start);

                if(jsonObject != null && jsonObject.getString("result").equals("true")) {

                    reviewArrayList.clear();
                    JSONArray reviewList = jsonObject.getJSONArray("review_list");
                    for(int i=0; i<reviewList.length(); i++) {
                        JSONObject reviewObject = reviewList.getJSONObject(i);
                        Review review = new Review();
                        review.setUserName(reviewObject.getString("user_name"));
                        review.setUserImage(reviewObject.getString("user_image"));
                        review.setReviewContent(reviewObject.getString("review_content"));
                        review.setReviewRate(Float.parseFloat(reviewObject.getString("review_rate")));
                        review.setReviewRegDate(reviewObject.getString("review_reg_date"));
                        reviewArrayList.add(review);
                        Log.d("MyTag", "review = " +review.getReviewContent());
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
                reviewListAdapter.notifyDataSetChanged();
                listViewGuideReview.invalidate();
                if(reviewArrayList.size() < 3)
                    relativeLayoutButtonMore.setVisibility(View.GONE);
            } else {
                Toast.makeText(getApplicationContext(), "Fail to call Guide Review", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    public class GuideCourseListTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";
            int type = setting.getType();

            try {
                JSONObject jsonObject = HttpConnector.callGETMethod(URLDefine.GUIDECOURSELIST_URL + "?user_idx=" +userIdx + "&type=" +type);

                if(jsonObject != null && jsonObject.getString("result").equals("true")) {

                    JSONArray courseList = jsonObject.getJSONArray("course_list");
                    courseSimpleArrayList.clear();
                    for(int i=0; i<courseList.length(); i++) {

                        CourseSimple course = new CourseSimple();
                        JSONObject courseObject = courseList.getJSONObject(i);
                        course.setCourseIdx(Integer.parseInt(courseObject.getString("course_idx")));
                        course.setCourseName(courseObject.getString("course_name"));
                        course.setCourseImage(courseObject.getString("course_image"));
                        course.setCoursePriceMin(Integer.parseInt(courseObject.getString("course_price_min")));
                        courseSimpleArrayList.add(course);
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
                courseRecyclerAdapter.notifyDataSetChanged();
                recyclerViewGuideCourse.invalidate();
            } else {
                Toast.makeText(getApplicationContext(), "Fail to call Guide Course list", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRecommendCourseInteraction(int courseIdx) {
        Intent intent = new Intent(GuideDetailActivity.this, CourseDetailActivity.class);
        activityManager.removeActvity(this);
        app.setCourseIdx(courseIdx);
        startActivity(intent);
        overridePendingTransition(0,0);
    }


    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();

                return true;
        }
        return super.onOptionsItemSelected( item );
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GuideDetailActivity.this, CourseDetailActivity.class);
        activityManager.removeActvity(this);
        startActivity(intent);
    }
}
