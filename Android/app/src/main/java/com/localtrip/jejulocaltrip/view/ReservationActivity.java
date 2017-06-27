package com.localtrip.jejulocaltrip.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.localtrip.jejulocaltrip.R;
import com.localtrip.jejulocaltrip.adapter.ReservationCalendarAdapter;
import com.localtrip.jejulocaltrip.dto.CourseDate;
import com.localtrip.jejulocaltrip.dto.CourseInfo;
import com.localtrip.jejulocaltrip.dto.DayInfo;
import com.localtrip.jejulocaltrip.dto.User;
import com.localtrip.jejulocaltrip.util.ActivityManager;
import com.localtrip.jejulocaltrip.util.HttpConnector;
import com.localtrip.jejulocaltrip.util.ImageAsyncTask;
import com.localtrip.jejulocaltrip.util.KoreaLocalTripApplication;
import com.localtrip.jejulocaltrip.util.URLDefine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ReservationActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private KoreaLocalTripApplication app;
    private ActivityManager activityManager;
    private User user;
    private int courseIdx;

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private TextView textViewCalendarYearMonth;
    private ImageButton imageButtonBeforMonth, imageButtonNextMonth;
    private GridView gridViewCalendar;
    private ImageButton imageButtonRemoveQuantity, imageButtonAddQuantity;
    private TextView textViewQuantity;
    private ImageView imageViewCourseImage;
    private TextView textViewCourseName, textViewCourseInfo;
    private EditText editTextCardNumber, editTextCardYear, editTextCardMonth;
    private EditText editTextUserName, editTextUserPhone, editTextUserEmail, editTextReservationRequest;
    private TextView textViewToTalQuantity, textViewTotalPrice;
    private Button buttonReservation;

    private ReservationCalendarAdapter reservationCalendarAdapter;
    private ArrayList<DayInfo> dayInfoArrayList;
    private Calendar calendar;

    private ArrayList<CourseDate> courseDateArrayList;
    private String courseName;
    private String courseInfo;
    private String courseImage;
    private int coursePriceMin;


    private int quantity = 1;
    private int reservationPrice;
    private String reservationDate;
    private String reservationName;
    private String reservationPhone;
    private String reservationEmail;
    private String reservationRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        app = (KoreaLocalTripApplication) getApplication();
        activityManager = app.instanceOfActivityManager();
        activityManager.addActivity(ReservationActivity.this);
        user = app.instanceOfUser();

        if(!user.isLogin()) {
            Intent intent = new Intent(ReservationActivity.this, LoginActivity.class);
            activityManager.removeActvity(this);
            startActivity(intent);
        }

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CourseInfo tmpCourseInfo = app.getTmpCourse();

        courseIdx = tmpCourseInfo.getCourseIdx();
        courseName = tmpCourseInfo.getCourseName();
        courseInfo = tmpCourseInfo.getCourseInfo();
        courseImage = tmpCourseInfo.getCourseImage();
        coursePriceMin = tmpCourseInfo.getCoursePriceMin();

        initLayout();

        calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        dayInfoArrayList = new ArrayList<>();

        initCalendar(calendar);
        courseDateArrayList = new ArrayList<>();
        new CourseDateTask().execute();

        initCourseInfo();
    }

    private void initLayout() {

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        textViewCalendarYearMonth = (TextView) findViewById(R.id.textView_year_month);
        imageButtonBeforMonth = (ImageButton) findViewById(R.id.imageButton_beforeMonth);
        imageButtonNextMonth = (ImageButton) findViewById(R.id.imageButton_nextMonth);
        gridViewCalendar = (GridView) findViewById(R.id.girdView_calendar);
        imageButtonRemoveQuantity = (ImageButton) findViewById(R.id.imageButton_removeQuantity);
        imageButtonAddQuantity = (ImageButton) findViewById(R.id.imageButton_addQuantity);
        textViewQuantity = (TextView) findViewById(R.id.textView_quantity);
        imageViewCourseImage = (ImageView) findViewById(R.id.imageView_courseImage);
        textViewCourseName = (TextView) findViewById(R.id.textView_courseName);
        textViewCourseInfo = (TextView) findViewById(R.id.textView_courseInfo);
        editTextCardNumber = (EditText) findViewById(R.id.editText_cardNumber);
        editTextCardYear = (EditText) findViewById(R.id.editText_cardYear);
        editTextCardMonth = (EditText) findViewById(R.id.editText_cardMonth);

        editTextUserName = (EditText) findViewById(R.id.editText_userName);
        editTextUserPhone = (EditText) findViewById(R.id.editText_userPhone);
        editTextUserEmail = (EditText) findViewById(R.id.editText_userEmail);

        editTextUserName.setText(user.getUserName());
        editTextUserPhone.setText(user.getUserPhone());
        editTextUserEmail.setText(user.getUserEmail());

        buttonReservation = (Button) findViewById(R.id.button_reservation);
        textViewTotalPrice = (TextView) findViewById(R.id.textView_totalCost);
        textViewToTalQuantity = (TextView) findViewById(R.id.textView_totalQuantity);
        editTextReservationRequest = (EditText) findViewById(R.id.editText_reservationRequest);

        gridViewCalendar.setOnItemClickListener(this);

        imageButtonBeforMonth.setOnClickListener(this);
        imageButtonNextMonth.setOnClickListener(this);
        imageButtonRemoveQuantity.setOnClickListener(this);
        imageButtonAddQuantity.setOnClickListener(this);
        buttonReservation.setOnClickListener(this);

    }

    private void initCalendar(Calendar calendar) {

        int lastMonthStartDay;
        int dayOfMonth;
        int thisMonthLastDay;

        dayInfoArrayList.clear();

        dayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
        thisMonthLastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.MONTH, -1);
        lastMonthStartDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.MONTH, 1);

        dayOfMonth += 7;
        lastMonthStartDay -= (dayOfMonth - 1) - 1;

        textViewCalendarYearMonth.setText(calendar.get((Calendar.YEAR)) + ". " + (calendar.get(Calendar.MONTH) + 1));
        DayInfo dayInfo;

        int year = calendar.get(Calendar.YEAR);

        for (int i = 0; i < dayOfMonth - 1; i++) {
            int date = lastMonthStartDay + i;
            int month = calendar.get(Calendar.MONTH);
            dayInfo = new DayInfo();
            dayInfo.setDay(Integer.toString(date));
            dayInfo.setMonth(Integer.toString(month));
            dayInfo.setYear(Integer.toString(year));
            dayInfo.setInMonth(false);

            dayInfoArrayList.add(dayInfo);
        }
        for (int i = 1; i <= thisMonthLastDay; i++) {
            dayInfo = new DayInfo();
            int month = calendar.get(Calendar.MONTH) + 1;
            dayInfo.setDay(Integer.toString(i));
            dayInfo.setMonth(Integer.toString(month));
            dayInfo.setYear(Integer.toString(year));
            dayInfo.setInMonth(true);

            dayInfoArrayList.add(dayInfo);
        }
        for (int i = 1; i < 42 - (thisMonthLastDay + dayOfMonth - 1) + 1; i++) {
            dayInfo = new DayInfo();
            int month = calendar.get(Calendar.MONTH) + 2;
            dayInfo.setDay(Integer.toString(i));
            dayInfo.setMonth(Integer.toString(month));
            dayInfo.setYear(Integer.toString(year));
            dayInfo.setInMonth(false);
            dayInfoArrayList.add(dayInfo);
        }

        reservationCalendarAdapter = new ReservationCalendarAdapter(ReservationActivity.this, dayInfoArrayList);
        gridViewCalendar.setAdapter(reservationCalendarAdapter);
    }

    private void initCourseInfo() {
        new ImageAsyncTask(ReservationActivity.this, imageViewCourseImage).execute(courseImage);
        textViewCourseName.setText(courseName);
        textViewCourseInfo.setText(courseInfo);
        textViewTotalPrice.setText("$ " + coursePriceMin);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(dayInfoArrayList.get(position).isInCourse()) {
            for (int i = 0; i < dayInfoArrayList.size(); i++) {

                dayInfoArrayList.get(i).setSelected(false);
                if (i == position) {
                    dayInfoArrayList.get(i).setSelected(true);
                    DayInfo dayInfo = dayInfoArrayList.get(i);
                    reservationDate = dayInfo.getYear() + "-" + dayInfo.getMonth() + "-" + dayInfo.getDay();
                }

            }
        }

        reservationCalendarAdapter.notifyDataSetChanged();
        gridViewCalendar.invalidate();

    }

    public class CourseDateTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
//            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";

            String start = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
            Log.d("MyTag", "start = " + start);
            String end = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            Log.d("MyTag", "end = " + end);

            try {
                JSONObject jsonObject = HttpConnector.callGETMethod(URLDefine.COURSEDATE_URL + "?course_idx=" + courseIdx + "&start=" + start + "&end=" + end);

                if (jsonObject != null && jsonObject.getString("result").equals("true")) {

                    courseDateArrayList.clear();
                    JSONArray courseDateList = jsonObject.getJSONArray("course_date_list");
                    for (int i = 0; i < courseDateList.length(); i++) {
                        JSONObject courseDateObject = courseDateList.getJSONObject(i);
                        String courseDateStr[] = courseDateObject.getString("course_date").split("-");
                        CourseDate courseDate = new CourseDate();
                        courseDate.setYear(Integer.parseInt(courseDateStr[0]));
                        courseDate.setMonth(Integer.parseInt(courseDateStr[1]));
                        courseDate.setDay(Integer.parseInt(courseDateStr[2]));
                        courseDateArrayList.add(courseDate);
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
//            progressBar.setVisibility(View.GONE);
            if (result.equals("true")) {
                checkDate();

            } else {
                Toast.makeText(getApplicationContext(), "Not available date for reservation this month", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    private void checkDate() {
        for (int i = 0; i < dayInfoArrayList.size(); i++) {
            DayInfo dayInfo = dayInfoArrayList.get(i);
            for (int j = 0; j < courseDateArrayList.size(); j++) {
                CourseDate courseDate = courseDateArrayList.get(j);
                if ((Integer.parseInt(dayInfo.getYear()) == courseDate.getYear() &&
                        Integer.parseInt(dayInfo.getMonth()) == courseDate.getMonth() &&
                        Integer.parseInt(dayInfo.getDay()) == courseDate.getDay())) {
                    dayInfoArrayList.get(i).setInCourse(true);
                }
            }
        }

        reservationCalendarAdapter.notifyDataSetChanged();
        gridViewCalendar.invalidate();
    }


    public class ReservationTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            reservationName = editTextUserName.getText().toString();
            reservationEmail = editTextUserEmail.getText().toString();
            reservationPhone = editTextUserPhone.getText().toString();
            reservationPrice = quantity * coursePriceMin;
            reservationRequest = editTextReservationRequest.getText().toString();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";
            int userIdx = 1;

            HashMap<String, String> parameterEntry = new HashMap<>();
            parameterEntry.put("user_idx", String.valueOf(userIdx));
            parameterEntry.put("course_idx", String.valueOf(courseIdx));
            parameterEntry.put("reservation_person_number", String.valueOf(quantity));
            parameterEntry.put("reservation_date", reservationDate);
            parameterEntry.put("reservation_price", String.valueOf(reservationPrice));
            parameterEntry.put("reservation_phone", reservationPhone);
            parameterEntry.put("reservation_request", reservationRequest);
            parameterEntry.put("reservation_name", reservationName);
            parameterEntry.put("reservation_email", reservationEmail);

            try {
                JSONObject jsonObject = HttpConnector.callPOSTMethod(URLDefine.RESERVATION_URL, parameterEntry);

                if (jsonObject != null && jsonObject.getString("result").equals("true")) {
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
                Toast.makeText(getApplicationContext(), "Success to reservation", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ReservationActivity.this, MainActivity.class);
                activityManager.removeActvity(ReservationActivity.this);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Fail to reservation", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.imageButton_beforeMonth:
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
                calendar.add(Calendar.MONTH, -1);
                initCalendar(calendar);
                new CourseDateTask().execute();
                break;
            case R.id.imageButton_nextMonth:
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
                calendar.add(Calendar.MONTH, +1);
                initCalendar(calendar);
                new CourseDateTask().execute();
                break;
            case R.id.imageButton_removeQuantity:
                if (quantity - 1 > 0)
                    quantity -= 1;
                textViewQuantity.setText(String.valueOf(quantity));
                textViewToTalQuantity.setText(String.valueOf(quantity));
                textViewTotalPrice.setText(String.valueOf(quantity * coursePriceMin));
                break;
            case R.id.imageButton_addQuantity:
                quantity += 1;
                textViewQuantity.setText(String.valueOf(quantity));
                textViewToTalQuantity.setText(String.valueOf(quantity));
                textViewTotalPrice.setText(String.valueOf(quantity * coursePriceMin));
                break;
            case R.id.button_reservation:
                new ReservationTask().execute();
                break;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                activityManager.removeActvity(this);
                Intent intent = new Intent(ReservationActivity.this, CourseDetailActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected( item );
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ReservationActivity.this, CourseDetailActivity.class);
        activityManager.removeActvity(this);
        startActivity(intent);
    }
}
