package com.localtrip.jejulocaltrip.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.localtrip.jejulocaltrip.R;
import com.localtrip.jejulocaltrip.dto.Setting;
import com.localtrip.jejulocaltrip.dto.User;
import com.localtrip.jejulocaltrip.util.ActivityManager;
import com.localtrip.jejulocaltrip.util.HttpConnector;
import com.localtrip.jejulocaltrip.util.KoreaLocalTripApplication;
import com.localtrip.jejulocaltrip.util.URLDefine;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private KoreaLocalTripApplication app;
    private ActivityManager activityManager;
    private User user;
    private Setting setting;

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private EditText editTextUserEmail, editTextUserPass;
    private Button buttonLogin;
    private TextView textViewJoin;

    private String userEmail, userPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        app = (KoreaLocalTripApplication)getApplication();
        activityManager = app.instanceOfActivityManager();
        activityManager.addActivity(this);
        user = app.instanceOfUser();
        setting = app.instanceOfSetting();

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initLayout();
    }

    private void initLayout() {
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        editTextUserEmail = (EditText) findViewById(R.id.editText_userEmail);
        editTextUserPass = (EditText)findViewById(R.id.editText_userPass);
        buttonLogin = (Button)findViewById(R.id.button_login);
        textViewJoin = (TextView)findViewById(R.id.textView_join);

        buttonLogin.setOnClickListener(this);
        textViewJoin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                userEmail = editTextUserEmail.getText().toString();
                userPass = editTextUserPass.getText().toString();
                if(userEmail.length() <= 0 || userPass.length() <= 0)
                    Toast.makeText(getApplicationContext(), "Insert Login Info", Toast.LENGTH_SHORT).show();
                else
                    new LoginAsyncTask().execute();
                break;
            case R.id.textView_join:
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                activityManager.removeActvity(this);
                startActivity(intent);
                break;
        }
    }


    public class LoginAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            String result = "false";

            HashMap<String, String> loginEntry = new HashMap<>();
            loginEntry.put("user_email", userEmail);
            loginEntry.put("user_pass", userPass);

            try {
                JSONObject jsonObject = HttpConnector.callPOSTMethod(URLDefine.LOGIN_URL, loginEntry);

                if(jsonObject != null && jsonObject.getString("result").equals("true")) {

                    JSONObject userInfo = jsonObject.getJSONObject("user_info");

                    user.setUserIdx(Integer.parseInt(userInfo.getString("user_idx")));
                    user.setUserEmail(userInfo.getString("user_email"));
                    user.setUserName(userInfo.getString("user_name"));
                    user.setUserPhone(userInfo.getString("user_phone"));
                    user.setUserType(Integer.parseInt(userInfo.getString("user_type")));
                    user.setUserImage(userInfo.getString("user_image"));

                    result = "true";
                }

            } catch (JSONException e) {
                Log.d("MyTag", e.toString());
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            if (result.equals("true")) {
                user.saveLogin(userEmail, userPass);
                user.setLogin(true);
                setting.setType(user.getUserType());
                activityManager.removeActvity(LoginActivity.this);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "login fail ..", Toast.LENGTH_SHORT).show();
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
        activityManager.removeActvity(this);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
