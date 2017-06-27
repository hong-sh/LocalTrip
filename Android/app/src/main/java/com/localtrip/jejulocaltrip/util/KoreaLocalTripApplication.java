package com.localtrip.jejulocaltrip.util;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.localtrip.jejulocaltrip.dto.CourseInfo;
import com.localtrip.jejulocaltrip.dto.CourseSimple;
import com.localtrip.jejulocaltrip.dto.GuideSimple;
import com.localtrip.jejulocaltrip.dto.Setting;
import com.localtrip.jejulocaltrip.dto.User;

import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * Created by hong on 2016. 7. 11..
 */
public class KoreaLocalTripApplication extends Application {

    private ActivityManager activityManager;
    private User user;
    private Setting setting;
    private ArrayList<CourseSimple> recommendCourseArrayList;
    private ArrayList<GuideSimple> recommendGuideArrayList;
    private int courseIdx;
    private int userIdx;
    private CourseInfo tmpCourse;

    @Override
    public void onCreate() {
        super.onCreate();
        setDefaultFont( this,"SERIF", "NanumBarunpenB.ttf" );
    }

    private void setDefaultFont(Context context,
                                      String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
                fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }

    private void replaceFont(String staticTypefaceFieldName,
                                      final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public int getCourseIdx() {
        return courseIdx;
    }

    public void setCourseIdx(int courseIdx) {
        this.courseIdx = courseIdx;
    }

    public int getUserIdx() {
        return userIdx;
    }

    public void setUserIdx(int userIdx) {
        this.userIdx = userIdx;
    }

    public CourseInfo getTmpCourse() {
        return tmpCourse;
    }

    public void setTmpCourse(CourseInfo tmpCourse) {
        this.tmpCourse = tmpCourse;
    }

    public ActivityManager instanceOfActivityManager() {
        if(activityManager == null)
            activityManager = new ActivityManager();

        return activityManager;
    }

    public User instanceOfUser() {
        if(user == null) {
            user = new User(getApplicationContext());
        }
        return user;
    }

    public Setting instanceOfSetting() {
        if(setting == null) {
            setting = new Setting();
            setting.setContext(getApplicationContext());
            setting.getPreferences();
        }
        return setting;
    }

    public ArrayList<CourseSimple> instanceOfRecommendCourse() {
        if(recommendCourseArrayList == null)
            recommendCourseArrayList = new ArrayList<>();

        return recommendCourseArrayList;
    }

    public ArrayList<GuideSimple> instanceOfRecommendGuide() {
        if(recommendGuideArrayList == null)
            recommendGuideArrayList = new ArrayList<>();

        return recommendGuideArrayList;
    }


}