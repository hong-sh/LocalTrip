package com.localtrip.jejulocaltrip.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.localtrip.jejulocaltrip.R;
import com.localtrip.jejulocaltrip.adapter.CourseRecyclerAdapter;
import com.localtrip.jejulocaltrip.dto.CourseThumbNail;
import com.localtrip.jejulocaltrip.dto.Setting;
import com.localtrip.jejulocaltrip.util.ActivityManager;
import com.localtrip.jejulocaltrip.util.CourseListInterface;
import com.localtrip.jejulocaltrip.util.HttpConnector;
import com.localtrip.jejulocaltrip.util.KoreaLocalTripApplication;
import com.localtrip.jejulocaltrip.util.URLDefine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener , CourseListInterface,
       SearchMapFragment.OnFragmentInteractionListener, TextWatcher{

    private KoreaLocalTripApplication app;
    private ActivityManager activityManager;
    private Setting setting;

    private ProgressBar progressBar;
    private EditText editTextSearch;
    private ImageButton imageButtonSearchOK;
    private RecyclerView recyclerViewCourse;
    private RelativeLayout relativeLayoutMap;

    private ImageButton imageButtonHome, imageButtonSearch, imageButtonWishlist, imageButtonMyPage;

    private CourseRecyclerAdapter courseRecyclerAdapter;
    private ArrayList<CourseThumbNail> courseThumbNailArrayList;
    private String tagString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        app = (KoreaLocalTripApplication) getApplication();
        activityManager = app.instanceOfActivityManager();
        activityManager.addActivity(SearchActivity.this);
        setting = app.instanceOfSetting();

        initLayout();
    }

    private void initLayout() {

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        editTextSearch = (EditText) findViewById(R.id.editText_search);
        imageButtonSearchOK = (ImageButton) findViewById(R.id.imageButton_searchOK);
        recyclerViewCourse = (RecyclerView) findViewById(R.id.recyclerView_searchCourse);
        relativeLayoutMap = (RelativeLayout)findViewById(R.id.layout_content);

        imageButtonSearch = (ImageButton) findViewById(R.id.imageButton_search);
        imageButtonHome = (ImageButton) findViewById(R.id.imageButton_home);
        imageButtonWishlist = (ImageButton) findViewById(R.id.imageButton_wishlist);
        imageButtonMyPage = (ImageButton) findViewById(R.id.imageButton_mypage);

        recyclerViewCourse.setHasFixedSize( true );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewCourse.setLayoutManager(layoutManager);
        courseThumbNailArrayList = new ArrayList<>();
        courseRecyclerAdapter = new CourseRecyclerAdapter(SearchActivity.this, courseThumbNailArrayList);
        recyclerViewCourse.setAdapter(courseRecyclerAdapter);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.layout_content, new SearchMapFragment());
        fragmentTransaction.commit();

        editTextSearch.addTextChangedListener(this);
        imageButtonSearchOK.setOnClickListener(this);
        imageButtonSearch.setOnClickListener(this);
        imageButtonHome.setOnClickListener(this);
        imageButtonWishlist.setOnClickListener(this);
        imageButtonMyPage.setOnClickListener(this);

    }

    @Override
    public void onCourseListInteraction(int courseIdx) {
        activityManager.removeActvity(this);
        Intent intent = new Intent(SearchActivity.this, CourseDetailActivity.class);
        app.setCourseIdx(courseIdx);
        startActivity(intent);
    }

    public class SearchTagTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            tagString = editTextSearch.getText().toString();
            progressBar.setVisibility(View.VISIBLE);
            relativeLayoutMap.setVisibility(View.GONE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";


            try {
                tagString = URLEncoder.encode(tagString, "UTF-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }

            try {
                JSONObject jsonObject = HttpConnector.callGETMethod(URLDefine.TAG_URL + "?tag_name=" + tagString + "&start_date=" + setting.getStartDate() + "&end_date=" + setting.getEndDate());

                courseThumbNailArrayList.clear();

                if (jsonObject != null && jsonObject.getString("result").equals("true")) {

                    JSONArray courseList = jsonObject.getJSONArray("course_list");
                    for (int i = 0; i < courseList.length(); i++) {

                        JSONObject courseObject = courseList.getJSONObject(i);
                        CourseThumbNail courseThumbNail = new CourseThumbNail();
                        courseThumbNail.setCourseIdx(Integer.parseInt(courseObject.getString("course_idx")));
                        courseThumbNail.setCourseName(courseObject.getString("course_name"));
                        courseThumbNail.setCourseImage(courseObject.getString("course_image"));
                        courseThumbNail.setCoursePrice(Integer.parseInt(courseObject.getString("course_price_min")));
                        courseThumbNail.setUserImage(courseObject.getString("user_image"));
                        courseThumbNail.setUserName(courseObject.getString("user_name"));
                        courseThumbNail.setCourseRate(Float.parseFloat(courseObject.getString("course_total_rate")));
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
            recyclerViewCourse.setVisibility(View.VISIBLE);
            if (result.equals("true")) {
                courseRecyclerAdapter.notifyDataSetChanged();
                recyclerViewCourse.invalidate();
            } else {
                Toast.makeText(getApplicationContext(), "Fail to call Search Course List", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.imageButton_searchOK:
                new SearchTagTask().execute();
                break;
            case R.id.imageButton_home:
                intent = new Intent(SearchActivity.this, MainActivity.class);
                activityManager.removeActvity(this);
                startActivity(intent);
                break;
            case R.id.imageButton_wishlist:
                intent = new Intent(SearchActivity.this, WishListActivity.class);
                activityManager.removeActvity(this);
                startActivity(intent);
                break;
            case R.id.imageButton_mypage:
                intent = new Intent(SearchActivity.this, MyPageActivity.class);
                activityManager.removeActvity(this);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = s.toString();
        if(!(text.length() > 0)) {
            recyclerViewCourse.setVisibility(View.GONE);
            relativeLayoutMap.setVisibility(View.VISIBLE);
        }



    }

    @Override
    public void onFragmentInteraction(String tagString) {
        editTextSearch.setText(tagString);
        new SearchTagTask().execute();
    }
}
