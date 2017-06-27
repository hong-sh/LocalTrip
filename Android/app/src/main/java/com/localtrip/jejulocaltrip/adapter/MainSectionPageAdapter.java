package com.localtrip.jejulocaltrip.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.localtrip.jejulocaltrip.view.MainImageSectionFragment;

/**
 * Created by hong on 2017. 5. 29..
 */

public class MainSectionPageAdapter extends FragmentPagerAdapter {

    public MainSectionPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = MainImageSectionFragment.newInstance(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
