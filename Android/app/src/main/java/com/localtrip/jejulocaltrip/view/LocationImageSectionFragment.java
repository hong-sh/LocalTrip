package com.localtrip.jejulocaltrip.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.localtrip.jejulocaltrip.R;
import com.localtrip.jejulocaltrip.util.ImageAsyncTask;

import java.util.ArrayList;


public class LocationImageSectionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private int position;
    private ArrayList<String> imageUrlStringArrayList;
    private View rootView;

    public LocationImageSectionFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static LocationImageSectionFragment newInstance(int position, ArrayList<String> imageUrlStringArrayList) {
        LocationImageSectionFragment fragment = new LocationImageSectionFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putStringArrayList("imageUrls", imageUrlStringArrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt("position");
            imageUrlStringArrayList = getArguments().getStringArrayList("imageUrls");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_main_image_section, container, false);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView_viewPager);
        new ImageAsyncTask(getContext(), imageView).execute(imageUrlStringArrayList.get(position));
        return rootView;
    }

}
