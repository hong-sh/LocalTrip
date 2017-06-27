package com.localtrip.jejulocaltrip.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.localtrip.jejulocaltrip.R;
import com.localtrip.jejulocaltrip.adapter.LocationSectionPageAdapter;
import com.localtrip.jejulocaltrip.adapter.ReviewListAdapter;
import com.localtrip.jejulocaltrip.dto.Location;
import com.localtrip.jejulocaltrip.dto.Review;
import com.localtrip.jejulocaltrip.util.HttpConnector;
import com.localtrip.jejulocaltrip.util.URLDefine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LocationDialogFragment extends DialogFragment implements View.OnClickListener {

    private int locationIdx;
    private View rootView;
    private ProgressBar progressBar;
    private LinearLayout layoutBack;

    private ListView listViewLocationReview;
    private ImageButton buttonDialogClose;
    private ViewPager viewPagerLocationImage;
    private RatingBar ratingBarLocationRate;
    private TextView textViewLocationName, textViewLocationInfo, textViewLocationAddr, textViewLocationTel;
    private Button buttonMoreReview;

    private ReviewListAdapter reviewListAdapter;
    private LocationSectionPageAdapter locationSectionPageAdapter;

    private Location location;
    private ArrayList<String> imageUrlStringArrayList;
    private ArrayList<Review> reviewArrayList;
    private int start = 0;

    public LocationDialogFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static LocationDialogFragment newInstance(int locationIdx) {
        LocationDialogFragment fragment = new LocationDialogFragment();
        Bundle args = new Bundle();
        args.putInt("locationIdx", locationIdx);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            locationIdx = getArguments().getInt("locationIdx");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_location_dialog, container, false);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        layoutBack = (LinearLayout)rootView.findViewById(R.id.layout_back);
        listViewLocationReview = (ListView) rootView.findViewById(R.id.listView_locationReview);
        View headrView = inflater.inflate(R.layout.listview_location_header, null, false);
        listViewLocationReview.addHeaderView(headrView);
        buttonDialogClose = (ImageButton) headrView.findViewById(R.id.button_location_close);
        buttonDialogClose.setOnClickListener(this);
        ratingBarLocationRate = (RatingBar) headrView.findViewById(R.id.ratingBar_locationRate);
        textViewLocationName = (TextView) headrView.findViewById(R.id.textView_locationName);
        textViewLocationInfo = (TextView) headrView.findViewById(R.id.textView_locationInfo);
        textViewLocationAddr = (TextView) headrView.findViewById(R.id.textView_locationAddr);
        textViewLocationTel = (TextView) headrView.findViewById(R.id.textView_locationTel);
        viewPagerLocationImage = (ViewPager) headrView.findViewById(R.id.viewPager_locationImage);
        viewPagerLocationImage.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                viewPagerLocationImage.setCurrentItem(position);

            }
        });


        reviewArrayList = new ArrayList<>();
        reviewListAdapter = new ReviewListAdapter(getActivity(), reviewArrayList);
        listViewLocationReview.setAdapter(reviewListAdapter);
        View footerView = inflater.inflate(R.layout.listview_footer_more, null, false);
        listViewLocationReview.addFooterView(footerView);
        buttonMoreReview = (Button) footerView.findViewById(R.id.button_more);
        buttonMoreReview.setOnClickListener(this);


        layoutBack.setVisibility(View.VISIBLE);

        new LocationFullTask().execute();
        new LocationReviewListLimitTask().execute();


        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_location_close:
                layoutBack.setVisibility(View.INVISIBLE);
                Animation dismissAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down);
                rootView.startAnimation(dismissAnimation);
                dismiss();
                break;
            case R.id.button_more:
                start += 3;
                new LocationReviewListLimitTask().execute();
                break;
        }
    }


    public class LocationFullTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";

            try {
                JSONObject jsonObject = HttpConnector.callGETMethod(URLDefine.LOCATIONFULL_URL + "?location_idx=" + locationIdx);

                if (jsonObject != null && jsonObject.getString("result").equals("true")) {

                    JSONObject locationInfo = jsonObject.getJSONObject("location_info");
                    location = new Location();
                    location.setLocationIdx(Integer.parseInt(locationInfo.getString("location_idx")));
                    location.setLocationName(locationInfo.getString("location_name"));
                    location.setLocationImage1(locationInfo.getString("location_image1"));
                    location.setLocationImage2(locationInfo.getString("location_image2"));
                    location.setLocationInfo(locationInfo.getString("location_info"));
                    location.setLocationAddr(locationInfo.getString("location_addr"));
                    location.setLocationTel(locationInfo.getString("location_tel"));
                    location.setLocationTotalRate(Float.parseFloat(locationInfo.getString("location_total_rate")));
                    location.setLocationTotalReivew(Integer.parseInt(locationInfo.getString("location_total_review")));
                    location.setDetailCourseImage(locationInfo.getString("detail_course_image"));

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
                textViewLocationName.setText(location.getLocationName());
                textViewLocationInfo.setText(location.getLocationInfo());
                textViewLocationAddr.setText(location.getLocationAddr());
                textViewLocationTel.setText(location.getLocationTel());
                ratingBarLocationRate.setRating(location.getLocationTotalRate());

                imageUrlStringArrayList = new ArrayList<>();
                imageUrlStringArrayList.add(location.getDetailCourseImage());
                imageUrlStringArrayList.add(location.getLocationImage1());
                imageUrlStringArrayList.add(location.getLocationImage2());

                locationSectionPageAdapter = new LocationSectionPageAdapter(getActivity().getSupportFragmentManager(), imageUrlStringArrayList);
                viewPagerLocationImage.setAdapter(locationSectionPageAdapter);
                viewPagerLocationImage.setCurrentItem(0);

            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Fail to call location info", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    public class LocationReviewListLimitTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";


            try {
                JSONObject jsonObject = HttpConnector.callGETMethod(URLDefine.LOCATION_REVIEWLIST_LIMIT_URL + "?location_idx=" + locationIdx + "&start=" + start);

                if (jsonObject != null && jsonObject.getString("result").equals("true")) {

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
                listViewLocationReview.invalidate();
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "No more Location Review", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }


}
