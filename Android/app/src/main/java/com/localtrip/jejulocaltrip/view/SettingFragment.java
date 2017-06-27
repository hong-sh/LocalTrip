package com.localtrip.jejulocaltrip.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.localtrip.jejulocaltrip.R;
import com.localtrip.jejulocaltrip.adapter.SettingCalendarAdapter;
import com.localtrip.jejulocaltrip.dto.DayInfo;

import java.util.ArrayList;
import java.util.Calendar;


public class SettingFragment extends DialogFragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RadioButton radioButtonStartDate, radioButtonEndDate;
    private ImageButton imageButtonBeforeMonth, imageButtonNextMonth;
    private TextView textViewCalendarYearMonth;
    private GridView gridViewCalendar;
    private Button buttonEng, buttonKor, buttonChina1, buttonChina2, buttonJapan, buttonOK;


    private SettingCalendarAdapter settingCalendarAdapter;
    private ArrayList<DayInfo> dayInfoArrayList;
    private Calendar calendar;


    private int type = -1;
    private String startDate, endDate;


    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        radioButtonStartDate = (RadioButton) rootView.findViewById(R.id.radioButton_startDate);
        radioButtonEndDate = (RadioButton) rootView.findViewById(R.id.radioButton_endDate);

        imageButtonBeforeMonth = (ImageButton) rootView.findViewById(R.id.imageButton_beforeMonth);
        imageButtonNextMonth = (ImageButton) rootView.findViewById(R.id.imageButton_nextMonth);
        textViewCalendarYearMonth = (TextView) rootView.findViewById(R.id.textView_year_month);
        gridViewCalendar = (GridView) rootView.findViewById(R.id.girdView_calendar);

        buttonEng = (Button) rootView.findViewById(R.id.buttonEnglish);
        buttonKor = (Button) rootView.findViewById(R.id.buttonKorea);
        buttonChina1 = (Button) rootView.findViewById(R.id.buttonChinese1);
        buttonChina2 = (Button) rootView.findViewById(R.id.buttonChinese2);
        buttonJapan = (Button) rootView.findViewById(R.id.buttonJapan);
        buttonOK = (Button) rootView.findViewById(R.id.button_setting);

        gridViewCalendar.setOnItemClickListener(this);

        imageButtonBeforeMonth.setOnClickListener(this);
        imageButtonNextMonth.setOnClickListener(this);
        buttonEng.setOnClickListener(this);
        buttonKor.setOnClickListener(this);
        buttonChina1.setOnClickListener(this);
        buttonChina2.setOnClickListener(this);
        buttonJapan.setOnClickListener(this);
        buttonOK.setOnClickListener(this);


        calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        dayInfoArrayList = new ArrayList<>();

        initCalendar(calendar);

        return rootView;
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

        settingCalendarAdapter = new SettingCalendarAdapter(getActivity(), dayInfoArrayList);
        gridViewCalendar.setAdapter(settingCalendarAdapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int type, String startDate, String endDate) {
        if (mListener != null) {
            mListener.onFragmentInteraction(type, startDate, endDate);
            dismiss();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonEnglish:
                type = 0;
                buttonEng.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                buttonKor.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                buttonChina1.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                buttonChina2.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                buttonJapan.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                break;
            case R.id.buttonKorea:
                type = 1;
                buttonEng.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                buttonKor.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                buttonChina1.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                buttonChina2.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                buttonJapan.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                break;
            case R.id.buttonChinese1:
                type = 2;
                buttonEng.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                buttonKor.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                buttonChina1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                buttonChina2.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                buttonJapan.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                break;
            case R.id.buttonChinese2:
                type = 3;
                buttonEng.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                buttonKor.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                buttonChina1.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                buttonChina2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                buttonJapan.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                break;
            case R.id.buttonJapan:
                type = 4;
                buttonEng.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                buttonKor.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                buttonChina1.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                buttonChina2.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                buttonJapan.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                break;

            case R.id.imageButton_beforeMonth:
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
                calendar.add(Calendar.MONTH, -1);
                initCalendar(calendar);
                break;
            case R.id.imageButton_nextMonth:
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
                calendar.add(Calendar.MONTH, +1);
                initCalendar(calendar);
                break;
            case R.id.button_setting:
                if(type < 0 || startDate == null || endDate == null)
                    Toast.makeText(getActivity().getApplicationContext(), "Select Setting value", Toast.LENGTH_SHORT).show();
                else
                    onButtonPressed(type, startDate, endDate);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        DayInfo dayInfo = dayInfoArrayList.get(position);
        if (!dayInfo.isInMonth())
            return;

        String dateStr = dayInfo.getYear() + "-" + dayInfo.getMonth() + "-" + dayInfo.getDay();
        if (radioButtonStartDate.isChecked()) {
            startDate = dateStr;
            radioButtonStartDate.setText("Start Date : " + startDate);

        } else if (radioButtonEndDate.isChecked()) {
            endDate = dateStr;
            radioButtonEndDate.setText("End Date : " + endDate);
        }

        if (startDate != null && endDate == null || startDate == null && endDate != null) {
            for(int i=0; i<dayInfoArrayList.size(); i++) {
                dayInfoArrayList.get(i).setSelected(false);
            }
            dayInfoArrayList.get(position).setSelected(true);
        } else if (startDate != null & endDate != null) {
            for (int i = 0; i < dayInfoArrayList.size(); i++) {
                if (dayInfoArrayList.get(i).isInMonth() &&
                        Integer.parseInt(dayInfoArrayList.get(i).getDay()) >= Integer.parseInt(startDate.split("-")[2]) &&
                        Integer.parseInt(dayInfoArrayList.get(i).getDay()) <= Integer.parseInt(endDate.split("-")[2])) {
                    dayInfoArrayList.get(i).setSelected(true);
                } else {
                    dayInfoArrayList.get(i).setSelected(false);
                }
            }
        }


        settingCalendarAdapter.notifyDataSetChanged();
        gridViewCalendar.invalidate();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int type, String startDate, String endDate);
    }
}
