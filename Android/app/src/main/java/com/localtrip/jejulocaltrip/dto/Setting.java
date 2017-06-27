package com.localtrip.jejulocaltrip.dto;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by hong on 2017. 6. 3..
 */

public class Setting {

    private int type;
    private String startDate;
    private String endDate;
    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void savedSetting() {
        SharedPreferences pref = context.getSharedPreferences("setting", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("type", type);
        editor.putString("startDate", startDate);
        editor.putString("endDate", endDate);
        editor.commit();
    }

    public void removeSetting() {
        SharedPreferences pref = context.getSharedPreferences("setting", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    public HashMap<String, String> getPreferences(){
        SharedPreferences pref = context.getSharedPreferences("setting", context.MODE_PRIVATE);

        HashMap<String, String> settingInfo = new HashMap<>();
        settingInfo.put("type", String.valueOf(pref.getInt("type", -1)));
        settingInfo.put("startDate", pref.getString("startDate", null));
        settingInfo.put("endDate", pref.getString("endDate", null));

        this.type = Integer.parseInt(settingInfo.get("type"));
        this.startDate = settingInfo.get("startDate");
        this.endDate = settingInfo.get("endDate");

        return settingInfo;
    }
}
