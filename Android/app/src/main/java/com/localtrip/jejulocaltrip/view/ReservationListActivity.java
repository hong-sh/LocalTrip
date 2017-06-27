package com.localtrip.jejulocaltrip.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.localtrip.jejulocaltrip.R;
import com.localtrip.jejulocaltrip.adapter.ReservationRecyclerAdapter;
import com.localtrip.jejulocaltrip.dto.Reservation;
import com.localtrip.jejulocaltrip.dto.User;
import com.localtrip.jejulocaltrip.util.ActivityManager;
import com.localtrip.jejulocaltrip.util.HttpConnector;
import com.localtrip.jejulocaltrip.util.KoreaLocalTripApplication;
import com.localtrip.jejulocaltrip.util.URLDefine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReservationListActivity extends AppCompatActivity {

    private KoreaLocalTripApplication app;
    private ActivityManager activityManager;
    private User user;

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private RecyclerView recyclerViewReservationList;
    private ReservationRecyclerAdapter reservationRecyclerAdapter;

    private ArrayList<Reservation> reservationArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list);

        app = (KoreaLocalTripApplication)getApplication();
        activityManager = app.instanceOfActivityManager();
        activityManager.addActivity(this);
        user = app.instanceOfUser();

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initLayout();

        new ReservationListTask().execute();
    }

    private void initLayout() {

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        recyclerViewReservationList = (RecyclerView)findViewById(R.id.recyclerView_reservationList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewReservationList.setLayoutManager(layoutManager);
        reservationArrayList = new ArrayList<>();
        reservationRecyclerAdapter = new ReservationRecyclerAdapter(ReservationListActivity.this, reservationArrayList);
        recyclerViewReservationList.setAdapter(reservationRecyclerAdapter);

    }

    public class ReservationListTask extends AsyncTask<Void, Void, String> {

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

                JSONObject jsonObject = HttpConnector.callGETMethod(URLDefine.RESERVATIONLIST_URL + "?user_idx=" +userIdx);

                if(jsonObject != null && jsonObject.getString("result").equals("true")) {

                    JSONArray reservationList = jsonObject.getJSONArray("reservation_list");
                    reservationArrayList.clear();
                    for(int i=0; i<reservationList.length(); i++) {
                        JSONObject detailObject = reservationList.getJSONObject(i);
                        Reservation reservation = new Reservation();
                        reservation.setReservationIdx(Integer.parseInt(detailObject.getString("reservation_idx")));
                        reservation.setUserIdx(Integer.parseInt(detailObject.getString("user_idx")));
                        reservation.setCourseIdx(Integer.parseInt(detailObject.getString("course_idx")));
                        reservation.setReservationPersonNumber(Integer.parseInt(detailObject.getString("reservation_person_number")));
                        reservation.setReservationDate(detailObject.getString("reservation_date"));
                        reservation.setReservationRequest(detailObject.getString("reservation_request"));
                        reservation.setUserName(detailObject.getString("reservation_name"));
                        reservation.setUserEmail(detailObject.getString("reservation_email"));
                        reservation.setReservationPrice(Integer.parseInt(detailObject.getString("reservation_price")));
                        reservation.setReservationPhone(detailObject.getString("reservation_phone"));
                        reservation.setUserName(detailObject.getString("user_name"));
                        reservation.setCourseImage(detailObject.getString("course_image"));
                        reservation.setCourseName(detailObject.getString("course_name"));
                        reservationArrayList.add(reservation);
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
                reservationRecyclerAdapter.notifyDataSetChanged();
                recyclerViewReservationList.invalidate();
            } else {
                Toast.makeText(getApplicationContext(), "Fail to call Reservation List", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
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
        Intent intent = new Intent(ReservationListActivity.this, MyPageActivity.class);
        activityManager.removeActvity(this);
        startActivity(intent);
    }
}
