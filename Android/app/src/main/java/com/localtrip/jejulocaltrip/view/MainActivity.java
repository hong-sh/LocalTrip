package com.localtrip.jejulocaltrip.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.localtrip.jejulocaltrip.R;
import com.localtrip.jejulocaltrip.adapter.MainSectionPageAdapter;
import com.localtrip.jejulocaltrip.adapter.RecommendCourseRecyclerAdapter;
import com.localtrip.jejulocaltrip.adapter.RecommendGuideRecyclerAdapter;
import com.localtrip.jejulocaltrip.dto.CourseSimple;
import com.localtrip.jejulocaltrip.dto.GuideSimple;
import com.localtrip.jejulocaltrip.dto.Setting;
import com.localtrip.jejulocaltrip.util.ActivityManager;
import com.localtrip.jejulocaltrip.util.HttpConnector;
import com.localtrip.jejulocaltrip.util.KoreaLocalTripApplication;
import com.localtrip.jejulocaltrip.util.RecommendCourseInterface;
import com.localtrip.jejulocaltrip.util.RecommendGuideInterface;
import com.localtrip.jejulocaltrip.util.URLDefine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RecommendCourseInterface, RecommendGuideInterface, SettingFragment.OnFragmentInteractionListener {

    private KoreaLocalTripApplication app;
    private ActivityManager activityManager;
    private Setting setting;

    private ViewPager viewPager;
    private ProgressBar progressBar;
    private Button buttonSetting;
    private ImageButton imageButtonHome, imageButtonSearch, imageButtonWishList, imageButtonMyPage;
    private RecyclerView recyclerViewRecommendCourse, recyclerViewRecommendGuide;
    private RecyclerView.Adapter recyclerViewAdapterRecommendCourse, recyclerViewAdapterRecommendGuide;

    private ArrayList<CourseSimple> courseSimpleArrayList;
    private ArrayList<GuideSimple> guideSimpleArrayList;
    private int type;
    private String startDate, endDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (KoreaLocalTripApplication) getApplication();
        activityManager = app.instanceOfActivityManager();
        activityManager.addActivity(MainActivity.this);
        setting = app.instanceOfSetting();
        courseSimpleArrayList = app.instanceOfRecommendCourse();
        guideSimpleArrayList = app.instanceOfRecommendGuide();

        initLayout();
        checkSetting();


    }


    private void initLayout() {

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        MainSectionPageAdapter mainSectionPageAdapter = new MainSectionPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mainSectionPageAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);

            }
        });

        buttonSetting = (Button) findViewById(R.id.button_setting);
        buttonSetting.setOnClickListener(this);

        recyclerViewRecommendCourse = (RecyclerView) findViewById(R.id.recyclerview_recommendCourse);
        recyclerViewRecommendCourse.setHasFixedSize(true);
        recyclerViewRecommendCourse.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewAdapterRecommendCourse = new RecommendCourseRecyclerAdapter(MainActivity.this, courseSimpleArrayList);
        recyclerViewRecommendCourse.setAdapter(recyclerViewAdapterRecommendCourse);

        recyclerViewRecommendGuide = (RecyclerView) findViewById(R.id.recyclerview_recommendGuide);
        recyclerViewRecommendGuide.setHasFixedSize(true);
        recyclerViewRecommendGuide.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewAdapterRecommendGuide = new RecommendGuideRecyclerAdapter(MainActivity.this, guideSimpleArrayList);
        recyclerViewRecommendGuide.setAdapter(recyclerViewAdapterRecommendGuide);

        imageButtonHome = (ImageButton) findViewById(R.id.imageButton_home);
        imageButtonSearch = (ImageButton) findViewById(R.id.imageButton_search);
        imageButtonWishList = (ImageButton) findViewById(R.id.imageButton_wishlist);
        imageButtonMyPage = (ImageButton) findViewById(R.id.imageButton_mypage);

        imageButtonHome.setOnClickListener(this);
        imageButtonSearch.setOnClickListener(this);
        imageButtonWishList.setOnClickListener(this);
        imageButtonMyPage.setOnClickListener(this);

    }

    private void checkSetting() {

        if (setting.getType() < 0) {
            FragmentManager fm = getSupportFragmentManager();
            SettingFragment settingFragment = new SettingFragment();
            settingFragment.show(fm, "setting");
        } else {
            this.type = setting.getType();
            this.startDate = setting.getStartDate();
            this.endDate = setting.getEndDate();
            if (courseSimpleArrayList.size() == 0 || guideSimpleArrayList.size() == 0) {
                new RecommendCourseListTask().execute();
                new RecommendGuideListTask().execute();
            }
        }
    }

    public class RecommendCourseListTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";

            try {
                JSONObject jsonObject = HttpConnector.callGETMethod(URLDefine.RECOMMEND_COURSELIST_URL + "?type=" + type + "&start_date=" + startDate + "&end_date=" + endDate);

                if (jsonObject != null && jsonObject.getString("result").equals("true")) {

                    JSONArray courseList = jsonObject.getJSONArray("course_list");
                    courseSimpleArrayList.clear();
                    for (int i = 0; i < courseList.length(); i++) {
                        JSONObject courseObject = courseList.getJSONObject(i);
                        CourseSimple course = new CourseSimple();
                        course.setCourseIdx(Integer.parseInt(courseObject.getString("course_idx")));
                        course.setCourseName(courseObject.getString("course_name"));
                        course.setCourseImage(courseObject.getString("course_image"));
                        course.setCoursePriceMin(Integer.parseInt(courseObject.getString("course_price_min")));
                        courseSimpleArrayList.add(course);
                        Log.d("MyTag", "course = " + course.getCourseName());
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
                recyclerViewAdapterRecommendCourse.notifyDataSetChanged();
                recyclerViewRecommendCourse.invalidate();
            } else {
                Toast.makeText(getApplicationContext(), "Fail..", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    public class RecommendGuideListTask extends AsyncTask<Void, Void, String> {

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
                JSONObject jsonObject = HttpConnector.callGETMethod(URLDefine.RECOMMEND_GUIDELIST_URL + "?type=" + type);

                if (jsonObject != null && jsonObject.getString("result").equals("true")) {

                    JSONArray guideList = jsonObject.getJSONArray("guide_list");
                    guideSimpleArrayList.clear();
                    for (int i = 0; i < guideList.length(); i++) {
                        JSONObject guideObject = guideList.getJSONObject(i);
                        GuideSimple guideSimple = new GuideSimple();
                        guideSimple.setUserIdx(Integer.parseInt(guideObject.getString("user_idx")));
                        guideSimple.setUserName(guideObject.getString("user_name"));
                        guideSimple.setUserImage(guideObject.getString("user_image"));
                        guideSimpleArrayList.add(guideSimple);
                        Log.d("MyTag", "course = " + guideSimple.getUserName());
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
                recyclerViewAdapterRecommendGuide.notifyDataSetChanged();
                recyclerViewRecommendGuide.invalidate();
            } else {

                Toast.makeText(getApplicationContext(), "Fail..", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {
            case R.id.imageButton_home:
                break;
            case R.id.imageButton_search:
                intent = new Intent(MainActivity.this, SearchActivity.class);
                activityManager.removeActvity(this);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            case R.id.imageButton_wishlist:
                intent = new Intent(MainActivity.this, WishListActivity.class);
                activityManager.removeActvity(this);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            case R.id.imageButton_mypage:
                intent = new Intent(MainActivity.this, MyPageActivity.class);
                activityManager.removeActvity(this);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            case R.id.button_setting:
                FragmentManager fm = getSupportFragmentManager();
                SettingFragment settingFragment = new SettingFragment();
                settingFragment.show(fm, "setting");
                break;
        }
    }

    @Override
    public void onFragmentInteraction(int type, String startDate, String endDate) {
        setting.setType(type);
        setting.setStartDate(startDate);
        setting.setEndDate(endDate);
        setting.savedSetting();

        this.type = setting.getType();
        this.startDate = setting.getStartDate();
        this.endDate = setting.getEndDate();
        new RecommendCourseListTask().execute();
        new RecommendGuideListTask().execute();
    }


    @Override
    public void onRecommendCourseInteraction(int courseIdx) {
        Intent intent = new Intent(MainActivity.this, CourseDetailActivity.class);
        activityManager.removeActvity(this);
        app.setCourseIdx(courseIdx);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    @Override
    public void onRecommendGuideInteraction(int userIdx) {
        Intent intent = new Intent(MainActivity.this, GuideDetailActivity.class);
        activityManager.removeActvity(this);
        app.setUserIdx(userIdx);
        startActivity(intent);
        overridePendingTransition(0,0);
    }


}
