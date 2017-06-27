package com.localtrip.jejulocaltrip.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.localtrip.jejulocaltrip.R;
import com.localtrip.jejulocaltrip.dto.DayInfo;

import java.util.ArrayList;

/**
 * Created by hong on 2017. 6. 1..
 */

public class SettingCalendarAdapter extends BaseAdapter {


    private ArrayList<DayInfo> dayInfoArrayList;
    private Context context;
    private LayoutInflater layoutInflater;


    public SettingCalendarAdapter(Context context, ArrayList<DayInfo> dayInfoArrayList) {
        this.context = context;
        this.dayInfoArrayList = dayInfoArrayList;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return dayInfoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return dayInfoArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DayInfo dayInfo = dayInfoArrayList.get(position);
        DayViewHolder dayViewHolder;

        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.gridview_calendar_item, null, false);
            dayViewHolder = new DayViewHolder(convertView);
            convertView.setTag(dayViewHolder);
        } else {
            dayViewHolder = (DayViewHolder)convertView.getTag();
        }

        dayViewHolder.relativeLayouttDay.setBackgroundColor(context.getResources().getColor(R.color.colorLightWhite));

        if(!dayInfo.isInMonth())
            dayViewHolder.textViewDay.setTextColor(context.getResources().getColor(R.color.colorLightGray));
        else
            dayViewHolder.textViewDay.setTextColor(context.getResources().getColor(R.color.colorDarkGray));

        if(dayInfo.isSelected())
            dayViewHolder.relativeLayouttDay.setBackground(context.getResources().getDrawable(R.drawable.gridview_calender_frame_click));


        dayViewHolder.textViewDay.setText(dayInfo.getDay());



        return convertView;
    }

    public class DayViewHolder {
        public RelativeLayout relativeLayouttDay;
        public TextView textViewDay;

        public DayViewHolder(View itemView) {


            relativeLayouttDay = (RelativeLayout) itemView.findViewById(R.id.layout_day);
            textViewDay = (TextView) itemView.findViewById(R.id.textView_day);

        }
    }
}
