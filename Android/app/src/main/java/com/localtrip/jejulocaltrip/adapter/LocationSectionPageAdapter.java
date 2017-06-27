package com.localtrip.jejulocaltrip.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.localtrip.jejulocaltrip.view.LocationImageSectionFragment;

import java.util.ArrayList;

/**
 * Created by hong on 2017. 6. 1..
 */

public class LocationSectionPageAdapter extends FragmentPagerAdapter {

    private ArrayList<String> imageUrlStringArrayList;

    public LocationSectionPageAdapter(FragmentManager fm, ArrayList<String> imageUrlStringArrayList) {
        super(fm);
        this.imageUrlStringArrayList = imageUrlStringArrayList;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = LocationImageSectionFragment.newInstance(position, imageUrlStringArrayList);
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
