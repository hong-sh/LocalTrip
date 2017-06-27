package com.localtrip.jejulocaltrip.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.localtrip.jejulocaltrip.R;
import com.localtrip.jejulocaltrip.dto.User;
import com.localtrip.jejulocaltrip.util.ActivityManager;
import com.localtrip.jejulocaltrip.util.CircleImageAsyncTask;
import com.localtrip.jejulocaltrip.util.CircleImageView;
import com.localtrip.jejulocaltrip.util.KoreaLocalTripApplication;

public class MyPageActivity extends AppCompatActivity implements View.OnClickListener{

    private KoreaLocalTripApplication app;
    private ActivityManager activityManager;
    private User user;

    private CircleImageView circleImageViewUserImage;
    private TextView textViewUserName, textViewUserEmail;

    private RelativeLayout relativeLayoutWishList, relativeLayoutReservationList;
    private ImageButton imageButtonHome, imageButtonSearch, imageButtonWishlist, imageButtonMyPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        app = (KoreaLocalTripApplication)getApplication();
        activityManager = app.instanceOfActivityManager();
        activityManager.addActivity(MyPageActivity.this);
        user = app.instanceOfUser();

        if(!user.isLogin()) {
            Intent intent = new Intent(MyPageActivity.this, LoginActivity.class);
            activityManager.removeActvity(this);
            startActivity(intent);
        }

        initLayout();
    }

    private void initLayout() {
        circleImageViewUserImage = (CircleImageView)findViewById(R.id.imageView_userImage);
        textViewUserName = (TextView)findViewById(R.id.textView_userName);
        textViewUserEmail = (TextView)findViewById(R.id.textView_userEmail);

        new CircleImageAsyncTask(MyPageActivity.this, circleImageViewUserImage).execute(user.getUserImage());
        textViewUserName.setText(user.getUserName());
        textViewUserEmail.setText(user.getUserEmail());

        relativeLayoutWishList = (RelativeLayout)findViewById(R.id.layout_wishlist);
        relativeLayoutReservationList = (RelativeLayout)findViewById(R.id.layout_reservationList);
        relativeLayoutWishList.setOnClickListener(this);
        relativeLayoutReservationList.setOnClickListener(this);

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
                intent = new Intent(MyPageActivity.this, MainActivity.class);
                activityManager.removeActvity(this);
                startActivity(intent);
                break;
            case R.id.imageButton_search:
                intent = new Intent(MyPageActivity.this, SearchActivity.class);
                activityManager.removeActvity(this);
                startActivity(intent);
                break;
            case R.id.imageButton_wishlist:
                intent = new Intent(MyPageActivity.this, WishListActivity.class);
                activityManager.removeActvity(this);
                startActivity(intent);
                break;
            case R.id.imageButton_mypage:
                break;
            case R.id.layout_wishlist:
                intent = new Intent(MyPageActivity.this, WishListActivity.class);
                activityManager.removeActvity(this);
                startActivity(intent);
                break;
            case R.id.layout_reservationList:
                intent = new Intent(MyPageActivity.this, ReservationListActivity.class);
                activityManager.removeActvity(this);
                startActivity(intent);
                break;
        }
    }
}
