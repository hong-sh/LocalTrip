package com.localtrip.jejulocaltrip.util;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by hong on 2016. 7. 11..
 */
public class ActivityManager {

    private ArrayList<Activity> arrActivity;

    public ActivityManager() {
        // TODO Auto-generated constructor stub
    }

    // 액티비티 추가
    public void addActivity(Activity activity) {
        if (arrActivity == null) {
            arrActivity = new ArrayList<Activity>();
        }

        Log.d("ActivityManager", "Enter add = " + activity.toString());
        arrActivity.add(activity);
    }

    // 액티비티 제거
    public void removeActvity(Activity activity) {

        if (arrActivity != null) {
            int index = arrActivity.lastIndexOf(activity);
            Log.d("ActivityManager", "index = " + index);
            if (index >= 0) {
                Activity a = arrActivity.remove(index);
                Log.d("ActivityManager", "activity = " + a.toString());
                // 에니메이션 효과 삭제
                a.overridePendingTransition(0, 0);
                a.finish();
            }
        }
    }

    public Activity getLastActivity() {
        return arrActivity.get(arrActivity.size() - 1);
    }

    //액티비티 완전종료
    public void removeAllActvity() {
        for (int i = 0; i < arrActivity.size(); i++) {
            Activity a = arrActivity.get(i);
            a.finish();
        }
        arrActivity.clear();
        arrActivity = null;
    }

    public int countActivity() {
        int count = arrActivity.size();
        return count;
    }

}