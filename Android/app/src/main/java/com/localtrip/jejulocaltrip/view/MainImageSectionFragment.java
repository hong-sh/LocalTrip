package com.localtrip.jejulocaltrip.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.localtrip.jejulocaltrip.R;


public class MainImageSectionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private int position;
    private View rootView;

    public MainImageSectionFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MainImageSectionFragment newInstance(int position) {
        MainImageSectionFragment fragment = new MainImageSectionFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt("position");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_main_image_section, container, false);
        ImageView imageView = (ImageView)rootView.findViewById(R.id.imageView_viewPager);
        switch (position) {
            case 0:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.main_image2));
                break;
            case 1:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.main_image1));
                break;
            case 2:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.main_image3));
                break;
            case 3:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.main_image4));
                break;
        }

        return rootView;
    }

}
