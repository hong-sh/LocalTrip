package com.localtrip.jejulocaltrip.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.localtrip.jejulocaltrip.R;
import com.localtrip.jejulocaltrip.adapter.CourseRecyclerAdapter;
import com.localtrip.jejulocaltrip.dto.CourseThumbNail;
import com.localtrip.jejulocaltrip.dto.User;
import com.localtrip.jejulocaltrip.util.ActivityManager;
import com.localtrip.jejulocaltrip.util.CourseListInterface;
import com.localtrip.jejulocaltrip.util.HttpConnector;
import com.localtrip.jejulocaltrip.util.KoreaLocalTripApplication;
import com.localtrip.jejulocaltrip.util.URLDefine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WishListActivity extends AppCompatActivity implements View.OnClickListener, CourseListInterface {

    private KoreaLocalTripApplication app;
    private ActivityManager activityManager;
    private User user;

    private ProgressBar progressBar;
    private RecyclerView recyclerViewWishlist;
    private ImageButton imageButtonHome, imageButtonSearch, imageButtonWishlist, imageButtonMyPage;

    private CourseRecyclerAdapter courseRecyclerAdapter;
    private ArrayList<CourseThumbNail> courseThumbNailArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);

        app = (KoreaLocalTripApplication)getApplication();
        activityManager = app.instanceOfActivityManager();
        activityManager.addActivity(WishListActivity.this);

        user = app.instanceOfUser();
        if(!user.isLogin()) {
            Intent intent = new Intent(WishListActivity.this, LoginActivity.class);
//            activityManager.removeActvity();
            startActivity(intent);
        }

        initLayout();

        new WishListTask().execute();
    }

    private void initLayout() {
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        recyclerViewWishlist = (RecyclerView)findViewById(R.id.recyclerView_wishlist);
        recyclerViewWishlist.setHasFixedSize( true );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewWishlist.setLayoutManager(layoutManager);
        courseThumbNailArrayList = new ArrayList<>();
        courseRecyclerAdapter = new CourseRecyclerAdapter(WishListActivity.this, courseThumbNailArrayList);
        recyclerViewWishlist.setAdapter(courseRecyclerAdapter);

        imageButtonHome = (ImageButton)findViewById(R.id.imageButton_home);
        imageButtonSearch = (ImageButton)findViewById(R.id.imageButton_search);
        imageButtonWishlist = (ImageButton)findViewById(R.id.imageButton_wishlist);
        imageButtonMyPage = (ImageButton)findViewById(R.id.imageButton_mypage);

        imageButtonHome.setOnClickListener(this);
        imageButtonSearch.setOnClickListener(this);
        imageButtonWishlist.setOnClickListener(this);
        imageButtonMyPage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.imageButton_home:
                intent = new Intent(WishListActivity.this, MainActivity.class);
                activityManager.removeActvity(this);
                startActivity(intent);
                break;
            case R.id.imageButton_search:
                intent = new Intent(WishListActivity.this, SearchActivity.class);
                activityManager.removeActvity(this);
                startActivity(intent);
                break;
            case R.id.imageButton_wishlist:
                break;
            case R.id.imageButton_mypage:
                intent = new Intent(WishListActivity.this, MyPageActivity.class);
                activityManager.removeActvity(this);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onCourseListInteraction(int courseIdx) {
        activityManager.removeActvity(this);
        Intent intent = new Intent(WishListActivity.this, CourseDetailActivity.class);
        app.setCourseIdx(courseIdx);
        startActivity(intent);
    }


    public class WishListTask extends AsyncTask<Void, Void, String> {

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
                JSONObject jsonObject = HttpConnector.callGETMethod(URLDefine.WISHLIST_URL + "?user_idx=" +userIdx);

                if(jsonObject != null && jsonObject.getString("result").equals("true")) {

                    JSONArray wishlistList = jsonObject.getJSONArray("wishlist");
                    courseThumbNailArrayList.clear();
                    for(int i=0; i<wishlistList.length(); i++) {
                        JSONObject wishlistObject = wishlistList.getJSONObject(i);
                        CourseThumbNail courseThumbNail = new CourseThumbNail();
                        courseThumbNail.setUserName(wishlistObject.getString("user_name"));
                        courseThumbNail.setUserImage(wishlistObject.getString("user_image"));
                        courseThumbNail.setCourseIdx(Integer.parseInt(wishlistObject.getString("course_idx")));
                        courseThumbNail.setCourseName(wishlistObject.getString("course_name"));
                        courseThumbNail.setCourseImage(wishlistObject.getString("course_image"));
                        courseThumbNail.setCoursePrice(Integer.parseInt(wishlistObject.getString("course_price_min")));
                        courseThumbNail.setCourseRate(Float.parseFloat(wishlistObject.getString("course_total_rate")));
                        courseThumbNailArrayList.add(courseThumbNail);
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
                recyclerViewWishlist.invalidate();
            } else {
                Toast.makeText(getApplicationContext(), "Fail to call user wishlist", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }


}
