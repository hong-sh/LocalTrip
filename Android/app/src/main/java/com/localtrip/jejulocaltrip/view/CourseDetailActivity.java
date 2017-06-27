package com.localtrip.jejulocaltrip.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.localtrip.jejulocaltrip.R;
import com.localtrip.jejulocaltrip.adapter.CourseDetailListAdapter;
import com.localtrip.jejulocaltrip.adapter.ReviewListAdapter;
import com.localtrip.jejulocaltrip.dto.CourseDetail;
import com.localtrip.jejulocaltrip.dto.CourseInfo;
import com.localtrip.jejulocaltrip.dto.GuideSimple;
import com.localtrip.jejulocaltrip.dto.Review;
import com.localtrip.jejulocaltrip.dto.User;
import com.localtrip.jejulocaltrip.util.ActivityManager;
import com.localtrip.jejulocaltrip.util.CircleImageAsyncTask;
import com.localtrip.jejulocaltrip.util.CircleImageView;
import com.localtrip.jejulocaltrip.util.HttpConnector;
import com.localtrip.jejulocaltrip.util.ImageAsyncTask;
import com.localtrip.jejulocaltrip.util.KoreaLocalTripApplication;
import com.localtrip.jejulocaltrip.util.URLDefine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CourseDetailActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private KoreaLocalTripApplication app;
    private ActivityManager activityManager;
    private User user;

    private Toolbar toolbar;
    private ListView listViewCourseDetail, listViewCourseReview;
    private View footerViewReview;
    private Button buttonMoreReview;
    private Button buttonReservation;
    private ProgressBar progressBar;

    private CourseDetailListAdapter courseDetailListAdapter;
    private ReviewListAdapter reviewListAdapter;


    private ImageView imageViewCourseImage;
    private ImageButton imageButtonWishList;
    private TextView textViewCourseName, textViewCoursePrice;
    private RatingBar ratingBarCourseRate;
    private CircleImageView circleImageViewGuideImage;
    private TextView textViewGuideIntroduce;
    private TextView textViewGuideName;
    private Button buttonMoreGuideProfile;
    private TextView textViewCourseDrive, textViewCourseTime, textViewCourseInfo;


    private TextView textViewCourseMeetingLocation, textViewCourseMeetingTime;
    private ImageView imageViewCourseMeetingLocation;
    private ImageView mapViewCourseMeetingLocation;
    private TextView textViewCourseOption, textViewCourseNoOption, textViewCourseEtc;


    private ArrayList<CourseDetail> courseDetailArrayList;
    private ArrayList<Review> reviewArrayList;
    private int courseIdx;
    private CourseInfo courseInfo;
    private GuideSimple guideSimple;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        app = (KoreaLocalTripApplication) getApplication();
        activityManager = app.instanceOfActivityManager();
        activityManager.addActivity(CourseDetailActivity.this);
        user = app.instanceOfUser();

        initLayout();
        courseIdx = app.getCourseIdx();


        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new CourseInfoTask().execute();
        new CourseWishListTask().execute();
        new CourseDetailListTask().execute();
        new GuideSimpleTask().execute();
        new CourseReviewListLimitTask().execute();
        new GoogleMapTask().execute();
    }

    private void initLayout() {

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        listViewCourseDetail = (ListView) findViewById(R.id.listView_courseDetail);
        courseDetailArrayList = new ArrayList<>();
        courseDetailListAdapter = new CourseDetailListAdapter(CourseDetailActivity.this, courseDetailArrayList);
        listViewCourseDetail.setAdapter(courseDetailListAdapter);
        listViewCourseDetail.setOnItemClickListener(this);

        View headerViewDetailCourse = getLayoutInflater().inflate(R.layout.listview_course_detail_header, null, false);
        View footerViewDetailCourse = getLayoutInflater().inflate(R.layout.listview_course_detail_footer, null, false);
        listViewCourseDetail.addHeaderView(headerViewDetailCourse);
        listViewCourseDetail.addFooterView(footerViewDetailCourse);

        listViewCourseReview = (ListView) headerViewDetailCourse.findViewById(R.id.listView_courseReview);
        reviewArrayList = new ArrayList<>();
        reviewListAdapter = new ReviewListAdapter(CourseDetailActivity.this, reviewArrayList);
        listViewCourseReview.setAdapter(reviewListAdapter);

        footerViewReview = getLayoutInflater().inflate(R.layout.listview_footer_more, null, false);
        buttonMoreReview = (Button) footerViewReview.findViewById(R.id.button_more);
        buttonMoreReview.setOnClickListener(this);

        imageViewCourseImage = (ImageView) headerViewDetailCourse.findViewById(R.id.imageView_courseImage);
        imageButtonWishList = (ImageButton) headerViewDetailCourse.findViewById(R.id.imageButton_wishlist);
        imageButtonWishList.setOnClickListener(this);
        textViewCourseName = (TextView) headerViewDetailCourse.findViewById(R.id.textView_courseName);
        textViewCoursePrice = (TextView) headerViewDetailCourse.findViewById(R.id.textView_coursePrice);
        ratingBarCourseRate = (RatingBar) headerViewDetailCourse.findViewById(R.id.ratingBar_courseRate);
        circleImageViewGuideImage = (CircleImageView) headerViewDetailCourse.findViewById(R.id.imageView_guideImage);
        textViewGuideName = (TextView)headerViewDetailCourse.findViewById(R.id.textView_guideName);
        textViewGuideIntroduce = (TextView) headerViewDetailCourse.findViewById(R.id.textView_guideIntroduce);
        buttonMoreGuideProfile = (Button) headerViewDetailCourse.findViewById(R.id.button_guideProfile);
        buttonMoreGuideProfile.setOnClickListener(this);
        textViewCourseDrive = (TextView) headerViewDetailCourse.findViewById(R.id.textView_courseDrive);
        textViewCourseTime = (TextView) headerViewDetailCourse.findViewById(R.id.textView_courseSpendTime);
        textViewCourseInfo = (TextView) headerViewDetailCourse.findViewById(R.id.textView_courseInfo);

        textViewCourseMeetingLocation = (TextView) footerViewDetailCourse.findViewById(R.id.textView_courseMeetingLocation);
        textViewCourseMeetingTime = (TextView) footerViewDetailCourse.findViewById(R.id.textView_courseMeetingTime);
        imageViewCourseMeetingLocation = (ImageView) footerViewDetailCourse.findViewById(R.id.imageView_courseMeetingLocation);
        mapViewCourseMeetingLocation = (ImageView) footerViewDetailCourse.findViewById(R.id.imageView_map);

        textViewCourseOption = (TextView) footerViewDetailCourse.findViewById(R.id.textView_courseOption);
        textViewCourseNoOption = (TextView) footerViewDetailCourse.findViewById(R.id.textView_courseNoOption);
        textViewCourseEtc = (TextView) footerViewDetailCourse.findViewById(R.id.textView_courseEtc);

        buttonReservation = (Button) findViewById(R.id.button_reservation);
        buttonReservation.setOnClickListener(this);
    }

    public class CourseInfoTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";

            try {
                JSONObject jsonObject = HttpConnector.callGETMethod(URLDefine.COURSEINFO_URL + "?course_idx=" + courseIdx);

                if (jsonObject != null && jsonObject.getString("result").equals("true")) {

                    JSONObject courseInfoObject = jsonObject.getJSONObject("course_info");
                    courseInfo = new CourseInfo();
                    courseInfo.setCourseIdx(courseIdx);
                    courseInfo.setCourseName(courseInfoObject.getString("course_name"));
                    courseInfo.setCourseImage(courseInfoObject.getString("course_image"));
                    courseInfo.setCoursePriceMin(Integer.parseInt(courseInfoObject.getString("course_price_min")));
                    courseInfo.setCourseTime(Integer.parseInt(courseInfoObject.getString("course_time")));
                    courseInfo.setCourseDrive(courseInfoObject.getString("course_drive"));
                    courseInfo.setCourseMeetingLocation(courseInfoObject.getString("course_meeting_location"));
                    courseInfo.setCourseMeetingLocationLat(Double.parseDouble(courseInfoObject.getString("course_meeting_location_lat")));
                    courseInfo.setCourseMeetingLocationLng(Double.parseDouble(courseInfoObject.getString("course_meeting_location_lng")));
                    courseInfo.setCourseMeetingLocationImage(courseInfoObject.getString("course_meeting_location_image"));
                    courseInfo.setCourseMeetingTime(courseInfoObject.getString("course_meeting_time").substring(0, 5));
                    courseInfo.setCourseOption(courseInfoObject.getString("course_option"));
                    courseInfo.setCourseNonOption(courseInfoObject.getString("course_nonoption"));
                    courseInfo.setCourseEtc(courseInfoObject.getString("course_etc"));
                    courseInfo.setCourseInfo(courseInfoObject.getString("course_info"));
                    courseInfo.setCourseTotalRate(Float.parseFloat(courseInfoObject.getString("course_total_rate")));
                    courseInfo.setCourseTotalReivew(Integer.parseInt(courseInfoObject.getString("course_total_review")));
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
                new ImageAsyncTask(CourseDetailActivity.this, imageViewCourseImage).execute(courseInfo.getCourseImage());
                textViewCourseName.setText(courseInfo.getCourseName());
                textViewCoursePrice.setText("$ " + courseInfo.getCoursePriceMin());
                ratingBarCourseRate.setRating(courseInfo.getCourseTotalRate());
                textViewCourseDrive.setText(courseInfo.getCourseDrive());
                textViewCourseTime.setText(courseInfo.getCourseTime() + " hours");
                textViewCourseInfo.setText(courseInfo.getCourseInfo());
                textViewCourseMeetingLocation.setText(courseInfo.getCourseMeetingLocation());
                textViewCourseMeetingTime.setText(courseInfo.getCourseMeetingTime());
                new ImageAsyncTask(CourseDetailActivity.this, imageViewCourseMeetingLocation).execute(courseInfo.getCourseMeetingLocationImage());
                textViewCourseOption.setText(courseInfo.getCourseOption());
                textViewCourseNoOption.setText(courseInfo.getCourseNonOption());
                textViewCourseEtc.setText(courseInfo.getCourseEtc());

            } else {
                Toast.makeText(getApplicationContext(), "Fail to call course info", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    public class CourseReviewListLimitTask extends AsyncTask<Void, Void, String> {

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
                JSONObject jsonObject = HttpConnector.callGETMethod(URLDefine.COURSE_REVIEWLIST_LIMIT_URL + "?course_idx=" + courseIdx + "&start=" + start);

                if (jsonObject != null && jsonObject.getString("result").equals("true")) {

                    reviewArrayList.clear();
                    JSONArray reviewList = jsonObject.getJSONArray("review_list");
                    for (int i = 0; i < reviewList.length(); i++) {
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
                listViewCourseReview.invalidate();
                if (reviewListAdapter.getCount() >= 3)
                    listViewCourseReview.addFooterView(footerViewReview);
            } else {
                Toast.makeText(getApplicationContext(), "Fail to call course review", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    public class CourseWishListTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";

            try {
                JSONObject jsonObject = HttpConnector.callGETMethod(URLDefine.COURSE_WISHLIST_URL + "?user_idx=" + user.getUserIdx() +"&course_idx=" + courseIdx);

                if (jsonObject != null && jsonObject.getString("result").equals("true")) {

                    JSONObject wishlist = jsonObject.getJSONObject("wishlist");
                    if(wishlist != null)
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
            if(result.equals("true")){
                imageButtonWishList.setImageDrawable(getResources().getDrawable(R.drawable.ic_wishlist_active));
            }
            else if(result.equals("false")){
                imageButtonWishList.setImageDrawable(getResources().getDrawable(R.drawable.ic_wishlist));
            }
            super.onPostExecute(result);
        }
    }

    public class GuideSimpleTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";

            try {
                JSONObject jsonObject = HttpConnector.callGETMethod(URLDefine.GUIDEPROFILE_URL + "?course_idx=" + courseIdx);

                if (jsonObject != null && jsonObject.getString("result").equals("true")) {

                    JSONObject guideProfileObject = jsonObject.getJSONObject("guide_profile");

                    guideSimple = new GuideSimple();
                    guideSimple.setUserIdx(Integer.parseInt(guideProfileObject.getString("user_idx")));
                    guideSimple.setUserName(guideProfileObject.getString("user_name"));
                    guideSimple.setUserImage(guideProfileObject.getString("user_image"));
                    guideSimple.setGuideIdx(Integer.parseInt(guideProfileObject.getString("guide_idx")));
                    guideSimple.setGuideIntroduce(guideProfileObject.getString("guide_introduce"));


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
                new CircleImageAsyncTask(CourseDetailActivity.this, circleImageViewGuideImage).execute(guideSimple.getUserImage());
                textViewGuideIntroduce.setText(guideSimple.getGuideIntroduce());
                textViewGuideName.setText(guideSimple.getUserName());
            } else {
                Toast.makeText(getApplicationContext(), "Fail to call Guide profile", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    public class CourseDetailListTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";

            try {

                JSONObject jsonObject = HttpConnector.callGETMethod(URLDefine.DETAILLIST_URL + "?course_idx=" + courseIdx);

                if (jsonObject != null && jsonObject.getString("result").equals("true")) {

                    JSONArray detailList = jsonObject.getJSONArray("detail_list");
                    courseDetailArrayList.clear();
                    for (int i = 0; i < detailList.length(); i++) {
                        JSONObject detailObject = detailList.getJSONObject(i);
                        CourseDetail courseDetail = new CourseDetail();
                        courseDetail.setLocationIdx(Integer.parseInt(detailObject.getString("location_idx")));
                        courseDetail.setLocationName(detailObject.getString("location_name"));
                        courseDetail.setLocationTotalRate(Float.parseFloat(detailObject.getString("location_total_rate")));
                        courseDetail.setDetailCourseTime(Integer.parseInt(detailObject.getString("detail_course_time")));
                        courseDetail.setDetailCourseInfo(detailObject.getString("detail_course_info"));
                        courseDetail.setDetailCourseImage(detailObject.getString("detail_course_image"));
                        courseDetailArrayList.add(courseDetail);
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
                courseDetailListAdapter.notifyDataSetChanged();
                listViewCourseDetail.invalidate();
            } else {
                Toast.makeText(getApplicationContext(), "Fail to call detail course list", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    public class GoogleMapTask extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(Void... params) {

            Bitmap bitmap = null;

            try {
                InputStream is = new java.net.URL(URLDefine.GOOGLE_MAP_URL + courseInfo.getCourseMeetingLocationLat() + "," + courseInfo.getCourseMeetingLocationLng()).openStream();

                bitmap = BitmapFactory.decodeStream(is);

            } catch (IOException e) {
                Log.d("MyTag", e.toString());
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            progressBar.setVisibility(View.GONE);
            if (result != null) {
                mapViewCourseMeetingLocation.setImageBitmap(result);
            } else {
                Toast.makeText(getApplicationContext(), "Fail to call google map", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {
            case R.id.imageButton_wishlist:
                new UpdateWishListTask().execute();
                break;
            case R.id.button_guideProfile:
                intent = new Intent(CourseDetailActivity.this, GuideDetailActivity.class);
                app.setCourseIdx(courseIdx);
                app.setUserIdx(guideSimple.getUserIdx());
                startActivity(intent);
                break;
            case R.id.button_reservation:
                intent = new Intent(CourseDetailActivity.this, ReservationActivity.class);
                CourseInfo tmpCourseInfo = new CourseInfo();
                tmpCourseInfo.setCourseIdx(courseIdx);
                tmpCourseInfo.setCourseName(courseInfo.getCourseName());
                tmpCourseInfo.setCourseInfo(courseInfo.getCourseInfo());
                tmpCourseInfo.setCourseImage(courseInfo.getCourseImage());
                tmpCourseInfo.setCoursePriceMin(courseInfo.getCoursePriceMin());
                app.setTmpCourse(tmpCourseInfo);
                startActivity(intent);
                break;
        }

    }


    private String action;
    public class UpdateWishListTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";
            int userIdx = user.getUserIdx();

            try {
                JSONObject jsonObject = HttpConnector.callGETMethod(URLDefine.UPDATEWISHLIST_URL + "?user_idx=" +userIdx +"&course_idx=" +courseIdx);

                if(jsonObject != null && jsonObject.getString("result").equals("true")) {

                    action = jsonObject.getString("action");

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
                if(action.equals("insert")){
                    imageButtonWishList.setImageDrawable(getResources().getDrawable(R.drawable.ic_wishlist_active));
                    Toast.makeText(getApplicationContext(), "Success to add wishlist", Toast.LENGTH_SHORT).show();
                }
                else if(action.equals("delete")){
                    imageButtonWishList.setImageDrawable(getResources().getDrawable(R.drawable.ic_wishlist));
                    Toast.makeText(getApplicationContext(), "Success to remove wishlist", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Fail to update Wishlist", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        LocationDialogFragment dialogFragment = LocationDialogFragment.newInstance((int)view.getTag());
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN );
        fragmentTransaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down);
        fragmentTransaction.add(android.R.id.content, dialogFragment);
        fragmentTransaction.commit();

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
        activityManager.removeActvity(this);
        Intent intent = new Intent(CourseDetailActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
